import React from "react";

import { useState } from "react";

const Img = ({ src, alt, className, defaultSrc }) => {
    const [imgSrc, setImgSrc] = useState(src || defaultSrc);

    return (
        <img
            loading="lazy"
            src={imgSrc}
            alt={alt || "image"}
            className={className}
            onError={() => setImgSrc(defaultSrc)}
        />
    );
};

export default Img;

