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
                <div className="brand-box">
                    <Link className="brand-image-box" to="launches">
                        <img className="brand-image" src={`${import.meta.env.VITE_CLOUDFRONT_URL}/assets/logo/moonkeyeu-logo.svg`} alt="MoonkeyEU logo"/>
                    </Link>
                    <div className="navbar-brand">
                        <h1>MoonkeyEU</h1>
                    </div>
                </div>

                <input className="hamb-checkbox" type="checkbox" id="checkbox"/>
                    <label className="hamb-label" htmlFor="checkbox" aria-label="Toggle Navigation">
                        <span className="line1 hamb-line" aria-hidden="true"></span>
                        <span className="line2 hamb-line" aria-hidden="true"></span>
                        <span className="line3 hamb-line" aria-hidden="true"></span>
                    </label>
                <div className="nav-list-container">
                    <ul className="nav-list">
                        <li className="nav-item"><NavLink to="launches">Launches</NavLink></li>
                        <li className="nav-item"><NavLink to="astronauts">Astronauts</NavLink></li>
                        <li className="nav-item"><NavLink to="programs">Programs</NavLink></li>
                        <li className="nav-item" ><NavLink to="agencies">Agencies</NavLink></li>
                        <li className="nav-item"><NavLink to="vehicles">Vehicles</NavLink></li>
                        <li className="nav-item"><NavLink to="news">News</NavLink></li>
                        <li className="nav-item"><NavLink to="locations">Locations</NavLink></li>
                        <div className="user-option">
                            {!status.isPending ?
                                (!!user ?
                                    <NavLink to="profile" type="submit" className="btn-transparent">
                                        <FontAwesomeIcon icon={faUserAstronaut} />
                                    </NavLink>
                                    :
                                    <Button type="button" className="btn-transparent" onClick={() => openModal("PopUpFormModal", null, "form")}>
                                        <FontAwesomeIcon icon={faUser} />
                                    </Button>
                                )
                                :
                                <div>
                                    <div className="skeleton bg-dark-cosmos-300 skeleton-circle"></div>
                                </div>
                            }
                            {!status.isPending ?
                                (!!user ?
                                    <NavLink className="btn-transparent" to="/bookmarks">
                                        <FontAwesomeIcon icon={faBookmark} />
                                    </NavLink>
                                    :
                                    <Button type="button" className="btn-transparent" onClick={() => onBookmark()}>
                                        <FontAwesomeIcon icon={faBookmark} />
                                    </Button>
                                )
                                :
                                <div>
                                    <div className="skeleton bg-dark-cosmos-300 skeleton-circle"></div>
                                </div>
                            }
                        </div>
                    </ul>
                </div>
            </nav>
        </header>
    );
};

export default Header;
