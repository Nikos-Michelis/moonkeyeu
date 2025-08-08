import React from 'react';
import ContentSection from "@/components/sections/ContentSection.jsx";
import SkeletonLandscapeLoader from "@/components/skeleton/SkeletonLandscapeLoader.jsx";
import ProgramsCard from "@/components/cards/ProgramsCard.jsx";

const ProgramsSection = ({ programs, isFetching, isError }) => {
    const items = programs._embedded?.programSummarizedDTOes || [];
    const contentConfig = {
        component: SkeletonLandscapeLoader,
        styles: {
            wrapper: "small-wrapper",
            section: "programs-articles",
        },
    };
    return (
        <ContentSection
            items={items}
            isFetching={isFetching}
            isError={isError}
            contentConfig={contentConfig}
            CardComponent={ProgramsCard}
            itemKeyExtractor={(item) => item.id}
        />
    );
};
export default ProgramsSection;
