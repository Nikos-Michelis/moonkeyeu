import React, {useEffect} from "react";
import Heading from "../components/utils/Heading.jsx";
import Pagination from "../components/pagination/Pagination.jsx";
import AstronautsSection from "../components/sections/AstronautsSection.jsx";
import {useSearchParams} from "react-router-dom";
import usePagination from "@/hooks/paging-filtering/usePagination.jsx";
import AstronautsFiltering from "@/components/filtering/AstronautsFiltering.jsx";
import {useParameterizedQuery, useSimpleQuery} from "@/services/queries.jsx";
import Head from "@/components/seo/Head.jsx";
import JsonLdGeneric from "@/components/seo/jsonld/JsonLdGeneric.jsx";

function Astronauts() {
    const baseUrl = `${import.meta.env.VITE_BACKEND_BASE_URL}/public/astronauts`;
    const filtersUrl = `${import.meta.env.VITE_BACKEND_BASE_URL}/public/astronauts/filters`;
    const [searchParams] = useSearchParams();
    const pagination = usePagination();
    const queryData
        = useParameterizedQuery({
            url: `${baseUrl}?${searchParams}`,
            params: `pagination-${searchParams.toString()}`,
            cacheKey: "astronauts-pagination",
            queryOptions:{
                enabled: !!searchParams.toString().length > 0,
            }
        });
    const filterData
        = useSimpleQuery({
        url: filtersUrl,
        cacheKey: "astronauts-filters",
        staleTime: Infinity
        });
    useEffect(() => {
        if (queryData.data) {
            pagination.setPagination(queryData.data?.page);
        }
    }, [queryData.data, pagination]);

    return (
        <>
             <Head
                 title="Astronauts"
                 description="Meet the astronauts from different missions and backgrounds – Apply filters to find those that align with your interests."
             />
            <JsonLdGeneric
                type="CollectionPage"
                title="Astronauts"
                description="Meet the astronauts from different missions and backgrounds – Apply filters to find those that align with your interests."
            />
             <Heading
                 title="Astronauts"
                 description="Meet the astronauts from different missions and backgrounds – Apply filters to find those that align with your interests."
             />
             <AstronautsFiltering
                filters={filterData.data}
                searchPlaceHolder="e.g. Armstrong"
                isPending={filterData.isPending}
                isFetching={filterData.isFetching}
                isError={filterData.isError}
             />
             <Pagination
                {...pagination}
                isPending={queryData.isPending}
                isFetching={queryData.isFetching}
            />
             <AstronautsSection
                 astronauts={queryData.data || {}}
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

export default Astronauts;
