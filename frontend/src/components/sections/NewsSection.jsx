import React from 'react';
import NewsArticle from "@/components/cards/NewsArticle.jsx";
import ContentSection from "@/components/sections/ContentSection.jsx";
import SkeletonLandscapeLoader from "@/components/skeleton/SkeletonLandscapeLoader.jsx";
const NewsSection = ({ articles, isLoading, isFetching, isError }) => {
    const items = articles.results || [];
    const contentConfig = {
        component: SkeletonLandscapeLoader,
        styles: {
            wrapper: "small-wrapper",
            section: "news-articles",
        },
    };
    return (
        <ContentSection
            items={items}
            isFetching={isFetching}
            isLoading={isLoading}
            isError={isError}
            contentConfig={contentConfig}
            CardComponent={NewsArticle}
            itemKeyExtractor={(item) => item.id}
        />
    );
};
export default NewsSection;
