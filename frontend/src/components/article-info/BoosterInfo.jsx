import React from "react";
import Img from "@/components/utils/Img.jsx";

const BoosterInfo = ({launcher, type, landing}) =>{
    const checkValue = (value) => {
        return (value ? value : "―");
    }
    const booleanConverter = (value) => {
        return value ? value === true ? "Yes" : "No" : null;
    }
    return(
        <>
            <div className="container flex flex-wrap justify-space-around align-center padding-block-8" data-type="full-bleed" data-spacing="none">
                <div className="article__img-box margin-block-2">
                    <Img
                        src={launcher?.images?.[0]?.image_url}
                        alt={launcher?.images?.[0]?.name || "default"}
                        className="article__img"
                        defaultSrc={`${import.meta.env.VITE_CLOUDFRONT_URL}/assets/logo/moonkeyeu-logo-transparent.svg`}
                    />
                </div>
                <div className="panel panel--small">
                    <h3 className="panel__title">{type} - {launcher?.serial_number}</h3>
                    <hr/>
                    <div className="panel__wrapper">
                        <div className="panel__container">
                            <div className="panel__detail-box fs-small-200 clr-star-300 padding-2">
                                <p className="panel__text">Serial</p>
                                <p className="panel__text">{checkValue(launcher?.serial_number)}</p>
                            </div>
                            <div className="panel__detail-box fs-small-200 clr-star-300 padding-2">
                                <p className="panel__text">Flight Proven</p>
                                <p className="panel__text">{booleanConverter(checkValue(launcher?.flight_proven))}</p>
                            </div>
                        </div>
                    </div>
                    <div className="panel__wrapper">
                        <div className="panel__container">
                            <div className="panel__detail-box fs-small-200 clr-star-300 padding-2">
                                <p className="panel__text">Status</p>
                                <p className="panel__text">{checkValue(launcher?.status)}</p>
                            </div>
                            <div className="panel__detail-box fs-small-200 clr-star-300 padding-2">
                                <p className="panel__text">flights</p>
                                <p className="panel__text">{checkValue(launcher?.flights)}</p>
                            </div>
                        </div>
                    </div>
                    <hr/>
                    <div className="panel__wrapper">
                        <div className="panel__container">
                            <div className="panel__detail-box fs-small-200 clr-star-300 padding-2">
                                <p className="panel__text">Landing Attempt</p>
                                <p className="panel__text">{booleanConverter(checkValue(landing?.attempt))}</p>
                            </div>
                            <div className="panel__detail-box fs-small-200 clr-star-300 padding-2">
                                <p className="panel__text">Type</p>
                                <p className="panel__text">{checkValue(landing?.landing_type?.abbrev)}</p>
                            </div>
                        </div>
                    </div>
                    <div className="panel__wrapper">
                        <div className="panel__container">
                            <div className="panel__detail-box fs-small-200 clr-star-300 padding-2">
                                <p className="panel__text">Landing Success</p>
                                <p className="panel__text">{booleanConverter(checkValue(landing?.success))}</p>
                            </div>
                            <div className="panel__detail-box fs-small-200 clr-star-300 padding-2">
                                <p className="panel__text">Location</p>
                                <p className="panel__text">{checkValue(landing?.landing_zone?.abbrev)}</p>
                            </div>
                        </div>
                    </div>
                    <hr/>
                </div>
            </div>
            { landing &&
                <>
                    <div className="article__info-box">
                        <h3>{landing?.landing_type?.name} - ({landing?.landing_type?.abbrev})</h3>
                        <p>{landing?.landing_type?.description}</p>
                    </div>
                    <div className="article__info-box">
                        <h3>{landing?.landing_zone?.name} - ({landing?.landing_zone?.abbrev})</h3>
                        <p>{landing?.landing_zone?.description}</p>
                    </div>
                    <div className="article__info-box">
                        <h3>{landing?.landing_zone?.location?.name}</h3>
                        <p>{landing?.landing_zone?.location?.description}</p>
                    </div>
                </>
            }
        </>
    );
}
export default BoosterInfo;