import React from "react";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faImage } from '@fortawesome/free-regular-svg-icons';

function SkeletonBookMarkLoader(){

    return(
    <div className="container __bookmark flex flex-column bg-hr-400 rounded-md" data-type="full-width">
        <div className="card-img-box skeleton bg-dark-cosmos-300 opacity-6 skeleton-img">
            <div className="thumbnail">
                <div className="img-icon-box flex justify-center align-center">
                    <FontAwesomeIcon icon={faImage} className="clr-star-300 fs-big-600" />
                </div>
            </div>
        </div>
        <div className="clr-star-300 padding-2">
            <div className="flex justify-space-between align-center">
                <div className="skeleton bg-dark-cosmos-300 opacity-6 skeleton-text"></div>
            </div>
            <div className="skeleton bg-dark-cosmos-300 opacity-6 skeleton-subtitle"></div>
        </div>
    </div>
    )
}
export default SkeletonBookMarkLoader;