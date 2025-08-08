import React from "react";
import RocketCard from "@/components/cards/RocketCard.jsx";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faGear } from '@fortawesome/free-solid-svg-icons';
import StatePagination from "@/components/pagination/StatePagination.jsx";
import {SkeletonLoader} from "@/components/loader/SkeletonLoader.jsx";
import SkeletonLandscapeLoader from "@/components/skeleton/SkeletonLandscapeLoader.jsx";

const RocketConfig = ({ queryData,  pagination }) =>{
    const parsedLaunches = queryData?.data?._embedded?.rocketConfigSummarizedDTOes || [];
    const contentConfig = {
        component: SkeletonLandscapeLoader,
        count: 4,
        styles: {
            wrapper: "article-card--medium-wrapper",
            section: "rockets-articles",
            card_type: "landscape-card__container--article-card"
        },
    };

    return(
        <section className="rockets-section">
            <div className="article__heading-box">
                <FontAwesomeIcon icon={faGear} />
                <h2>Rocket Configs</h2>
            </div>
            <hr className="hr-100-sm bg-hr-600" />
            <div className="flex justify-center align-center padding-block-8">
                <div className="container" data-spacing="none" data-type="full-width">
                    <div className="margin-block-5 margin-inline-4">
                        { pagination?.totalItems > 4 && <StatePagination pagination={pagination}/> }
                            <div className="grid-layout__landscape padding-block-2 margin-block-4">
                                <SkeletonLoader
                                    isPending={queryData?.isPending}
                                    isFetching={queryData?.isFetching}
                                    isError={queryData?.isError}
                                    contentConfig={contentConfig}
                                >
                                        {parsedLaunches?.length > 0 && (
                                            parsedLaunches.map((config) => (
                                                <RocketCard
                                                    key={config.id}
                                                    {...config}
                                                    cardStyles={contentConfig?.styles}
                                                />
                                            ))
                                        )}
                                </SkeletonLoader>
                            </div>
                        { pagination?.totalItems> 4 && <StatePagination pagination={pagination}/> }
                    </div>
                </div>
            </div>
        </section>
    )
}
export default RocketConfig;