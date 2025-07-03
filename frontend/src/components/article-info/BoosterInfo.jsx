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
            <div className="container flex flex-wrap justify-center align-center padding-block-8" data-type="full-bleed" data-spacing="none">
                <div className="img-box container flex flex-column justify-center align-center margin-block-start-5" data-type="full-bleed">
                    <Img
                        src={launcher?.images?.[0]?.image_url}
                        alt={launcher?.images?.[0]?.name || "default"}
                        className="booster-image"
                        defaultSrc={`${import.meta.env.VITE_CLOUDFRONT_URL}/assets/logo/moonkeyeu-logo-transparent.svg`}
                    />
                </div>
                <div className="panel-body small">
                    <h3 className="panel-title">{type} - {launcher?.serial_number}</h3>
                    <hr className="hr-5-sm bg-hr-600" />
                    <div className="panel-info-wrapper">
                        <div className="panel-info-container">
                            <div className="detail-wrapper fs-small-200 clr-star-300 padding-2">
                                <p className="info-panel-row">Serial</p>
                                <p className="info-panel-row">{checkValue(launcher?.serial_number)}</p>
                            </div>
                            <div className="detail-wrapper fs-small-200 clr-star-300 padding-2">
                                <p className="info-panel-row">Flight Proven</p>
                                <p className="info-panel-row">{booleanConverter(checkValue(launcher?.flight_proven))}</p>
                            </div>
                        </div>
                    </div>
                    <div className="panel-info-wrapper">
                        <div className="panel-info-container">
                            <div className="detail-wrapper fs-small-200 clr-star-300 padding-2">
                                <p className="info-panel-row">Status</p>
                                <p className="info-panel-row">{checkValue(launcher?.status)}</p>
                            </div>
                            <div className="detail-wrapper fs-small-200 clr-star-300 padding-2">
                                <p className="info-panel-row">flights</p>
                                <p className="info-panel-row">{checkValue(launcher?.flights)}</p>
                            </div>
                        </div>
                    </div>
                    <hr className="hr-5-sm bg-hr-600" />
                    <div className="panel-info-wrapper">
                        <div className="panel-info-container">
                            <div className="detail-wrapper fs-small-200 clr-star-300 padding-2">
                                <p className="info-panel-row">Landing Attempt</p>
                                <p className="info-panel-row">{booleanConverter(checkValue(landing?.attempt))}</p>
                            </div>
                            <div className="detail-wrapper fs-small-200 clr-star-300 padding-2">
                                <p className="info-panel-row">Type</p>
                                <p className="info-panel-row">{checkValue(landing?.landing_type?.abbrev)}</p>
                            </div>
                        </div>
                    </div>
                    <div className="panel-info-wrapper">
                        <div className="panel-info-container">
                            <div className="detail-wrapper fs-small-200 clr-star-300 padding-2">
                                <p className="info-panel-row">Landing Success</p>
                                <p className="info-panel-row">{booleanConverter(checkValue(landing?.success))}</p>
                            </div>
                            <div className="detail-wrapper fs-small-200 clr-star-300 padding-2">
                                <p className="info-panel-row">Location</p>
                                <p className="info-panel-row">{checkValue(landing?.landing_zone?.abbrev)}</p>
                            </div>
                        </div>
                    </div>
                    <hr className="hr-5-sm bg-hr-600" />
                </div>
            </div>
            { landing &&
                <>
                    <div className="info-box">
                        <h3>{landing?.landing_type?.name} - ({landing?.landing_type?.abbrev})</h3>
                        <p>{landing?.landing_type?.description}</p>
                    </div>
                    <div className="info-box">
                        <h3>{landing?.landing_zone?.name} - ({landing?.landing_zone?.abbrev})</h3>
                        <p>{landing?.landing_zone?.description}</p>
                    </div>
                    <div className="info-box">
                        <h3>{landing?.landing_zone?.location?.name}</h3>
                        <p>{landing?.landing_zone?.location?.description}</p>
                    </div>
                </>
            }
        </>
    );
}
export default BoosterInfo;