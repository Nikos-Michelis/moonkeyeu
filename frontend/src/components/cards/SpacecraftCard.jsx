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
        history,
        human_rated,
        images,
        wiki_url,
        cardStyles
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
        <article className={`landscape-card flex justify-center ${cardStyles?.wrapper || 'medium-wrapper'}`}>
            <div className={`landscape-card__container ${cardStyles?.card_type || ''}`}>
                <div className="landscape-card__media">
                    <Img
                        src={images?.[0]?.image_url}
                        alt={images?.[0]?.name || "default"}
                        className="landscape-card__image"
                        defaultSrc={`${import.meta.env.VITE_CLOUDFRONT_URL}/assets/logo/moonkeyeu-logo.svg`}
                    />
                </div>
                <section className="landscape-card__content flex flex-column justify-space-evenly">
                    {!showPanel &&
                        <>
                            <div className="landscape-card__details">
                                <div className="landscape-card__detail-box">
                                    <h3 className="title">{name}</h3>
                                </div>
                                {maiden_flight &&
                                    <div className="flex justify-space-between flex-wrap margin-block-2">
                                        <div>
                                            <small>{agency?.name}</small>
                                        </div>
                                        <div>
                                            <small>{formattedZonedDateTime}</small>
                                        </div>
                                    </div>
                                }
                                <div className="landscape-card__detail-box landscape-card--ellipsis">
                                    <p className="capability">{capability.endsWith('.') ? capability : capability + '.'} {history}</p>
                                </div>
                                <hr className="hr-100-sm bg-hr-600"/>
                            </div>
                        </>
                    }

                    {showPanel &&
                        <div className="panel">
                            <h4 className="panel-body__title">{name}</h4>
                            <hr/>
                            <div className="panel__wrapper">
                                <div className="panel__container">
                                    <div className="panel__detail-box fs-small-100 padding-1">
                                        <p className="panel__text">Active</p>
                                        <p className="panel__text">{checkValue(booleanConverter(in_use))}</p>
                                    </div>
                                    <div className="panel__detail-box fs-small-100 padding-1">
                                        <p className="panel__text">Human Rated</p>
                                        <p className="panel__text">{checkValue(booleanConverter(human_rated))}</p>
                                    </div>
                                </div>
                                <div className="panel__container">
                                    <div className="panel__detail-box fs-small-100 padding-1">
                                        <p className="panel__text">Height</p>
                                        <p className="panel__text">{checkValue(length, "Meters")}</p>
                                    </div>
                                    <div className="panel__detail-box fs-small-100 padding-1">
                                        <p className="panel__text">Diameter</p>
                                        <p className="panel__text">{checkValue(diameter, "Meters")}</p>
                                    </div>
                                </div>
                                <div className="panel__container">
                                    <div className="panel__detail-box fs-small-100 padding-1">
                                        <p className="panel__text">Maiden Flight</p>
                                        <p className="panel__text">{checkValue(maiden_flight)}</p>
                                    </div>
                                    <div className="panel__detail-box fs-small-100 padding-1">
                                        <p className="panel__text"></p>
                                        <p className="panel__text"></p>
                                    </div>
                                </div>
                            </div>
                            <hr/>
                        </div>
                    }
                    <div className="landscape-card__actions flex flex-wrap justify-center padding-block-2">
                        {(!url && id) && (
                            <div className="landscape-card__info">
                                <LinkButton className="btn btn--primary" to={id.toString()} >
                                    <FontAwesomeIcon icon={faCircleInfo} /> INFO
                                </LinkButton>
                            </div>
                        )}

                        {url && (
                            <div className="landscape-card__info">
                                <LinkButton className="btn btn--primary" to={url} >
                                    <FontAwesomeIcon icon={faShuttleSpace} /> View Launch
                                </LinkButton>
                            </div>)
                        }

                        { wiki_url ? (
                            <div className="landscape-card__wiki">
                                <LinkButton
                                    className="btn btn--primary"
                                    to={wiki_url}
                                    isExternal={true}
                                >
                                    <FontAwesomeIcon icon={faWikipediaW} /> Wiki
                                </LinkButton>
                            </div>
                        ) : (
                            <Tooltip message="No Wiki Available">
                                <div className="landscape-card__wiki">
                                    <LinkButton
                                        className="btn btn--primary"
                                        isExternal={true}
                                    >
                                        <FontAwesomeIcon icon={faWikipediaW} /> Wiki
                                    </LinkButton>
                                </div>
                            </Tooltip>
                        )}
                        <div className="landscape-card__share">
                            <Tooltip  message={copied ? "Copied!" : "Copied to clipboard!"}>
                                <Button className="btn btn--primary" onClick={() => handleShare(`/vehicles/spacecraft/${id}`)} disabled={copied}>
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

export default SpacecraftCard;
