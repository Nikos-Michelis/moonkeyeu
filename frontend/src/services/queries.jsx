import {useErrorBoundary} from "react-error-boundary";
import {handleGet} from "@/services/api.jsx";
import {keepPreviousData, useQuery} from "@tanstack/react-query";

export const useParameterizedQuery = (
    {
        url,
        params = '',
        cacheKey,
        staleTime = 5 * 60 * 1000,
        placeholderData = keepPreviousData,
        refetchInterval = 30 * 60 * 1000,
        refetchOnWindowFocus = true,
        retry = 2,
        enableBoundary = true,
        queryOptions = {},
        options = { withCredentials: false, Csrf: false }
    }) => {
    const { showBoundary } = useErrorBoundary();
       return useQuery({
            queryKey: [cacheKey, params],
            queryFn: () => handleGet(url, options),
            placeholderData: placeholderData,
            refetchInterval: refetchInterval,
            refetchOnWindowFocus: refetchOnWindowFocus,
            refetchOnReconnect: true,
            staleTime: staleTime,
            retry: retry,
            ...queryOptions,
            throwOnError: (error) => enableBoundary ? showBoundary(error?.response) : false,
       });
};

export const useSimpleQuery = (
    {
        url,
        cacheKey,
        staleTime = 5 * 60 * 1000,
        placeholderData = keepPreviousData,
        refetchInterval = 30 * 60 * 1000,
        refetchOnWindowFocus = true,
        queryOptions= {},
        options = { withCredentials: false, Csrf: false }
    }) => {
     return useQuery({
         queryKey: [cacheKey],
         queryFn: () => handleGet(url, options),
         placeholderData: placeholderData,
         refetchInterval: refetchInterval,
         refetchOnWindowFocus: refetchOnWindowFocus,
         refetchOnReconnect: true,
         staleTime: staleTime,
         ...queryOptions,
     });
};

