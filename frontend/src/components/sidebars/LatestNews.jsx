import React from 'react';
import NewsArticle from "../cards/NewsAsideArticle.jsx";
import {useSpaceFlightNews} from "@/context/SpaceFlightNewsProvider.jsx";
import NasaApod from "@/components/sidebars/NasaApod.jsx";
import {SkeletonLoader} from "@/components/loader/SkeletonLoader.jsx";
import SkeletonSidebarLoader from "@/components/skeleton/SkeletonSidebarLoader.jsx";
import {Link} from "react-router-dom";
import {useNasaApod} from "@/context/NasaApodProvider.jsx";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faNewspaper } from '@fortawesome/free-regular-svg-icons';

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
                    <div className="container __aside flex flex-column justify-center align-center bg-secondary-300 padding-4" data-type="full-bleed">
                        <div className="sidebar-heading padding-2 text-center">
                            <h3 className="ff-accent">Latest News</h3>
                        </div>
                        <SkeletonLoader
                            isPending={isPendingNews}
                            isFetching={isFetchingNews}
                            isError={isErrorNews}
                            contentConfig={contentConfig}>
                        <div className="sidebar-articles-list">
                                {newsData?.results?.length > 0 && (
                                    newsData.results.map((article) => (
                                        <NewsArticle
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
                        <div className="flex justify-center">
                            <Link className="btn btn-primary btn-lg"  to="/news">
                                <FontAwesomeIcon icon={faNewspaper} /> SEE MORE NEWS
                            </Link>
                        </div>
                    </div>
                </div>
            </section>
            {nasaApod?.media_type === "image" &&
                <NasaApod
                    nasaApod={nasaApod}
                    isPendingNasaApod={isPendingNasaApod}
                    isFetchingNasaApod={isFetchingNasaApod}
                    isErrorNasaApod={isErrorNasaApod}
                />
            }
        </>
    );
};

export default LatestNews;
