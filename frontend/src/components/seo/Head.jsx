import { Helmet } from "react-helmet-async";
import React from "react";
import {useLocation} from "react-router-dom";

const Head = (
    {
        title ,
        description =  "Stay up to date with upcoming and past spaceflights from NASA, SpaceX, and other leading space agencies around the world.",
        image = `${import.meta.env.VITE_CLOUDFRONT_URL}/assets/logo/moonkeyeu-logo.jpg`,
        url = "https://www.moonkeyeu.com",
        alt,
        type = "website"
    }) => {
    const location = useLocation();
    const currentUrl = location.pathname === "/launches"
        ? `${window.location.origin}/`
        : `${window.location.origin}${location.pathname}`;
    const fullTitle = `MoonkeyEU - ${title || "Space Launch Tracker"}`;
    const checkedDescription = description || "Stay up to date with upcoming and past spaceflights from NASA, SpaceX, and other leading space agencies around the world.";
    const imgAlt = alt || `MoonkeyEU - ${title || "Space Launch Tracker"}`;
    return (
        <>
            <Helmet>
                <title>{fullTitle}</title>
                <meta name="description" content={checkedDescription} />
                <meta property="og:type" content={type} />
                <meta property="og:title" content={fullTitle} />
                <meta property="og:description" content={checkedDescription} />
                <meta property="og:site_name" content="MoonkeyEU" />
                <meta property="og:image" content={image} />
                <meta property="og:image:type" content="image/jpeg" />
                <meta property="og:image:width" content="1024" />
                <meta property="og:image:height" content="630" />
                <meta property="og:locale" content="en_US" />
                <meta property="og:url" content={url} />
                <meta name="twitter:card" content="summary_large_image" />
                <meta name="twitter:site" content="@moonkeyeu" />
                <meta name="twitter:title" content={fullTitle} />
                <meta name="twitter:description" content={checkedDescription} />
                <meta name="twitter:image" content={image} />
                <meta name="twitter:image:alt" content={imgAlt} />
                <meta name="robots" content="index, follow" />
                <link rel="canonical" href={currentUrl} />
            </Helmet>
        </>
    );
};

export default Head;
