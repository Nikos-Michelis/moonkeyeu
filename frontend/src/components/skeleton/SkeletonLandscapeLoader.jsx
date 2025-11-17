import React from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faImage } from '@fortawesome/free-regular-svg-icons';
const SkeletonLandscapeLoader = ({wrapper, img, card_type}) => {
    return (
        <article className={`landscape-card flex justify-center ${wrapper}`}>
            <div className={`landscape-card__container ${card_type}`}>
                <div className="landscape-card__media skeleton bg-dark-cosmos-300 opacity-6">
                    <div className={`skeleton__container ${`skeleton__container${img}` || ''}`}>
                        <div className="skeleton__container--icon">
                            <FontAwesomeIcon icon={faImage} />
                        </div>
                    </div>
                </div>
                <div className="skeleton__info skeleton__info--landscape">
                    <div className="skeleton skeleton--text bg-dark-cosmos-300 opacity-6"></div>
                    <div className="skeleton skeleton--text bg-dark-cosmos-300 opacity-6"></div>
                    <div className="skeleton skeleton--text bg-dark-cosmos-300 opacity-6"></div>
                    <div className="skeleton skeleton--text bg-dark-cosmos-300 opacity-6"></div>
                    <div className="skeleton skeleton--text bg-dark-cosmos-300 opacity-6"></div>
                    <div className="skeleton skeleton--text bg-dark-cosmos-300 opacity-6"></div>
                </div>
            </div>
        </article>
    );
};

export default SkeletonLandscapeLoader;
