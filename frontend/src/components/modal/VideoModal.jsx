import React from 'react';
import {YouTubeEmbed} from "@/components/api/youtube-window/YouTubeEmbed.jsx";
import {useModal} from "@/context/ModalProvider.jsx";
import {Button} from "@/components/button/Button.jsx";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faXmark } from '@fortawesome/free-solid-svg-icons';

export function VideoModal() {
    const { modals, closeModal } = useModal();
    const modal = modals["videoPlayerModal"] || { isOpen: false, data: null };

    if (!modal.isOpen || !modal.data) return null;
    const handleClose = () => {
        closeModal("videoPlayerModal");
    };

    return(
        <div className="modal">
            <div className="modal-container modal-youtube">
                <Button
                    onClick={handleClose}
                    className="btn--transparent fs-small-600 pos-fixed top-0 right-0">
                    <FontAwesomeIcon icon={faXmark} />
                </Button>
                <div className="modal-content">
                    <YouTubeEmbed videoUrl={modal.data} />
                </div>
            </div>
        </div>
    );
}