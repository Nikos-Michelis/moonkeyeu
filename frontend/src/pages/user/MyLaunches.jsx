import React, {useEffect} from "react";
import {useParams, useSearchParams} from "react-router-dom";
import Heading from "@/components/utils/Heading.jsx";
import LaunchesSection from "@/components/sections/LaunchesSection.jsx";
import {useParameterizedQuery} from "@/services/queries.jsx";
import {useAuth} from "@/context/AuthProvider.jsx";
import Pagination from "@/components/pagination/Pagination.jsx";
import usePagination from "@/hooks/paging-filtering/usePagination.jsx";
import BasicFiltering from "@/components/filtering/BasicFiltering.jsx";
import Head from "@/components/seo/Head.jsx";
import JsonLdGeneric from "@/components/seo/jsonld/JsonLdGeneric.jsx";

const defaultFilters = {
    page: 1,
    limit: 12,
    ordering: "asc"
};
const options = {
    showBackBtn: true,
    showItemsLimit: false,
    maxItems: 100
}
function MyLaunches() {
    const baseUrl = import.meta.env.VITE_BACKEND_BASE_URL;
    const {name} = useParams();
    const { user, status} = useAuth();
    const [searchParams] = useSearchParams();
    const pagination = usePagination();
    const queryData = useParameterizedQuery({
        url: `${baseUrl}/user/user-bookmarks/${name}?${searchParams.toString()}`,
        params: `bookmark-${name}-${searchParams.toString()}`,
        cacheKey: "my-launches",
        queryOptions: {
            enabled: !!user && !!searchParams.toString().length > 0,
            retry: 1
        },
        options: { withCredentials: false, Bearer: true }
    })

    useEffect(() => {
        if (queryData.data) {
            pagination.setPagination(queryData.data?.page);
        }
    }, [queryData.data, pagination]);


    return (
        <>
            <Head
                title="My Launches"
                description="View your favorite space launches."
            />
            <JsonLdGeneric
                type="CollectionPage"
                title="Members"
                description="Manage and view all registered members from your dashboard."
            />
            <Heading
                title={`My Launches - ${name}`}
                description="View and manage your bookmarked space launches."
            />
            <BasicFiltering
                defaultFilters={defaultFilters}
                searchPlaceHolder="e.g. Falcon 9 Block 5"
            />
            <Pagination
                {...pagination}
                isPending={queryData.isPending}
                isFetching={queryData.isFetching}
            />
            <LaunchesSection
                launches={queryData.data || {}}
                isPending={queryData.isPending || status.isPending}
                isFetching={queryData.isFetching}
                isError={queryData.isError}
                isBookmarked={true}
                options={options}
                navUrl="/launches/"
            />
            <Pagination
                {...pagination}
                isPending={queryData.isPending}
                isFetching={queryData.isFetching}
            />
        </>
    );
}

export default MyLaunches;
