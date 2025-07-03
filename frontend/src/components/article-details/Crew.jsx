import React from "react";
import AstronautLandScapeCard from "@/components/cards/AstronautLandScapeCard.jsx";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faUserAstronaut } from '@fortawesome/free-solid-svg-icons';

const Crew = ({crew}) =>{
    return(
        <section className="crew-section">
            <div className="heading-box">
                <FontAwesomeIcon icon={faUserAstronaut} />
                <h2>Crew</h2>
            </div>
            <hr className="hr-7-sm bg-hr-600" />
            <div className="flex flex-wrap justify-center align-center padding-block-8">
                <div className="flex justify-center">
                    <div className={`container flex justify-center ${crew.length > 2 ? "scrolling" : ""}`} data-spacing="none">
                        <div className="margin-block-5 margin-inline-4">
                            <div className="landscape-grid padding-block-2">
                                {crew.length > 0 &&
                                        crew.map(crew =>
                                            <AstronautLandScapeCard key={crew.id} {...crew} />)
                                }
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    )
}
export default Crew;