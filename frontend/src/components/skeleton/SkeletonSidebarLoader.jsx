import React from 'react';
const SkeletonSidebarLoader = () => {
    return (
        <article className="portrait-card-wrapper small-card flex flex-column margin-block-2">
            <div className="portrait-card-container flex flex-column">
                <div className="sidebar-articles-list">
                    <span className="card-loader"></span>
                </div>
            </div>
        </article>
    );
};

export default SkeletonSidebarLoader;
