import React from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faImage } from '@fortawesome/free-regular-svg-icons';
const SkeletonLandscapeLoader = ({skeletonStyle, skeletonImgStyle}) => {
    return (
        <article className={`landscape-card-outer-wrapper flex justify-center ${skeletonStyle}`}>
            <div className="landscape-card-wrapper flex justify-center">
                <div className="card-info-container flex flex-column justify-center align-center">
                    <div className="card-img-box skeleton bg-dark-cosmos-300 opacity-6 skeleton-img">
                        <div className={`card-img ${skeletonImgStyle}`}>
                            <div className={`img-icon-box`}>
                                <FontAwesomeIcon icon={faImage} />
                            </div>
                        </div>
                    </div>
                    <div className="card-info-section flex flex-column justify-space-evenly skeleton-container">
                        <div className="skeleton bg-dark-cosmos-300 opacity-6 skeleton-text"></div>
                        <div className="skeleton bg-dark-cosmos-300 opacity-6 skeleton-text"></div>
                        <div className="skeleton bg-dark-cosmos-300 opacity-6 skeleton-text"></div>
                        <div className="skeleton bg-dark-cosmos-300 opacity-6 skeleton-text"></div>
                        <div className="skeleton bg-dark-cosmos-300 opacity-6 skeleton-text"></div>
                        <div className="skeleton bg-dark-cosmos-300 opacity-6 skeleton-text"></div>
                    </div>
                </div>
            </div>
        </article>
    );
};

export default SkeletonLandscapeLoader;
