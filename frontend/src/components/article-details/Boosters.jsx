import React from "react";
import BoosterInfo from "@/components/article-info/BoosterInfo.jsx";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faBolt } from '@fortawesome/free-solid-svg-icons';

const Boosters = ({stage}) =>{
    return(
        <section className="agency-section">
            <div className="article__heading-box">
                <FontAwesomeIcon icon={faBolt} />
                <h2>Booster</h2>
            </div>
            <hr className="hr-100-sm bg-hr-600" />
            {stage.length > 0 &&
                stage.map(stage => (
                   <BoosterInfo key={stage.id} {...stage}></BoosterInfo>
                ))}
        </section>
    )
}
export default Boosters;