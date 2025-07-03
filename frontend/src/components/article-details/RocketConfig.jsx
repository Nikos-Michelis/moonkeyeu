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
            wrapper: "medium-wrapper",
            img: "small-img",
            section: "rockets-articles",
        },
    };

    return(
        <section className="agency-section">
            <div className="heading-box">
                <FontAwesomeIcon icon={faGear} />
                <h2>Rocket Configs</h2>
            </div>
            <hr className="hr-7-sm bg-hr-600" />
            <div className="container flex flex-wrap justify-center align-center padding-block-8" data-type="full-bleed" data-spacing="none">
                <div className="flex justify-center">
                    <div className={`container flex justify-center `} data-spacing="none">
                        <div className="margin-block-5 margin-inline-4">
                            { pagination?.totalItems > 4 && <StatePagination pagination={pagination}/> }
                                <div className="landscape-grid padding-block-2 margin-block-4">
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
                                                    />
                                                ))
                                            )}
                                    </SkeletonLoader>
                                </div>
                            { pagination?.totalItems> 4 && <StatePagination pagination={pagination}/> }
                        </div>
                    </div>
                </div>
            </div>
        </section>
    )
}
export default RocketConfig;