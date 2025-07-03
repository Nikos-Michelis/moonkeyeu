import { useState, useEffect } from 'react';
/**
 * useDebounce hook to debounce a value change with a specified delay.
 *
 * @param {any} value - The value to be debounced (can be a string, number, or object).
 * @param {number} [delay=500] - The debounce delay in milliseconds (default is 500ms).
 * @returns {any} - The debounced value, which will only change after the specified delay.
 *
 * @example
 * const debouncedValue = useDebounce(inputValue, 300);
 * useEffect(() => {
 *   setValue(debounceValue)
 *}, [debouncedValue]);
 *
 */
export const useDebounce = (value, delay = 500) => {
    const [debouncedValue, setDebouncedValue] = useState(value);

    useEffect(() => {
        const timeout = setTimeout(() => {
            setDebouncedValue(value);
        }, delay);

        return () => {
            clearTimeout(timeout);
        };
    }, [value, delay]);

    return debouncedValue;
}
