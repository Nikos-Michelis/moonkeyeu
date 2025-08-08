import React from "react";
import { Button } from "@/components/button/Button.jsx";
import { useSearchParams } from "react-router-dom";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faClockRotateLeft } from '@fortawesome/free-solid-svg-icons';

const PreviousBtn = () => {
    const [searchParams, setSearchParams] = useSearchParams();
    const upcoming = searchParams.get("upcoming");
    const handleLaunchState = () => {
        const isUpcoming = upcoming === "true";
        searchParams.set("upcoming", (!isUpcoming).toString());
        searchParams.set("page", 1);
        setSearchParams(searchParams, { replace: true });
    };
    return (
        <Button className="btn--transparent btn-previous margin-block-end-2" onClick={handleLaunchState}>
            <FontAwesomeIcon icon={faClockRotateLeft} /> {upcoming === "true" ? "Previous" : "Upcoming"}
        </Button>
    );
};

export default PreviousBtn;
