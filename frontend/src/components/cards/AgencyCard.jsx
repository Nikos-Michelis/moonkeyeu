import React from 'react';
import {LinkButton} from "@/components/button/LinkButton.jsx";
import Tooltip from "@/components/tooltip/Tooltip.jsx";
import useClipboard from "@/hooks/util/useClipboard.jsx";
import Img from "@/components/utils/Img.jsx";
import { Button } from "@/components/button/Button.jsx";
import { Link } from "react-router-dom";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faCircleInfo, faShareFromSquare } from '@fortawesome/free-solid-svg-icons';
import { faWikipediaW } from '@fortawesome/free-brands-svg-icons';

const AgencyCard = ({id, name, type, administrator, description, spacecraft, launchers, wiki_url, images, country, isDetailed = false, cardStyles}) => {
    const { copied, copyToClipboard } = useClipboard();
    const tooltipInfoMessage = id ? "" : "No Info Available";
    const handleShare = () => {
        const url = window.location.origin + window.location.pathname + "/" + id;
        copyToClipboard(url);
    };
    const checkValue = (value) => { return (value ? value : "―"); }

    return (
        <article className={`landscape-card flex justify-center ${cardStyles?.wrapper || ''}`}>
            <div className={`landscape-card__container ${cardStyles?.card_type || ''}`}>
                <div className="landscape-card__media">
                    <Img
                        src={images?.[0]?.image_url}
                        alt={images?.[0]?.name || "default"}
                        className="landscape-card__image landscape-card__image--scale-down"
                        defaultSrc={`${import.meta.env.VITE_CLOUDFRONT_URL}/assets/logo/moonkeyeu-logo-transparent.svg`}
                    />
                </div>
                <section className="landscape-card__content flex flex-column justify-space-evenly">
                    {!isDetailed &&
                        <>
                            <div className="landscape-card__detail-box">
                                <h4>{name}</h4>
                            </div>
                            <div className="landscape-card__detail-box">
                                <h5 className="agency-type">{type}</h5>
                            </div>
                            <div className="landscape-card__detail-box landscape-card--ellipsis">
                                <p>{description}</p>
                            </div>
                            <hr className="hr-100-sm bg-hr-600"/>
                        </>
                    }
                    {isDetailed &&
                        <div className="panel">
                            <h4 className="panel__title">{checkValue(name)}</h4>
                            <hr/>
                            <div className="panel__wrapper">
                                <div className="panel__container">
                                    <div className="panel__detail-box fs-small-100 padding-1">
                                        <p className="panel__text">Type</p>
                                        <p className="panel__text">{checkValue(type)}</p>
                                    </div>
                                    <div className="panel__detail-box fs-small-100 padding-1">
                                        <p className="panel__text">CountryCode</p>
                                        <p className="panel__text">{country?.[0]?.alpha_3_code}</p>
                                    </div>
                                </div>
                                <div className="panel__container">
                                    <div className="panel__detail-box fs-small-100 padding-1">
                                        <p className="panel__text">Spacecraft</p>
                                        <p className="panel__text">{checkValue(spacecraft)}</p>
                                    </div>
                                    <div className="panel__detail-box fs-small-100 padding-1">
                                        <p className="panel__text">Launchers</p>
                                        <p className="panel__text">{checkValue(launchers)}</p>
                                    </div>
                                </div>
                                <div className="panel__container">
                                    <div className="panel__detail-box fs-small-100 padding-1">
                                        <p className="panel__text">Administrator</p>
                                        <p className="panel__text">{checkValue(administrator)}</p>
                                    </div>
                                </div>
                            </div>
                            <hr/>
                        </div>
                    }
                    <div className="landscape-card__actions flex flex-wrap justify-center padding-block-2">
                        {id ? (
                            <div className="landscape-card__info-box">
                                <Link className="btn btn--primary" to={`/agencies/${id.toString()}`} >
                                    <FontAwesomeIcon icon={faCircleInfo} /> INFO
                                </Link>
                            </div>
                        ) : (
                            <Tooltip message={tooltipInfoMessage}>
                                <div className="landscape-card__info">
                                    <Link className="btn btn--primary" to="#" >
                                        <FontAwesomeIcon icon={faCircleInfo} /> INFO
                                    </Link>
                                </div>
                            </Tooltip>
                        )}
                        { wiki_url ? (
                            <div className="landscape-card__wiki">
                                <LinkButton
                                    className="btn btn--primary"
                                    to={wiki_url}
                                    isExternal={true}
                                >
                                    <FontAwesomeIcon icon={faWikipediaW} /> WIKI
                                </LinkButton>
                            </div>
                        ) : (
                            <Tooltip message="No Wiki Available">
                                <div className="landscape-card__wiki">
                                    <LinkButton
                                        className="btn btn--primary"
                                        isExternal={true}
                                        disabled={true}
                                    >
                                        <FontAwesomeIcon icon={faWikipediaW} /> WIKI
                                    </LinkButton>
                                </div>
                            </Tooltip>
                        )}
                        <div className="landscape-card__share">
                            <Tooltip copied={copied} message={copied ? "Copied!" :"Copied to clipboard!"}>
                                <Button className="btn btn--primary" onClick={handleShare} disabled={copied}>
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

export default AgencyCard;
