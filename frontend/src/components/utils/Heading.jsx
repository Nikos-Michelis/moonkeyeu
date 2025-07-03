import React from "react";

function Heading({ title, description }) {
    return (
        <div className="container flex flex-column text-center padding-block-start-14 padding-block-end-10">
            <h1 className="clr-star-300">{title}</h1>
            <p className="fs-small-500 text-center clr-star-300">{description}</p>
            <hr className="hr-3-xs" />
        </div>
    );
}

export default Heading;
