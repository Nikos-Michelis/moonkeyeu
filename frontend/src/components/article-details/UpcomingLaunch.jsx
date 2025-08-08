import React from "react";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faStopwatch} from "@fortawesome/free-solid-svg-icons";
import LaunchCard from "@/components/cards/LaunchCard.jsx";

const UpcomingLaunch = ({launch}) => {
    const contentConfig = {
        styles: {
            wrapper: "article-card--large-wrapper",
            card_type: "landscape-card__container--article-card"
        },
    };
    return (
        <section className="upcoming-launch-section">
            <div className="article__heading-box">
                <FontAwesomeIcon icon={faStopwatch} />
                <h2>Upcoming Launch</h2>
            </div>
            <hr className="hr-100-sm bg-hr-600" />
            <div className="container" data-spacing="none" data-type="full-width">
                <div className="margin-block-8 margin-inline-4">
                    <LaunchCard
                        key={launch?.id}
                        {...(launch)}
                        navUrl={'/launches/'}
                        cardStyles={contentConfig?.styles}
                    />
                </div>
            </div>
        </section>
    );
}
export default UpcomingLaunch;