import React from "react";
import {LinkButton} from "@/components/button/LinkButton.jsx";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faLinkedin, faGithub } from '@fortawesome/free-brands-svg-icons';

const Footer = () => {
    const currentYear = new Date().getFullYear();
    return (
        <footer className="footer">
            <div className="footer__social_box padding-8">
                <LinkButton
                    className="btn--transparent"
                    to="https://www.linkedin.com/in/nikolaos-michelis-91a921251/"
                    isExternal={true}
                >
                    <FontAwesomeIcon icon={faLinkedin} />
                </LinkButton>
                <LinkButton
                    className="btn--transparent"
                    to="https://github.com/Nikos-Michelis/MoonkeyEu"
                    isExternal={true}
                >
                    <FontAwesomeIcon icon={faGithub} />
                </LinkButton>
            </div>
            <div className="footer__container footer__container--start">
                <div className="footer__info-box">
                    <p>Thanks to
                        <LinkButton className="btn--transparent" to="https://github.com/TheSpaceDevs" isExternal={true}> The Space Devs</LinkButton> for the data feed.
                    </p>
                </div>
                <div className="footer__info-box">
                    <p>MoonkeyEU Space Launch Tracker. Developed with ❤️ by
                        <LinkButton className="btn--transparent" to="https://github.com/Nikos-Michelis/MoonkeyEu" isExternal={true}> Michelis Nikolaos.</LinkButton>
                    </p>
                </div>
            </div>
            <div className="footer__container footer__container--center">
                <div className="footer__info-box clr-star-200">
                    <span>
                        <LinkButton className="btn--transparent" to="https://nikos-michelis.github.io/">About</LinkButton> | <LinkButton to="/privacy" className="btn--transparent">Privacy Policy</LinkButton> | <LinkButton to="/contact" className="btn--transparent">Contact</LinkButton>
                    </span>
                </div>
                <hr className="hr-30-sm bg-hr-400"/>
                <div className="footer__info-box--copyright">
                    <p>
                        &copy; <span>{currentYear}</span> Copyright
                        <span> MoonkeyEU</span>
                    </p>
                </div>
            </div>
        </footer>
    );
};

export default Footer;
