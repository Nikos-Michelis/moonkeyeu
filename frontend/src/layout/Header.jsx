import React, {useEffect} from "react";
import {useModal} from "@/context/ModalProvider.jsx";
import {useAuth} from "@/context/AuthProvider.jsx";
import {Button} from "@/components/button/Button.jsx";
import {Link, NavLink} from "react-router-dom";
import toast from "react-hot-toast";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faRocket, faUserAstronaut } from '@fortawesome/free-solid-svg-icons';
import { faUser, faBookmark } from '@fortawesome/free-regular-svg-icons';

const Header = () => {
    const { openModal } = useModal();
    const {user, status } = useAuth();

    const onBookmark = () => {
        openModal("PopUpFormModal", null, "form");
        toast(
            "You're almost there! Sign up or log in to bookmark your favorites launches.", {
                icon: <FontAwesomeIcon icon={faRocket} />
            });
    }

    return (

        <header>
            <nav className="navbar">
                <div className="navbar__brand-box">
                    <Link className="navbar__image-link" to="launches">
                        <img className="navbar__brand-icon" src={`${import.meta.env.VITE_CLOUDFRONT_URL}/assets/logo/moonkeyeu-logo.svg`} alt="MoonkeyEU logo"/>
                    </Link>
                    <div className="navbar__brand-title">
                        <h1>MoonkeyEU</h1>
                    </div>
                </div>

                <input className="navbar__hamburger-checkbox" type="checkbox" id="checkbox"/>
                <label className="navbar__hamburger-label" htmlFor="checkbox" aria-label="Toggle Navigation">
                    <span className="navbar__hamburger-line navbar__hamburger-line--top" aria-hidden="true"></span>
                    <span className="navbar__hamburger-line navbar__hamburger-line--middle" aria-hidden="true"></span>
                    <span className="navbar__hamburger-line navbar__hamburger-line--bottom" aria-hidden="true"></span>
                </label>

                <div className="navbar__nav-container">
                    <ul className="navbar__nav-list">
                        <li className="navbar__nav-item">
                            <NavLink className="navbar__nav-link" to="launches">Launches</NavLink>
                        </li>
                        <li className="navbar__nav-item">
                            <NavLink className="navbar__nav-link" to="astronauts">Astronauts</NavLink>
                        </li>
                        <li className="navbar__nav-item">
                            <NavLink className="navbar__nav-link" to="programs">Programs</NavLink>
                        </li>
                        <li className="navbar__nav-item">
                            <NavLink className="navbar__nav-link" to="agencies">Agencies</NavLink>
                        </li>
                        <li className="navbar__nav-item">
                            <NavLink className="navbar__nav-link" to="vehicles">Vehicles</NavLink>
                        </li>
                        <li className="navbar__nav-item">
                            <NavLink className="navbar__nav-link" to="news">News</NavLink>
                        </li>
                        <li className="navbar__nav-item">
                            <NavLink className="navbar__nav-link" to="locations">Locations</NavLink>
                        </li>

                        <div className="navbar__user-options">
                            {!status.isPending ? (
                                !!user ? (
                                    <NavLink
                                        to="profile"
                                        type="submit"
                                        className="navbar__user-link btn--transparent">
                                        <FontAwesomeIcon icon={faUserAstronaut} />
                                    </NavLink>
                                ) : (
                                    <Button
                                        type="button"
                                        className="navbar__user-button btn--transparent"
                                        onClick={() => openModal("PopUpFormModal", null, "form")}>
                                        <FontAwesomeIcon icon={faUser} />
                                    </Button>
                                )
                            ) : (
                                <div>
                                    <div className="skeleton skeleton--circle bg-dark-cosmos-300"></div>
                                </div>
                            )}

                            {!status.isPending ? (
                                !!user ? (
                                    <NavLink
                                        className="navbar__user-link btn--transparent"
                                        to="/bookmarks">
                                        <FontAwesomeIcon icon={faBookmark} />
                                    </NavLink>
                                ) : (
                                    <Button
                                        type="button"
                                        className="navbar__user-button btn--transparent"
                                        onClick={() => onBookmark()}>
                                        <FontAwesomeIcon icon={faBookmark} />
                                    </Button>
                                )
                            ) : (
                                <div>
                                    <div className="skeleton skeleton--circle bg-dark-cosmos-300"></div>
                                </div>
                            )}
                        </div>
                    </ul>
                </div>
            </nav>
        </header>
    );
};

export default Header;
