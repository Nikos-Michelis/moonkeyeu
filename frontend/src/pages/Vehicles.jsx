import Heading from "@/components/utils/Heading.jsx";
import VehiclesSection from "@/components/sections/VehiclesSection.jsx";
import Head from "@/components/seo/Head.jsx";
import React from "react";
import JsonLdGeneric from "@/components/seo/jsonld/JsonLdGeneric.jsx";

function Vehicles() {
    return (
        <>
            <Head
                title="Vehicles"
                description="Explore the vehicles that shape space exploration — from historic milestones to today's cutting-edge technology."
            />
            <JsonLdGeneric
                type="CollectionPage"
                title="Vehicles"
                description="Explore the vehicles that shape space exploration — from historic milestones to today's cutting-edge technology."
            />
            <Heading
                title="Spaceflight Vehicles"
                description="Explore the vehicles that shape space exploration — from historic milestones to today's cutting-edge technology."
            />
            <VehiclesSection />
        </>
    );
}


export default Vehicles;
