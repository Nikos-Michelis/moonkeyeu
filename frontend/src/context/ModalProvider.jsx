import React, {createContext, useContext, useEffect, useState} from 'react';

const ModalContext = createContext();
export const ModalProvider = ({ children }) => {
    const [modals, setModals] = useState({});
    const [modalStatus, setModalStatus] = useState({});
    const [currentModalId, setCurrentModalId] = useState(null);

    const openModal = (modalId, modalData, modalType, triggerRef) => {
        setModals((prev) => {
            const isSameModalOpen = currentModalId === modalId && prev[modalId]?.isOpen;
            const newModals = {};

            if (isSameModalOpen) {
                setCurrentModalId(null);
                return newModals;
            }

            newModals[modalId] = {
                isOpen: true,
                data: modalData,
                type: modalType,
                ref: triggerRef
            };
            setCurrentModalId(modalId);

            return newModals;
        });
    };

    const closeModal = (modalId) => {
        setModals(prev => (({ [modalId]: _, ...rest }) => rest)(prev));
        if (currentModalId === modalId) {
            setCurrentModalId(null);
        }
    };

    const clearAllModals = () => {
        setModals({});
        setCurrentModalId(null);
    };

    const setStatus = (modalId, status) => {
        setModalStatus((prev) => ({
            ...prev,
            [modalId]: {
                ...prev[modalId],
                ...status,
            },
        }));
    };

    useEffect(() => {
        const isAnyModalOpen = Object.values(modals).some(modal => modal?.isOpen && modal?.type !=="dropdown");

        if (isAnyModalOpen) {
            document.body.style.overflow = "hidden";
        } else {
            document.body.style.overflow = "";
        }

        return () => {
            document.body.style.overflow = "";
        };
    }, [modals]);

    return (
        <ModalContext.Provider value={{ modals, openModal, closeModal, clearAllModals, modalStatus, setStatus, currentModalId }}>
            {children}
        </ModalContext.Provider>
    );
};

export const useModal = () => {
    const modalContext = useContext(ModalContext);
    if (!modalContext) {
        throw new Error("ModalContext must be used within ModalProvider");
    }
    return modalContext;
};

