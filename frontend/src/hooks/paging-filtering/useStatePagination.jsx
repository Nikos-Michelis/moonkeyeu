import {useCallback, useState} from 'react';

const FIRST_PAGE = 1;
export const useStatePagination = (itemsPerPage = 6) => {
    const [page, setPage] = useState(FIRST_PAGE);
    const [totalItems, setTotalItems] = useState(0);
    const totalPages = Math.ceil(totalItems / itemsPerPage);
    const nextPage = () => setPage(prev => Math.min(prev + 1, totalPages));
    const prevPage = () => setPage(prev => Math.max(prev - 1, 1));

    const firstPage = useCallback(() => {
        setPage(FIRST_PAGE);
    }, [page]);

    const lastPage = useCallback(() => {
        setPage(totalPages);
    }, [totalPages]);

    return { page, totalPages, itemsPerPage, totalItems, nextPage, prevPage, firstPage, lastPage, setTotalItems };
};
