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

const AgencyCard = ({id, name, type, administrator, description, spacecraft, launchers, info_url, wiki_url, images, country, isDetailed = false }) => {
    const { copied, copyToClipboard } = useClipboard();
    const tooltipInfoMessage = id ? "" : "No Info Available";

    const handleShare = () => {
        const url = window.location.origin + window.location.pathname + "/" + id;
        copyToClipboard(url);
    };
    const checkValue = (value) => { return (value ? value : "―"); }

    return (
        <article className="landscape-card-outer-wrapper flex justify-center small-wrapper">
            <div className="landscape-card-wrapper flex justify-center">
                <div className="card-info-container flex flex-column justify-center align-center">
                    <div className="card-img-box">
                        <Img
                            src={images?.[0]?.image_url}
                            alt={images?.[0]?.name || "default"}
                            className="card-img scale-down-img"
                            defaultSrc={`${import.meta.env.VITE_CLOUDFRONT_URL}/assets/logo/moonkeyeu-logo-transparent.svg`}
                        />
                    </div>
                    <section className="card-info-section flex flex-column justify-space-evenly">
                        {!isDetailed &&
                            <>
                                <div className="card-detail-container">
                                    <h4 className="rocket-name">{name}</h4>
                                    <div className="card-detail-box">
                                        <h5 className="agency-type">{type}</h5>
                                    </div>
                                    <div className="card-detail-box ellipsis">
                                        <p>{description}</p>
                                    </div>
                                </div>
                                <hr className="hr-7-sm bg-hr-600"/>
                            </>
                        }
                        {isDetailed &&
                            <div className="card-detail-container">
                                <div className="panel-body">
                                    <h4 className="astronaut-name">{checkValue(name)}</h4>
                                    <hr className="hr-7-sm bg-hr-600"/>
                                    <div className="panel-info-wrapper">
                                        <div className="panel-info-container">
                                            <div className="detail-wrapper fs-small-100 padding-1">
                                                <p className="info-panel-row">Type</p>
                                                <p className="info-panel-row">{checkValue(type)}</p>
                                            </div>
                                            <div className="detail-wrapper fs-small-100 padding-1">
                                                <p className="info-panel-row">CountryCode</p>
                                                <p className="info-panel-row">{country?.[0]?.alpha_3_code}</p>
                                            </div>
                                        </div>
                                        <div className="panel-info-container">
                                            <div className="detail-wrapper fs-small-100 padding-1">
                                                <p className="info-panel-row">Spacecraft</p>
                                                <p className="info-panel-row">{checkValue(spacecraft)}</p>
                                            </div>
                                            <div className="detail-wrapper fs-small-100 padding-1">
                                                <p className="info-panel-row">Launchers</p>
                                                <p className="info-panel-row">{checkValue(launchers)}</p>
                                            </div>
                                        </div>
                                        <div className="panel-info-container">
                                            <div className="detail-wrapper fs-small-100 padding-1">
                                                <p className="info-panel-row">Administrator</p>
                                                <p className="info-panel-row">{checkValue(administrator)}</p>
                                            </div>
                                        </div>
                                    </div>
                                    <hr className="hr-7-sm bg-hr-600"/>
                                </div>
                            </div>
                        }
                        <div className="flex flex-wrap justify-center padding-block-2">
                            {id ? (
                                <div className="info">
                                    <Link className="btn btn-primary" to={`/agencies/${id.toString()}`} >
                                        <FontAwesomeIcon icon={faCircleInfo} /> INFO
                                    </Link>
                                </div>
                            ) : (
                                <Tooltip message={tooltipInfoMessage}>
                                    <div className="info">
                                        <Link className="btn btn-primary" to="#" >
                                            <FontAwesomeIcon icon={faCircleInfo} /> INFO
                                        </Link>
                                    </div>
                                </Tooltip>
                            )}
                            { wiki_url ? (
                                <div className="wiki">
                                    <LinkButton
                                        className="btn btn-primary"
                                        to={wiki_url}
                                        isExternal={true}
                                    >
                                        <FontAwesomeIcon icon={faWikipediaW} /> WIKI
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
                                            <FontAwesomeIcon icon={faWikipediaW} /> WIKI
                                        </LinkButton>
                                    </div>
                                </Tooltip>
                            )}
                            <div className="share">
                                <Tooltip copied={copied} message={copied ? "Copied!" :"Copied to clipboard!"}>
                                    <Button className="btn btn-primary" onClick={handleShare} disabled={copied}>
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

export default AgencyCard;
