import React from 'react';
import {LinkButton} from "@/components/button/LinkButton.jsx";
export const VehicleOptionCard = ({ disable, title, description, linkText, sectionImage, link }) => {
    return (
        <>
            <article className="portrait-card-wrapper vehicle-card-option flex justify-space-around" style={{backgroundImage: `url(${sectionImage})`}}>
                <div className="portrait-card-container flex flex-column justify-center">
                    <h1>{title}</h1>
                    <p>{description}</p>
                    <div className="flex justify-center flex-wrap">
                        <LinkButton className="btn btn-primary" to={link} disabled={disable}>{linkText} </LinkButton>
                    </div>
                </div>
            </article>
        </>

    );
};
