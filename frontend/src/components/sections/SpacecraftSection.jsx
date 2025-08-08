import React from 'react';
import SpacecraftCard from "@/components/cards/SpacecraftCard.jsx";
import ContentSection from "@/components/sections/ContentSection.jsx";
import SkeletonLandscapeLoader from "@/components/skeleton/SkeletonLandscapeLoader.jsx";
const SpacecraftSection = ({ spacecraft, isFetching, isError }) => {
    const items = spacecraft._embedded?.spacecraftConfigSummarizedDTOes || [];
    const contentConfig = {
        component: SkeletonLandscapeLoader,
        styles: {
            wrapper: "medium-wrapper",
            section: "spacecraft-articles",
        },
    };
    return (
        <ContentSection
            items={items}
            isFetching={isFetching}
            isError={isError}
            contentConfig={contentConfig}
            CardComponent={SpacecraftCard}
            itemKeyExtractor={(item) => item.id}
        />
    );
};
export default SpacecraftSection;

