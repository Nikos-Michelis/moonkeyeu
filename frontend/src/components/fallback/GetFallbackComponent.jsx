import React, {useEffect} from "react";
import FallbackComponent from "@/components/fallback/FallbackComponent.jsx";

/**
 * Returns a fallback component based on error status code.
 * @param {Error} error - The error object caught by ErrorBoundary.
 * @returns {React.JSX.Element} A specific fallback component.
 */
export const GetFallbackComponent = ({ error }) => {
    useEffect(() => {
        console.log(error)
    }, [error]);
    switch (error?.response?.status) {
        case 404:
            return (
                <FallbackComponent
                    code={404}
                    message="Oops! Somthing went wrong, try again later."
                    error={error?.response?.data?.error}
                />
            );
        case 500:
            return (
                <FallbackComponent
                    code={500}
                    message="Oops! Somthing went wrong, try again later."
                    error={error?.response?.data?.error}
                />
            );
        default:
            return(
                <FallbackComponent
                    heading="We'll be back soon!"
                    message="Our website is down for maintenance."
                />
            );
    }
};

