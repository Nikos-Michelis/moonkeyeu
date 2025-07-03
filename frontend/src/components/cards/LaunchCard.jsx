import React, {useRef} from 'react';
import CountdownTimer from "../timers/CountdownTimer.jsx";
import {DateTime} from "luxon";
import Tooltip from "@/components/tooltip/Tooltip.jsx";
import {Button} from "@/components/button/Button.jsx";
import {useModal} from "@/context/ModalProvider.jsx";
import {LinkButton} from "@/components/button/LinkButton.jsx";
import useClipboard from "@/hooks/util/useClipboard.jsx";
import {useAuth} from "@/context/AuthProvider.jsx";
import {useParams} from "react-router-dom";
import useComparator from "@/hooks/util/useComparator.jsx";
import Img from "@/components/utils/Img.jsx";
import {useDeleteMutation} from "@/services/mutations.jsx";
import toast from "react-hot-toast";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faShareFromSquare, faCircleInfo, faCircleCheck, faTriangleExclamation, faRocket, faTrash, faCalendarDays, faLocationDot } from '@fortawesome/free-solid-svg-icons';
import { faBookmark } from '@fortawesome/free-regular-svg-icons';
import { faYoutube } from '@fortawesome/free-brands-svg-icons';

const LaunchCard = ({navUrl, id, agency, fullname, net, location, image, status:launchStatus, video_urls, isBookmarked = false}) => {
    const zonedDateTime = DateTime.fromISO(net).setZone(DateTime.local().zoneName);
    const formattedZonedDateTime = zonedDateTime.toFormat('MMMM dd, yyyy - hh:mm a ZZZZ');
    const baseUrl = import.meta.env.VITE_BACKEND_BASE_URL;
    const {name} = useParams();
    const { openModal } = useModal();
    const { user, status } = useAuth();
    const triggerRef = useRef(null);
    const item = useComparator(
        video_urls?.filter(video =>
            video.videoUrl.includes("youtube.com") || video.videoUrl.includes("youtu.be")
        ),
        (a, b) => a.priority > b.priority);
    const { copied, copyToClipboard } = useClipboard();
    const tooltipVideoMessage = item?.videoUrl ? "" : "No Video Available";
    const tooltipInfoMessage = id ? "" : "No Info Available";

    const handleShare = () => {
        const url = `${window.location.origin}${navUrl || window.location.pathname}/${id}`;
        copyToClipboard(url);
    };
    const removeLaunchMutation =
        useDeleteMutation({
            successMessage: "Launch removed successfully!",
            queryKeysToInvalidate: ["my-launches", "user-bookmarks"]
        });
    const handleRemove = (id) => {
        removeLaunchMutation.mutate(
            {
                url: baseUrl + "/user/bookmark/delete/" + name + "/" + id,
                options: { withCredentials: true, Bearer: true },
            });
    };
    const onBookmark = () => {
        openModal(user ? "bookmarkModal" : "PopUpFormModal", id, "form", triggerRef)
        !user && toast("You're almost there! Sign up or log in to bookmark your favorites launches.", { icon: <FontAwesomeIcon icon={faRocket} />});
    }
    return (
        <article className="landscape-card-outer-wrapper flex justify-center large-wrapper">
            <div className="landscape-card-wrapper flex justify-center">
                <div className="card-info-container flex flex-column justify-center align-center">
                    <div className="bookmark-article-box flex justify-center align-center">
                        {isBookmarked ?
                            <Button
                                type="submit" 
                                className="btn-transparent"
                                onClick={() => handleRemove(id)}>
                                <FontAwesomeIcon icon={faTrash} />
                            </Button>
                            :
                            <Button
                                ref={triggerRef}
                                type="submit"
                                className="btn-transparent"
                                disabled={status.isPending}
                                onClick={() => onBookmark()}>
                                <FontAwesomeIcon icon={faBookmark} />
                            </Button>
                        }
                    </div>
                    <div className="card-img-box">
                        <Img
                            src={image?.image_url}
                            alt={image?.name || "default"}
                            className="card-img"
                            defaultSrc={`${import.meta.env.VITE_CLOUDFRONT_URL}/assets/logo/moonkeyeu-logo.svg`}
                        />
                    </div>
                    <section className="card-info-section flex flex-column justify-space-evenly">
                        <div className="card-detail-container">
                            <h3 className="rocket-name">{fullname}</h3>
                            <p><small className="fw-semi-bold">{agency}</small></p>
                            <div className="card-detail-box">
                                <div>
                                    {launchStatus === 'Launch Successful' || launchStatus === 'Go for Launch' ? (
                                        <FontAwesomeIcon icon={faCircleCheck} />
                                    ) : (
                                        <FontAwesomeIcon icon={faTriangleExclamation} />
                                    )}
                                </div>
                                <p className="fw-regular"><small>{launchStatus}</small></p>
                            </div>
                            <div className="card-detail-box launch-schedule margin-block-1">
                                <div>
                                    <FontAwesomeIcon icon={faCalendarDays} />
                                </div>
                                <p className="fw-regular"><small>{formattedZonedDateTime}</small></p>
                            </div>
                            {location &&
                                <div className="card-detail-box launch-location">
                                    <div>
                                        <FontAwesomeIcon icon={faLocationDot} />
                                    </div>
                                    <p className="fw-regular"><small>{location?.name}</small></p>
                                </div>
                            }
                        </div>
                        { zonedDateTime > Date.now() && (
                            <CountdownTimer net={zonedDateTime} />
                        ) }

                        <hr className="hr-7-sm bg-hr-600"/>
                        <div className="flex flex-wrap justify-center padding-block-2" data-type="narrow">
                            {id ? (
                                <div className="info">
                                    <LinkButton className="btn btn-primary" to={navUrl? navUrl + id: id} >
                                        <FontAwesomeIcon icon={faCircleInfo} /> INFO
                                    </LinkButton>
                                </div>
                            ) : (
                                <Tooltip message={tooltipInfoMessage}>
                                    <div className="info">
                                        <LinkButton className="btn btn-primary">
                                            <FontAwesomeIcon icon={faCircleInfo} /> INFO
                                        </LinkButton>
                                    </div>
                                </Tooltip>
                            )}

                            {item?.videoUrl ? (
                                <div className="video">
                                    <Button className="btn btn-primary" onClick={() => openModal("videoPlayerModal", item.videoUrl, "video")}>
                                        <FontAwesomeIcon icon={faYoutube} /> WATCH
                                    </Button>
                                </div>
                            ) : (
                                <Tooltip message={tooltipVideoMessage}>
                                    <div className="">
                                        <Button className="btn btn-primary" disabled={true}>
                                            <FontAwesomeIcon icon={faYoutube} /> WATCH
                                        </Button>
                                    </div>
                                </Tooltip>
                            )}
                            <div className="share">
                                <Tooltip  message={copied ? "Copied!" : "Copied to clipboard!"}>
                                    <Button className="btn btn-primary" onClick={handleShare} disabled={copied}>
                                        <FontAwesomeIcon icon={faShareFromSquare} /> SHARE
                                    </Button>
                                </Tooltip>
                            </div>
                        </div>
                    </section>
                </div>
            </div>
        </article>
    );
};

export default LaunchCard;
