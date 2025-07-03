import React from "react";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faRocket} from "@fortawesome/free-solid-svg-icons";
import LaunchCard from "@/components/cards/LaunchCard.jsx";

const UpcomingLaunch = ({launch}) => {
    return (
        <section className="upcoming-launch-section">
            <div className="heading-box">
                <FontAwesomeIcon icon={faRocket} />
                <h2>Upcoming Launch</h2>
            </div>
            <hr className="hr-7-sm bg-hr-600" />
            <div className="flex flex-wrap justify-center align-center padding-block-8">
                <div className="flex justify-center">
                    <LaunchCard
                        key={launch?.id}
                        {...(launch)}
                        navUrl={'/launches/'}
                    />
                </div>
            </div>
        </section>
    );
}
export default UpcomingLaunch;