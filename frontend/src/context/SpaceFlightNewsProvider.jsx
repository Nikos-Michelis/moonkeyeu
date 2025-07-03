import React, {createContext, useContext, useEffect, useMemo} from "react";
import toast from "react-hot-toast";
import {useSimpleQuery} from "@/services/queries.jsx";

export const SpaceFlightNewsContext = createContext({
    newsData: null,
    isLoadingNews: false,
    isFetchingNews: false,
    isErrorNews: false,
});

export const SpaceFlightNewsProvider = ({ children }) => {
    const baseUrl = `${import.meta.env.VITE_NEWS_API_URL}&limit=4&ordering=-published_at`;
    const hours = 600000;
    const { data, isPending, isFetching, isError} = useSimpleQuery({
        url: baseUrl,
        cacheKey: 'latest-news',
        staleTime: hours,
        refetchInterval: hours,
        refetchOnWindowFocus: true
    });
    useEffect(() => {
        isError && toast.error("Oops! Something went wrong with the Space News API.");
    }, [isError]);

    const providerValues = useMemo(()=> (
        {
            data,
            isPending,
            isError,
            isFetching
        }), [ data, isPending, isError, isFetching])

    return (
        <SpaceFlightNewsContext.Provider value={providerValues}>
            {children}
        </SpaceFlightNewsContext.Provider>
    );
};
export const useSpaceFlightNews = () => {
    const spaceFlightNewsContext = useContext(SpaceFlightNewsContext);
    if (!spaceFlightNewsContext) {
        throw new Error("SpaceFlightNewsContext must be used within SpaceFlightNewsProvider");
    }
    return spaceFlightNewsContext;
};

