import { useState, useEffect } from "react";

export const useSimpleTimer = (initialMillis, interval) => {
    const [millis, setMillis] = useState(initialMillis);
    const [seconds, setSeconds] = useState(Math.floor(initialMillis / 1000));

    useEffect(() => {
        setMillis(initialMillis);
        setSeconds(Math.floor(initialMillis / 1000));
    }, [initialMillis]);

    useEffect(() => {
        if (millis <= 0) return;

        const timer = setInterval(() => {
            setMillis((prevMillis) => {
                if (prevMillis <= interval) {
                    clearInterval(timer);
                    return 0;
                }
                return prevMillis - interval;
            });
        }, interval);

        return () => clearInterval(timer);
    }, [millis, interval]);

    useEffect(() => {
        setSeconds(Math.floor(millis / 1000));
    }, [millis]);

    return { seconds };
};
