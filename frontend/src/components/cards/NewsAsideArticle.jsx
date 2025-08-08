import React from "react";
import Img from "@/components/utils/Img.jsx";
const NewsAsideArticle = ({imageSrc, title, author, url}) => {
    return (
        <article className="portrait-card margin-block-4">
            <div className="portrait-card__container portrait-card__container--small bg-dark-cosmos-300 flex flex-column">
                <a href={url} className="portrait-card__image-link" target="_blank" rel="noopener noreferrer">
                    <Img
                        src={imageSrc}
                        alt={title || "default"}
                        className={`portrait-card__sidebar-img`}
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

export default NewsAsideArticle;
