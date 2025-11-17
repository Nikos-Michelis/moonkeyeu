import React from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faImage } from '@fortawesome/free-regular-svg-icons';
const SkeletonPortraitLoader = ({container, media}) => {
    return (
        <article className={`portrait-card portrait-card__container ${container}`}>
                <div className={`skeleton skeleton__media ${media} bg-dark-cosmos-300 opacity-6`}>
                    <div className="skeleton__container skeleton__container--portrait">
                        <div className="skeleton__container--icon">
                            <FontAwesomeIcon icon={faImage} />
                        </div>
                    </div>
                </div>
                <div className="skeleton__info">
                    <div className="skeleton skeleton--text bg-dark-cosmos-300 opacity-6"></div>
                    <div className="skeleton skeleton--text bg-dark-cosmos-300 opacity-6"></div>
                    <div className="skeleton skeleton--text bg-dark-cosmos-300 opacity-6"></div>
                    <div className="margin-block-start-4">
                        <div className="skeleton skeleton--text bg-dark-cosmos-300 opacity-6"></div>
                    </div>
                </div>
        </article>
    );
};

export default SkeletonPortraitLoader;
