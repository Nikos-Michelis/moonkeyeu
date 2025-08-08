import React from "react";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faImage } from '@fortawesome/free-regular-svg-icons';

function SkeletonBookMarkLoader(){

    return(
    <div className="bookmark-card container flex flex-column bg-hr-400 rounded-md" data-type="full-width">
        <div className="card-img-box skeleton bg-dark-cosmos-300 opacity-6 skeleton-img">
            <div className="bookmark-card__thumbnail">
                <div className="bookmark-card__icon flex justify-center align-center">
                    <FontAwesomeIcon icon={faImage} className="clr-star-300 fs-big-600" />
                </div>
            </div>
        </div>
        <div className="clr-star-300 padding-2">
            <div className="skeleton skeleton--text bg-dark-cosmos-300 opacity-6"></div>
            <div className="skeleton skeleton--subtitle bg-dark-cosmos-300 opacity-6"></div>
        </div>
    </div>
    )
}
export default SkeletonBookMarkLoader;