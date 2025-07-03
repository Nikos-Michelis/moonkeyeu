import { useMemo } from "react";

/**
 * @param items list of items for comparison
 * @param compareFn compare function (e.g a > b)
 * @returns {any}
 */

const useComparator = (items, compareFn) => {
    return useMemo(() => {
        if (!items || items.length === 0) return null;

        return items.reduce((best, current) =>
            compareFn(best, current) ? best : current
        );

    }, [items, compareFn]);
};

export default useComparator;
