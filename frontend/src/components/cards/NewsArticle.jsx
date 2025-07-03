import React from 'react';
import {DateTime} from "luxon";
import {Link} from "react-router-dom";
import Img from "@/components/utils/Img.jsx";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faShuttleSpace } from '@fortawesome/free-solid-svg-icons';
import { faNewspaper } from '@fortawesome/free-regular-svg-icons';

const NewsArticle = ({cardOptions, title, url, image_url, news_site, summary,
                         published_at, launches, soLaunchBtn= true}) => {
    const zonedDateTime = DateTime.fromISO(published_at).setZone(DateTime.local().zoneName);
    const formattedZonedDateTime = zonedDateTime.toFormat('MMMM dd, yyyy - hh:mm a ZZZZ');
    return (
        <article className={`landscape-card-outer-wrapper flex justify-center ${cardOptions?.styles?.wrapper || "small-wrapper"}`}>
            <div className="landscape-card-wrapper flex justify-center">
                <div className={`card-info-container flex flex-column justify-center align-center ${cardOptions?.styles?.container}`}>
                    <div className="card-img-box">
                        <Img
                            src={image_url}
                            alt={title || "default"}
                            className="card-img small-img"
                            defaultSrc={`${import.meta.env.VITE_CLOUDFRONT_URL}/assets/logo/moonkeyeu-logo.svg`}
                        />
                    </div>
                    <section className="card-info-section flex flex-column justify-space-evenly">
                        <div className="card-detail-container">
                                <h4>{news_site}</h4>
                                <small className="fw-semi-bold fs-small-100">{formattedZonedDateTime}</small>
                            <div className="card-detail">
                                <p>{title}</p>
                            </div>
                        </div>
                        <hr className="hr-7-sm bg-hr-600"/>
                        <div className="flex flex-wrap justify-center padding-block-2">
                            <a className="btn btn-primary" href={url} target="_blank"  rel="noopener noreferrer">
                                <FontAwesomeIcon icon={faNewspaper} /> Read Article
                            </a>
                            {soLaunchBtn && launches.length > 0 &&
                                <Link className="btn btn-primary" to={`/launches/${launches[0].launch_id}`} replace={true}>
                                    <FontAwesomeIcon icon={faShuttleSpace} /> View Launch
                                </Link>
                            }
                        </div>
                    </section>
                </div>
            </div>
        </article>
    );
};

export default NewsArticle;
