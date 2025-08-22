import React, {useEffect} from "react";
import Heading from "@/components/utils/Heading.jsx";
import NewsSection from "@/components/sections/NewsSection.jsx";
import Pagination from "@/components/pagination/Pagination.jsx";
import {useSearchParams} from "react-router-dom";
import BasicFiltering from "@/components/filtering/BasicFiltering.jsx";
import usePagination from "@/hooks/paging-filtering/usePagination.jsx";
import {useParameterizedQuery} from "@/services/queries.jsx";
import Head from "@/components/seo/Head.jsx";
import JsonLdGeneric from "@/components/seo/jsonld/JsonLdGeneric.jsx";
function News() {
    const baseUrl = import.meta.env.VITE_NEWS_API_URL;
    const defaultFilters = {limit: 12, offset: 0, ordering:"-published_at"};
    const pagination =  usePagination({useOffset: true});
    const [searchParams] = useSearchParams();
    const queryData
        = useParameterizedQuery({
            url: `${baseUrl}&${searchParams}`,
            params: `pagination-${searchParams.toString()}`,
            cacheKey: "news-pagination",
            enableBoundary: false,
            queryOptions:{
                enabled: !!searchParams.toString().length > 0,
            }
        });
    useEffect(() => {
        if (queryData.data) {
            pagination.setPagination(queryData.data);
        }
    }, [queryData.data, pagination]);

    return (
        <>
            <Head
                title="News"
                description="Stay up to date by exploring the latest space news."
            />
            <JsonLdGeneric
                type="CollectionPage"
                title="News"
                description="Stay up to date by exploring the latest space news."
            />
            <Heading
                title="News"
                description="Stay up to date by exploring the latest space news."
            />
            <BasicFiltering
                defaultFilters={defaultFilters} s
                searchPlaceHolder="e.g. SpaceNews"
                field="published_at"
            />
            <Pagination
                {...pagination}
                isPending={queryData.isPending}
                isFetching={queryData.isFetching}
            />
            <NewsSection
                articles={queryData.data || {}}
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

export default News;
