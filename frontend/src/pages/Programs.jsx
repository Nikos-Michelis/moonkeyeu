import React, {useEffect} from "react";
import Heading from "@/components/utils/Heading.jsx";
import Pagination from "@/components/pagination/Pagination.jsx";
import {useSearchParams} from "react-router-dom";
import BasicFiltering from "@/components/filtering/BasicFiltering.jsx";
import usePagination from "@/hooks/paging-filtering/usePagination.jsx";
import ProgramsSection from "@/components/sections/ProgramsSection.jsx";
import {useParameterizedQuery} from "@/services/queries.jsx";
import Head from "@/components/seo/Head.jsx";
import JsonLdGeneric from "@/components/seo/jsonld/JsonLdGeneric.jsx";

const defaultFilters = {
    page: 1,
    limit: 12,
    ordering: "desc"
};
function Programs() {
    const baseUrl = `${import.meta.env.VITE_BACKEND_BASE_URL}/public/programs`;
    const [searchParams] = useSearchParams();
    const pagination = usePagination();
    const queryData
        = useParameterizedQuery({
            url: `${baseUrl}?${searchParams}`,
            params: `pagination-${searchParams.toString()}`,
            cacheKey: "programs-pagination",
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
                title="Programs"
                description="Uncover the full spectrum of space programs, past and present."
            />
            <JsonLdGeneric
                type="CollectionPage"
                title="Programs"
                description="Uncover the full spectrum of space programs, past and present."
            />
            <Heading
                 title="Programs"
                 description="Uncover the full spectrum of space programs, past and present."
            />
            <BasicFiltering
                 defaultFilters={defaultFilters}
                 searchPlaceHolder="e.g. Polaris"
            />
            <Pagination
                {...pagination}
                isPending={queryData.isPending}
                isFetching={queryData.isFetching}
            />
             <ProgramsSection
                 programs={queryData.data || {}}
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

export default Programs;
