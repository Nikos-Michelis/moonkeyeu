import {Button} from "@/components/button/Button.jsx";
import {useModal} from "@/context/ModalProvider.jsx";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {faSpinner, faXmark} from '@fortawesome/free-solid-svg-icons';

export function PromptModal({ modalId }) {
    const { modals, closeModal, modalStatus } = useModal();
    const modal = modals[modalId] || { isOpen: false, data: {} };
    const {
        title = "Are you sure?",
        message = "This action cannot be undone.",
        details = [],
        confirmLabel = "Confirm",
        cancelLabel = "Cancel",
        hasConfirmBtn = true,
        confirmFn = () => {},
    } = modal.data || {};

    const handleClose = () => closeModal(modalId);

    if (!modal.isOpen) return null;

    return (
        <div className="form-popup-container bookmark-form-container">
            <Button
                onClick={handleClose}
                className="btn-transparent close-btn clr-dark-cosmos-300"
            >
                <FontAwesomeIcon icon={faXmark} />
            </Button>
            <div className="form-box small-form flex flex-column justify-center align-center">
                <div className="modal-header">
                    <h3>{title}</h3>
                </div>
                <div className="margin-inline-2 margin-block-8">
                    <ul className="padding-0">
                        {details.map((item, index) => (
                            <li key={index}>{item}</li>
                        ))}
                    </ul>
                </div>
            </div>
            <div className="flex flex-wrap justify-center">
                <Button
                    className="btn btn-primary"
                    onClick={handleClose}
                >
                    {cancelLabel}
                </Button>
                {hasConfirmBtn &&
                    <Button
                        className="btn btn-primary bg-warning-200"
                        onClick={confirmFn}
                        disabled={modalStatus?.[modalId]?.isPending}
                    >
                        {modalStatus?.[modalId]?.isPending && <FontAwesomeIcon icon={faSpinner} spin />} {confirmLabel}
                    </Button>
                }
            </div>
        </div>
    );
}
