import { useSearchParams } from "react-router-dom";
import { useCallback, useState } from "react";

const FIRST_PAGE = 1;
const usePagination = (
    {
        initialPage = FIRST_PAGE,
        limit = 12,
        initialTotalPages = 0,
        initialTotalElements = 0,
        useOffset = false
    } = {}
) => {
    const [searchParams, setSearchParams] = useSearchParams();
    limit =  parseInt(searchParams.get("limit")) || limit
    const offset = useOffset
        ? parseInt(searchParams.get("offset")) || 0
        : (parseInt(searchParams.get("page")) || initialPage - 1) * limit;
    const page = useOffset
        ? Math.floor(offset / limit) + 1
        : parseInt(searchParams.get("page")) || initialPage;
    const [totalPages, setTotalPages] = useState(initialTotalPages);
    const [totalElements, setTotalElements] = useState(initialTotalElements);

    const setPageUrl = useCallback((newPage) => {
            const newParams = new URLSearchParams(searchParams);
            if (useOffset) {
                newParams.set("offset", (newPage - 1) * limit);
            } else {
                newParams.set("page", newPage);
            }
            setSearchParams(newParams);
        }, [limit, searchParams, useOffset]
    );
    const firstPage = useCallback(() => {
        setPageUrl(FIRST_PAGE);
    }, [setPageUrl]);

    const lastPage = useCallback(() => {
        setPageUrl(totalPages);
    }, [totalPages, setPageUrl]);

    const nextPage = useCallback(() => {
        setPageUrl(Math.min(page + 1, totalPages));
    }, [page, totalPages, setPageUrl]);

    const previousPage = useCallback(() => {
        setPageUrl(Math.max(page - 1, FIRST_PAGE));
    }, [page, setPageUrl]);

    const setPagination = useCallback((data) => {
        if (useOffset) {
            setTotalPages(Math.ceil(data.count / limit));
            setTotalElements(data.count);
        } else {
            setTotalPages(data.totalPages);
            setTotalElements(data.totalElements);
        }
    }, [useOffset, limit, initialTotalElements]);

    return {
        page,
        offset,
        totalPages,
        totalElements,
        nextPage,
        previousPage,
        firstPage,
        lastPage,
        setPagination,
    };
};

export default usePagination;
