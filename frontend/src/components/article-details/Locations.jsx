import React from "react";
import Img from "@/components/utils/Img.jsx";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {faCircleInfo, faLocationDot} from '@fortawesome/free-solid-svg-icons';
import {LinkButton} from "@/components/button/LinkButton.jsx";
import Tooltip from "@/components/tooltip/Tooltip.jsx";
import {faWikipediaW} from "@fortawesome/free-brands-svg-icons";

const Location = ({pad}) => {
    pad = pad || [];
    const location =  pad.location || []
    return(
        <section className="location-section">
            <div className="heading-box">
                <FontAwesomeIcon icon={faLocationDot} />
                <h2>Location</h2>
            </div>
            <hr className="hr-7-sm bg-hr-600" />
            <div className="container flex flex-wrap justify-center align-center padding-block-8" data-type="full-bleed" data-spacing="none">
                <div className="info-box">
                    <h3>{pad.name}</h3>
                    <h5>{location.name}</h5>
                    <p>{location.description}</p>
                </div>
                <div className="img-box container flex flex-column justify-center align-center margin-block-start-5" data-type="full-bleed">
                    <Img
                        src={pad.map_image}
                        alt={pad.name || "default"}
                        className="location-image"
                        defaultSrc={`${import.meta.env.VITE_CLOUDFRONT_URL}/assets/logo/moonkeyeu-logo-transparent.svg`}
                    />
                    <div className="flex justify-center flex-wrap padding-block-start-4">
                        { pad?.id ? (
                            <div className="info">
                                <LinkButton
                                    className="btn btn-primary"
                                    to={`/locations/${pad?.id}`}
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
                        { pad?.wiki_url ? (
                            <div className="wiki">
                                <LinkButton
                                    className="btn btn-primary"
                                    to={pad?.wiki_url}
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
export default Location;