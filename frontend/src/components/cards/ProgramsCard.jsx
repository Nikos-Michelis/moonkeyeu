import React from 'react';
import {Link} from "react-router-dom";
import useClipboard from "@/hooks/util/useClipboard.jsx";
import Tooltip from "@/components/tooltip/Tooltip.jsx";
import {Button} from "@/components/button/Button.jsx";
import Img from "@/components/utils/Img.jsx";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faCircleInfo, faShareFromSquare} from "@fortawesome/free-solid-svg-icons";
const ProgramsCard = ({segment, id, name, info_url, wiki_url, description, start_date, type, images}) => {
    const { copied, copyToClipboard } = useClipboard();
    const tooltipInfoMessage = id ? "" : "No Info Available";
    const handleShare = () => {
        const url =  segment ? `${window.location.origin}/${segment}/${id}` : `${window.location.origin}/${window.location.pathname}/${id}`;
        copyToClipboard(url);
    };
    return (
        <article className="landscape-card-outer-wrapper flex justify-center small-wrapper">
            <div className="landscape-card-wrapper flex justify-center">
                <div className="card-info-container flex flex-column justify-center align-center">
                    <div className="card-img-box">
                        <Img
                            src={images?.[0]?.image_url}
                            alt={images?.[0]?.name || "default"}
                            className="card-img scale-down-img"
                            defaultSrc={`${import.meta.env.VITE_CLOUDFRONT_URL}/assets/logo/moonkeyeu-logo.svg`}
                        />
                    </div>
                    <section className="card-info-section flex flex-column justify-space-evenly">
                        <div className="card-detail-container">
                            <h3 className="program-name">{name}</h3>
                            <div className="card-detail-box program-description ellipsis">
                                <p>{description}</p>
                            </div>
                        </div>
                        <hr className="hr-7-sm bg-hr-600"/>
                        <div className="flex flex-wrap justify-center padding-block-2">
                            {id ? (
                                <div className="info">
                                    <Link className="btn btn-primary" to={segment ? `/${segment}/${id.toString()}` : id.toString()} >
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

export default ProgramsCard;
