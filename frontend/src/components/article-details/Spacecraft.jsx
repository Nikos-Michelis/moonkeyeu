import React from "react";
import Img from "@/components/utils/Img.jsx";
import {LinkButton} from "@/components/button/LinkButton.jsx";
import Tooltip from "@/components/tooltip/Tooltip.jsx";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {
    faShuttleSpace,
    faCircleInfo,
    faSatellite,
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
            <div className="heading-box">
                <FontAwesomeIcon icon={faShuttleSpace} />
                <h2>Spacecraft</h2>
            </div>
            <hr className="hr-7-sm bg-hr-600" />
            <div className="container flex flex-wrap justify-center align-center padding-block-8" data-type="full-bleed" data-spacing="none">
                <div className="img-box container flex flex-column justify-center align-center margin-block-start-5" data-type="full-bleed">
                    <Img
                        src={configuration.images?.[0]?.image_url}
                        alt={configuration.images?.[0]?.name || "default"}
                        className="article-img"
                        defaultSrc={`${import.meta.env.VITE_CLOUDFRONT_URL}/assets/logo/moonkeyeu-logo.svg`}
                    />
                </div>
                <div className="panel-body small">
                    <h3 className="panel-title">{checkValue(configuration.name)}</h3>
                    <hr className="hr-5-sm bg-hr-600" />
                    <div className="panel-info-wrapper">
                        <div className="panel-info-container">
                            <div className="detail-wrapper fs-small-200 clr-star-300 padding-2">
                                <p className="info-panel-row">Serial</p>
                                <p className="info-panel-row">{checkValue(spacecraft.serial_number)}</p>
                            </div>
                            <div className="detail-wrapper fs-small-200 clr-star-300 padding-2">
                                <p className="info-panel-row">Status</p>
                                <p className="info-panel-row">{checkValue(spacecraft.status)}</p>
                            </div>
                        </div>
                    </div>
                    <div className="panel-info-wrapper">
                        <div className="panel-info-container">
                            <div className="detail-wrapper fs-small-200 clr-star-300 padding-2">
                                <p className="info-panel-row">Crew Capacity</p>
                                <p className="info-panel-row">{checkValue(configuration.crew_capacity)}</p>
                            </div>
                            <div className="detail-wrapper fs-small-200 clr-star-300 padding-2">
                                <p className="info-panel-row">flights</p>
                                <p className="info-panel-row">{checkValue()}</p>
                            </div>
                        </div>
                    </div>
                    <hr className="hr-5-sm bg-hr-600" />
                    <div className="panel-info-wrapper">
                        <div className="panel-info-container">
                            <div className="detail-wrapper fs-small-200 clr-star-300 padding-2">
                                <p className="info-panel-row">Landing Attempt</p>
                                <p className="info-panel-row">{booleanConverter(checkValue(landing.attempt))}</p>
                            </div>
                            <div className="detail-wrapper fs-small-200 clr-star-300 padding-2">
                                <p className="info-panel-row">Type</p>
                                <p className="info-panel-row">{checkValue(landingType.abbrev)}</p>
                            </div>
                        </div>
                    </div>
                    <div className="panel-info-wrapper">
                        <div className="panel-info-container">
                            <div className="detail-wrapper fs-small-200 clr-star-300 padding-2">
                                <p className="info-panel-row">Landing Success</p>
                                <p className="info-panel-row">{booleanConverter(checkValue(landing.success))}</p>
                            </div>
                            <div className="detail-wrapper fs-small-200 clr-star-300 padding-2">
                                <p className="info-panel-row">Location</p>
                                <p className="info-panel-row">{checkValue(landingZone.abbrev)}</p>
                            </div>
                        </div>
                    </div>
                    <hr className="hr-5-sm bg-hr-600" />
                    <div className="flex justify-center flex-wrap padding-block-start-4">
                        {configuration?.id ? (
                            <div className="info">
                                <LinkButton className="btn btn-primary" to={`/vehicles/spacecraft/${configuration?.id}`} >
                                    <FontAwesomeIcon icon={faCircleInfo} />
                                </LinkButton>
                            </div>
                        ) : (
                            <Tooltip message="No Info Available">
                                <div className="info">
                                    <LinkButton className="btn btn-primary">
                                        <FontAwesomeIcon icon={faCircleInfo} />
                                    </LinkButton>
                                </div>
                            </Tooltip>
                        )}
                        { configuration?.wiki_url ? (
                            <div className="wiki">
                                <LinkButton
                                    className="btn btn-primary"
                                    to={configuration?.wiki_url}
                                    isExternal={true}
                                >
                                    <FontAwesomeIcon icon={faWikipediaW} />
                                </LinkButton>
                            </div>
                        ) : (
                            <Tooltip message="No Wiki Available">
                                <div className="wiki">
                                    <LinkButton
                                        className="btn btn-primary"
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
                    <div className="info-box">
                        <FontAwesomeIcon icon={faLocationDot} />
                        <span>{stage.destination}</span>
                    </div>
                    {landing?.landing_zone &&
                        <div className="info-box">
                            <FontAwesomeIcon icon={faHelicopterSymbol} />
                            <span>{landing?.landing_zone?.name} - ({landing?.landing_zone?.abbrev}) - {landing?.landing_type?.name} - ({landing?.landing_type?.abbrev})</span>
                        </div>
                    }
                    <div className="info-box">
                        <p>{configuration.details}</p>
                    </div>
                </>}
        </section>
    );
}
export default spacecraft;