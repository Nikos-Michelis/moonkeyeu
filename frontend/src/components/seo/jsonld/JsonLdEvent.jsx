import {Helmet} from "react-helmet-async";

const JsonLdEvent = (
    {
        title,
        description,
        image,
        startDate,
        endDate,
        location,
        agency
    }) => {
    const fullTitle = `MoonkeyEU - ${title || "Space Launch Tracker"}`;
    const imageUrl = image || `${import.meta.env.VITE_CLOUDFRONT_URL}/assets/logo/moonkeyeu-logo.jpg`;
    const checkedDescription = description || "Stay up to date with upcoming and past spaceflights from NASA, SpaceX, and other leading space agencies around the world.";

    const jsonLd = {
        "@context": "http://schema.org",
        "@type": "Event",
        "name": fullTitle,
        "description": checkedDescription,
        ...(startDate && { "startDate": startDate }),
        ...(endDate && { "endDate": endDate }),
        "eventAttendanceMode": "https://schema.org/MixedEventAttendanceMode",
        "eventStatus": "https://schema.org/EventScheduled",
        "location": {
            "@type": "Place",
            "name": location?.name,
            "address": {
                "@type": "PostalAddress",
                "addressLocality": location?.name,
                "addressRegion": location?.location?.name,
            }
        },
        "image": {
            "@type": "ImageObject",
            "url": imageUrl,
            "height": 1024,
            "width": 683
        },
        "offers": {
            "@type": "Offer",
            "url": "https://www.moonkeyeu.com",
            "price": "0",
            "priceCurrency": "USD",
            "availability": "https://schema.org/InStock",
            ...(startDate && { "validFrom": startDate }),
        },
        "performer": [
            {
                "@type": "PerformingGroup",
                "name": agency
            }
        ],
        "organizer": {
            "@type": "Organization",
            "name": agency,
            "url": "https://www.moonkeyeu.com"
        }
    };

    return (
        <Helmet>
            <script type="application/ld+json">
                {JSON.stringify(jsonLd)}
            </script>
        </Helmet>
    );
};

export default JsonLdEvent;