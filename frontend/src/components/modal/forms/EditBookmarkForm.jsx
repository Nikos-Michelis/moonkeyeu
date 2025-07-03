import React, {useEffect, useState} from 'react';
import {useModal} from "@/context/ModalProvider.jsx";
import {Button} from "@/components/button/Button.jsx";
import Input from "@/components/utils/Input.jsx";
import {useForm} from "react-hook-form";
import ErrorBox from "@/components/utils/ErrorBox.jsx";
import {useUpdateMutation} from "@/services/mutations.jsx";
import Img from "@/components/utils/Img.jsx";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {faArrowRight, faSpinner, faXmark} from '@fortawesome/free-solid-svg-icons';

export function EditBookmarkForm() {
    const baseUrl = import.meta.env.VITE_BACKEND_BASE_URL;
    const { modals, closeModal } = useModal();
    const modal = modals["editBookmarkModal"] || { isOpen: false, data: null };
    const [apiError, setApiError] = useState(null);
    const {
        register,
        handleSubmit,
        reset,
        setValue,
        formState: {errors}} = useForm({
        mode: 'onChange',
    });

    const updateBookmarkMutation =
        useUpdateMutation({
            successMessage: "Bookmark name updated successfully!",
            queryKeysToInvalidate: ["user-bookmarks"]
        });
    const onSubmit = (data) => {
        updateBookmarkMutation.mutate(
            {
                data: data,
                url: baseUrl + "/user/bookmark/update",
                options: { withCredentials: true, Bearer: true }
            },
            {
                onSuccess:() => {
                    reset();
                    handleClose();
                },
                onError: (error) => {
                    setApiError(error.response?.data);
                },
            }
        );
    };
    useEffect(() => {
        if (modal?.data?.bookmark) {
            setValue("currentName", modal?.data?.bookmark);
        }
    }, [modal?.data]);

    const handleClose = () => {
        closeModal("editBookmarkModal");
        reset();
    };

    if (!modal.isOpen) return null;

    return (
        <div className="form-popup-container bookmark-form-container">
            <Button
                onClick={handleClose}
                className="btn-transparent close-btn clr-dark-cosmos-300">
                <FontAwesomeIcon icon={faXmark} />
            </Button>
            <div className="form-box small-form flex flex-column justify-center align-center">
                <div className="form-content padding-block-start-4">
                    <h2>Edit Bookmark</h2>
                    {(errors && Object.keys(errors).length > 0 || apiError?.validationErrors)
                        && <ErrorBox errors={errors} apiError={apiError}/>}
                    <div className="flex justify-center padding-block-start-4 padding-block-end-6">
                        <Img
                            src={modal?.data?.img}
                            className="thumbnail rounded-lg"
                            defaultSrc={`${import.meta.env.VITE_CLOUDFRONT_URL}/assets/logo/moonkeyeu-logo.svg`}
                        />
                    </div>
                    <form onSubmit={handleSubmit(onSubmit)}>
                        <div className="input-field">
                            <Input
                                className={`${errors.currentName ? 'input-error' : ''}`}
                                name="currentName"
                                type="text"
                                register={register}
                                value={modal?.data?.bookmark}
                                rules={{
                                    required: "Name is required.",
                                    min:{ value: 1, message:'Bookmark name should be at least 1 character.'},
                                    max:{ value: 155, message:'Bookmark name should be 155 characters max.'}
                                }}
                                errors={errors}
                                disabled={true}
                            />
                        </div>
                        <div className="input-field">
                            <Input
                                className={`${errors.bookmarkName ? 'input-error' : ''}`}
                                label="New Name"
                                name="newName"
                                type="text"
                                register={register}
                                rules={{
                                    required: "New Name is required.",
                                    min:{ value: 1, message:'Bookmark new name should be at least 1 character.'},
                                    max:{ value: 155, message:'Bookmark new name should be 155 characters max.'}
                                }}
                                errors={errors}/>
                        </div>
                        <div className="flex flex-wrap justify-center">
                            <Button className="btn btn-primary btn-big" type="submit">
                                {updateBookmarkMutation.isPending
                                    ? <FontAwesomeIcon icon={faSpinner} spin />
                                    : <FontAwesomeIcon icon={faArrowRight} />} Continue
                            </Button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    );
}