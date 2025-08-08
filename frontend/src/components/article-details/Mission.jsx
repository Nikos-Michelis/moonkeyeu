import React from "react";
import useNumberFormatter from "@/hooks/util/useNumberFormatter.jsx";
import useComparator from "@/hooks/util/useComparator.jsx";
import Img from "@/components/utils/Img.jsx";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {faTimeline} from '@fortawesome/free-solid-svg-icons';

const Mission = ({mission, launchCost, missionPatches}) => {
    const formattedNumber = useNumberFormatter(Number(launchCost));
    const missionPatch = useComparator(missionPatches, (a, b) => a.priority > b.priority);
    return(
        <section className="mission-section">
            <div className="article__heading-box">
                <FontAwesomeIcon icon={faTimeline} />
                <h2>Mission</h2>
            </div>
            <hr className="hr-100-sm bg-hr-600" />
            <div className="container flex flex-wrap justify-center align-center padding-block-8" data-type="full-bleed" data-spacing="none">
                <div className="article__info-box article__info-box--col">
                    <h3>{mission.name}</h3>
                    <ul className="article__list">
                        <li><span>Type: </span>{mission.type}</li>
                        <li><span>Orbit: </span>{mission.orbit}</li>
                        <li><span>Launch Cost: </span>{launchCost > 0 ? formattedNumber : "Unknown"}</li>
                    </ul>
                    <p>{mission.description}</p>
               </div>
                <div className="article__img-box margin-block-start-5">
                   <Img
                       src={missionPatch?.image_url}
                       alt={missionPatch?.name || "default"}
                       className="article__img article__img--small"
                       defaultSrc={`${import.meta.env.VITE_CLOUDFRONT_URL}/assets/logo/moonkeyeu-logo-transparent.svg`}
                   />
               </div>
           </div>
        </section>
    );
}
export default Mission;