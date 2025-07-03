import React from 'react';
import ContentSection from "@/components/sections/ContentSection.jsx";
import SkeletonLandscapeLoader from "@/components/skeleton/SkeletonLandscapeLoader.jsx";
import AgencyCard from "@/components/cards/AgencyCard.jsx";
const AgenciesSection = ({ agencies, isFetching, isError}) => {
    const items = agencies?.data || [];
    const contentConfig = {
        component: SkeletonLandscapeLoader,
        styles: {
            wrapper: "small-wrapper",
            img: "small-img",
            section: "spacecraft-articles",
            bottomGap:"bottom-gap"
        },
    };
    return (
        <ContentSection
            items={items}
            isFetching={isFetching}
            isError={isError}
            contentConfig={contentConfig}
            CardComponent={AgencyCard}
            itemKeyExtractor={(item) => item.id}
            isDetailed={true}
        />
    );
};
export default AgenciesSection;

