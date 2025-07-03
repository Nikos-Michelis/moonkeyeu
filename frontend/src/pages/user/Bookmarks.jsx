import React, {useEffect} from "react";
import BookmarksSection from "@/components/sections/BookmarksSection.jsx";
import Heading from "@/components/utils/Heading.jsx";
import {useAuth} from "@/context/AuthProvider.jsx";
import toast from "react-hot-toast";
import {useSimpleQuery} from "@/services/queries.jsx";
import Head from "@/components/seo/Head.jsx";
import JsonLdGeneric from "@/components/seo/jsonld/JsonLdGeneric.jsx";

function Bookmarks() {
    const baseUrl = import.meta.env.VITE_BACKEND_BASE_URL;
    const {user} = useAuth();

    const queryData = useSimpleQuery({
        url: baseUrl + "/user/user-bookmarks",
        cacheKey: "user-bookmarks",
        queryOptions: {
            enabled: !!user,
            retry: 1
        },
        options: { withCredentials: false, Bearer: true }
    })

    useEffect(() => {
        queryData.isError && toast.error("Oops! Something went wrong try again later.");
    }, [queryData]);

    return (
        <>
            <Head
                title="Bookmarks"
                description="View all your bookmarked space launches."
            />
            <JsonLdGeneric
                type="CollectionPage"
                title="Bookmarks"
                description="View all your bookmarked space launches."
            />
            <Heading
                title="My Bookmarks"
                description="Save and organize your favorite launches."
            />
            <BookmarksSection
                bookmarks={queryData.data || {}}
                isLoading={queryData.isPending}
                isFetching={queryData.isFetching}
                isError={queryData.isError}
            />
        </>
    );
}

export default Bookmarks;
