import React from "react";
import OpenStreetMap from "@/components/api/map/OpenStreetMap.jsx";
import { useSimpleQuery } from "@/services/queries.jsx";
import Head from "@/components/seo/Head.jsx";
import JsonLdGeneric from "@/components/seo/jsonld/JsonLdGeneric.jsx";

function Locations() {
    const baseUrl = `${import.meta.env.VITE_BACKEND_BASE_URL}/public/launch-pads`;
    const queryData
        = useSimpleQuery({
            url: baseUrl,
            cacheKey: "locations"
        });
    return (
        <>
            <Head
                title="Locations"
                description="Discover launch sites and spaceports used by NASA, SpaceX, and other space agencies around the world."
            />
            <JsonLdGeneric
                type="Page"
                title="Locations"
                description="Discover launch sites and spaceports used by NASA, SpaceX, and other space agencies around the world."
            />
            <OpenStreetMap
                locations={queryData.data}
                isPending={queryData.isPending}
                isFetching={queryData.isFetching}
                isError={queryData.isError}
            />
        </>
    );
}

export default Locations;
