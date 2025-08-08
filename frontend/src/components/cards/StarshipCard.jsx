import React from "react";
import {Link} from "react-router-dom";
import Img from "@/components/utils/Img.jsx";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faCloud, faArrowPointer, faArrowRight } from '@fortawesome/free-solid-svg-icons';
const StarshipCard = () => {
    return (
        <section className="starship-news">
            <div className="flex flex-column justify-center align-center margin-4">
                <div className="sidebar container flex flex-column justify-center align-center bg-secondary-300 padding-4" data-type="full-bleed">
                    <div className="sidebar__heading-box">
                        <h3 className="ff-accent">Starship</h3>
                    </div>
                    <div className="container margin-block-end-2">
                        <article className="portrait-card margin-block-4">
                            <div className="portrait-card__container portrait-card__container--small bg-dark-cosmos-300 flex flex-column">
                                <div className="starship-animation environment flex flex-column justify-center align-center">
                                    <div className="starship-container">
                                        <Img
                                            src={`${import.meta.env.VITE_CLOUDFRONT_URL}/assets/icons/starship-icon.png`}
                                            alt="Starship"
                                            className="img-starship"
                                        />
                                    </div>
                                    <div className="clouds">
                                        <div className="cloud-box">
                                           <FontAwesomeIcon icon={faCloud} className="cloud fa-xl" />
                                           <FontAwesomeIcon icon={faCloud} className="cloud fa-xl" />
                                        </div>
                                        <div className="cloud-box">
                                           <FontAwesomeIcon icon={faCloud} className="cloud fa-xl" />
                                           <FontAwesomeIcon icon={faCloud} className="cloud fa-xl" />
                                        </div>
                                    </div>
                                    <div className="cursor-box flex justify-center align-center">
                                        <FontAwesomeIcon icon={faArrowPointer} className="cursor" />
                                    </div>
                                    <div className="ground"></div>
                                </div>
                            </div>
                        </article>
                    </div>
                    <div className="flex justify-center">
                        <Link className="btn btn--primary btn-lg"  to="/programs/1">
                            <FontAwesomeIcon icon={faArrowRight} /> VIEW STARSHIP
                        </Link>
                    </div>
                </div>
            </div>
        </section>
    );
}
export default StarshipCard;