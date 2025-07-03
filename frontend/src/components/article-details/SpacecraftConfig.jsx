import React from "react";
import RocketCard from "@/components/cards/RocketCard.jsx";
import SpacecraftCard from "@/components/cards/SpacecraftCard.jsx";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faGear } from '@fortawesome/free-solid-svg-icons';

const SpacecraftConfig = ({ spacecraftConfigs }) =>{
    return(
        <section className="agency-section">
            <div className="heading-box">
                <FontAwesomeIcon icon={faGear} />
                <h2>Spacecraft Configs</h2>
            </div>
            <hr className="hr-7-sm bg-hr-600" />
            <div className="container __info flex flex-wrap justify-center align-center padding-block-8" data-type="full-bleed" data-spacing="none">
                <div className="flex justify-center">
                    <div className={`container flex justify-center ${spacecraftConfigs.length > 2 ? "scrolling" : ""}`} data-spacing="none">
                        <div className="margin-block-5 margin-inline-4">
                            <div className="landscape-grid padding-block-2">
                                {spacecraftConfigs?.length > 0 && (
                                    spacecraftConfigs.map((config) => (
                                        <SpacecraftCard
                                            showPanel={true}
                                            url={`/launches?page=1&limit=12&spacecraftConfig=${config?.id.toString()}&upcoming=all`}
                                            key={config.id}
                                            {...config}
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
export default SpacecraftConfig;