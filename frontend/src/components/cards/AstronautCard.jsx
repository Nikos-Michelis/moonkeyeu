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
        <article className="portrait-card-wrapper flex flex-column justify-start">
            <div className="portrait-card-container flex flex-column">
                <div className="card-image-wrapper flex flex-column">
                    <div className="portrait-card-img-box">
                        <Img
                            src={images?.[0]?.image_url}
                            alt={images?.[0]?.name || "default"}
                            className="card-img"
                            defaultSrc={`${import.meta.env.VITE_CLOUDFRONT_URL}/assets/logo/moonkeyeu-logo.svg`}
                        />
                    </div>
                </div>
                <div className="card-info-section flex flex-column justify-space-evenly margin-block-start-1 margin-inline-2 ">
                    <h2>{name}</h2>
                    <p>
                        {nationality.map(nation => nation?.nationality_name).join(" / ")}
                    </p>
                    <p>{agency? agency.name : "Unknown"}</p>
                </div>
                <hr className="hr-7-sm bg-hr-600"/>
                <div className="flex flex-wrap justify-center ">
                    {id ? (
                        <div className="info">
                            <LinkButton className="btn btn-primary" to={id.toString()} >
                                <FontAwesomeIcon icon={faCircleInfo} /> INFO
                            </LinkButton>
                        </div>
                    ) : (
                        <Tooltip message={tooltipInfoMessage}>
                            <div className="info">
                                <LinkButton className="btn btn-primary">
                                    <FontAwesomeIcon icon={faCircleInfo} /> INFO
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
            </div>
        </article>
    );
};

export default AstronautCard;
