import {useCallback, useLayoutEffect} from "react";
import { useSearchParams } from "react-router-dom";

const defaultFilters = {
    page: 1,
    limit: 12,
    ordering: "asc",
};
export function useAstronautsFilters() {
    const [filterParams, setSearchParams] = useSearchParams();
    const search = filterParams.get('search');
    const status = filterParams.get('status') ? parseInt(filterParams.get('status')) : undefined;
    const nationality = filterParams.get('nationality') ? parseInt(filterParams.get('nationality')) : undefined;
    const agency = filterParams.get('agency') ? parseInt(filterParams.get('agency')) : undefined;
    const limit = filterParams.get('limit') ? parseInt(filterParams.get('limit')) : defaultFilters.limit;
    const ordering = filterParams.get('ordering') ? filterParams.get('ordering') : defaultFilters.ordering;


    useLayoutEffect(() => {
        Object.entries(defaultFilters).forEach(([key, value]) => {
            if (!filterParams.has(key)) {
                filterParams.set(key, value.toString());
            }
        });
        setSearchParams(filterParams, { replace: true });
    }, [filterParams]);

    const setFilters = useCallback((filters) => {
        setSearchParams((params) => {
            Object.entries(filters).forEach(([key, value]) => {
                if (value) {
                    params.set(key, value.toString());
                    params.set('page', defaultFilters.page);
                } else {
                    params.delete(key);
                }
            });
            return params;
        },{replace: true});// Replace the current page with the new URL, and it doesn't retain the old parameters
    }, [filterParams]);// change the function when the params change

    const resetFilters = useCallback(() => {
        setSearchParams(defaultFilters, {replace: true});
    }, [filterParams]);

    return {
        search,
        agency,
        status,
        nationality,
        limit,
        ordering,
        setFilters,
        resetFilters,
    };
}
