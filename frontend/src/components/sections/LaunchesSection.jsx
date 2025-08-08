import React from "react";
import LaunchCard from "@/components/cards/LaunchCard.jsx";
import SkeletonLandscapeLoader from "@/components/skeleton/SkeletonLandscapeLoader.jsx";
import ContentSection from "@/components/sections/ContentSection.jsx";

const LaunchesSection = (
    {
        launches,
        isFetching,
        isPending,
        isError,
        isBookmarked,
        emptyList,
        options,
        navUrl = undefined
    }) => {
    const items =  launches._embedded?.launchNormalDTOes || launches || [];
    const contentConfig = {
        component: SkeletonLandscapeLoader,
        styles: {
            wrapper: "large-wrapper",
            section: "launches-articles",
        },
    };

    return (
        <>
            <ContentSection
                items={items}
                isFetching={isFetching}
                isPending={isPending}
                isError={isError}
                contentConfig={contentConfig}
                CardComponent={LaunchCard}
                itemKeyExtractor={(item) => item.id}
                isBookmarked={isBookmarked}
                emptyList={emptyList}
                options={options}
                navUrl={navUrl}
            />
        </>
    );
};

export default LaunchesSection;
