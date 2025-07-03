import React from "react";
import Img from "@/components/utils/Img.jsx";
const NewsArticle = ({imageSrc, title, author, url}) => {
    return (
        <article className="portrait-card-wrapper small-card flex flex-column margin-block-4 bg-dark-cosmos-300">
            <div className="portrait-card-container flex flex-column">
                <a href={url} className="sidebar-image-container" target="_blank" rel="noopener noreferrer">
                    <Img
                        src={imageSrc}
                        alt={title || "default"}
                        className={`sidebar-image`}
                        defaultSrc={`${import.meta.env.VITE_CLOUDFRONT_URL}/assets/logo/moonkeyeu-logo.svg`}
                    />
                </a>
                <div className="padding-block-start-3 padding-block-end-2 padding-inline-1">
                    <p className="fs-small-100 clr-secondary-200 text-center fw-regular">{title}</p>
                </div>
                <div className="padding-block-start-3 padding-block-end-2 padding-inline-end-1">
                    <p className="fs-small-100 clr-secondary-200 text-end fw-regular">{author}</p>
                </div>
            </div>
        </article>
    );
};

export default NewsArticle;
