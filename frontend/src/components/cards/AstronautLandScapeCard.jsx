import React from 'react';
import {LinkButton} from "@/components/button/LinkButton.jsx";
import Tooltip from "@/components/tooltip/Tooltip.jsx";
import Img from "@/components/utils/Img.jsx";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faBuilding, faCircleInfo } from '@fortawesome/free-solid-svg-icons';
import { faWikipediaW, faInstagram, faXTwitter } from '@fortawesome/free-brands-svg-icons';

const AstronautLandScapeCard = ({astronaut, role}) => {
    const socialMediaLinks = astronaut?.social_media?.length > 0 ? astronaut.social_media : [];
    const instagram = socialMediaLinks.find((sm) => sm.name === "Instagram")?.media_url;
    const twitter = socialMediaLinks.find((sm) => sm.name === "Twitter")?.media_url;
    const wiki = socialMediaLinks.find((sm) => sm.name === "Wiki")?.media_url;
    const checkValue = (value) => {
        return (value ? value : "―");
    }
    return (
        <article className="landscape-card-outer-wrapper flex justify-center small-wrapper">
            <div className="landscape-card-wrapper flex justify-center">
                <div className="card-info-container flex flex-column justify-center align-center article-card">
                    <div className="card-img-box">
                        <Img
                            src={astronaut?.images?.[0]?.image_url}
                            alt={astronaut?.images?.[0]?.title || "default"}
                            className="card-img small-img"
                            defaultSrc={`${import.meta.env.VITE_CLOUDFRONT_URL}/assets/logo/moonkeyeu-logo.svg`}
                        />
                    </div>
                    <section className="card-info-section flex flex-column justify-space-evenly">
                        <div className="card-detail-container">
                            <div className="panel-body">
                                <h4 className="astronaut-name">{astronaut.name}</h4>
                                <h5 className="astronaut-role">{role}</h5>
                                <hr className="hr-7-sm bg-hr-600"/>
                                <div className="panel-info-wrapper">
                                    <div className="panel-info-container">
                                        <div className="detail-wrapper fs-small-200 padding-1">
                                            <p className="info-panel-row">Nationality</p>
                                            <p className="info-panel-row">{checkValue(astronaut.nationality[0].nationality_name)}</p>
                                        </div>
                                        <div className="detail-wrapper fs-small-200 padding-1">
                                            <p className="info-panel-row">Status</p>
                                            <p className="info-panel-row">{checkValue(astronaut.status)}</p>
                                        </div>
                                    </div>
                                    <div className="panel-info-container">
                                        <div className="detail-wrapper fs-small-200 padding-1">
                                            <p className="info-panel-row">Date Of Birth</p>
                                            <p className="info-panel-row">{checkValue(astronaut.date_of_birth)}</p>
                                        </div>
                                        <div className="detail-wrapper fs-small-200 padding-1">
                                            <p className="info-panel-row">Type</p>
                                            <p className="info-panel-row">{checkValue(astronaut.agency.type)}</p>
                                        </div>
                                    </div>
                                </div>
                                <hr className="hr-7-sm bg-hr-600"/>
                                <div className="media-links-container">
                                    { instagram ? (
                                        <div className="instagram">
                                            <LinkButton
                                                className="btn-instragram"
                                                to={instagram}
                                                isExternal={true}
                                            >
                                                <FontAwesomeIcon icon={faInstagram} />
                                            </LinkButton>
                                        </div>
                                    ) : (
                                        <Tooltip message="No Instagram Available">
                                            <div className="instagram">
                                                <LinkButton
                                                    className="btn-instragram"
                                                    isExternal={true}
                                                    disabled={true}
                                                >
                                                    <FontAwesomeIcon icon={faInstagram} />
                                                </LinkButton>
                                            </div>
                                        </Tooltip>
                                    )}
                                    { twitter ? (
                                        <div className="x-twitter">
                                            <LinkButton
                                                className="btn-x-twitter"
                                                to={twitter}
                                                isExternal={true}
                                            >
                                                <FontAwesomeIcon icon={faXTwitter} />
                                            </LinkButton>
                                        </div>
                                    ) : (
                                        <Tooltip message="No Twitter Available">
                                            <div className="x-twitter">
                                                <LinkButton
                                                    className="btn-x-twitter"
                                                    isExternal={true}
                                                    disabled={true}
                                                >
                                                    <FontAwesomeIcon icon={faXTwitter} />
                                                </LinkButton>
                                            </div>
                                        </Tooltip>
                                    )}
                                    { wiki ? (
                                        <div className="wiki">
                                            <LinkButton
                                                className="btn-wiki"
                                                to={wiki}
                                                isExternal={true}
                                            >
                                                <FontAwesomeIcon icon={faWikipediaW} />
                                            </LinkButton>
                                        </div>
                                    ) : (
                                        <Tooltip message="No Wiki Available">
                                            <div className="wiki">
                                                <LinkButton
                                                    className="btn-wiki"
                                                    isExternal={true}
                                                    disabled={true}
                                                >
                                                    <FontAwesomeIcon icon={faWikipediaW} />
                                                </LinkButton>
                                            </div>
                                        </Tooltip>
                                    )}
                                    { astronaut?.agency?.id ? (
                                        <div className="agency">
                                            <LinkButton
                                                className="btn-agency"
                                                to={`/agencies/${astronaut?.agency?.id}`}
                                                isExternal={false}
                                            >
                                                <FontAwesomeIcon icon={faBuilding} />
                                            </LinkButton>
                                        </div>
                                    ) : (
                                        <Tooltip message="No agency available">
                                            <div className="agency">
                                                <LinkButton
                                                    className="btn-agency"
                                                    isExternal={false}
                                                    disabled={true}>
                                                    <FontAwesomeIcon icon={faBuilding} />
                                                </LinkButton>
                                            </div>
                                        </Tooltip>
                                    )}
                                    { astronaut.id ? (
                                        <div className="astroanut">
                                            <LinkButton
                                                className="btn-agency"
                                                to={`/astronauts/${astronaut.id}`}
                                                isExternal={false}
                                            >
                                                <FontAwesomeIcon icon={faCircleInfo} />
                                            </LinkButton>
                                        </div>
                                    ) : (
                                        <Tooltip message="No agency available">
                                            <div className="astroanut">
                                                <LinkButton
                                                    className="btn-agency"
                                                    isExternal={false}
                                                    disabled={true}>
                                                    <FontAwesomeIcon icon={faCircleInfo} />
                                                </LinkButton>
                                            </div>
                                        </Tooltip>
                                    )}
                                </div>
                            </div>
                        </div>
                    </section>
                </div>
            </div>
        </article>
    );
};

export default AstronautLandScapeCard;
