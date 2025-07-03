import React, {useEffect} from "react";
import LaunchesSection from "../components/sections/LaunchesSection.jsx";
import LaunchFiltering from "../components/filtering/LaunchFiltering.jsx";
import Heading from "../components/utils/Heading.jsx";
import {useSearchParams} from "react-router-dom";
import usePagination from "@/hooks/paging-filtering/usePagination.jsx";
import Pagination from "@/components/pagination/Pagination.jsx";
import {useParameterizedQuery, useSimpleQuery} from "@/services/queries.jsx";
import Head from "@/components/seo/Head.jsx";
import JsonLdGeneric from "@/components/seo/jsonld/JsonLdGeneric.jsx";

const options = {
    showPrevBtn: true,
}
function Home() {
    const baseUrl = `${import.meta.env.VITE_BACKEND_BASE_URL}/public/launches`;
    const filtersUrl = `${import.meta.env.VITE_BACKEND_BASE_URL}/public/launches/filters`;
    const [searchParams] = useSearchParams();
    const pagination = usePagination();
    const queryData
        = useParameterizedQuery({
            url: `${baseUrl}?${searchParams}`,
            params: `pagination-${searchParams.toString()}`,
            cacheKey: "launches-pagination",
            queryOptions:{
                enabled: !!searchParams.toString().length > 0,
            }
        });
    const filterData
        = useSimpleQuery({
            url: filtersUrl,
            cacheKey: "launches-filters",
            staleTime: Infinity,
        });

    useEffect(() => {
        if (queryData.data) {
            pagination.setPagination(queryData.data?.page);
        }
    }, [queryData.data, pagination]);

    return (
        <>
            <Head
                title="Space Launch Tracker"
                description="Stay up to date with upcoming and past spaceflights from NASA, SpaceX, and other leading space agencies around the world."
            />
            <JsonLdGeneric
                type="CollectionPage"
                title="Launches"
                description="Stay up to date with upcoming and past spaceflights from NASA, SpaceX, and other leading space agencies around the world."
                createdAt={queryData?.data?.window_start}
                updatedAt={queryData?.data?.window_end}
            />
            <Heading
                title={`${searchParams.get("upcoming") === "true" ? "Upcoming" : "Previous"} Launches`}
                description="Discover past and upcoming space launches – Use filters to easily find results that match your specific interests."
            />
            <LaunchFiltering
                filters={filterData.data}
                searchPlaceHolder="e.g. Falcon 9 | Block 5"
                isPending={filterData.isPending}
                isFetching={filterData.isFetching}
                isError={filterData.isError}
            />
            <Pagination
                {...pagination}
                isPending={queryData.isPending}
                isFetching={queryData.isFetching}
            />
            <LaunchesSection
                launches={queryData.data || {}}
                isPending={queryData.isPending}
                isFetching={queryData.isFetching}
                isError={queryData.isError}
                options={options}
            />
            <Pagination
                {...pagination}
                isPending={queryData.isPending}
                isFetching={queryData.isFetching}
            />
        </>
    );
}

export default Home;
