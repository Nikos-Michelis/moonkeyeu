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
            <section className="error">
                <div className="container flex flex-column justify-center text-center" data-type="wide" data-height="full">
                    {code && <h1 className="error__code">{code}</h1>}
                    {heading &&
                        <div>
                            <FontAwesomeIcon icon={faGears} />
                            <h1 className="error__heading">{heading}</h1>
                        </div>
                    }
                    <hr className="hr-75-xs" />
                    {message && <h2 className="error__message">{message}</h2>}
                    {error && <p className="error_details">{error}</p>}
                    <hr className="hr-75-xs" />
                </div>
            </section>
        </>
    );

}
export default FallbackComponent;