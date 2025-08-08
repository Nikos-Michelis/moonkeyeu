import React from "react";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faBuilding } from '@fortawesome/free-solid-svg-icons';
import AgencyCard from "@/components/cards/AgencyCard.jsx";

const Agencies = ({ agencies }) =>{
    const contentConfig = {
        styles: {
            wrapper: "article-card--small-wrapper",
            section: "launches-articles",
            card_type: "landscape-card__container--article-card"
        },
    };
    return(
        <section className="agency-section">
            <div className="article__heading-box">
                <FontAwesomeIcon icon={faBuilding} />
                <h2>Related Agencies</h2>
            </div>
            <hr className="hr-100-sm bg-hr-600" />
            <div className="flex justify-center align-center padding-block-8">
                <div className="container"
                     data-type="full-width"
                     data-spacing="none"
                     data-scroll={agencies.length > 2 ? "vertical" : undefined}
                >
                    <div className="margin-block-5 margin-inline-4">
                        <div className="grid-layout__landscape padding-block-2">
                            {agencies?.length > 0 && (
                                agencies.map((agency) => (
                                    <AgencyCard
                                        key={agency.id}
                                        {...agency}
                                        cardStyles={contentConfig?.styles}
                                    />
                                ))
                            )}
                        </div>
                    </div>
                </div>
            </div>
        </section>
    )
}
export default Agencies;