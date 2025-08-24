import React, {useRef, useState} from "react";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faAngleDown, faAngleUp, faLink} from "@fortawesome/free-solid-svg-icons";
import {LinkButton} from "@/components/button/LinkButton.jsx";
import {faXTwitter, faYoutube} from "@fortawesome/free-brands-svg-icons";
import {useClickOutside} from "@/hooks/util/useClickOutside.jsx";

const YoutubeDropdown = ({options, setVideo, placeholder, defaultValue}) => {
    const [isOpen, setIsOpen] = useState(false);
    const dropdownRef = useRef(null);
    const isYoutubeUrl = (option) => {
        return (option?.videoUrl?.includes("youtube.com") || option?.videoUrl?.includes("youtu.be"))
    };
    const handleIconDisplay = (option) => {
        if (option.source === "x.com"){
            return faXTwitter;
        } else if (option.source === "youtube.com" || isYoutubeUrl(option)) {
            return faYoutube;
        } else {
            return faLink
        }
    }
    const handleUrlIcon = (option) => {
        return(
            <FontAwesomeIcon icon={handleIconDisplay(option)} />
        )
    }

    const handleClickOutside = (event) => {
        if (dropdownRef.current && !dropdownRef.current.contains(event.target)) {
            setIsOpen(false);
        }
    };

    useClickOutside({ modalRef: dropdownRef, handler: handleClickOutside });

    return(
        <div className="select" ref={dropdownRef}>
            <div className="select__btn select__btn--yt-video" onClick={() => setIsOpen(!isOpen)}>
                <div className="flex flex-column">
                    <div className="flex justify-center align-center">
                        <FontAwesomeIcon icon={faYoutube} className="margin-inline-end-1 fs-small-300"/>
                        <h2 className="fs-small-300">{placeholder}</h2>
                    </div>
                    <p>Official Livestream</p>
                </div>
                <FontAwesomeIcon icon={isOpen ? faAngleUp : faAngleDown} />
            </div>
            {isOpen && (
                <div className="select__content select__content--full-width">
                    <div className="select__options">
                        {options.length > 0 ? (
                            options.map((option) => (
                                <LinkButton
                                    className={`btn--transparent ${defaultValue === option?.priority ? "selected" : ""}`}
                                    to={!isYoutubeUrl(option) && option.videoUrl }
                                    key={option.id}
                                    onClick={() => setVideo(option)}
                                    isExternal={true}
                                >
                                    <span className="margin-inline-2">{handleUrlIcon(option)}</span>
                                    <span className="ellipsis-1-lines">{option?.title}</span>
                                </LinkButton>
                            ))
                        ) : (
                            <div>No results found</div>
                        )}
                    </div>
                </div>
            )}
        </div>
    );
};

export default YoutubeDropdown;
