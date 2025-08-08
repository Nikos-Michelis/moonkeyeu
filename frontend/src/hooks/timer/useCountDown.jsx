import {useCallback, useEffect, useRef, useState} from "react";

/**
 * @param initialTimeInMillis initial date-time milliseconds
 * @param interval
 */

export const useCountDown = (initialTimeInMillis, interval) => {
    const targetTime = useRef(initialTimeInMillis);
    const [remainingTime, setRemainingTime] = useState({ months: 0, days: 0, hours: 0, minutes: 0, seconds: 0 });
    const intervalIdRef = useRef(null);

    const dateTimeFormat = useCallback((distance) => {
        const months = Math.floor(distance / (1000 * 60 * 60 * 24 * 30));
        const days = Math.floor((distance % (1000 * 60 * 60 * 24 * 30)) / (1000 * 60 * 60 * 24));
        const hours = Math.floor((distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
        const minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
        const seconds = Math.floor((distance % (1000 * 60)) / 1000);
        return { months, days, hours, minutes, seconds };
    }, []);

    const start = useCallback(() => {
         intervalIdRef.current = setInterval(() => {
            const distance = targetTime.current - Date.now();
            if (distance < 0) {
                setRemainingTime({ months: 0, days: 0, hours: 0, minutes: 0, seconds: 0 });
                clearInterval(intervalIdRef.current);
                return;
            }
            setRemainingTime(dateTimeFormat(distance));
        }, interval);

    }, [initialTimeInMillis]);
    useEffect(() => {
        start();
        return () => {
            clearInterval(intervalIdRef.current);
        };
    }, [start]);

    return remainingTime;
};


