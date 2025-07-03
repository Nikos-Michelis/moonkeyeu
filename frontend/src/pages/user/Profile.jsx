import React, {useEffect} from "react";
import {Button} from "@/components/button/Button.jsx";
import ScrollToTop from "@/components/utils/ScrollToTop.jsx";
import {LinkButton} from "@/components/button/LinkButton.jsx";
import {useAuth} from "@/context/AuthProvider.jsx";
import {useModal} from "@/context/ModalProvider.jsx";
import {useHasRole} from "@/hooks/rbac/useHasRole.jsx";
import {useNavigate} from "react-router-dom";
import {DateTime} from "luxon";
import {useDeleteMutation} from "@/services/mutations.jsx";
import Head from "@/components/seo/Head.jsx";
import JsonLdGeneric from "@/components/seo/jsonld/JsonLdGeneric.jsx";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faUserAstronaut, faChevronLeft, faSpinner, faTableColumns, faDoorOpen, faPenToSquare, faGear, faEraser } from '@fortawesome/free-solid-svg-icons';

const Profile = () =>{
    const baseUrl = import.meta.env.VITE_BACKEND_BASE_URL;
    const { logout, invalidateCredentials, user, status } = useAuth();
    const navigate = useNavigate();
    const { hasRole } = useHasRole();
    const { openModal, closeModal, setStatus } = useModal();
    const roles = ["DEVELOPER", "ADMIN", "MODERATOR"]

    const deleteAccount =
        useDeleteMutation({
            successMessage: "You have successfully deleted your account. Your data will be permanently removed within 30 days.",
        });
    const handleDeleteAccount = () => {
        deleteAccount.mutate(
            {
                url: baseUrl + "/user/account/deactivate",
                data: null,
                options: { withCredentials: true, Bearer: true }
            },
            {
                onSuccess: () => {
                    invalidateCredentials();
                    closeModal("deleteAccountModal");
                    navigate("/launches");
                },
            }
        )
    };

    useEffect(() => {
        setStatus("deleteAccountModal", { isPending: deleteAccount.isPending });
    }, [deleteAccount.isPending]);

    const zonedDateTime = DateTime.fromISO(user?.createdAt).setZone(DateTime.local().zoneName);
    const formattedZonedDateTime = zonedDateTime.toFormat('MMMM dd, yyyy');

    return(
        <>
            <Head
                title="Profile"
                description="View and edit your profile information."
            />
            <JsonLdGeneric
                title="Profile"
                description="View and edit your profile information."
            />
            <ScrollToTop behavior="auto" />
            <section className="article-section">
                <div className="container flex justify-center" data-height="full" data-type="medium" data-spacing="none">
                    <div className="container overlay padding-inline-8 padding-block-10" data-type="fixed-inherit" data-spacing="none">
                        <div className="container flex justify-start padding-block-end-4">
                            <Button className="btn-transparent" onClick={() => window.history.back()}>
                                <FontAwesomeIcon icon={faChevronLeft} /> Back
                            </Button>
                        </div>
                        <div className="profile-img margin-block-end-8">
                            <FontAwesomeIcon icon={faUserAstronaut} className="fa-user-astronaut" />
                        </div>
                        <div className="article-info-container container flex flex-column" data-spacing="none">
                            <section className="account-managment-section">
                                <div className="flex align-center clr-star-300">
                                    <FontAwesomeIcon icon={faGear} className="fs-small-700" />
                                    <h2 className="padding-1">Account Management</h2>
                                </div>
                                <div className="panel-body">
                                    <h4 className="astronaut-name"></h4>
                                    <hr/>
                                    <div className="panel-info-wrapper">
                                        <div className="panel-info-container">
                                            <div className="detail-wrapper fs-small-200 clr-star-300 padding-2">
                                                <p className="info-panel-row">Username</p>
                                                <p className="info-panel-row">{user?.username}</p>
                                            </div>
                                            <div className="detail-wrapper fs-small-200 clr-star-300 padding-2">
                                                <p className="info-panel-row">Email</p>
                                                <p className="info-panel-row">{user?.email}</p>
                                            </div>
                                        </div>
                                        <div className="panel-info-container">
                                            <div className="detail-wrapper fs-small-200 clr-star-300 padding-2">
                                                <p className="info-panel-row">Role</p>
                                                <p className="info-panel-row">{user?.role?.[0]}</p>
                                            </div>
                                            <div className="detail-wrapper fs-small-200 clr-star-300 padding-2">
                                                <p className="info-panel-row">Member Since</p>
                                                <p className="info-panel-row">{formattedZonedDateTime}</p>
                                            </div>
                                        </div>
                                    </div>
                                    <hr />
                                </div>
                                <div className="container flex flex-wrap justify-center align-center padding-block-8" data-type="full-bleed">
                                    <div className="container flex flex-wrap justify-center">
                                        {hasRole(roles) && (
                                            <LinkButton className="btn btn-primary btn-big-hg" to="/dashboard">
                                                <FontAwesomeIcon icon={faTableColumns} /> Dashboard
                                            </LinkButton>
                                        )}
                                        <LinkButton className="btn btn-primary btn-big-hg" to="change-password">
                                            <FontAwesomeIcon icon={faPenToSquare} /> Change Password
                                        </LinkButton>
                                        <Button className="btn btn-primary btn-big-hg" onClick={() => logout()}>
                                            { status.isPending
                                                ? <FontAwesomeIcon icon={faSpinner} spin />
                                                : <FontAwesomeIcon icon={faDoorOpen} />
                                            } Logout
                                        </Button>
                                        <Button
                                            className="btn bg-warning-200 btn-primary btn-big-hg"
                                            onClick={() =>
                                                openModal("deleteAccountModal", {
                                                    title: "Delete Account - Are you sure?",
                                                    details: [
                                                        "This action is permanent.",
                                                        "This action cannot be reversed.",
                                                        "Your data will be deleted, including your bookmarks and other related data."
                                                    ],
                                                    confirmLabel: "Confirm Deletion",
                                                    cancelLabel: "Cancel",
                                                    confirmFn: handleDeleteAccount,
                                                }, "prompt")
                                            }
                                        >
                                            <FontAwesomeIcon icon={ faEraser } /> Delete Account
                                        </Button>
                                    </div>
                                </div>
                            </section>
                        </div>
                        <hr className="hr-6-sm bg-hr-600"/>
                    </div>
                </div>
            </section>
        </>
    );

}
export default Profile;