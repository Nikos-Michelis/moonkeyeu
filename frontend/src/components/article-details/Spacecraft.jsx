import React from "react";
import Img from "@/components/utils/Img.jsx";
import {LinkButton} from "@/components/button/LinkButton.jsx";
import Tooltip from "@/components/tooltip/Tooltip.jsx";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {
    faShuttleSpace,
    faCircleInfo,
    faHelicopterSymbol,
    faLocationDot
} from '@fortawesome/free-solid-svg-icons';
import { faWikipediaW } from '@fortawesome/free-brands-svg-icons';

const spacecraft = ({ stage }) =>{
    const spacecraft = stage.spacecraft || {}
    const configuration = stage.spacecraft?.configuration || {}
    const landing = stage.landing || {}
    const landingZone = stage.landing?.landing_zone || {}
    const landingType = stage.landing?.landing_type || {}
    const checkValue = (value) => {
        return (value ? value : "―");
    }
    const booleanConverter = (value) => {
        return value ? value === true ? "Yes" : "No" : null;
    }
    return(
        <section className="spacecraft-section">
            <div className="article__heading-box">
                <FontAwesomeIcon icon={faShuttleSpace} />
                <h2>Spacecraft</h2>
            </div>
            <hr className="hr-100-sm bg-hr-600" />
            <div className="container flex flex-wrap justify-space-around align-center padding-block-8" data-type="full-bleed" data-spacing="none">
                <div className="article__img-box margin-block-2">
                    <Img
                        src={configuration.images?.[0]?.image_url}
                        alt={configuration.images?.[0]?.name || "default"}
                        className="article__img"
                        defaultSrc={`${import.meta.env.VITE_CLOUDFRONT_URL}/assets/logo/moonkeyeu-logo.svg`}
                    />
                </div>
                <div className="panel panel--small">
                    <h3 className="panel__title">{checkValue(configuration.name)}</h3>
                    <hr className="hr-75-sm bg-hr-600" />
                    <div className="panel__wrapper">
                        <div className="panel__container">
                            <div className="panel__detail-box fs-small-200 clr-star-300 padding-2">
                                <p className="panel__text">Serial</p>
                                <p className="panel__text">{checkValue(spacecraft.serial_number)}</p>
                            </div>
                            <div className="panel__detail-box fs-small-200 clr-star-300 padding-2">
                                <p className="panel__text">Status</p>
                                <p className="panel__text">{checkValue(spacecraft.status)}</p>
                            </div>
                        </div>
                    </div>
                    <div className="panel__wrapper">
                        <div className="panel__container">
                            <div className="panel__detail-box fs-small-200 clr-star-300 padding-2">
                                <p className="panel__text">Crew Capacity</p>
                                <p className="panel__text">{checkValue(configuration.crew_capacity)}</p>
                            </div>
                            <div className="panel__detail-box fs-small-200 clr-star-300 padding-2">
                                <p className="panel__text">flights</p>
                                <p className="panel__text">{checkValue()}</p>
                            </div>
                        </div>
                    </div>
                    <hr className="hr-75-sm bg-hr-600" />
                    <div className="panel__wrapper">
                        <div className="panel__container">
                            <div className="panel__detail-box fs-small-200 clr-star-300 padding-2">
                                <p className="panel__text">Landing Attempt</p>
                                <p className="panel__text">{booleanConverter(checkValue(landing.attempt))}</p>
                            </div>
                            <div className="panel__detail-box fs-small-200 clr-star-300 padding-2">
                                <p className="panel__text">Type</p>
                                <p className="panel__text">{checkValue(landingType.abbrev)}</p>
                            </div>
                        </div>
                    </div>
                    <div className="panel__wrapper">
                        <div className="panel__container">
                            <div className="panel__detail-box fs-small-200 clr-star-300 padding-2">
                                <p className="panel__text">Landing Success</p>
                                <p className="panel__text">{booleanConverter(checkValue(landing.success))}</p>
                            </div>
                            <div className="panel__detail-box fs-small-200 clr-star-300 padding-2">
                                <p className="panel__text">Location</p>
                                <p className="panel__text">{checkValue(landingZone.abbrev)}</p>
                            </div>
                        </div>
                    </div>
                    <hr className="hr-75-sm bg-hr-600" />
                    <div className="flex justify-center flex-wrap padding-block-start-4">
                        {configuration?.id ? (
                            <div className="article__btn-info">
                                <LinkButton className="btn btn--primary" to={`/vehicles/spacecraft/${configuration?.id}`} >
                                    <FontAwesomeIcon icon={faCircleInfo} />
                                </LinkButton>
                            </div>
                        ) : (
                            <Tooltip message="No Info Available">
                                <div className="article__btn-info">
                                    <LinkButton className="btn btn--primary">
                                        <FontAwesomeIcon icon={faCircleInfo} />
                                    </LinkButton>
                                </div>
                            </Tooltip>
                        )}
                        { configuration?.wiki_url ? (
                            <div className="article__wiki">
                                <LinkButton
                                    className="btn btn--primary"
                                    to={configuration?.wiki_url}
                                    isExternal={true}
                                >
                                    <FontAwesomeIcon icon={faWikipediaW} />
                                </LinkButton>
                            </div>
                        ) : (
                            <Tooltip message="No Wiki Available">
                                <div className="article__wiki">
                                    <LinkButton
                                        className="btn btn--primary"
                                        isExternal={true}
                                        disabled={true}
                                    >
                                        <FontAwesomeIcon icon={faWikipediaW} />
                                    </LinkButton>
                                </div>
                            </Tooltip>
                        )}
                    </div>
                </div>
            </div>
            { landing &&
                <>
                    <div className="article__info-box">
                        <FontAwesomeIcon icon={faLocationDot} />
                        <span>{stage.destination}</span>
                    </div>
                    {landing?.landing_zone &&
                        <div className="article__info-box">
                            <FontAwesomeIcon icon={faHelicopterSymbol} />
                            <span>{landing?.landing_zone?.name} - ({landing?.landing_zone?.abbrev}) - {landing?.landing_type?.name} - ({landing?.landing_type?.abbrev})</span>
                        </div>
                    }
                    <div className="article__info-box">
                        <p>{configuration.details}</p>
                    </div>
                </>
            }
        </section>
    );
}
export default spacecraft;