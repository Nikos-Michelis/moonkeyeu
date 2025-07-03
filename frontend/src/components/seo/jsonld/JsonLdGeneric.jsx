import {useLocation} from "react-router-dom";
import {Helmet} from "react-helmet-async";

const jsonLdGeneric = (
    {
        title,
        type = "WebPage",
        description,
        image,
        alt,
        createdAt,
        updatedAt,
    }) =>{
    const location = useLocation();
    const currentUrl = location.pathname === "/launches"
        ? `${window.location.origin}/`
        : `${window.location.origin}${location.pathname}`;
    const fullTitle =  location.pathname === "/launches" ?  "MoonkeyEU" : `MoonkeyEU - ${title || "Space Launch Tracker"}`;
    const imageUrl = image || `${import.meta.env.VITE_CLOUDFRONT_URL}/assets/logo/moonkeyeu-logo.jpg`;
    const checkedDescription = description || "Stay up to date with upcoming and past spaceflights from NASA, SpaceX, and other leading space agencies around the world.";
    const imgAlt = alt || `${title || "MoonkeyEU - Space Launch Tracker"}`;

    const jsonLd = {
            "@context": "https://schema.org",
            "@graph": [
            {
                "@type": type,
                "@id": currentUrl,
                "url": currentUrl,
                "name": fullTitle,
                "isPartOf": {
                    "@id": "https://www.moonkeyeu.com/#website"
                },
                "about": {
                    "@id": "https://www.moonkeyeu.com/#organization"
                },
                "primaryImageOfPage": {
                    "@id": `${currentUrl}/#primaryimage`
                },
                "image": {
                    "@id": `${currentUrl}/#primaryimage`
                },
                "thumbnailUrl": imageUrl,
                ...(createdAt && { "datePublished": createdAt }),
                ...(updatedAt && { "dateModified": updatedAt }),
                "description": checkedDescription,
                /*"breadcrumb": {
                    "@id": "https://www.example.com/spacecraft/apollo-5-lm/#breadcrumb"
                },*/
                "inLanguage": "en-US",
                "potentialAction": [
                    {
                        "@type": "ReadAction",
                        "target": currentUrl
                    }
                ]
            },
            {
                "@type": "ImageObject",
                "inLanguage": "en-US",
                "@id": `${currentUrl}/#primaryimage`,
                "url": imageUrl ,
                "contentUrl": imageUrl,
                "width": 1024,
                "height": 630,
                "caption": imgAlt
            },
            /*{
                "@type": "BreadcrumbList",
                "@id": "https://www.example.com/spacecraft/apollo-5-lm/#breadcrumb",
                "itemListElement": [
                    {
                        "@type": "ListItem",
                        "position": 1,
                        "name": "Home",
                        "item": "https://www.example.com/"
                    },
                    {
                        "@type": "ListItem",
                        "position": 2,
                        "name": "Spacecraft",
                        "item": "https://www.example.com/spacecraft/"
                    },
                    {
                        "@type": "ListItem",
                        "position": 3,
                        "name": "Apollo 5 LM"
                    }
                ]
            },*/
            {
                "@type": "WebSite",
                "@id": "https://www.moonkeyeu.com/#website",
                "url": "https://www.moonkeyeu.com/",
                "name": "MoonkeyEU",
                "description": checkedDescription,
                "publisher": { "@id": "https://www.moonkeyeu.com/#organization" },
                "alternateName": "MoonkeyEU",
                "potentialAction": [
                    {
                        "@type": "SearchAction",
                        "target": {
                            "@type": "EntryPoint",
                            "urlTemplate": "https://www.moonkeyeu.com/?s={search_term_string}"
                        },
                        "query-input": {
                            "@type": "PropertyValueSpecification",
                            "valueRequired": true,
                            "valueName": "search_term_string"
                        }
                    }
                ],
                "inLanguage": "en-US"
            },
            {
                "@type": "Organization",
                "@id": "https://www.moonkeyeu.com/#organization",
                "name": "MoonkeyEU",
                "url": "https://www.moonkeyeu.com/",
                "logo": {
                    "@type": "ImageObject",
                    "inLanguage": "en-US",
                    "@id": "https://www.moonkeyeu.com/#/schema/logo/image/",
                    "url": "https://cdn.moonkeyeu.com/media/assets/logo/moonkeyeu-logo.png",
                    "contentUrl": "https://cdn.moonkeyeu.com/media/assets/logo/moonkeyeu-logo.png",
                    "width": 1024,
                    "height": 683,
                    "caption": "MoonkeyEU"
                },
                "image": {
                    "@id": "https://www.moonkeyeu.com/#/schema/logo/image/"
                },
                /*"sameAs": [
                    "https://www.facebook.com/",
                    "https://x.com/",
                    "https://www.instagram.com/",
                    "https://www.pinterest.com/",
                    "https://www.youtube.com/"
                ]*/
            }
        ]
    }
    return (
        <Helmet>
            <script type="application/ld+json">
                {JSON.stringify(jsonLd)}
            </script>
        </Helmet>
    );
}
export default jsonLdGeneric;