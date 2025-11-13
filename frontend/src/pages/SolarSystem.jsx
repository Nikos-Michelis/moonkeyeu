import React, {useEffect} from "react";
import Heading from "../components/utils/Heading.jsx";
import Pagination from "../components/pagination/Pagination.jsx";
import AstronautsSection from "../components/sections/AstronautsSection.jsx";
import {useSearchParams} from "react-router-dom";
import usePagination from "@/hooks/paging-filtering/usePagination.jsx";
import AstronautsFiltering from "@/components/filtering/AstronautsFiltering.jsx";
import {useParameterizedQuery, useSimpleQuery} from "@/services/queries.jsx";
import Head from "@/components/seo/Head.jsx";
import JsonLdGeneric from "@/components/seo/jsonld/JsonLdGeneric.jsx";
import PlanetsSection from "@/components/sections/PlanetsSection.jsx";

function SolarSystem() {
    const baseUrl = `${import.meta.env.VITE_BACKEND_BASE_URL}/public/astronauts`;
   /* const queryData
        = useParameterizedQuery({
            url: `${baseUrl}?${searchParams}`,
            params: `pagination-${searchParams.toString()}`,
            cacheKey: "astronauts-pagination",
            queryOptions:{
                enabled: !!searchParams.toString().length > 0,
            }
        });*/
    const planetData = [
        {
            id: 1,
            name: "Mercury",
            category: "Planet",
            subtype: "Terrestrial Planet",
            radius: 2439.7,
            tilt: 0.034,
            rotation: 1406.4,   // 58.6 days → 58.6 * 24
            orbit: 88,
            distance: 57900000,
            gravity: 3.7,       // m/s²
            moons: 0,
            info: "The smallest planet in our solar system and nearest to the Sun.",
            img_day: "mercury.jpg",
            isStar: false,
        },
        {
            id: 2,
            name: "Venus",
            category: "Planet",
            subtype: "Terrestrial Planet",
            radius: 6051.8,
            tilt: 177.4,
            rotation: 5832,
            orbit: 225,
            distance: 108200000,
            gravity: 8.87,
            moons: 0,
            info: "Second planet from the Sun, known for its extreme temperatures and thick atmosphere.",
            img_atmosphere: "venus_atmosphere.jpg",
            img_day: "venus.jpg",
            isStar: false,
            hasRing: false,
        },
        {
            id: 3,
            name: "Earth",
            category: "Planet",
            subtype: "Terrestrial Planet",
            radius: 6371,
            tilt: 23.5,
            rotation: 24,
            orbit: 365,
            distance: 150000000,
            gravity: 9.807,
            moons: 1,
            info: "Third planet from the Sun and the only known planet to harbor life.",
            img_atmosphere: "earth_atmosphere.jpg",
            img_day: "earth_daymap.jpg",
            img_night: "earth_nightmap.jpg",
            isStar: false,
            hasRing: false,
        },
        {
            id: 4,
            name: "Mars",
            category: "Planet",
            subtype: "Terrestrial Planet",
            radius: 3389.5,
            tilt: 25.19,
            rotation: 24.6,
            orbit: 687,
            distance: 227900000,
            gravity: 3.721,
            moons: 2,
            info: "Known as the Red Planet, famous for its reddish appearance and potential for human colonization.",
            img_day: "mars.jpg",
            isStar: false,
            hasRing: false,
        },
        {
            id: 5,
            name: "Jupiter",
            category: "Planet",
            subtype: "Gas Giant",
            radius: 69911,
            tilt: 3.13,
            rotation: 9.9,
            orbit: 4380,
            distance: 778500000,
            gravity: 24.79,
            moons: 95,
            info: "The largest planet in our solar system, known for its Great Red Spot.",
            img_day: "jupiter.jpg",
            isStar: false,
            hasRing: true,
        },
        {
            id: 6,
            name: "Saturn",
            category: "Planet",
            subtype: "Gas Giant",
            radius: 58232,
            tilt: 26.73,
            rotation: 10.7,
            orbit: 10758,
            distance: 1427000000,
            gravity: 10.44,
            moons: 146,
            ring_in_radius: 18,
            ring_out_radius: 29,
            info: "Distinguished by its extensive ring system, the second-largest planet in our solar system.",
            img_day: "saturnmap.jpg",
            img_ring: "saturn_ring.jpg",
            isStar: false,
            hasRing: true,
        },
        {
            id: 7,
            name: "Uranus",
            category: "Planet",
            subtype: "Ice Giant",
            radius: 25362,
            tilt: 97.77,
            rotation: 17.2,
            orbit: 30681,
            distance: 2871000000,
            gravity: 8.69,
            moons: 27,
            ring_in_radius: 8,
            ring_out_radius: 10,
            info: "Known for its unique sideways rotation and pale blue color.",
            img_day: "uranus.jpg",
            img_ring: "uranus_ring.jpg",
            isStar: false,
            hasRing: true,
        },
        {
            id: 8,
            name: "Neptune",
            category: "Planet",
            subtype: "Ice Giant",
            radius: 24622,
            tilt: 28.32,
            rotation: 16.1,
            orbit: 60225,
            distance: 4495000000,
            gravity: 11.15,
            moons: 14,
            info: "The most distant planet from the Sun in our solar system, known for its deep blue color.",
            img_day: "neptune.jpg",
            isStar: false,
            hasRing: false,
        },
        {
            id: 9,
            name: "Pluto",
            category: "Dwarf Planet",
            subtype: "Kuiper Belt Object",
            radius: 1188.3,
            tilt: 122.53,
            rotation: 153.6,
            orbit: 90520,
            distance: 5906000000,
            gravity: 0.62,
            moons: 5,
            info: "A dwarf planet in the Kuiper Belt, once considered the ninth planet.",
            img_day: "plutomap.jpg",
            isStar: false,
            hasRing: false,
        },
        {
            id: 10,
            name: "Sun",
            category: "Star",
            subtype: "G-type Main Sequence",
            radius: 695700,
            tilt: 7.25,
            rotation: 600,
            orbit: 0,
            distance: 0,
            gravity: 274,
            moons: 0,
            info: "A G-type main-sequence star that provides the energy necessary for life on Earth.",
            img_day: "sun.jpg",
            isStar: true,
            hasRing: false,
        },
        {
            id: 11,
            name: "Moon",
            category: "Satellite",
            subtype: "Natural Satellite",
            radius: 1737,
            tilt: 6.68,
            rotation: 655.2,
            orbit: 27.3,
            distance: 384400,
            gravity: 1.62,
            moons: 0,
            info: "Earth's only natural satellite.",
            img_day: "moon.jpg",
            isStar: false,
            hasRing: false,
        }
    ];



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
                 planets={planetData || {}}
             />
        </>
    );
}

export default SolarSystem;
