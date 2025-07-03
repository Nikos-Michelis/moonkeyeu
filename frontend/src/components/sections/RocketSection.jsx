import React from 'react';
import RocketCard from "@/components/cards/RocketCard.jsx";
import ContentSection from "@/components/sections/ContentSection.jsx";
import SkeletonLandscapeLoader from "@/components/skeleton/SkeletonLandscapeLoader.jsx";

const RocketSection = ({rocket, isFetching, isError}) => {
    const items = rocket._embedded?.rocketConfigSummarizedDTOes || [];
    const contentConfig = {
        component: SkeletonLandscapeLoader,
        styles: {
            wrapper: "medium-wrapper",
            img: "small-img",
            section: "rocket-articles",
        },
    };
    return (
        <ContentSection
            items={items}
            isFetching={isFetching}
            isError={isError}
            contentConfig={contentConfig}
            CardComponent={RocketCard}
            itemKeyExtractor={(item) => item.id}
        />
    );
};

export default RocketSection;
