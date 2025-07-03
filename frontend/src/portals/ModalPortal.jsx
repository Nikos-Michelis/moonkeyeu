import React, {useCallback, useRef} from "react";
import { createPortal } from "react-dom";
import {useModal} from "@/context/ModalProvider.jsx";
import {useClickOutside} from "@/hooks/util/useClickOutside.jsx";

const modalRoot = document.getElementById("portal");

export const ModalPortal = ({ children }) => {
    const { closeModal, currentModalId, modals } = useModal();
    const modalRef = useRef(null);
    const modal = modals[currentModalId] || {};

    const closeDropdown = useCallback(() => {
        closeModal(currentModalId);
    }, [closeModal, currentModalId]);

    useClickOutside({ modalRef: modalRef, triggerRef: modal?.ref, handler: modal.type === "dropdown" ? closeDropdown : undefined });

    return createPortal(
        <div
            style={
            modal.type !== "dropdown" ? {
                position: 'fixed',
                zIndex: 9999,
                inset: '12px',
                pointerEvents: 'none'
            } : undefined}
            ref={modalRef}
            onClick={(e) => e.stopPropagation()}
        >
            {children}
        </div>,
        modalRoot
    );

};
