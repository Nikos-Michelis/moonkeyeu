import React from 'react';
import {LinkButton} from "@/components/button/LinkButton.jsx";
import Tooltip from "@/components/tooltip/Tooltip.jsx";
import {Button} from "@/components/button/Button.jsx";
import useClipboard from "@/hooks/util/useClipboard.jsx";
import Img from "@/components/utils/Img.jsx";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faShareFromSquare, faShuttleSpace} from "@fortawesome/free-solid-svg-icons";

const RocketCard = (
    {
        id,
        details,
        flight_proven,
        serial_number,
        successful_landings,
        attempted_landings,
        flights,
        status,
        images,
        cardStyles
    }) => {
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
        <article className={`landscape-card flex justify-center ${cardStyles?.wrapper || 'medium-wrapper'}`}>
            <div className="landscape-card__container">
                    <div className="landscape-card__media">
                        <Img
                            src={images?.[0]?.image_url}
                            alt={images?.[0]?.name || "default"}
                            className="landscape-card__image"
                            defaultSrc={`${import.meta.env.VITE_CLOUDFRONT_URL}/assets/logo/moonkeyeu-logo.svg`}
                        />
                    </div>
                    <section className="landscape-card__content flex flex-column justify-space-evenly">
                        <div className="panel">
                            <h4 className="panel__title">{checkValue(serial_number)}</h4>
                            <hr/>
                            <div className="panel__wrapper">
                                <div className="panel__container">
                                    <div className="panel__detail-box fs-small-100 padding-1">
                                        <p className="panel__text">Status</p>
                                        <p className="panel__text">{checkValue(status)}</p>
                                    </div>
                                    <div className="panel__detail-box fs-small-100 padding-1">
                                        <p className="panel__text"></p>
                                        <p className="panel__text"></p>
                                    </div>
                                </div>
                                <div className="panel__container">
                                    <div className="panel__detail-box fs-small-100 padding-1">
                                        <p className="panel__text">flights</p>
                                        <p className="panel__text">{checkValue(flights)}</p>
                                    </div>
                                    <div className="panel__detail-box fs-small-100 padding-1">
                                        <p className="panel__text">Flight Proven</p>
                                        <p className="panel__text">{checkValue(booleanConverter(flight_proven))}</p>
                                    </div>
                                </div>
                                <div className="panel__container">
                                    <div className="panel__detail-box fs-small-100 padding-1">
                                        <p className="panel__text">Successful Landings</p>
                                        <p className="panel__text">{checkValue(successful_landings)}</p>
                                    </div>
                                    <div className="panel__detail-box fs-small-100 padding-1">
                                        <p className="panel__text">Attempted Landings</p>
                                        <p className="panel__text">{checkValue(attempted_landings)}</p>
                                    </div>
                                </div>
                                <hr/>
                                <div className="panel__container">
                                    <div className="panel__detail-box fs-small-100">
                                        <p className="panel__text padding-block-2">{details}</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div className="landscape-card__actions flex flex-wrap justify-center padding-block-2">
                            {id ? (
                                <div className="landscape-card__info">
                                    <LinkButton className="btn btn--primary" to={`/launches?page=1&limit=12&launcher=${id.toString()}&upcoming=all`} >
                                        <FontAwesomeIcon icon={faShuttleSpace} /> View Launch
                                    </LinkButton>
                                </div>
                            ) : (
                                <Tooltip message={tooltipInfoMessage}>
                                    <div className="landscape-card__info">
                                        <LinkButton className="btn btn--primary" >
                                            <FontAwesomeIcon icon={faShuttleSpace} /> View Launch
                                        </LinkButton>
                                    </div>
                                </Tooltip>
                            )}
                            <div className="landscape-card__share">
                                <Tooltip  message={copied ? "Copied!" : "Copied to clipboard!"}>
                                    <Button className="btn btn--primary" onClick={() => handleShare(`launches?page=1&limit=12&launcher=${id.toString()}&upcoming=all`)} disabled={copied}>
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
