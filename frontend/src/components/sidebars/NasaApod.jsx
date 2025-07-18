import React from 'react';
import NewsArticle from "../cards/NewsAsideArticle.jsx";
import {SkeletonLoader} from "@/components/loader/SkeletonLoader.jsx";
import SkeletonSidebarLoader from "@/components/skeleton/SkeletonSidebarLoader.jsx";
import {Link} from "react-router-dom";
import { faImage } from '@fortawesome/free-regular-svg-icons';
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {LinkButton} from "@/components/button/LinkButton.jsx";

const NasaApod = ({nasaApod, isPendingNasaApod, isFetchingNasaApod, isErrorNasaApod}) => {
    const contentConfig = {
        count:1,
        component: SkeletonSidebarLoader,
    };
    return (
        <section className="nasa-apod-image">
            <div className="flex flex-column justify-center align-center margin-4">
                <div className="container __aside flex flex-column justify-center align-center bg-secondary-300 padding-4" data-type="full-bleed">
                    <div className="sidebar-heading padding-2 text-center">
                        <h3 className="ff-accent">Astronomy Picture of the Day</h3>
                    </div>
                    <SkeletonLoader
                        isPending={isPendingNasaApod}
                        isFetching={isFetchingNasaApod}
                        isError={isErrorNasaApod}
                        contentConfig={contentConfig}>
                        {nasaApod ? (
                            <NewsArticle
                                imageSrc={nasaApod.url}
                                title={nasaApod.title}
                                author="NASA"
                                date={nasaApod.date}
                            />
                            ) : (
                                <div>No image available at the moment.</div>
                            )
                        }
                        </SkeletonLoader>
                        <div className="flex justify-center">
                            <LinkButton className="btn btn-primary btn-lg" to="/nasa-apod">
                                <FontAwesomeIcon icon={faImage} /> VIEW IMAGE
                            </LinkButton>
                        </div>
                </div>
            </div>
        </section>

    );
};

export default NasaApod;
