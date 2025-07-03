import { useEffect } from "react";
import { useLocation } from "react-router-dom";
import useScrollToTop from "@/hooks/util/useScrollToTop.jsx";

const ScrollToTop = ({behavior= "smooth"}) => {
    const { pathname } = useLocation();
    const triggerScrollToTop = useScrollToTop(behavior);

    useEffect(() => {
        triggerScrollToTop(true);
    }, [pathname]);

    return null;
};

export default ScrollToTop;
