import React, {
    createContext,
    useCallback,
    useContext,
    useEffect,
    useLayoutEffect,
    useMemo, useRef,
    useState
} from "react";
import {useMutation, useQueryClient} from "@tanstack/react-query";
import { api, handlePost } from "@/services/api.jsx";
import toast from "react-hot-toast";
import showErrorToast from "@/components/utils/ShowErrorToast.jsx";
import { useSimpleQuery } from "@/services/queries.jsx";

export const SecurityContext = createContext(undefined);
export const AuthProvider = ({ children }) => {
    const baseUrl = import.meta.env.VITE_BACKEND_BASE_URL;
    const logoutUrl = baseUrl + '/user/logout';
    const refreshUrl = baseUrl + '/auth/refresh-token';
    const myAccountUrl = baseUrl + '/user/my-account';
    const csrfUrl = baseUrl + '/csrf/token';
    const [token, setToken] = useState(null);
    let isRefreshing = useRef(false);
    const queryClient = useQueryClient();
    let failedQueue = [];

    const csrfQuery
        = useSimpleQuery({
        url: csrfUrl,
        cacheKey: "csrf",
        placeholderData: false,
        refetchOnWindowFocus: false,
        staleTime: 15 * 60 * 1000,
        queryOptions:{
            retry: 1,
        },
        options: { withCredentials: true, Bearer: false }
    });

    const userQuery
        = useSimpleQuery({
        url: myAccountUrl,
        cacheKey: "user",
        placeholderData: false,
        refetchOnWindowFocus: false,
        staleTime: 15 * 60 * 1000,
        queryOptions:{
            enabled: !!csrfQuery?.data,
            retry: false,
        },
        options: { withCredentials: false, Bearer: true }
    });

    const logoutMutation = useMutation({
        mutationFn: ({ data, url, options }) => handlePost(url, data, options),
        retry: 0
    });

    const refreshMutation = useMutation({
        mutationFn: ({ data, url, options }) => handlePost(url, data, options),
        retry: 0
    });

    const logout = useCallback( () => {
        logoutMutation.mutate(
            { url: logoutUrl, options: { withCredentials: true, Bearer: false }
            }, {
                onSuccess: () => {
                    invalidateCredentials();
                    toast.success("You have been logged out successfully. See you next time!");
                },
                onError: (error) => {
                    showErrorToast(error, error?.response?.data?.error);
                },
            }
        )
    }, [token, userQuery.data]);

    const invalidateCredentials = useCallback(() => {
        queryClient.removeQueries(["user"]);
        setToken(null);
    }, [queryClient]);

    useEffect(() => {
        if (token && csrfQuery.data){
            userQuery.refetch();
        }
    }, [token, csrfQuery?.data, userQuery?.data]);

    useEffect(() => {
        if (userQuery?.isSuccess && userQuery.data){
            toast.success(`Welcome back, ${userQuery?.data?.username}!`);
        }
    }, [userQuery?.data]);

    function subscribeTokenRefresh(callback) {
        failedQueue.push(callback);

    }

    function onRefreshed(newToken) {
        failedQueue.forEach(callback => callback(newToken));
        failedQueue = [];
    }

    useLayoutEffect(() => {
        const authInterceptor = api.interceptors.request.use((config) => {
            if (config.Bearer && token) {
                config.headers.Authorization = `Bearer ${token}`;
            }
            if (config.Csrf && csrfQuery?.data ) {
                config.headers["X-XSRF-TOKEN"] = csrfQuery?.data?.token;
            }
            return config;
        });

        return () => api.interceptors.request.eject(authInterceptor);
    }, [token, csrfQuery?.data]);

    useLayoutEffect(() => {
        const refreshInterceptor = api.interceptors.response.use(
            (response) => response, async (error) => {
                const originalRequest = error.config;

                if (originalRequest.url.includes(baseUrl + "/auth/") && !originalRequest.url.includes(baseUrl + '/auth/refresh-token')) {
                    return Promise.reject(error);
                }

                if ((error?.response?.status === 401 && token) && (error?.response?.data?.businessErrorCode === 308 || error?.response?.data?.businessErrorCode === 309)){
                    invalidateCredentials();
                    showErrorToast(401, "Your session has expired.");
                    return Promise.reject(error);
                }

                if (
                    !originalRequest._retry
                    && (error?.response?.status === 401 && (error?.response?.data?.error === "Unauthorized" || error?.response?.data?.businessErrorCode === 303))
                ) {
                    if (!isRefreshing.current) {
                        isRefreshing.current = true;
                        try {
                            const response = await refreshMutation.mutateAsync({
                                url: refreshUrl,
                                data: null,
                                options: { withCredentials: true }
                            });
                            setToken(response.access_token);
                            onRefreshed(token);
                            api.headers.defaults.common['Authorization'] = `Bearer ${token}`;
                            return Promise.all(failedQueue.map(cb => cb(token))).then(() => {
                                return api(originalRequest);
                            });
                        } catch (error){
                            return Promise.reject(error);
                        } finally {
                            isRefreshing.current = false;
                            failedQueue = [];
                        }
                    }
                    return new Promise((resolve, reject) => {
                        subscribeTokenRefresh((newToken) => {
                            originalRequest.headers.Authorization = `Bearer ${newToken}`;
                            originalRequest._retry = true;
                            resolve(api(originalRequest));
                        });
                    });
                }
                return Promise.reject(error);
            }
        );
        return () => api.interceptors.response.eject(refreshInterceptor);
    }, [token]);

    const combinedStatus = useMemo(() => ({
        isPending: (
            logoutMutation.isPending ||
            refreshMutation.isPending ||
            (csrfQuery.isPending || csrfQuery.isFetching) ||
            ((userQuery?.isPending || userQuery?.isFetching) || userQuery?.isRefetching)
        ),
        isError: userQuery?.isError || logoutMutation.isError,
        isSuccess: userQuery?.isSuccess,
    }), [refreshMutation.isPending, logoutMutation, csrfQuery, userQuery,]);

    const providerValues = useMemo(() => ({
            logout,
            invalidateCredentials,
            user: userQuery.data || undefined ,
            token,
            setToken,
            csrf: csrfQuery.data || undefined ,
            status: combinedStatus,
            error: logoutMutation.error,
        }),
        [ userQuery?.data, combinedStatus.isSuccess, combinedStatus.isError, logoutMutation.error ]
    );

    return (
        <SecurityContext.Provider value={providerValues}>
            {children}
        </SecurityContext.Provider>
    );
};

export const useAuth = () => {
    const securityContext = useContext(SecurityContext);
    if (!securityContext) {
        throw new Error("SecurityContext must be used within AuthProvider");
    }
    return securityContext;
};