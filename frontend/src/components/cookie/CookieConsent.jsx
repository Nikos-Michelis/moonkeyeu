import {useCookies} from "react-cookie";
import {Button} from "@/components/button/Button.jsx";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faCookieBite } from '@fortawesome/free-solid-svg-icons';

export default function CookieConsent(){
    const[cookies, setCookie] = useCookies(["cookieConsent"])
    const giveCookieConsent = () => {
        const expirationDate = new Date();
        expirationDate.setDate(expirationDate.getDate() + 365);
        setCookie("cookieConsent", true, { path: "/", expires: expirationDate });
    };
    return(
        <div className="container pos-fixed bottom-0 left-0 flex flex-wrap justify-space-between align-center clr-dark-cosmos-300 bg-main-300 border-t-xs border-star-300 z-overlay" data-type="full-width">
            <div className="container flex flex-wrap justify-space-between align-center margin-inline-4" data-type="full-bleed">
                <div>
                    <FontAwesomeIcon icon={faCookieBite} className="fs-small-800 margin-4" />
                </div>
                <div className="container flex flex-column margin-4" data-type="wide">
                    <p>
                        We use cookies to provide a personalized experience and ensure the normal operation of this website.
                        With your consent, you'll have access to features like bookmarks and other tailored functionalities.
                        Without them, the smooth operation is directly affected. By continuing to use this site, you agree to our use of cookies.
                    </p>
                </div>
                <Button className="btn btn-primary clr-secondary-300 margin-4" onClick={giveCookieConsent}>I understand</Button>
            </div>
        </div>
    );
}