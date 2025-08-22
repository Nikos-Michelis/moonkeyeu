import React, {createContext, useContext, useEffect, useMemo} from "react";
import toast from "react-hot-toast";
import {useSimpleQuery} from "@/services/queries.jsx";

export const NasaApodContext = createContext({
    nasaApod: null,
    isLoadingNasaImage: false,
    isErrorNasaImage: false,
});

export const NasaApodProvider = ({ children }) => {
    const baseUrl = import.meta.env.VITE_BACKEND_BASE_URL;
    const hours = 2 * 60 * 60 * 1000;
    const { data, isPending, isFetching, isError}
        = useSimpleQuery({
            url: `${baseUrl}/public/nasa/apod`,
            cacheKey: 'nasa-apod',
            staleTime: hours,
            refetchInterval: hours,
            refetchOnWindowFocus: false
        });

    useEffect(() => {
        isError && toast.error("Oops! Something went wrong with the Nasa Apod API.");
    }, [isError]);

    const providerValues = useMemo(()=> (
        {
            data,
            isError,
            isPending,
            isFetching
        }),[data, isError, isPending, isFetching ])

    return (
        <NasaApodContext.Provider value={providerValues}>
            {children}
        </NasaApodContext.Provider>
    );
};


export const useNasaApod = () => {
    const nasaApodContext = useContext(NasaApodContext);
    if (!nasaApodContext) {
        throw new Error("NasaApodContext must be used within NasaApodProvider");
    }
    return nasaApodContext;
};
