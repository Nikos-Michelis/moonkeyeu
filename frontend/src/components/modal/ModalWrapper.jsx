import React, {useRef} from "react";
import {useModal} from "@/context/ModalProvider.jsx";
const ModalWrapper = ({children}) => {
    const { currentModalId, modals } = useModal();
    const modalRef = useRef(null);
    const modal = modals[currentModalId] || {};
    return (
        <>
            <div className={`blur-bg-overlay${(modal.isOpen && (modal.type !== "dropdown" )) ? " active" : ""}`}></div>
            <div ref={modalRef} className={`modal-wrapper${(modal.isOpen && (modal.type !== "dropdown" )) ? " active" : ""}`}>
                {children}
            </div>
        </>
    );
}
export default ModalWrapper;