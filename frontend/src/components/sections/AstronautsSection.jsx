import React from 'react';
import AstronautCard from '../cards/AstronautCard.jsx';
import LatestNews from "../sidebars/LatestNews.jsx";
import {SkeletonLoader} from "@/components/loader/SkeletonLoader.jsx";
import SkeletonPortraitLoader from "@/components/skeleton/SkeletonPortraitLoader.jsx";
import BuyMeACoffee from "@/components/button/BuyMeACoffee.jsx";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faFilter} from "@fortawesome/free-solid-svg-icons";
const AstronautsSection = ({astronauts, isPending, isFetching, isError}) => {
    const contentConfig = {
        component: SkeletonPortraitLoader,
    };
    const items = astronauts._embedded?.astronautNormalDTOes || [];
    const emptyList= {
        heading: "No Results Match Current Settings!",
        message: "Check your filtering settings using the above",
        icon: faFilter
    }
    return (
        <section className="astronauts-section">
            <div className="flex justify-center">
                <div className="grid-layout--col container container--light-overlay flex justify-center rounded-md" data-layout="grid-wrapper" data-spacing="none">
                    <div className="container">
                        <div className={`${items.length > 0 || (isFetching || isPending) ? "grid-layout__portrait" : ""}`}>
                            <SkeletonLoader
                                isFetching={isFetching}
                                isLoading={isPending}
                                isError={isError}
                                contentConfig={contentConfig}>
                                 {items.length > 0 ? (
                                     items.map(astronaut => (
                                        <AstronautCard key={astronaut?.id} {...astronaut} />
                                     ))
                                 ) : (
                                     <div className="padding-8 text-center">
                                         <h2>{emptyList.heading}</h2>
                                         <p>{emptyList.message} <FontAwesomeIcon icon={emptyList.icon}/></p>
                                     </div>
                                 )}
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
