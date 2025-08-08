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
        image,
        cardStyles
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
        <article className={`landscape-card flex justify-center ${cardStyles?.wrapper || 'medium-wrapper'}`}>
            <div className={`landscape-card__container ${cardStyles?.card_type || ''}`}>
                <div className="landscape-card__media">
                    <Img
                        src={image?.image_url}
                        alt={image?.name || "default"}
                        className="landscape-card__image"
                        defaultSrc={`${import.meta.env.VITE_CLOUDFRONT_URL}/assets/logo/moonkeyeu-logo.svg`}
                    />
                </div>
                <section className="landscape-card__content flex flex-column justify-space-evenly">
                    <div className="panel">
                        <h4 className="panel__title">{fullname}</h4>
                        <hr/>
                        <div className="panel__wrapper">
                            <div className="panel__container">
                                <div className="panel__detail-box fs-small-100 padding-1">
                                    <p className="panel__text">Active</p>
                                    <p className="panel__text">{checkValue(booleanConverter(active))}</p>
                                </div>
                                <div className="panel__detail-box fs-small-100 padding-1">
                                    <p className="panel__text">Reusable</p>
                                    <p className="panel__text">{checkValue(booleanConverter(reusable))}</p>
                                </div>
                            </div>
                            <div className="panel__container">
                                <div className="panel__detail-box fs-small-100 padding-1">
                                    <p className="panel__text">Height</p>
                                    <p className="panel__text">{checkValue(length, "Meters")}</p>
                                </div>
                                <div className="panel__detail-box fs-small-100 padding-1">
                                    <p className="panel__text">Diameter</p>
                                    <p className="panel__text">{checkValue(diameter, "Meters")}</p>
                                </div>
                            </div>
                            <div className="panel__container">
                                <div className="panel__detail-box fs-small-100 padding-1">
                                    <p className="panel__text">Max Stages</p>
                                    <p className="panel__text">{checkValue(max_stage)}</p>
                                </div>
                                <div className="panel__detail-box fs-small-100 padding-1">
                                    <p className="panel__text"></p>
                                    <p className="panel__text"></p>
                                </div>
                            </div>
                            <hr/>
                            <div className="panel__container">
                                <div className="panel__detail-box fs-small-100 padding-1">
                                    <p className="panel__text">Liftoff Thrust</p>
                                    <p className="panel__text">{checkValue(to_thrust, "Tonnes")}</p>
                                </div>
                                <div className="panel__detail-box fs-small-100 padding-1">
                                    <p className="panel__text">Mass To LEO</p>
                                    <p className="panel__text">{checkValue(leo_capacity, "kg")}</p>
                                </div>
                            </div>
                             <div className="panel__container">
                                 <div className="panel__detail-box fs-small-100 padding-1">
                                     <p className="panel__text">Liftoff Mass</p>
                                     <p className="panel__text">{checkValue(launch_mass, "Tonnes")}</p>
                                 </div>
                                <div className="panel__detail-box fs-small-100 padding-1">
                                    <p className="panel__text">Mass To GTO</p>
                                    <p className="panel__text">{checkValue(gto_capacity, "kg")}</p>
                                </div>
                            </div>
                            <div className="panel__container">
                                <div className="panel__detail-box fs-small-100 padding-1">
                                    <p className="panel__text">Maiden Flight</p>
                                    <p className="panel__text">{checkValue(maiden_flight)}</p>
                                </div>
                                <div className="panel__detail-box fs-small-100 padding-1">
                                    <p className="panel__text">Total Launches</p>
                                    <p className="panel__text">{checkValue(total_launches)}</p>
                                </div>
                            </div>
                        </div>
                        <hr/>
                    </div>
                    <div className="landscape-card__actions flex flex-wrap justify-center padding-block-2">
                        {id ? (
                            <div className="landscape-card__info">
                                <LinkButton className="btn btn--primary" to={`/launches?page=1&limit=12&rocketConfig=${id.toString()}&upcoming=all`}>
                                    <FontAwesomeIcon icon={faShuttleSpace} /> View Launch
                                </LinkButton>
                            </div>
                        ) : (
                            <Tooltip message={tooltipInfoMessage}>
                                <div className="landscape-card__info">
                                    <LinkButton className="btn btn--primary">
                                        <FontAwesomeIcon icon={faShuttleSpace} /> View Launch
                                    </LinkButton>
                                </div>
                            </Tooltip>
                        )}
                        <div className="landscape-card__share">
                            <Tooltip  message={copied ? "Copied!" : "Copied to clipboard!"}>
                                <Button className="btn btn--primary" onClick={() => handleShare(`launches?page=1&limit=12&rocketConfig=${id.toString()}&upcoming=all`)} disabled={copied}>
                                    <FontAwesomeIcon icon={faShareFromSquare} /> SHARE
                                </Button>
                            </Tooltip>
                        </div>
                    </div>
                </section>
            </div>
        </article>
    );
};

export default RocketCard;
