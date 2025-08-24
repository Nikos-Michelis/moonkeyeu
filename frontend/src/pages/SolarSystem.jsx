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
            radius: 2.4397,
            tilt: 0.034,
            rotation: "58.6 Earth days",
            orbit: "88 Earth days",
            distance: 57.9,
            moons: "0",
            info: "The smallest planet in our solar system and nearest to the Sun.",
            img_day: "mercury.jpg",
            isStar: false,
            hasRing: false,
        },
        {
            id: 2,
            name: "Venus",
            radius: 6.0518,
            tilt: 177.4,
            rotation: "243 Earth days",
            orbit: "225 Earth days",
            distance: 108.2,
            moons: "0",
            info: "Second planet from the Sun, known for its extreme temperatures and thick atmosphere.",
            img_day: "venus.jpg",
            isStar: false,
            hasRing: false,
        },
        {
            id: 3,
            name: "Earth",
            radius: 6.371,
            tilt: 23.5,
            rotation: "24 hours",
            orbit: "365 days",
            distance: 150,
            moons: "1 (Moon)",
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
            radius: 3.3895,
            tilt: 25.19,
            rotation: "1.03 Earth days",
            orbit: "687 Earth days",
            distance: 227.9,
            moons: "2 (Phobos and Deimos)",
            info: "Known as the Red Planet, famous for its reddish appearance and potential for human colonization.",
            img_day: "mars.jpg",
            isStar: false,
            hasRing: false,
        },
        {
            id: 5,
            name: "Jupiter",
            radius: 69.911,
            tilt: 3.13,
            rotation: "9.9 hours",
            orbit: "12 Earth years",
            distance: 778.5,
            moons: "95 known moons",
            info: "The largest planet in our solar system, known for its Great Red Spot.",
            img_day: "jupiter.jpg",
            isStar: false,
            hasRing: true,
        },
        {
            id: 6,
            name: "Saturn",
            radius: 58.232,
            tilt: 26.73,
            rotation: "10.7 hours",
            orbit: "29.5 Earth years",
            distance: 1427,
            moons: "146 known moons",
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
            radius: 25.362,
            tilt: 97.77,
            rotation: "17.2 hours",
            orbit: "84 Earth years",
            distance: 2871,
            moons: "27",
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
            radius: 24.622,
            tilt: 28.32,
            rotation: "16.1 hours",
            orbit: "165 Earth years",
            distance: 4495,
            moons: "14",
            info: "The most distant planet from the Sun, known for its deep blue color.",
            img_day: "neptune.jpg",
            isStar: false,
            hasRing: false,
        },
        {
            id: 9,
            name: "Pluto",
            radius: 1.1883,
            tilt: 122.53,
            rotation: "6.4 Earth days",
            orbit: "248 Earth years",
            distance: 5906,
            moons: "5",
            info: "A dwarf planet in the Kuiper Belt, once considered the ninth planet.",
            img_day: "plutomap.jpg",
            isStar: false,
            hasRing: false,
        },
        {
            id: 10,
            name: "Sun",
            radius: 695.7,
            tilt: 7.25,
            rotation: "25 Earth days",
            orbit: "0",
            distance: 0,
            moons: "0",
            info: "A G-type main-sequence star that provides the energy necessary for life on Earth.",
            img_day: "sun.jpg",
            isStar: true,
            hasRing: false
        },
        {
            id: 11,
            name: "Moon",
            radius: 1.737,
            tilt: 6.68,
            rotation: "27.3 days",
            orbit: "27.3 days",
            distance: 0.384,
            moons: "0",
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
