import {useCallback, useEffect} from "react";
import {useSearchParams} from "react-router-dom";

export function useBasicFilters(defaultFilters) {
    const [filterParams, setSearchParams] = useSearchParams();
    const search = filterParams.get('search');
    const limit = filterParams.get('limit') ? parseInt(filterParams.get('limit')) : defaultFilters.limit;
    const ordering = filterParams.get('ordering') ? filterParams.get('ordering') : defaultFilters.ordering;

    useEffect(() => {
        Object.entries(defaultFilters).forEach(([key, value]) => {
            if (!filterParams.has(key)) {
                filterParams.set(key, value.toString());
            }
        });
        setSearchParams(filterParams, { replace: true });
    }, [filterParams, setSearchParams]);

    const setFilters = useCallback((filters) => {
        setSearchParams((params) => {
            Object.entries(filters).forEach(([key, value]) => {
                if (value) {
                    params.set(key, value.toString());
                    params.has('offset') && params.set('offset', defaultFilters.offset);
                    params.has('page') && params.set('page', defaultFilters.page);
                } else {
                    params.delete(key);
                }
            });
            return params;
        },{replace: true});
    }, [filterParams]);

    const resetFilters = useCallback(() => {
        setSearchParams(defaultFilters, {replace: true});
    }, [filterParams]);

    return {
        search,
        limit,
        ordering,
        setFilters,
        resetFilters,
    };
}
