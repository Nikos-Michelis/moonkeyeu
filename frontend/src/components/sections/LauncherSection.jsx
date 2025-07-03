import React from 'react';
import ContentSection from "@/components/sections/ContentSection.jsx";
import SkeletonLandscapeLoader from "@/components/skeleton/SkeletonLandscapeLoader.jsx";
import LauncherCard from "@/components/cards/LauncherCard.jsx";

const LauncherSection = ({launcher, isFetching, isError}) => {
    const items = launcher._embedded?.launcherDTOes || [];
    const contentConfig = {
        component: SkeletonLandscapeLoader,
        styles: {
            wrapper: "medium-wrapper",
            img: "small-img",
            section: "launcher-articles",
        },
    };
    return (
        <ContentSection
            items={items}
            isFetching={isFetching}
            isError={isError}
            contentConfig={contentConfig}
            CardComponent={LauncherCard}
            itemKeyExtractor={(item) => item.id}
        />
    );
};

export default LauncherSection;
