import React, {useEffect} from 'react';
import {Button} from "@/components/button/Button.jsx";
import {useModal} from "@/context/ModalProvider.jsx";
import {useDeleteMutation} from "@/services/mutations.jsx";
import toast from "react-hot-toast";
import {DateTime} from "luxon";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faTrash } from '@fortawesome/free-solid-svg-icons';

const MessageCard = ({id, category, email, message, created_at}) => {
    const zonedDateTime = DateTime.fromISO(created_at).setZone(DateTime.local().zoneName);
    const formattedZonedDateTime = zonedDateTime.toFormat('MMMM dd, yyyy - hh:mm a');
    const baseUrl = `${import.meta.env.VITE_BACKEND_BASE_URL}/dashboard/contact/messages`;
    const { openModal, closeModal, setStatus } = useModal();

    const removeMessageMutation =
        useDeleteMutation({
            queryKeysToInvalidate: ["contact-messages"],
            mutationOptions: {
                onSuccess: () => {
                    closeModal("deleteMessage");
                    toast.success("Message successfully deleted!");
                },
            },
        });
    const handleRemove = (id) => {
        removeMessageMutation.mutate(
            {
                url: `${baseUrl}/delete/${id}`,
                options: { withCredentials: true, Bearer: true },
            }
        );
    };


    useEffect(() => {
        setStatus("deleteMessage", { isPending: removeMessageMutation.isPending});
    }, [removeMessageMutation.isPending]);

    return (
        <article className="landscape-card flex justify-center small-wrapper">
            <div className="landscape-card__container">
                <section className="landscape-card__content flex flex-column justify-space-evenly">
                    <div className="panel">
                        <h4 className="panel__title">{category}</h4>
                        <hr/>
                        <div className="panel__wrapper">
                            <div className="panel__container">
                                <div className="panel__detail-box fs-small-100 padding-1">
                                    <p className="panel__text">Email</p>
                                    <p className="panel__text">{email}</p>
                                </div>
                                <div className="panel__detail-box fs-small-100 padding-1">
                                    <p className="panel__text">CreatedAt</p>
                                    <p className="panel__text">{formattedZonedDateTime}</p>
                                </div>
                            </div>
                            <hr/>
                            <div className="panel__container padding-1">
                                <div className="panel__detail-box fs-small-100 padding-2"
                                     data-spacing="none"
                                     data-scroll={message.length> 2  ? "vertical" : undefined}>
                                    <p className="panel__text padding-block-2">{message}</p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div className="landscape-card__actions flex flex-wrap justify-center padding-block-2">
                        <Button
                            className="btn btn--primary bg-warning-200"
                            onClick={() =>
                                openModal("deleteMessage", {
                                    title: "Delete Message - Are you sure?",
                                    details: ["This action is permanent.", "This action cannot be reversed.",],
                                    confirmLabel: "Confirm Deletion",
                                    cancelLabel: "Cancel",
                                    confirmFn: () => { handleRemove(id) },
                                }, "prompt")
                            }>
                            <FontAwesomeIcon icon={faTrash} /> Delete
                        </Button>
                    </div>
                </section>
            </div>
        </article>
    );
};

export default MessageCard;
