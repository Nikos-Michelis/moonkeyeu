import React from 'react';
const SkeletonSidebarLoader = () => {
    return (
        <article className="portrait-card margin-block-3">
            <div className="portrait-card__container portrait-card__container--small">
                <span className="card-loader"></span>
            </div>
        </article>
    );
};

export default SkeletonSidebarLoader;
