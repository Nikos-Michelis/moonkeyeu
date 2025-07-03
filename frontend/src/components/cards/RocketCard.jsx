import React from 'react';
import {LinkButton} from "@/components/button/LinkButton.jsx";
import Tooltip from "@/components/tooltip/Tooltip.jsx";
import Img from "@/components/utils/Img.jsx";
import useClipboard from "@/hooks/util/useClipboard.jsx";
import {Button} from "@/components/button/Button.jsx";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faShareFromSquare, faShuttleSpace} from "@fortawesome/free-solid-svg-icons";

const RocketCard = (
    {
        id,
        fullname,
        active,
        reusable,
        length,
        diameter,
        max_stage,
        launch_mass,
        leo_capacity,
        gto_capacity,
        to_thrust,
        total_launches,
        maiden_flight,
        image
    }) => {
    const tooltipInfoMessage = id ? "" : "No Info Available";
    const { copied, copyToClipboard } = useClipboard();
    const handleShare = (copiedUrl) => {
        const url = `${window.location.origin}/${copiedUrl}` || `${window.location.origin}/${window.location.pathname}/${id}`;
        copyToClipboard(url);
    };

    const checkValue = (value, metric= "") => {
        return (value ? `${value} ${metric}` : "―");
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
                            src={image?.image_url}
                            alt={image?.name || "default"}
                            className="card-img"
                            defaultSrc={`${import.meta.env.VITE_CLOUDFRONT_URL}/assets/logo/moonkeyeu-logo.svg`}
                        />
                    </div>
                    <section className="card-info-section flex flex-column justify-space-evenly">
                        <div className="card-detail-container">
                            <div className="panel-body">
                                <h4 className="rocket-name">{fullname}</h4>
                                <hr className="hr-7-sm bg-hr-600"/>
                                <div className="panel-info-wrapper">
                                    <div className="panel-info-container">
                                        <div className="detail-wrapper fs-small-100 padding-1">
                                            <p className="info-panel-row">Active</p>
                                            <p className="info-panel-row">{checkValue(booleanConverter(active))}</p>
                                        </div>
                                        <div className="detail-wrapper fs-small-100 padding-1">
                                            <p className="info-panel-row">Reusable</p>
                                            <p className="info-panel-row">{checkValue(booleanConverter(reusable))}</p>
                                        </div>
                                    </div>
                                    <div className="panel-info-container">
                                        <div className="detail-wrapper fs-small-100 padding-1">
                                            <p className="info-panel-row">Height</p>
                                            <p className="info-panel-row">{checkValue(length, "Meters")}</p>
                                        </div>
                                        <div className="detail-wrapper fs-small-100 padding-1">
                                            <p className="info-panel-row">Diameter</p>
                                            <p className="info-panel-row">{checkValue(diameter, "Meters")}</p>
                                        </div>
                                    </div>
                                    <div className="panel-info-container">
                                        <div className="detail-wrapper fs-small-100 padding-1">
                                            <p className="info-panel-row">Max Stages</p>
                                            <p className="info-panel-row">{checkValue(max_stage)}</p>
                                        </div>
                                        <div className="detail-wrapper fs-small-100 padding-1">
                                            <p className="info-panel-row"></p>
                                            <p className="info-panel-row"></p>
                                        </div>
                                    </div>
                                    <hr className="hr-7-sm bg-hr-600"/>
                                    <div className="panel-info-container">
                                        <div className="detail-wrapper fs-small-100 padding-1">
                                            <p className="info-panel-row">Liftoff Thrust</p>
                                            <p className="info-panel-row">{checkValue(to_thrust, "Tonnes")}</p>
                                        </div>
                                        <div className="detail-wrapper fs-small-100 padding-1">
                                            <p className="info-panel-row">Mass To LEO</p>
                                            <p className="info-panel-row">{checkValue(leo_capacity, "kg")}</p>
                                        </div>
                                    </div>
                                     <div className="panel-info-container">
                                         <div className="detail-wrapper fs-small-100 padding-1">
                                             <p className="info-panel-row">Liftoff Mass</p>
                                             <p className="info-panel-row">{checkValue(launch_mass, "Tonnes")}</p>
                                         </div>
                                        <div className="detail-wrapper fs-small-100 padding-1">
                                            <p className="info-panel-row">Mass To GTO</p>
                                            <p className="info-panel-row">{checkValue(gto_capacity, "kg")}</p>
                                        </div>
                                    </div>
                                    <div className="panel-info-container">
                                        <div className="detail-wrapper fs-small-100 padding-1">
                                            <p className="info-panel-row">Maiden Flight</p>
                                            <p className="info-panel-row">{checkValue(maiden_flight)}</p>
                                        </div>
                                        <div className="detail-wrapper fs-small-100 padding-1">
                                            <p className="info-panel-row">Total Launches</p>
                                            <p className="info-panel-row">{checkValue(total_launches)}</p>
                                        </div>
                                    </div>
                                </div>
                                <hr className="hr-7-sm bg-hr-600"/>
                            </div>
                        </div>
                        <div className="flex flex-wrap justify-center padding-block-2">
                            {id ? (
                                <div className="info">
                                    <LinkButton className="btn btn-primary" to={`/launches?page=1&limit=12&rocketConfig=${id.toString()}&upcoming=all`}>
                                        <FontAwesomeIcon icon={faShuttleSpace} /> View Launch
                                    </LinkButton>
                                </div>
                            ) : (
                                <Tooltip message={tooltipInfoMessage}>
                                    <div className="info">
                                        <LinkButton className="btn btn-primary">
                                            <FontAwesomeIcon icon={faShuttleSpace} /> View Launch
                                        </LinkButton>
                                    </div>
                                </Tooltip>
                            )}
                            <div className="share">
                                <Tooltip  message={copied ? "Copied!" : "Copied to clipboard!"}>
                                    <Button className="btn btn-primary" onClick={() => handleShare(`launches?page=1&limit=12&rocketConfig=${id.toString()}&upcoming=all`)} disabled={copied}>
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
