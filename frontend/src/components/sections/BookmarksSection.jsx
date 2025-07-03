import React from 'react';
import BookmarkCard from "@/components/cards/BookmarkCard.jsx";
import SkeletonBookMarkLoader from "@/components/skeleton/SkeletonBookMarkLoader.jsx";
import {useAuth} from "@/context/AuthProvider.jsx";
import {SkeletonLoader} from "@/components/loader/SkeletonLoader.jsx";
import BuyMeACoffee from "@/components/button/BuyMeACoffee.jsx";
import LatestNews from "@/components/sidebars/LatestNews.jsx";
import {Button} from "@/components/button/Button.jsx";
import { faChevronLeft } from '@fortawesome/free-solid-svg-icons';
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";

const BookmarksSection = ({ bookmarks, isPending, isFetching, isError }) =>{
    const { status } = useAuth();
    const items = bookmarks || [];
    const contentConfig = {
        component: SkeletonBookMarkLoader,
        styles: {
            wrapper: "small-wrapper",
            section: "bookmark-section",
            grid: "__bookmark-grid",
            bottomGap:"bottom-gap"
        },
    };

    const emptyList= {
        heading: "You haven't added any bookmarks yet!",
        message: "",
        icon: ""
    }

    const options = {
        showBackBtn: true,
        showItemsLimit: true,
        maxItems: 20
    }

    return(
        <section className="bookmarks-section">
            <div className="flex justify-center margin-block-end-15">
                <div className="container __wrapper __portrait flex justify-center overlay rounded-md" data-type="fixed-inherit" data-spacing="none">
                    <div className="container">
                        <div className="flex justify-space-between margin-block-start-4 margin-inline-4">
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
                        {bookmarks.length > 0 ?
                            <div className="portrait-grid">
                                <SkeletonLoader
                                    isFetching={isFetching}
                                    isPending={isPending || status.isPending}
                                    isError={isError}
                                    contentConfig={contentConfig}>
                                    {bookmarks.map(bookmark => (
                                            <BookmarkCard
                                                key={bookmark?.id}
                                                {...bookmark}
                                            />
                                        ))}
                                </SkeletonLoader>
                            </div>
                        :
                            <div className="padding-8 text-center">
                                <h2>{emptyList.heading}</h2>
                                <p>{emptyList.message} {emptyList.icon}</p>
                            </div>
                        }
                    </div>
                    <aside>
                        <BuyMeACoffee />
                        <LatestNews />
                    </aside>
                </div>
            </div>
        </section>
    );
}
export default BookmarksSection;