import React from 'react';
import {DateTime} from "luxon";
import {Link} from "react-router-dom";
import Img from "@/components/utils/Img.jsx";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faShuttleSpace } from '@fortawesome/free-solid-svg-icons';
import { faNewspaper } from '@fortawesome/free-regular-svg-icons';

const NewsArticle = (
    {
        cardStyles,
        soLaunchBtn= true,
        title,
        url,
        image_url,
        news_site,
        published_at,
        launches,
    }) => {
    const zonedDateTime = DateTime.fromISO(published_at).setZone(DateTime.local().zoneName);
    const formattedZonedDateTime = zonedDateTime.toFormat('MMMM dd, yyyy - hh:mm a ZZZZ');
    return (
        <article className={`landscape-card flex justify-center ${cardStyles?.wrapper || "small-wrapper"}`}>
            <div className={`landscape-card__container ${cardStyles?.card_type || ''}`}>
                <div className="landscape-card__media">
                    <Img
                        src={image_url}
                        alt={title || "default"}
                        className="landscape-card__image"
                        defaultSrc={`${import.meta.env.VITE_CLOUDFRONT_URL}/assets/logo/moonkeyeu-logo.svg`}
                    />
                </div>
                <section className="landscape-card__content flex flex-column justify-space-evenly">
                    <div className="landscape-card__details">
                        <div className="landscape-card__detail-box">
                            <h3 className="title">{news_site}</h3>
                        </div>
                        <div className="landscape-card__detail-box">
                            <small className="fw-semi-bold fs-small-100 margin-block-2">{formattedZonedDateTime}</small>
                        </div>
                        <div className="landscape-card__detail-box">
                            <p>{title}</p>
                        </div>
                    </div>
                    <hr className="hr-100-sm bg-hr-600"/>
                    <div className="landscape-card__actions flex flex-wrap justify-center padding-block-2">
                        <a className="btn btn--primary" href={url} target="_blank"  rel="noopener noreferrer">
                            <FontAwesomeIcon icon={faNewspaper} /> Read Article
                        </a>
                        {soLaunchBtn && launches.length > 0 &&
                            <Link className="btn btn--primary" to={`/launches/${launches[0].launch_id}`} replace={true}>
                                <FontAwesomeIcon icon={faShuttleSpace} /> View Launch
                            </Link>
                        }
                    </div>
                </section>
            </div>
        </article>
    );
};

export default NewsArticle;
