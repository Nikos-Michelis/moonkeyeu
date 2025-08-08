import React, {useEffect, useState} from 'react';
import {useModal} from "@/context/ModalProvider.jsx";
import {Button} from "@/components/button/Button.jsx";
import {useForm} from "react-hook-form";
import {useAuth} from "@/context/AuthProvider.jsx";
import {useDebounce} from "@/hooks/util/useDebounce.jsx";
import ErrorBox from "@/components/utils/ErrorBox.jsx";
import Input from "@/components/utils/Input.jsx";
import {useNavigate} from "react-router-dom";
import {useCreateMutation, useDeleteMutation} from "@/services/mutations.jsx";
import {useSimpleQuery} from "@/services/queries.jsx";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {
    faAngleUp,
    faAngleDown,
    faXmark,
    faEye,
    faSpinner,
    faArrowRight,
} from '@fortawesome/free-solid-svg-icons';

export function AddBookmarkForm() {
    const baseUrl = import.meta.env.VITE_BACKEND_BASE_URL;
    const { modals, closeModal } = useModal();
    const navigate = useNavigate();
    const modal = modals["bookmarkModal"] || {};
    const [toggle, setToggle] = useState(false)
    const [bookmark, setBookmark] = useState(null)
    const debouncedValue = useDebounce(bookmark, 500);
    const [apiError, setApiError] = useState(null);
    const {user} = useAuth();
    const {
        register,
        handleSubmit,
        reset,
        formState: {errors}} = useForm({ mode: 'onChange' });

    const queryData = useSimpleQuery({
        url: baseUrl + "/user/user-bookmarks",
        cacheKey: "user-bookmarks",
        queryOptions: {
            enabled: !!user,
            retry: 2
        },
        options: { withCredentials: false, Bearer: true }
    })

    const createBookmarkMutation =
        useCreateMutation({
            successMessage: "Bookmark added!",
            queryKeysToInvalidate: ["user-bookmarks"],
            retry: 1
        });

    const addToBookmarkMutation =
        useCreateMutation({
            successMessage: "Launch added successfully!",
            queryKeysToInvalidate: ["user-bookmarks", "my-launches"],
            retry: 1
        });

    const removeLaunchMutation =
        useDeleteMutation({
            successMessage: "Launch removed successfully!",
            queryKeysToInvalidate: ["user-bookmarks", "my-launches"],
            retry: 1
        });

    const handleAddToBookmark = (data) => {
        addToBookmarkMutation.mutate(
            {
                data: data,
                url: baseUrl + "/user/bookmark/add",
                options: { withCredentials: true, Bearer: true }
            },
            {
                onSuccess: () => {
                    reset();
                },
                onError: (error) => {
                    setApiError(error.response?.data);
                },
            }
        );
    };

     const handleRemoveFromBookmark = (data) => {
         removeLaunchMutation.mutate(
             {
             url: baseUrl + `/user/bookmark/delete/${data.bookmark}/${data.id}`,
             options: { withCredentials: true, Bearer: true }
         })
    };

    const onSubmit = (data) => {
        createBookmarkMutation.mutate(
            {
                data: data,
                url: baseUrl + "/user/bookmark/create",
                options: { withCredentials: true, Bearer: true }
            },
            {
                onSuccess: () => {
                    reset();
                },
                onError: (error) => {
                    setApiError(error.response?.data);
                },
            }
        );
    };
    const handleClose = () => {
        setToggle(false)
        setApiError(null);
        closeModal("bookmarkModal");
        reset();
    };
    const handleBookmarkChange = (e) => {
        const { name, checked } = e.target;
        setBookmark((prevBookmark) => ({
            ...prevBookmark,
            name: name,
            checked: checked
        }));
    };
    const handleOnNavigate = (url) => {
        closeModal("bookmarkModal");
        navigate(url);
    }
    useEffect(() => {
        if (debouncedValue?.name) {
            if (debouncedValue?.checked) {
                handleAddToBookmark({bookmarkName: debouncedValue.name, launchId: modal.data});
            } else {
                handleRemoveFromBookmark({bookmark: debouncedValue.name, id: modal.data,});
            }
        }
    }, [debouncedValue]);


    if (!modal.isOpen) return null;

    return(
        <div className="form-popup-container bookmark-form-container">
            <Button
                onClick={handleClose}
                className="btn--transparent btn--close clr-dark-cosmos-300"
            >
                <FontAwesomeIcon icon={faXmark} className="fs-small-500"/>
            </Button>
            <div className="form-box small-form flex flex-column justify-center align-center">
                <div className="padding-block-end-6">
                    <h3 className="margin-inline-2">Bookmark to...</h3>
                </div>
                <div className="container container--small-list padding-8"
                     data-type="full-width"
                     data-scroll={queryData.data && queryData.data.length > 5 ? "vertical" : undefined}
                     data-height="small"
                >
                    <div className="padding-inline-2">
                        { queryData.data && queryData.data.length > 0
                            && queryData.data.map(bookmark =>
                                <div className="container flex justify-space-between align-center margin-block-1 margin-inline-4" key={bookmark.id}>
                                    <input
                                        key={bookmark?.id}
                                        type="checkbox"
                                        name={bookmark?.bookmark}
                                        value={bookmark?.bookmark}
                                        defaultChecked={bookmark?.launches.length > 0
                                            ? bookmark.launches.some(launch => launch.id === modal.data)
                                            : false
                                        }
                                        onChange={handleBookmarkChange}
                                        disabled={addToBookmarkMutation.isPending || removeLaunchMutation.isPending}
                                    />
                                    <p className="margin-inline-8 clr-dark-cosmos-300 fw-regular ellipsis-single-md fs-small-200">{bookmark?.bookmark}</p>
                                    <Button className="btn btn--transparent clr-dark-cosmos-300" onClick={() => handleOnNavigate(`bookmarks/${bookmark.bookmark}`)}>
                                        <FontAwesomeIcon icon={faEye} />
                                    </Button>
                                </div>
                            )}
                    </div>
                </div>
                <div className="flex flex-wrap justify-center margin-block-4">
                    <Button onClick={() => setToggle(!toggle)}
                            className="btn--transparent btn--big--transparent clr-dark-cosmos-300">
                        {toggle ? (
                            <FontAwesomeIcon icon={faAngleUp} />
                        ) : (
                            <FontAwesomeIcon icon={faAngleDown} />
                        )}
                    </Button>
                </div>
                <div className={`form-content height-fade ${ toggle ? "show md" : ""} padding-0`}>
                    <h2>Add Bookmark</h2>
                    {(errors && Object.keys(errors).length > 0 || apiError?.validationErrors)
                        && <ErrorBox errors={errors} apiError={apiError}/>}
                    <form onSubmit={handleSubmit(onSubmit)}>
                        <div className="input-field">
                            <Input
                                className={`${errors.email ? 'input-error' : ''}`}
                                label="Bookmark Name"
                                name="bookmarkName"
                                type="text"
                                register={register}
                                rules={{
                                    required: 'Bookmark Name is required.',
                                    min:{ value: 1, message:'Book mark name should be at least 1 character.'},
                                    max:{ value: 155, message:'Book mark name should be 155 characters max.'}
                                }}
                                errors={errors}
                                disabled={createBookmarkMutation.isPending || addToBookmarkMutation.isPending || removeLaunchMutation.isPending}/>
                        </div>
                        <div className="flex flex-wrap justify-center">
                            <Button
                                className="btn btn--primary btn--big"
                                type="submit"
                                disabled={createBookmarkMutation.isPending || addToBookmarkMutation.isPending || removeLaunchMutation.isPending}
                            >
                                { createBookmarkMutation.isPending
                                    ? <FontAwesomeIcon icon={faSpinner} spin />
                                    : <FontAwesomeIcon icon={faArrowRight} />
                                } Continue
                            </Button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    );
}