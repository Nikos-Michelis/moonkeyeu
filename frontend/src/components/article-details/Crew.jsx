import React from "react";
import AstronautLandScapeCard from "@/components/cards/AstronautLandScapeCard.jsx";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faUserAstronaut } from '@fortawesome/free-solid-svg-icons';

const Crew = ({crew}) =>{
    const contentConfig = {
        styles: {
            wrapper: "article-card--small-wrapper",
            section: "launches-articles",
            card_type: "landscape-card__container--article-card"
        },
    };
    return(
        <section className="crew-section">
            <div className="article__heading-box">
                <FontAwesomeIcon icon={faUserAstronaut} />
                <h2>Crew</h2>
            </div>
            <hr className="hr-100-sm bg-hr-600" />
            <div className="flex justify-center align-center padding-block-8">
                <div className="container"
                     data-type="full-width"
                     data-spacing="none"
                     data-scroll={crew.length > 2 ? "vertical" : undefined}
                >
                    <div className="margin-block-5 margin-inline-4">
                        <div className="grid-layout__landscape padding-block-2">
                            {crew.length > 0 &&
                                    crew.map(crew =>
                                        <AstronautLandScapeCard
                                            key={crew.id}
                                            cardStyles={contentConfig?.styles}
                                            {...crew} />
                                    )
                            }
                        </div>
                    </div>
                </div>
            </div>
        </section>
    )
}
export default Crew;