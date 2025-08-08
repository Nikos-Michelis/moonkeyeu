import React from 'react';
import {Link} from "react-router-dom";
import useClipboard from "@/hooks/util/useClipboard.jsx";
import Tooltip from "@/components/tooltip/Tooltip.jsx";
import {Button} from "@/components/button/Button.jsx";
import Img from "@/components/utils/Img.jsx";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faCircleInfo, faShareFromSquare} from "@fortawesome/free-solid-svg-icons";
const ProgramsCard = (
    {
        segment,
        id,
        name,
        info_url,
        wiki_url,
        description,
        start_date,
        type,
        images,
        cardStyles
    }) => {
    const { copied, copyToClipboard } = useClipboard();
    const tooltipInfoMessage = id ? "" : "No Info Available";
    const handleShare = () => {
        const url =  segment ? `${window.location.origin}/${segment}/${id}` : `${window.location.origin}/${window.location.pathname}/${id}`;
        copyToClipboard(url);
    };
    return (
        <article className={`landscape-card flex justify-center ${cardStyles?.wrapper || 'small-wrapper'}`}>
            <div className={`landscape-card__container ${cardStyles?.card_type || ''}`}>
                <div className="landscape-card__media">
                    <Img
                        src={images?.[0]?.image_url}
                        alt={images?.[0]?.name || "default"}
                        className="landscape-card__image landscape-card__image--scale-down"
                        defaultSrc={`${import.meta.env.VITE_CLOUDFRONT_URL}/assets/logo/moonkeyeu-logo.svg`}
                    />
                </div>
                <section className="landscape-card__content flex flex-column justify-space-evenly">
                    <div className="landscape-card__detail-box">
                        <h3 className="title">{name}</h3>
                    </div>
                    <div className="landscape-card__detail-box landscape-card--ellipsis">
                        <p>{description}</p>
                    </div>
                    <hr className="hr-100-sm bg-hr-600"/>
                    <div className="landscape-card__actions flex flex-wrap justify-center padding-block-2">
                        {id ? (
                            <div className="launch-card__info">
                                <Link className="btn btn--primary" to={segment ? `/${segment}/${id.toString()}` : id.toString()} >
                                    <FontAwesomeIcon icon={faCircleInfo} /> INFO
                                </Link>
                            </div>
                        ) : (
                            <Tooltip message={tooltipInfoMessage}>
                                <div className="launch-card__info">
                                    <Link className="btn btn--primary" to="#" >
                                        <FontAwesomeIcon icon={faCircleInfo} /> INFO
                                    </Link>
                                </div>
                            </Tooltip>
                        )}
                        <div className="launch-card__share">
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

export default ProgramsCard;
