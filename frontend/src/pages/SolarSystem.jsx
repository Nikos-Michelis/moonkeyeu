import React from "react";
import Heading from "../components/utils/Heading.jsx";
import {useParameterizedQuery, useSimpleQuery} from "@/services/queries.jsx";
import Head from "@/components/seo/Head.jsx";
import JsonLdGeneric from "@/components/seo/jsonld/JsonLdGeneric.jsx";
import PlanetsSection from "@/components/sections/PlanetsSection.jsx";

function SolarSystem() {
    const baseUrl = `${import.meta.env.VITE_BACKEND_BASE_URL}/public/celestial-bodies`;
    const queryData
        = useSimpleQuery({
            url: `${baseUrl}`,
            params: `celestial-bodies`,
            cacheKey: "celestial-bodies",
        });

    return (
        <>
            <Head
                 title="Solar System"
                 description="Discover the characteristics of every planet in our Solar System."
            />
            <JsonLdGeneric
                type="CollectionPage"
                title="Solar System"
                description="Discover the characteristics of every planet in our Solar System."
            />
             <Heading
                 title="Solar System"
                 description="Discover the characteristics of every planet in our Solar System."
             />
            <PlanetsSection
                planets={queryData.data || {}}
                isFetching={queryData?.isFetching}
                isPending={queryData?.isPending}
                isError={queryData?.isError}
            />
        </>
    );
}

export default SolarSystem;
