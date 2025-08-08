import React from 'react';
import {LinkButton} from "@/components/button/LinkButton.jsx";
import Tooltip from "@/components/tooltip/Tooltip.jsx";
import Img from "@/components/utils/Img.jsx";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faBuilding } from '@fortawesome/free-regular-svg-icons';
import { faWikipediaW, faInstagram, faXTwitter } from '@fortawesome/free-brands-svg-icons';
import {faCircleInfo} from "@fortawesome/free-solid-svg-icons";

const AstronautLandScapeCard = ({cardStyles, astronaut, role}) => {
    const socialMediaLinks = astronaut?.social_media?.length > 0 ? astronaut.social_media : [];
    const instagram = socialMediaLinks.find((sm) => sm.name === "Instagram")?.media_url;
    const twitter = socialMediaLinks.find((sm) => sm.name === "Twitter")?.media_url;
    const wiki = socialMediaLinks.find((sm) => sm.name === "Wiki")?.media_url;
    const checkValue = (value) => {
        return (value ? value : "―");
    }
    return (
        <article className={`landscape-card flex justify-center ${cardStyles?.wrapper || "small-wrapper"}`}>
            <div className={`landscape-card__container ${cardStyles?.card_type || ''}`}>
                <div className="landscape-card__media">
                    <Img
                        src={astronaut?.images?.[0]?.image_url}
                        alt={astronaut?.images?.[0]?.title || "default"}
                        className="landscape-card__image landscape-card__image--small"
                        defaultSrc={`${import.meta.env.VITE_CLOUDFRONT_URL}/assets/logo/moonkeyeu-logo.svg`}
                    />
                </div>
                <section className="landscape-card__content flex flex-column justify-space-evenly">
                    <div className="panel">
                        <h4 className="panel__title">{astronaut.name}</h4>
                        <h5 className="panel__subtitle">{role}</h5>
                        <hr/>
                        <div className="panel__wrapper">
                            <div className="panel__container">
                                <div className="panel__detail-box fs-small-200 padding-1">
                                    <p className="panel__text">Nationality</p>
                                    <p className="panel__text">{checkValue(astronaut.nationality[0].nationality_name)}</p>
                                </div>
                                <div className="panel__detail-box fs-small-200 padding-1">
                                    <p className="panel__text">Status</p>
                                    <p className="panel__text">{checkValue(astronaut.status)}</p>
                                </div>
                            </div>
                            <div className="panel__container">
                                <div className="panel__detail-box fs-small-200 padding-1">
                                    <p className="panel__text">Date Of Birth</p>
                                    <p className="panel__text">{checkValue(astronaut.date_of_birth)}</p>
                                </div>
                                <div className="panel__detail-box fs-small-200 padding-1">
                                    <p className="panel__text">Type</p>
                                    <p className="panel__text">{checkValue(astronaut.agency.type)}</p>
                                </div>
                            </div>
                        </div>
                        <hr/>
                        <div className="panel__actions">
                            { instagram ? (
                                <div className="panel__instagram">
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
                                    <div className="panel__instagram">
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
                                <div className="panel__x-twitter">
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
                                    <div className="panel__x-twitter">
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
                                <div className="panel__wiki">
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
                                    <div className="panel__wiki">
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
                                <div className="panel__agency">
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
                                    <div className="panel__agency">
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
                                <div className="panel__astroanut">
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
                                    <div className="panel__astroanut">
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
                </section>
            </div>
        </article>
    );
};

export default AstronautLandScapeCard;
