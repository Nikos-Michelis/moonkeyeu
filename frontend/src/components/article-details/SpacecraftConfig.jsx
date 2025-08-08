import React from "react";
import SpacecraftCard from "@/components/cards/SpacecraftCard.jsx";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faGear } from '@fortawesome/free-solid-svg-icons';

const SpacecraftConfig = ({ spacecraftConfigs }) =>{
    const contentConfig = {
        styles: {
            wrapper: "article-card--medium-wrapper",
            card_type: "landscape-card__container--article-card"
        },
    };
    return(
        <section className="spacecrfat-section">
            <div className="article__heading-box">
                <FontAwesomeIcon icon={faGear} />
                <h2>Spacecraft Configs</h2>
            </div>
            <hr className="hr-100-sm bg-hr-600" />
            <div className="flex justify-center align-center padding-block-8">
                <div className="container"
                     data-spacing="none"
                     data-type="full-width"
                     data-scroll={spacecraftConfigs.length> 2  ? "vertical" : undefined}>
                    <div className="margin-block-5 margin-inline-4">
                        <div className="grid-layout__landscape padding-block-2">
                            {spacecraftConfigs?.length > 0 && (
                                spacecraftConfigs.map((config) => (
                                    <SpacecraftCard
                                        showPanel={true}
                                        url={`/launches?page=1&limit=12&spacecraftConfig=${config?.id.toString()}&upcoming=all`}
                                        key={config.id}
                                        {...config}
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
export default SpacecraftConfig;