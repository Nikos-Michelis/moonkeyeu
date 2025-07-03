import React from "react";
import {LinkButton} from "@/components/button/LinkButton.jsx";
import Tooltip from "@/components/tooltip/Tooltip.jsx";
import Img from "@/components/utils/Img.jsx";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {faBuilding, faCircleInfo} from '@fortawesome/free-solid-svg-icons';
import { faWikipediaW } from '@fortawesome/free-brands-svg-icons';

const Agency = ({ launchProvider }) =>{
    return(
        <section className="agency-section">
            <div className="heading-box">
                <FontAwesomeIcon icon={faBuilding} />
                <h2>Agency</h2>
            </div>
            <hr className="hr-7-sm bg-hr-600" />
            <div className="container flex flex-wrap justify-center align-center padding-block-8" data-type="full-bleed" data-spacing="none">
                <div className="img-box container flex flex-column justify-center align-center margin-block-start-5" data-type="full-bleed">
                    <Img
                        src={launchProvider?.images?.[0]?.image_url}
                        alt={launchProvider?.images?.[0]?.name || "default"}
                        className="article-img"
                        defaultSrc={`${import.meta.env.VITE_CLOUDFRONT_URL}/assets/logo/moonkeyeu-logo-transparent.svg`}
                    />
                    <div className="flex flex-wrap">
                        {launchProvider?.administrator? <span className="badge badge-pill">President: {launchProvider?.administrator}</span> : null}
                        <div className="badge badge-pill">Founded: {launchProvider?.founding_year}</div>
                        <div className="badge badge-pill bg-success-400">Successes: {launchProvider?.successful_launches}</div>
                        <div className="badge badge-pill bg-warning-200">Failures: {launchProvider?.failed_launches}</div>
                        <div className="badge badge-pill">Pending: {launchProvider?.pending_launches}</div>
                    </div>
                </div>
                <div className="info-box">
                    <h3>{launchProvider?.name}</h3>
                    <p>{launchProvider?.description}</p>
                    <div className="flex justify-center flex-wrap padding-block-start-4">
                        { launchProvider?.id ? (
                            <div className="info">
                                <LinkButton
                                    className="btn btn-primary"
                                    to={`/agencies/${launchProvider?.id}`}
                                >
                                    <FontAwesomeIcon icon={faCircleInfo} />
                                </LinkButton>
                            </div>
                        ) : (
                            <Tooltip message="No Info Available">
                                <div className="info">
                                    <LinkButton
                                        className="btn btn-primary"
                                        disabled={true}
                                    >
                                        <FontAwesomeIcon icon={faCircleInfo} />
                                    </LinkButton>
                                </div>
                            </Tooltip>
                        )}
                        { launchProvider.info_url ? (
                            <div className="info">
                                <LinkButton
                                    className="btn btn-primary"
                                    to={launchProvider.info_url}
                                    isExternal={true}
                                >
                                    <FontAwesomeIcon icon={faBuilding} />
                                </LinkButton>
                            </div>
                        ) : (
                            <Tooltip message="There is no official page available.">
                                <div className="info">
                                    <LinkButton
                                        className="btn btn-primary"
                                        isExternal={true}
                                        disabled={true}
                                    >
                                        <FontAwesomeIcon icon={faBuilding} />
                                    </LinkButton>
                                </div>
                            </Tooltip>
                        )}
                        { launchProvider.wiki_url ? (
                            <div className="wiki">
                                <LinkButton
                                    className="btn btn-primary"
                                    to={launchProvider.wiki_url}
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
        </section>
    )
}
export default Agency;