import React from "react";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faBuilding } from '@fortawesome/free-solid-svg-icons';
import AgencyCard from "@/components/cards/AgencyCard.jsx";

const Agencies = ({ agencies }) =>{
    return(
        <section className="agency-section">
            <div className="heading-box">
                <FontAwesomeIcon icon={faBuilding} />
                <h2>Related Agencies</h2>
            </div>
            <hr className="hr-7-sm bg-hr-600" />
            <div className="flex flex-wrap justify-center align-center padding-block-8">
                <div className="flex justify-center">
                    <div className={`container flex justify-center ${agencies.length > 2 ? "scrolling" : ""}`} data-spacing="none">
                        <div className="margin-block-5 margin-inline-4">
                            <div className="landscape-grid padding-block-2">
                                {agencies?.length > 0 && (
                                    agencies.map((agency) => (
                                        <AgencyCard
                                            key={agency.id}
                                            {...agency}
                                        />
                                    ))
                                )}
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    )
}
export default Agencies;