import React, {useEffect} from "react";
import Heading from "../components/utils/Heading.jsx";
import { useSearchParams } from "react-router-dom";
import usePagination from "@/hooks/paging-filtering/usePagination.jsx";
import Pagination from "@/components/pagination/Pagination.jsx";
import SpacecraftSection from "@/components/sections/SpacecraftSection.jsx";
import BasicFiltering from "@/components/filtering/BasicFiltering.jsx";
import {useParameterizedQuery} from "@/services/queries.jsx";
import Head from "@/components/seo/Head.jsx";
import JsonLdGeneric from "@/components/seo/jsonld/JsonLdGeneric.jsx";

const defaultFilters = {
    page: 1,
    limit: 12,
    ordering: "desc"
};

function Spacecraft() {
    const baseUrl = `${import.meta.env.VITE_BACKEND_BASE_URL}/public/spacecraft`;
    const [searchParams] = useSearchParams();
    const pagination = usePagination();

    const queryData
        = useParameterizedQuery({
            url: `${baseUrl}?${searchParams}`,
            params: `pagination-${searchParams.toString()}`,
            cacheKey: "spacecraft-pagination",
            queryOptions:{
                enabled: !!searchParams.toString().length > 0,
            }
        });
    useEffect(() => {
        if (queryData.data) {
            pagination.setPagination(queryData?.data?.page);
        }
    }, [queryData.data, pagination]);

    return (
        <>
            <Head
                title="Spacecraft"
                description="Explore the latest and historic spacecraft vehicles that shaped space exploration."
            />
            <JsonLdGeneric
                type="CollectionPage"
                title="Spacecraft"
                description="Explore the latest and historic spacecraft vehicles that shaped space exploration."
            />
            <Heading
                title="Spacecraft"
                description="Explore the latest and historic spacecraft vehicles that shaped space exploration."
            />
            <BasicFiltering
                defaultFilters={defaultFilters}
                searchPlaceHolder="e.g. Dream Chaser"
            />
           <Pagination
               {...pagination}
               isPending={queryData.isPending}
               isFetching={queryData.isFetching}
           />
           <SpacecraftSection
               spacecraft={queryData.data || {}}
               isPending={queryData.isPending}
               isFetching={queryData.isFetching}
               isError={queryData.isError}
           />
           <Pagination
               {...pagination}
               isPending={queryData.isPending}
               isFetching={queryData.isFetching}
           />
        </>
    );
}

export default Spacecraft;
