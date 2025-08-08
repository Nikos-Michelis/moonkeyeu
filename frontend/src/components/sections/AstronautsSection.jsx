import React from 'react';
import AstronautCard from '../cards/AstronautCard.jsx';
import LatestNews from "../sidebars/LatestNews.jsx";
import {SkeletonLoader} from "@/components/loader/SkeletonLoader.jsx";
import SkeletonPortraitLoader from "@/components/skeleton/SkeletonPortraitLoader.jsx";
import BuyMeACoffee from "@/components/button/BuyMeACoffee.jsx";
const AstronautsSection = ({astronauts, isFetching, isLoading, isError}) => {
    const contentConfig = {
        component: SkeletonPortraitLoader,
    };
    return (
        <section className="astronauts-section">
            <div className="flex justify-center">
                <div className="grid-layout--col container container--light-overlay flex justify-center rounded-md" data-layout="grid-wrapper" data-spacing="none">
                    <div className="container">
                        <div className="grid-layout__portrait">
                            <SkeletonLoader
                                isFetching={isFetching}
                                isLoading={isLoading}
                                isError={isError}
                                contentConfig={contentConfig}>
                                 {astronauts._embedded?.astronautNormalDTOes.map(astronaut => (
                                    <AstronautCard key={astronaut?.id} {...astronaut} />
                                ))}
                            </SkeletonLoader>
                          </div>
                    </div>
                    <aside>
                        <BuyMeACoffee />
                        <LatestNews />
                    </aside>
                </div>
            </div>
        </section>
    );
};

export default AstronautsSection;
