import { useModal } from "@/context/ModalProvider.jsx";
import { PromptModal } from "@/components/modal/PromptModal.jsx";
import {VideoModal} from "@/components/modal/VideoModal.jsx";
import PopUpForm from "@/components/modal/forms/PopUpForm.jsx";
import {AddBookmarkForm} from "@/components/modal/forms/AddBookmarkForm.jsx";
import {EditBookmarkForm} from "@/components/modal/forms/EditBookmarkForm.jsx";
import BookmarkDropdown from "@/components/modal/dropdown/BookmarkDropdown.jsx";
import ToastPortal from "@/portals/ToastPortal.jsx";
import React from "react";
import ModalWrapper from "@/components/modal/ModalWrapper.jsx";
const ModalLayout = () => {
    const { currentModalId, modals } = useModal();
    const modal = modals[currentModalId] || {};
    return (
        <>
            <ModalWrapper>
                <VideoModal />
                <PopUpForm />
                <AddBookmarkForm />
                <EditBookmarkForm />
                {modal.type === "prompt" && <PromptModal key={currentModalId} modalId={currentModalId} /> }
            </ModalWrapper>
            <BookmarkDropdown />
            <ToastPortal />

        </>
    );
};


export default ModalLayout;
