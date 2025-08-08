import React from "react";
import {Button} from "@/components/button/Button.jsx";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faMagnifyingGlass, faXmark} from "@fortawesome/free-solid-svg-icons";

const ModalButton =({triggerRef, setOpen, open}) => {
    const toggleOptions = (isClicked) => {
        setOpen((prevState) => prevState !== isClicked);
    };
    return (
        <div className="z-overlay margin-inline-2">
            <Button
                ref={triggerRef}
                className="btn--transparent bg-star-200 padding-2 rounded-xxl box-shadow-4"
                onClick={() => toggleOptions(true)}>
                {!open
                    ? <FontAwesomeIcon icon={faMagnifyingGlass} className="clr-dark-cosmos-300 fs-small-400" />
                    : <FontAwesomeIcon icon={faXmark} className="clr-dark-cosmos-300 fs-small-600"/>}
            </Button>
        </div>
    )
}
export default ModalButton;