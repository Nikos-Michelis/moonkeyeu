import React from 'react';
import {LinkButton} from "@/components/button/LinkButton.jsx";
import Tooltip from "@/components/tooltip/Tooltip.jsx";
import {Button} from "@/components/button/Button.jsx";
import useClipboard from "@/hooks/util/useClipboard.jsx";
import Img from "@/components/utils/Img.jsx";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faShareFromSquare, faShuttleSpace} from "@fortawesome/free-solid-svg-icons";

const RocketCard = ({id, details, flight_proven, serial_number, successful_landings, attempted_landings, flights, last_launch_date, first_launch_date, status, images}) => {
    const tooltipInfoMessage = id ? "" : "No Info Available";
    const { copied, copyToClipboard } = useClipboard();

    const handleShare = (copiedUrl) => {
        const url = `${window.location.origin}/${copiedUrl}` || `${window.location.origin}/${window.location.pathname}/${id}`;
        copyToClipboard(url);
    };
    const checkValue = (value) => {
        return (value ? value : "―");
    }
    const booleanConverter = (value) => {
        return value ? value === true ? "Yes" : "No" : null;
    }
    return (
        <article className="landscape-card-outer-wrapper flex justify-center medium-wrapper">
            <div className="landscape-card-wrapper flex justify-center">
                <div className="card-info-container flex flex-column justify-center align-center">
                    <div className="card-img-box">
                        <Img
                            src={images?.[0]?.image_url}
                            alt={images?.[0]?.name || "default"}
                            className="card-img small-img"
                            defaultSrc={`${import.meta.env.VITE_CLOUDFRONT_URL}/assets/logo/moonkeyeu-logo.svg`}
                        />
                    </div>
                    <section className="card-info-section flex flex-column justify-space-evenly">
                        <div className="card-detail-container">
                            <div className="panel-body">
                                <h4 className="booster-name">{checkValue(serial_number)}</h4>
                                <hr className="hr-7-sm bg-hr-600"/>
                                <div className="panel-info-wrapper">
                                    <div className="panel-info-container">
                                        <div className="detail-wrapper fs-small-100 padding-1">
                                            <p className="info-panel-row">Status</p>
                                            <p className="info-panel-row">{checkValue(status)}</p>
                                        </div>
                                        <div className="detail-wrapper fs-small-100 padding-1">
                                            <p className="info-panel-row"></p>
                                            <p className="info-panel-row"></p>
                                        </div>
                                    </div>
                                    <div className="panel-info-container">
                                        <div className="detail-wrapper fs-small-100 padding-1">
                                            <p className="info-panel-row">flights</p>
                                            <p className="info-panel-row">{checkValue(flights)}</p>
                                        </div>
                                        <div className="detail-wrapper fs-small-100 padding-1">
                                            <p className="info-panel-row">Flight Proven</p>
                                            <p className="info-panel-row">{checkValue(booleanConverter(flight_proven))}</p>
                                        </div>
                                    </div>
                                    <div className="panel-info-container">
                                        <div className="detail-wrapper fs-small-100 padding-1">
                                            <p className="info-panel-row">Successful Landings</p>
                                            <p className="info-panel-row">{checkValue(successful_landings)}</p>
                                        </div>
                                        <div className="detail-wrapper fs-small-100 padding-1">
                                            <p className="info-panel-row">Attempted Landings</p>
                                            <p className="info-panel-row">{checkValue(attempted_landings)}</p>
                                        </div>
                                    </div>
                                    <hr className="hr-7-sm bg-hr-600"/>
                                    <div className="panel-info-container">
                                        <div className="detail-wrapper fs-small-100">
                                            <p className="description-panel padding-block-2">{details}</p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div className="flex flex-wrap justify-center padding-block-2">
                            {id ? (
                                <div className="info">
                                    <LinkButton className="btn btn-primary" to={`/launches?page=1&limit=12&launcher=${id.toString()}&upcoming=all`} >
                                        <FontAwesomeIcon icon={faShuttleSpace} /> View Launch
                                    </LinkButton>
                                </div>
                            ) : (
                                <Tooltip message={tooltipInfoMessage}>
                                    <div className="info">
                                        <LinkButton className="btn btn-primary" >
                                            <FontAwesomeIcon icon={faShuttleSpace} /> View Launch
                                        </LinkButton>
                                    </div>
                                </Tooltip>
                            )}
                            <div className="share">
                                <Tooltip  message={copied ? "Copied!" : "Copied to clipboard!"}>
                                    <Button className="btn btn-primary" onClick={() => handleShare(`launches?page=1&limit=12&launcher=${id.toString()}&upcoming=all`)} disabled={copied}>
                                        <FontAwesomeIcon icon={faShareFromSquare} /> SHARE
                                    </Button>
                                </Tooltip>
                            </div>
                        </div>
                    </section>
                </div>
            </div>
        </article>
    );
};

export default RocketCard;
