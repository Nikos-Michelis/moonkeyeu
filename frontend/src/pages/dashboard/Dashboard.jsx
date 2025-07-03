import React from "react";
import {Button} from "@/components/button/Button.jsx";
import ScrollToTop from "@/components/utils/ScrollToTop.jsx";
import {LinkButton} from "@/components/button/LinkButton.jsx";
import {useHasRole} from "@/hooks/rbac/useHasRole.jsx";
import Head from "@/components/seo/Head.jsx";
import JsonLdGeneric from "@/components/seo/jsonld/JsonLdGeneric.jsx";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {
    faTableColumns,
    faChartLine,
    faMessage,
    faUsers,
    faDatabase,
    faChevronLeft
} from '@fortawesome/free-solid-svg-icons';

const Dashboard = () =>{
    const grafanaUrl = import.meta.env.VITE_GRAFANA_URL;
    const { hasRole } = useHasRole();
    const roles = ["DEVELOPER", "ADMIN", "MODERATOR"]

    return(
        <>
            <Head
                title="Dashboard"
                description="Access and manage the advanced functionalities."
            />
            <JsonLdGeneric
                title="Dashboard"
                description="Access and manage the advanced functionalities."
            />
            <ScrollToTop behavior="auto" />
                <section className="dashboard-section">
                    <div className="container flex justify-center" data-height="full" data-type="medium" data-spacing="none">
                        <div className="container overlay padding-inline-8 padding-block-10" data-type="fixed-inherit" data-spacing="none">
                            <div className="container flex justify-start padding-block-end-4">
                                <Button className="btn-transparent" onClick={() => window.history.back()}>
                                    <FontAwesomeIcon icon={faChevronLeft} /> Back
                                </Button>
                            </div>
                            <div className="container flex flex-column padding-8" data-spacing="none">
                                <section className="account-managment-section">
                                    <div className="flex align-center clr-star-300">
                                        <FontAwesomeIcon icon={faTableColumns} className="fs-small-700" />
                                        <h2 className="padding-1">Dashboard</h2>
                                    </div>
                                    <hr/>
                                    <div className="container flex flex-wrap justify-center align-center padding-block-8" data-type="full-bleed" data-spacing="none">
                                        <div className="container flex flex-wrap justify-center">
                                            <LinkButton
                                                className="btn btn-primary btn-big-hg"
                                                to="etl-report"
                                                disabled={true}
                                            >
                                                <FontAwesomeIcon icon={faDatabase} /> ETL Tasks
                                            </LinkButton>
                                            <LinkButton
                                                className="btn btn-primary btn-big-hg"
                                                to={grafanaUrl}
                                                disabled={!hasRole(roles)}
                                                isExternal={true}
                                            >
                                                <FontAwesomeIcon icon={faChartLine} /> Grafana
                                            </LinkButton>
                                            <LinkButton
                                                className="btn btn-primary btn-big-hg"
                                                to="members"
                                                disabled={!hasRole(roles)}
                                            >
                                                <FontAwesomeIcon icon={faUsers} /> Members
                                            </LinkButton>
                                            <LinkButton
                                                className="btn btn-primary btn-big-hg"
                                                to="messages"
                                                disabled={!hasRole(roles)}
                                            >
                                                <FontAwesomeIcon icon={faMessage} /> Messages
                                            </LinkButton>
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
export default Dashboard;