import React, {useEffect} from "react";
import Heading from "../components/utils/Heading.jsx";
import { useSearchParams } from "react-router-dom";
import usePagination from "@/hooks/paging-filtering/usePagination.jsx";
import Pagination from "@/components/pagination/Pagination.jsx";
import LauncherSection from "@/components/sections/LauncherSection.jsx";
import BasicFiltering from "@/components/filtering/BasicFiltering.jsx";
import {useParameterizedQuery} from "@/services/queries.jsx";
import Head from "@/components/seo/Head.jsx";
import JsonLdGeneric from "@/components/seo/jsonld/JsonLdGeneric.jsx";

const defaultFilters = {
    page: 1,
    limit: 12,
    ordering: "asc"
};
function Boosters() {
    const baseUrl = `${import.meta.env.VITE_BACKEND_BASE_URL}/public/launchers`;
    const [searchParams] = useSearchParams();
    const pagination = usePagination();
    const queryData
        = useParameterizedQuery({
            url: `${baseUrl}?${searchParams}`,
            params: `pagination-${searchParams.toString()}`,
            cacheKey: "launcher-pagination",
            queryOptions:{
                enabled: !!searchParams.toString().length > 0,
            }
        });

    useEffect(() => {
        if (queryData.data) {
            pagination.setPagination(queryData.data?.page);
        }
    }, [queryData.data, pagination]);


    return (
        <>
            <Head
                title="Boosters"
                description="Explore the latest and historic boosters that shaped space exploration"
            />
            <JsonLdGeneric
                type="CollectionPage"
                title="Boosters"
                description="Explore the latest and historic boosters that shaped space exploration"
            />
            <Heading
                title="Boosters"
                description="Explore the latest and historic boosters that shaped space exploration"
            />
            <BasicFiltering
                defaultFilters={defaultFilters}
                searchPlaceHolder="e.g. Booster 12"
            />
            <Pagination
                {...pagination}
                isPending={queryData.isPending}
                isFetching={queryData.isFetching}
            />
            <LauncherSection
                launcher={queryData.data || {}}
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

export default Boosters;
