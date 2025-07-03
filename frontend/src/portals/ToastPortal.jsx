import React, { useEffect, useState } from "react";
import { createPortal } from "react-dom";
import { Toaster } from "react-hot-toast";

export default function ToastPortal() {
    const [container, setContainer] = useState(null);

    useEffect(() => {
        const node = document.getElementById("portal");
        if (node) setContainer(node);
    }, []);

    if (!container) return null;

    return createPortal(<Toaster position="bottom-right" reverseOrder={false}/>, container);
}
