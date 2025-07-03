import React from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faImage } from '@fortawesome/free-regular-svg-icons';
const SkeletonPortraitLoader = () => {
    return (
        <article className="portrait-card-wrapper">
            <div className="portrait-card-container">
                <div className="card-img-box skeleton bg-dark-cosmos-300 opacity-6 skeleton-img">
                    <div className="card-img">
                        <div className="img-icon-box">
                            <FontAwesomeIcon icon={faImage} />
                        </div>
                    </div>
                </div>
                <div className="skeleton-container">
                    <div className="skeleton bg-dark-cosmos-300 opacity-6 skeleton-text"></div>
                    <div className="skeleton bg-dark-cosmos-300 opacity-6 skeleton-text"></div>
                    <div className="skeleton bg-dark-cosmos-300 opacity-6 skeleton-text"></div>
                    <div className="margin-block-start-4">
                        <div className="skeleton bg-dark-cosmos-300 opacity-6 skeleton-text"></div>
                    </div>
                </div>
            </div>
        </article>
    );
};

export default SkeletonPortraitLoader;
