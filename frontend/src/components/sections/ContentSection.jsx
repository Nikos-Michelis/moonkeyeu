import React from "react";
import LatestNews from "../sidebars/LatestNews.jsx";
import { SkeletonLoader } from "@/components/loader/SkeletonLoader.jsx";
import {Button} from "@/components/button/Button.jsx";
import BuyMeACoffee from "@/components/button/BuyMeACoffee.jsx";
import PreviousBtn from "@/components/button/PreviousBtn.jsx";
import StarshipCard from "@/components/cards/StarshipCard.jsx"
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faChevronLeft, faFilter} from '@fortawesome/free-solid-svg-icons';
const ContentSection = (
    {
        items,
        isFetching,
        isPending,
        isError,
        contentConfig,
        CardComponent,
        itemKeyExtractor,
        isBookmarked,
        emptyList= {
            heading: "No Results Match Current Settings!",
            message: "Check your filtering settings using the above",
            icon: <FontAwesomeIcon icon={faFilter} />
        },
        options = {
            showPrevBtn: false,
            showBackBtn: false,
            showItemsLimit: false,
            maxItems: 20
        },
        navUrl,
        isDetailed

    }) => {

    return (
        <section className={contentConfig?.styles?.section}>
            <div className="flex justify-center">
                <div className={`container __wrapper ${contentConfig?.styles?.bottomGap || ""} flex flex-wrap justify-center rounded-md overlay box-shadow-light`} data-spacing="none">
                    <div className="container __landscape padding-block-5 padding-inline-4" data-type="fixed-inherit" data-overflow="visible">
                        { options?.showPrevBtn && <div className="flex"><PreviousBtn/></div>}
                        { (options?.showBackBtn || options?.showItemsLimit) &&
                            <div className="flex justify-space-between margin-block-end-4">
                                { options?.showBackBtn &&
                                    (
                                        <Button className="btn-transparent margin-block-2" onClick={() => window.history.back()}>
                                            <FontAwesomeIcon icon={faChevronLeft} /> Back
                                        </Button>
                                    )
                                }
                                { options?.showItemsLimit &&
                                    (
                                        <div className="fs-big-300 fw-bold clr-star-300">
                                            <span>{items.length >= 0 ? items.length : 0}</span>
                                            <span> / </span>
                                            <span>{options?.maxItems}</span>
                                        </div>
                                    )
                                }
                             </div>
                        }
                        <div className={`landscape-grid ${contentConfig?.styles?.grid || ''}`}>
                           <SkeletonLoader
                                isPending={isPending}
                                isFetching={isFetching}
                                isError={isError}
                                contentConfig={contentConfig}
                           >
                                {items.length > 0 ? (
                                    items.map((item) => (
                                        <CardComponent
                                            key={itemKeyExtractor(item)}
                                            {...item}
                                            isBookmarked={isBookmarked}
                                            navUrl={navUrl}
                                            isDetailed={isDetailed}
                                        />
                                    ))
                                ) : (
                                    <div>
                                        <h2>{emptyList.heading}</h2>
                                        <p>{emptyList.message} {emptyList.icon}</p>
                                    </div>
                                )}
                           </SkeletonLoader>
                        </div>
                    </div>
                    <aside className="container">
                        <BuyMeACoffee />
                        <StarshipCard/>
                        <LatestNews />
                    </aside>
                </div>
            </div>
        </section>
    );
};

export default ContentSection;
