import { useState } from "react";

const useClipboard = () => {
    const [copied, setCopied] = useState(false);
    const copyToClipboard = async (text) => {
        try {
            if (text) {
                await navigator.clipboard.writeText(text);
                setCopied(true);
                setTimeout(() => setCopied(false), 2000);
            }
        } catch (err) {
            console.error("Failed to copy text: ", err);
            setCopied(false);
        }
    };

    return { copied, copyToClipboard };
};

export default useClipboard;
