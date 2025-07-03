import toast from "react-hot-toast";

const showErrorToast = (error, message = null) => {
    const statusCode = error?.response?.status;
    const defaultMessages = {
        400: "Bad request. Please check your input.",
        401: "Unauthorized. Please log in.",
        403: "Forbidden. You don't have permission.",
        404: "Not found. The requested resource is missing.",
        409: "Already exists.",
        500: "Server error. Please try again later.",
        503: "Service unavailable. Try again later."
    };

    const finalMessage = message || defaultMessages[statusCode] || "An unexpected error occurred.";
    toast.error(finalMessage);
};

export default showErrorToast;