import React from "react";
import useNumberFormatter from "@/hooks/util/useNumberFormatter.jsx";
import Img from "@/components/utils/Img.jsx";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {faRocket, faGear, faSatellite, faShuttleSpace} from '@fortawesome/free-solid-svg-icons';

const Rocket = (
    {
        name, variant, fullname, active,
        description, alias, min_stage, max_stage, maiden_flight,
        length, diameter, launch_cost, launch_mass, leo_capacity,
        gto_capacity, geo_capacity, sso_capacity, to_thrust, images,
    }) =>{
    const checkValue = (value, metric= "") => {
        return (value ? `${value} ${metric}` : "―");
    }
    const formattedNumber = checkValue(useNumberFormatter(launch_cost));
    return(
        <section className="rocket-section">
            <div className="article__heading-box">
                <FontAwesomeIcon icon={faRocket} />
                <h2>Rocket</h2>
            </div>
            <hr className="hr-100-sm bg-hr-600" />
            <div className="container flex flex-wrap justify-center align-center padding-block-8" data-type="full-bleed" data-spacing="none">
                <div className="article__img-box margin-block-start-5">
                    <Img
                        src={images?.[0]?.image_url}
                        alt={images?.[0]?.name || "default"}
                        className="article__img"
                        defaultSrc={`${import.meta.env.VITE_CLOUDFRONT_URL}/assets/logo/moonkeyeu-logo-transparent.svg`}
                    />
                    <div className="flex flex-wrap">
                        <div className="badge--pill">Configuration: {checkValue(variant)}</div>
                        <div className="badge--pill">Maiden Flight: {checkValue(maiden_flight)}</div>
                        { active
                            ?
                            <div className="badge--pill bg-success-400">Active</div>
                            :
                            <div className="badge--pill bg-warning-200">Retired</div>}
                    </div>
                </div>
                <div className="article__info-box article__info-box--col">
                    <h3>{fullname}</h3>
                    <p>{description}</p>
                </div>
                <div className="article__rocket-info flex flex-wrap margin-block-2" data-type="full-bleed">
                    <div className="info-col container fs-small-300 text-center padding-2 margin-block-10">
                        <div>
                            <FontAwesomeIcon
                                icon={faGear}
                                className="padding-block-end-6 fs-small-800 clr-dark-cosmos-300"
                            />
                            <h1 className="heading-4">Specifications</h1>
                        </div>
                        {min_stage && max_stage &&
                            <>
                                <div className="info-box-col">
                                    <p>Min Stage</p>
                                    <span>{checkValue(min_stage)}</span>
                                </div>
                                <hr className="hr-75-sm bg-hr-600" />
                                <div className="info-box-col">
                                    <p>Max Stage</p>
                                    <span>{checkValue(max_stage)}</span>
                                </div>
                            </>}
                        <hr className="hr-75-sm bg-hr-600" />
                        <div className="info-box-col">
                            <p>Length</p>
                            <span>{checkValue(length, "m")}</span>
                        </div>
                        <hr className="hr-75-sm bg-hr-600" />
                        <div className="info-box-col">
                            <p>Diameter</p>
                            <span>{checkValue(diameter, "m")}</span>
                        </div>
                        <hr className="hr-75-sm bg-hr-600" />
                        <div className="info-box-col">
                            <p>Launch Mass</p>
                            <span>{checkValue(launch_mass, "Tons")}</span>
                        </div>
                        <hr className="hr-75-sm bg-hr-600" />
                        <div className="info-box-col">
                            <p>Thrust</p>
                            <span>{checkValue(to_thrust, "kN")}</span>
                        </div>
                        <hr className="hr-75-sm bg-hr-600" />
                    </div>
                    <div className="info-col container fs-small-200 text-center padding-2 margin-block-10">
                        <div>
                            <FontAwesomeIcon
                                icon={faShuttleSpace}
                                className="padding-block-end-6 fs-small-800 clr-dark-cosmos-300"
                            />
                            <h1 className="heading-4">Family</h1>
                        </div>
                        <div className="info-box-col">
                            <p>Name</p>
                            <span>{checkValue(name)}</span>
                        </div>
                        <hr className="hr-75-sm bg-hr-600" />
                        <div className="info-box-col">
                            <p>Variant</p>
                            <span>{checkValue(variant)}</span>
                        </div>
                        <hr className="hr-75-sm bg-hr-600" />
                        <div className="info-box-col">
                            <p>Alias</p>
                            <span>{checkValue(alias)}</span>
                        </div>
                        <hr className="hr-75-sm bg-hr-600" />
                        <div className="info-box-col">
                            <p>Full Name</p>
                            <span>{checkValue(fullname)}</span>
                        </div>
                        <hr className="hr-75-sm bg-hr-600" />
                    </div>
                    <div className="info-col container fs-small-200 text-center padding-2 margin-block-10">
                        <div>
                            <FontAwesomeIcon
                                icon={faSatellite}
                                className="padding-block-end-6 fs-small-800 clr-dark-cosmos-300"
                            />
                            <h1 className="heading-4">Payload Capacity</h1>
                        </div>
                        <div className="info-box-col">
                            <p>Launch Cost</p>
                            <span>{formattedNumber}</span>
                        </div>
                        <hr className="hr-75-sm bg-hr-600" />
                        <div className="info-box-col">
                            <p>Low Earth Orbit</p>
                            <span>{checkValue(leo_capacity, "kg")}</span>
                        </div>
                        <hr className="hr-75-sm bg-hr-600" />
                        <div className="info-box-col">
                            <p>Geostationary Transfer Orbit</p>
                            <span>{checkValue(gto_capacity, "kg")}</span>
                        </div>
                        <hr className="hr-75-sm bg-hr-600" />
                        <div className="info-box-col">
                            <p>Direct Geostationary</p>
                            <span>{checkValue(geo_capacity, "kg")}</span>
                        </div>
                        <hr className="hr-75-sm bg-hr-600" />
                        <div className="info-box-col">
                            <p>Sun-Synchronous Capacity</p>
                            <span>{checkValue(sso_capacity, "kg")}</span>
                        </div>
                        <hr className="hr-75-sm bg-hr-600" />
                    </div>
                </div>
            </div>
        </section>
    )
}
export default Rocket;