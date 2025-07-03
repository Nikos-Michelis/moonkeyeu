import {useCallback, useLayoutEffect} from "react";
import {useSearchParams} from "react-router-dom";

const defaultFilters = {
    page: 1,
    limit: 12,
    upcoming: 'true',
   // ordering: 'asc',
};
export function useLaunchFilters() {
    const [filterParams, setSearchParams] = useSearchParams();
    const search = filterParams.get('search');
    const location = filterParams.get('location') ? parseInt(filterParams.get('location')) : undefined;
    const launcher = filterParams.get('launcher');
    const agency = filterParams.get('agency') ? parseInt(filterParams.get('agency')) : undefined;
    const upcoming = filterParams.get('upcoming') !== "all" ? filterParams.get('upcoming') : defaultFilters?.upcoming;
    const rocketConfig = filterParams.get('rocketConfig') ? parseInt(filterParams.get('rocketConfig')) : undefined;
    const spacecraftConfig = filterParams.get('spacecraftConfig') ? parseInt(filterParams.get('spacecraftConfig')) : undefined;
    const astronaut = filterParams.get('astronaut') ? parseInt(filterParams.get('astronaut')) : undefined;
    const limit = filterParams.get('limit') ? parseInt(filterParams.get('limit')) : defaultFilters?.limit;
    const ordering = filterParams.get('ordering') ? filterParams.get('ordering') : defaultFilters.ordering;

    useLayoutEffect(() => {
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
                    params.set('page', defaultFilters.page);
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
        location,
        launcher,
        agency,
        upcoming,
        rocketConfig,
        spacecraftConfig,
        astronaut,
        limit,
        ordering,
        setFilters,
        resetFilters,
    };
}
