import React from 'react';
import Tooltip from "@/components/tooltip/Tooltip.jsx";
import {Button} from "@/components/button/Button.jsx";
import useClipboard from "@/hooks/util/useClipboard.jsx";
import Img from "@/components/utils/Img.jsx";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faCircleInfo, faShareFromSquare} from "@fortawesome/free-solid-svg-icons";
import {LinkButton} from "@/components/button/LinkButton.jsx";

const AstronautCard = ({id, name, nationality, agency, images }) => {
    const tooltipInfoMessage = id ? "" : "No Info Available";
    const { copied, copyToClipboard } = useClipboard();
    const handleShare = () => {
        const url = window.location.origin + window.location.pathname + "/" + id;
        copyToClipboard(url);
    };
    return (
        <article className="portrait-card portrait-card__container">
                <div className="portrait-card__media">
                    <Img
                        src={images?.[0]?.image_url}
                        alt={images?.[0]?.name || "default"}
                        className="portrait-card__image"
                        defaultSrc={`${import.meta.env.VITE_CLOUDFRONT_URL}/assets/logo/moonkeyeu-logo.svg`}
                    />
                </div>
                <div className="portrait-card__info flex flex-column justify-space-evenly margin-block-start-1 margin-inline-2">
                    <h2 className="portrait-card__title">{name}</h2>
                    <p className="portrait-card__text">
                        {nationality.map(nation => nation?.nationality_name).join(" / ")}
                    </p>
                    <p className="portrait-card__text">{agency? agency.name : "Unknown"}</p>
                </div>
                <hr className="hr-100-sm bg-hr-600"/>
                <div className="portrait-card__actions flex flex-wrap justify-center margin-block-2">
                    {id ? (
                        <div className="portrait-card__action">
                            <LinkButton className="portrait-card__button btn btn--primary" to={id.toString()}>
                                <FontAwesomeIcon icon={faCircleInfo} /> INFO
                            </LinkButton>
                        </div>
                    ) : (
                        <Tooltip message={tooltipInfoMessage}>
                            <div className="portrait-card__action">
                                <LinkButton className="portrait-card__button btn btn0-primary">
                                    <FontAwesomeIcon icon={faCircleInfo} /> INFO
                                </LinkButton>
                            </div>
                        </Tooltip>
                    )}
                    <div className="portrait-card__action">
                        <Tooltip copied={copied} message={copied ? "Copied!" :"Copied to clipboard!"}>
                            <Button
                                className="btn btn--primary"
                                onClick={handleShare}
                                disabled={copied}
                            >
                                <FontAwesomeIcon icon={faShareFromSquare} /> SHARE
                            </Button>
                        </Tooltip>
                    </div>
                </div>
        </article>
    );
};

export default AstronautCard;
