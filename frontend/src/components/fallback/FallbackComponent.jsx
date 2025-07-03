import React from "react";
import Head from "@/components/seo/Head.jsx";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faGears } from '@fortawesome/free-solid-svg-icons';

const FallbackComponent = ({code = "", heading= "", message= "", error= ""}) =>{
    return(
        <>
            <Head
                title={code.toString()}
            />
            <section className="error-section">
                <div className="container __error flex" data-height="full">
                    <div className="container flex flex-column justify-center text-center" data-type="wide">
                        {code && <h1>{code}</h1>}
                        {heading &&
                            <div>
                                <FontAwesomeIcon icon={faGears} />
                                <h1 className="heading-message">{heading}</h1>
                            </div>
                        }
                        <hr className="hr-5-xs" />
                        {message && <h2>{message}</h2>}
                        {error && <p>{error}</p>}
                        <hr className="hr-5-xs" />
                    </div>
                </div>
            </section>
        </>
    );

}
export default FallbackComponent;