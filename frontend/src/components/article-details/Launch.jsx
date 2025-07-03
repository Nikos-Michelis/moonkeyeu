import React from "react";
import LaunchCard from "@/components/cards/LaunchCard.jsx";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import { faRocket } from '@fortawesome/free-solid-svg-icons';
import PropTypes from "prop-types";
import StatePagination from "@/components/pagination/StatePagination.jsx";
import {SkeletonLoader} from "@/components/loader/SkeletonLoader.jsx";
import SkeletonLandscapeLoader from "@/components/skeleton/SkeletonLandscapeLoader.jsx";

const Launch = ({ launches = {}, queryData= {}, navUrl= "", pagination, hasPagination = false }) => {
    const parsedLaunches = queryData?.data?._embedded?.launchNormalDTOes || launches || [];
    const contentConfig = {
        component: SkeletonLandscapeLoader,
        count: 4,
        styles: {
            wrapper: "large-wrapper",
            img: "small-img",
            section: "launches-articles",
        },
    };

    return (
        <section className="launches-section">
            <div className="heading-box">
                <FontAwesomeIcon icon={faRocket} />
                <h2>Related Launches</h2>
            </div>
            <hr className="hr-7-sm bg-hr-600" />
            <div className="flex flex-wrap justify-center align-center padding-block-8">
                <div className="flex justify-center">
                    <div className={`container flex justify-center ${( !hasPagination && launches.length > 2 ) ? "scrolling" : ""}`} data-spacing="none">
                        <div className="margin-block-5 margin-inline-4">
                            { (hasPagination && pagination?.totalItems > 4) && <StatePagination pagination={pagination}  {...queryData}/>}
                            <div className="landscape-grid padding-block-2 margin-block-4">
                                <SkeletonLoader
                                    isPending={queryData?.isPending}
                                    isFetching={queryData?.isFetching}
                                    isError={queryData?.isError}
                                    contentConfig={contentConfig}
                                >
                                    {parsedLaunches?.length > 0 && (
                                        parsedLaunches?.sort((a, b) => new Date(b?.net) - new Date(a?.net)).map((launch) => (
                                            <LaunchCard
                                                key={launch.id}
                                                {...(launch.launch ? launch.launch : launch)}
                                                navUrl={navUrl}
                                            />
                                        ))
                                    )}
                                </SkeletonLoader>
                            </div>
                            { (hasPagination && pagination?.totalItems > 4) && <StatePagination pagination={pagination}  {...queryData}/> }
                        </div>
                    </div>
                </div>
            </div>
        </section>
    )
}
export default Launch;

Launch.propTypes = {
    launches: PropTypes.array,
    navUrl: PropTypes.string.isRequired,
    pagination: PropTypes.object,
};
