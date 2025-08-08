import React, {useCallback, useRef} from "react";
import { Button } from "@/components/button/Button.jsx";
import { LinkButton } from "@/components/button/LinkButton.jsx";
import {useDeleteMutation} from "@/services/mutations.jsx";
import Img from "@/components/utils/Img.jsx";
import {useModal} from "@/context/ModalProvider.jsx";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faEllipsisVertical, faList  } from '@fortawesome/free-solid-svg-icons';
import toast from "react-hot-toast";

const BookmarkCard = ({ id, bookmark, launches }) => {
    const { openModal } = useModal();
    const cardRef = useRef();
    const triggerRef = useRef(null);
    const bookmarkUrl = `${import.meta.env.VITE_BACKEND_BASE_URL}/user/bookmark/delete/`;
    const removeBookmarkMutation =
        useDeleteMutation({
            successMessage: undefined,
            queryKeysToInvalidate: ["user-bookmarks"]
        });

    const handleRemove = useCallback(async (bookmark) => {
        await toast.promise(
            removeBookmarkMutation.mutateAsync(
                {
                    url: bookmarkUrl + bookmark,
                    options: { withCredentials: true, Bearer: true },
                }
            ),
            {
                loading: 'Removing bookmark...',
                success: <b>Bookmark removed!</b>,
                error: (err) => <b>{err.message || 'Could not remove bookmark.'}</b>,
            }
        );
    }, [bookmark]);

    const handleOpenDropdown = () => {
        if (!cardRef.current) return;

        const rect = cardRef.current.getBoundingClientRect();
        const dropdownWidth = 140;
        const left = rect.right + window.scrollX - dropdownWidth;
        const top = rect.bottom + window.scrollY - 25;

        openModal(
            `bookmarkDropdown-${id}`,
            {
                bookmark,
                img: launches?.[0]?.image?.image_url,
                position: { top, left },
                status: {
                    isPending: removeBookmarkMutation.isPending,
                    isSuccess: removeBookmarkMutation.isSuccess,
                    isError: removeBookmarkMutation.isError,
                },
                handleRemove,
            },
            "dropdown",
            triggerRef);
    };

    return (
        <article ref={cardRef} className="bookmark-card container flex flex-column bg-hr-600 rounded-md" data-type="full-width" data-overflow="visible">
            <div className="container" data-spacing="none">
                <Img
                    src={launches?.[0]?.image?.image_url}
                    alt={launches?.[0]?.image?.image_url?.name || "default"}
                    className="bookmark-card__thumbnail"
                    defaultSrc={`${import.meta.env.VITE_CLOUDFRONT_URL}/assets/logo/moonkeyeu-logo.svg`}
                />
            </div>
            <div className="clr-star-300 padding-2">
                <div className="flex justify-space-between align-center">
                    <h3 className="fs-small-300">{bookmark}</h3>
                    <Button
                        ref={triggerRef}
                        type="button"
                        className="btn--transparent"
                        onClick={handleOpenDropdown}
                    >
                        <FontAwesomeIcon icon={faEllipsisVertical} />
                    </Button>
                </div>
                <div className="flex">
                    <LinkButton className="btn--transparent" to={bookmark}>
                        <p className="fs-small-200">
                            View full list of launches
                        </p>
                    </LinkButton>
                </div>
            </div>
            <div className="bookmark-card__count">
                <FontAwesomeIcon icon={faList} className="fa-xs" /> {launches?.length > 0 ? `${launches?.length} ${launches?.length > 1? "Launches": "Launch"}` : "Empty List"}
            </div>
        </article>
    );
};

export default BookmarkCard;
