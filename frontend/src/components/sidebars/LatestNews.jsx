import React from 'react';
import {useSpaceFlightNews} from "@/context/SpaceFlightNewsProvider.jsx";
import NasaApod from "@/components/sidebars/NasaApod.jsx";
import {SkeletonLoader} from "@/components/loader/SkeletonLoader.jsx";
import SkeletonSidebarLoader from "@/components/skeleton/SkeletonSidebarLoader.jsx";
import {Link} from "react-router-dom";
import {useNasaApod} from "@/context/NasaApodProvider.jsx";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faNewspaper } from '@fortawesome/free-regular-svg-icons';
import NewsAsideArticle from "../cards/NewsAsideArticle.jsx";
import {LinkButton} from "@/components/button/LinkButton.jsx";

const LatestNews = () => {
    const { data: newsData, isPending: isPendingNews, isFetching: isFetchingNews, isError: isErrorNews } = useSpaceFlightNews();
    const { data: nasaApod, isPending: isPendingNasaApod, isFetching: isFetchingNasaApod, isError: isErrorNasaApod} = useNasaApod();
    const contentConfig = {
        count:4,
        component: SkeletonSidebarLoader,
    };

    return (
        <>
            <section className="latest-news">
                <div className="flex flex-column justify-center align-center margin-4">
                    <div className="sidebar container flex flex-column justify-center align-center bg-secondary-300 padding-4" data-type="full-bleed">
                        <div className="sidebar__heading-box">
                            <h3 className="ff-accent">Latest News</h3>
                        </div>
                        <SkeletonLoader
                            isPending={isPendingNews}
                            isFetching={isFetchingNews}
                            isError={isErrorNews}
                            contentConfig={contentConfig}>
                            <div className="sidebar__list">
                                    {newsData?.results?.length > 0 && (
                                        newsData.results.map((article) => (
                                            <NewsAsideArticle
                                                key={article.id}
                                                imageSrc={article.image_url}
                                                title={article.title}
                                                author={article.news_site}
                                                url={article.url}
                                            />
                                        )))
                                    }
                            </div>
                        </SkeletonLoader>
                        <div className="flex justify-center margin-block-start-2">
                            <LinkButton className="btn btn--primary" to="/news" disabled={isErrorNews}>
                                <FontAwesomeIcon icon={faNewspaper} /> SEE MORE NEWS
                            </LinkButton>
                        </div>
                    </div>
                </div>
            </section>
            <NasaApod
                nasaApod={nasaApod}
                isPendingNasaApod={isPendingNasaApod}
                isFetchingNasaApod={isFetchingNasaApod}
                isErrorNasaApod={isErrorNasaApod}
            />
        </>
    );
};

export default LatestNews;
