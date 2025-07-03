import React from 'react';
import {DateTime} from "luxon";
import {LinkButton} from "@/components/button/LinkButton.jsx";
import Tooltip from "@/components/tooltip/Tooltip.jsx";
import useClipboard from "@/hooks/util/useClipboard.jsx";
import {Button} from "@/components/button/Button.jsx";
import Img from "@/components/utils/Img.jsx";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {
    faCircleInfo,
    faShareFromSquare,
    faShuttleSpace
} from '@fortawesome/free-solid-svg-icons';

import { faWikipediaW } from '@fortawesome/free-brands-svg-icons';

const SpacecraftCard = (
    {
        showPanel = false,
        url,
        id,
        name,
        in_use,
        length,
        diameter,
        agency,
        maiden_flight,
        capability,
        details,
        history,
        human_rated,
        images,
        wiki_url,
    }) => {
    const zonedDateTime = DateTime.fromISO(maiden_flight || "");
    const formattedZonedDateTime = zonedDateTime.isValid
        ? zonedDateTime.toFormat('MMMM dd, yyyy')
        : undefined;
    const { copied, copyToClipboard } = useClipboard();
    const handleShare = (url) => {
        const copiedUrl = url ? window.location.origin + url : window.location.origin + window.location.pathname + "/" + id;
        copyToClipboard(copiedUrl);
    };
    const checkValue = (value, metric= "") => {
        return (value ? `${value} ${metric}` : "―");
    }
    const booleanConverter = (value) => {
        return value && value === true ? "Yes" : "No";
    }

    return (
        <article className="landscape-card-outer-wrapper flex justify-center medium-wrapper">
            <div className="landscape-card-wrapper flex justify-center">
                <div className="card-info-container flex flex-column justify-center align-center">
                    <div className="card-img-box">
                        <Img
                            src={images?.[0]?.image_url}
                            alt={images?.[0]?.name || "default"}
                            className="card-img"
                            defaultSrc={`${import.meta.env.VITE_CLOUDFRONT_URL}/assets/logo/moonkeyeu-logo.svg`}
                        />
                    </div>
                    <section className="card-info-section flex flex-column justify-space-evenly">
                        {!showPanel &&
                            <>
                                <div className="card-detail-container">
                                        <h4>{name}</h4>
                                    {maiden_flight &&
                                        <div className="flex justify-space-between flex-wrap margin-block-2">
                                            <div>
                                                <small className="margin-inline-1">{agency?.name}</small>
                                            </div>
                                            <div>
                                                <small className="margin-inline-1">{formattedZonedDateTime}</small>
                                            </div>
                                        </div>
                                    }
                                    <div className="card-detail-box ellipsis">
                                        <p className="capability">{capability.endsWith('.') ? capability : capability + '.'} {history}</p>
                                    </div>
                                    <hr className="hr-7-sm bg-hr-600"/>
                                </div>
                            </>
                        }

                        {showPanel &&
                            <div className="panel-body">
                                <h4 className="rocket-name">{name}</h4>
                                <hr className="hr-7-sm bg-hr-600"/>
                                <div className="panel-info-wrapper">
                                    <div className="panel-info-container">
                                        <div className="detail-wrapper fs-small-200 padding-1">
                                            <p className="info-panel-row">Active</p>
                                            <p className="info-panel-row">{checkValue(booleanConverter(in_use))}</p>
                                        </div>
                                        <div className="detail-wrapper fs-small-200 padding-1">
                                            <p className="info-panel-row">Human Rated</p>
                                            <p className="info-panel-row">{checkValue(booleanConverter(human_rated))}</p>
                                        </div>
                                    </div>
                                    <div className="panel-info-container">
                                        <div className="detail-wrapper fs-small-200 padding-1">
                                            <p className="info-panel-row">Height</p>
                                            <p className="info-panel-row">{checkValue(length, "Meters")}</p>
                                        </div>
                                        <div className="detail-wrapper fs-small-200 padding-1">
                                            <p className="info-panel-row">Diameter</p>
                                            <p className="info-panel-row">{checkValue(diameter, "Meters")}</p>
                                        </div>
                                    </div>
                                    <div className="panel-info-container">
                                        <div className="detail-wrapper fs-small-200 padding-1">
                                            <p className="info-panel-row">Maiden Flight</p>
                                            <p className="info-panel-row">{checkValue(maiden_flight)}</p>
                                        </div>
                                        <div className="detail-wrapper fs-small-200 padding-1">
                                            <p className="info-panel-row"></p>
                                            <p className="info-panel-row"></p>
                                        </div>
                                    </div>
                                </div>
                                <hr className="hr-7-sm bg-hr-600"/>
                            </div>
                        }
                        <div className="flex flex-wrap justify-center padding-block-2">
                            {(!url && id) && (
                                <div className="info">
                                    <LinkButton className="btn btn-primary" to={id.toString()} >
                                        <FontAwesomeIcon icon={faCircleInfo} /> INFO
                                    </LinkButton>
                                </div>
                            )}

                            {url && (
                                <div className="info">
                                    <LinkButton className="btn btn-primary" to={url} >
                                        <FontAwesomeIcon icon={faShuttleSpace} /> View Launch
                                    </LinkButton>
                                </div>)
                            }

                            { wiki_url ? (
                                <div className="wiki">
                                    <LinkButton
                                        className="btn btn-primary"
                                        to={wiki_url}
                                        isExternal={true}
                                    >
                                        <FontAwesomeIcon icon={faWikipediaW} /> Wiki
                                    </LinkButton>
                                </div>
                            ) : (
                                <Tooltip message="No Wiki Available">
                                    <div className="wiki">
                                        <LinkButton
                                            className="btn btn-primary"
                                            isExternal={true}
                                        >
                                            <FontAwesomeIcon icon={faWikipediaW} /> Wiki
                                        </LinkButton>
                                    </div>
                                </Tooltip>
                            )}
                            <div className="share">
                                <Tooltip  message={copied ? "Copied!" : "Copied to clipboard!"}>
                                    <Button className="btn btn-primary" onClick={() => handleShare(`/vehicles/spacecraft/${id}`)} disabled={copied}>
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

export default SpacecraftCard;
