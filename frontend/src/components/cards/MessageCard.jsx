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
        <article className="landscape-card-outer-wrapper flex justify-center small-wrapper">
            <div className="landscape-card-wrapper flex justify-center">
                <div className="card-info-container flex flex-column justify-center align-center">
                    <section className="card-info-section flex flex-column justify-space-evenly">
                        <div className="card-detail-container">
                            <div className="panel-body">
                                <h4 className="rocket-name">{category}</h4>
                                <hr className="hr-7-sm bg-hr-600"/>
                                <div className="panel-info-wrapper">
                                    <div className="panel-info-container">
                                        <div className="detail-wrapper fs-small-100 padding-1">
                                            <p className="info-panel-row">Email</p>
                                            <p className="info-panel-row">{email}</p>
                                        </div>
                                        <div className="detail-wrapper fs-small-100 padding-1">
                                            <p className="info-panel-row">CreatedAt</p>
                                            <p className="info-panel-row">{formattedZonedDateTime}</p>
                                        </div>
                                    </div>
                                    <hr className="hr-7-sm bg-hr-600"/>
                                    <div className="panel-info-container padding-1">
                                        <div className={`detail-wrapper fs-small-100 padding-2 ${message.length >= 50 && "scrolling"}`}>
                                            <p className="description-panel padding-block-2">{message}</p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div className="flex flex-wrap justify-center padding-block-2">
                            <Button
                                className="btn btn-primary bg-warning-200"
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
            </div>
        </article>
    );
};

export default MessageCard;
