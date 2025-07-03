import React, {useEffect} from "react";
import Heading from "@/components/utils/Heading.jsx";
import {useAuth} from "@/context/AuthProvider.jsx";
import MessagesSection from "@/components/sections/MessagesSection.jsx";
import usePagination from "@/hooks/paging-filtering/usePagination.jsx";
import Pagination from "@/components/pagination/Pagination.jsx";
import showErrorToast from "@/components/utils/ShowErrorToast.jsx";
import BasicFiltering from "@/components/filtering/BasicFiltering.jsx";
import {useSearchParams} from "react-router-dom";
import { useParameterizedQuery } from "@/services/queries.jsx";
import Head from "@/components/seo/Head.jsx";
import JsonLdGeneric from "@/components/seo/jsonld/JsonLdGeneric.jsx";

const defaultFilters = {
    page: 1,
    limit: 12,
    ordering: "desc"
};
function Messages() {
    const baseUrl = `${import.meta.env.VITE_BACKEND_BASE_URL}/dashboard/contact/messages`;
    const { user, status } = useAuth();
    const [searchParams] = useSearchParams();
    const pagination = usePagination();

    const queryData = useParameterizedQuery({
        url: baseUrl + "?" + searchParams,
        params: `pagination-${searchParams.toString()}`,
        cacheKey: "contact-messages",
        retry: 1,
        queryOptions: {
            enabled: !!user && !!searchParams.toString().length > 0,
        },
        options: { withCredentials: true, Bearer: true }
    })

    useEffect(() => {
        queryData.isError && showErrorToast(queryData?.error);
    }, [queryData.error]);

    useEffect(() => {
        if (queryData.data) {
            pagination.setPagination(queryData?.data?.page);
        }
    }, [queryData.data, pagination]);

    return (
        <>
            <Head
                title="Messages"
                description="View and manage community messages."
            />
            <JsonLdGeneric
                type="CollectionPage"
                title="Messages"
                description="View and manage community messages."
            />
            <Heading
                title="Contact Messages"
                description="View and manage community messages."
            />
            <BasicFiltering
                defaultFilters={defaultFilters}
                searchPlaceHolder="e.g. example@gmail.com"
            />
            <Pagination
                {...pagination}
                isPending={queryData.isPending}
                isFetching={queryData.isFetching}
            />
            <MessagesSection
                messages={queryData.data || {}}
                isPending={queryData.isPending || (status.isPending && !status.isError)}
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

export default Messages;
