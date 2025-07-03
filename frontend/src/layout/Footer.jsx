import React from "react";
import {LinkButton} from "@/components/button/LinkButton.jsx";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faLinkedin, faGithub } from '@fortawesome/free-brands-svg-icons';

const Footer = () => {
    const currentYear = new Date().getFullYear();
    return (
        <footer className="info_section">
            <div className="social_container">
                <div className="social_box">
                    <LinkButton
                        className="btn-transparent"
                        to="https://www.linkedin.com/in/nikolaos-michelis-91a921251/"
                        isExternal={true}
                    >
                        <FontAwesomeIcon icon={faLinkedin} />
                    </LinkButton>
                    <LinkButton
                        className="btn-transparent"
                        to="https://github.com/Nikos-Michelis/MoonkeyEu"
                        isExternal={true}
                    >
                        <FontAwesomeIcon icon={faGithub} />
                    </LinkButton>
                </div>
            </div>
            <div className="info-text-container">
                <div className="info-text">
                    <p>Thanks to
                        <LinkButton className="btn-transparent" to="https://github.com/TheSpaceDevs" isExternal={true}> The Space Devs</LinkButton> for the data feed.
                    </p>
                </div>
                <div className="info-text">
                    <p>MoonkeyEU Space Launch Tracker. Developed with ❤️ by
                        <LinkButton className="btn-transparent" to="https://github.com/Nikos-Michelis/MoonkeyEu" isExternal={true}> Michelis Nikolaos.</LinkButton>
                    </p>
                </div>
            </div>
            <div className="footer_section">
                <div className="more-info">
                    <p>
                        <LinkButton className="btn-transparent" to="https://nikos-michelis.github.io/">About</LinkButton> | <LinkButton to="/privacy" className="btn-transparent">Privacy Policy</LinkButton> | <LinkButton to="/contact" className="btn-transparent">Contact</LinkButton>
                    </p>
                </div>
                <hr/>
                <div className="container">
                    <p>
                        &copy; <span id="displayYear">{currentYear}</span> Copyright
                        <span> MoonkeyEU</span>
                    </p>
                </div>
            </div>
        </footer>
    );
};

export default Footer;
