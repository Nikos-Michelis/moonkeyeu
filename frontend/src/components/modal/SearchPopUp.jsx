import React, {useRef, useState} from "react";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faSearch} from "@fortawesome/free-solid-svg-icons";
import ModalButton from "@/components/button/ModalButton.jsx";
import {useClickOutside} from "@/hooks/util/useClickOutside.jsx";

const SearchPopUp = ({setSearchValue, value}) => {
    const [open, setOpen] = useState(false);
    const searchRef = useRef(null);
    const triggerRef = useRef(null);
    const handleClickOutside = (event) => {
        if (searchRef.current && !searchRef.current.contains(event.target)) {
            setOpen(false);
        }
    };

    useClickOutside({
        modalRef: searchRef,
        triggerRef: triggerRef,
        handler: handleClickOutside
    });
    return (
        <div className="flex justify-end align-center pos-fixed bottom-8 left-0 right-0 z-overlay margin-block-4 margin-inline-2">
                <div ref={searchRef} className={`box-shadow-2 width-fade ${ open ? "show md" : ""}`}>
                    <div className="flex justify-space-between" data-type="full-width">
                        <div className="search-container flex justify-end">
                            <input
                                className="searchbar search-small"
                                value={value || ""}
                                type="text"
                                name="search"
                                placeholder="e.g. SpaceX Starbase"
                                onChange={(e) => setSearchValue(e.target.value)}

                            />
                            <div className="search-button">
                                <FontAwesomeIcon icon={faSearch} />
                            </div>
                        </div>
                    </div>
                </div>
            <ModalButton triggerRef={triggerRef} setOpen={setOpen} open={open}/>
        </div>
    );
}
export default SearchPopUp;