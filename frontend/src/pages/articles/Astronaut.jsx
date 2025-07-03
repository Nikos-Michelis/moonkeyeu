import React from "react";
import {useParams} from "react-router-dom";
import Launch from "@/components/article-details/Launch.jsx";
import SkeletonArticleLoader from "@/components/skeleton/SkeletonArticleLoader.jsx";
import {SkeletonLoader} from "@/components/loader/SkeletonLoader.jsx";
import Tooltip from "@/components/tooltip/Tooltip.jsx";
import {LinkButton} from "@/components/button/LinkButton.jsx";
import useClipboard from "@/hooks/util/useClipboard.jsx";
import ScrollToTop from "@/components/utils/ScrollToTop.jsx";
import {Button} from "@/components/button/Button.jsx";
import Img from "@/components/utils/Img.jsx";
import {useParameterizedQuery} from "@/services/queries.jsx";
import Head from "@/components/seo/Head.jsx";
import JsonLdPerson from "@/components/seo/jsonld/JsonLdPerson.jsx";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {faBook, faBuilding, faChevronLeft, faShareFromSquare} from '@fortawesome/free-solid-svg-icons';
import {faInstagram, faWikipediaW, faXTwitter} from '@fortawesome/free-brands-svg-icons';

function Astronaut(){
    const baseUrl = `${import.meta.env.VITE_BACKEND_BASE_URL}/public/astronaut`;
    const {id} = useParams();
    const queryData
        = useParameterizedQuery({
            url: `${baseUrl}/${id}`,
            params: `astronaut-${id}`,
            cacheKey: "astronaut-article"
        });
    const data = queryData?.data || [];
    const agency = data?.agency || {};
    const socialMediaLinks = data?.social_media?.length > 0 ? data.social_media : [];
    const launches = data?.crew || [];
    const instagram = socialMediaLinks.find((sm) => sm.name === "Instagram")?.media_url;
    const twitter = socialMediaLinks.find((sm) => sm.name === "Twitter")?.media_url;
    const wiki = socialMediaLinks.find((sm) => sm.name === "Wiki")?.media_url;
    const { copied, copyToClipboard } = useClipboard();
    const contentConfig = {
        component: SkeletonArticleLoader,
        count: 1
    };

    const handleShare = () => {
        copyToClipboard(window.location.href)
    };
    const checkValue = (value) => {
        return (value ? value : "―");
    }

    return(
        <>
            <Head
                title={data?.name}
                description={data?.bio}
                image={data.images?.[0]?.image_url}
                alt={data.images?.[0]?.name}
                type="article"
            />
            <JsonLdPerson
                name={data?.name}
                birthDate={data?.date_of_birth}
                nationality={data.nationality?.length > 0 ? data.nationality[0]?.nationality_name : null}
                agency={agency?.name}
            />
            <ScrollToTop behavior="auto" />
            <SkeletonLoader
                isFetching={queryData.isFetching}
                isPending={queryData.isFetching}
                isError={queryData.isError}
                contentConfig={contentConfig}>
                <section className="article-section">
                    <div className="container __article flex justify-center" data-type="wide" data-spacing="none">
                        <div className="container __article overlay flex flex-column align-center" data-type="fixed" data-spacing="none">
                                <div className="container flex justify-start padding-block-start-7 padding-block-end-2">
                                    <Button className="btn-transparent" onClick={() => window.history.back()}>
                                        <FontAwesomeIcon icon={faChevronLeft} /> Back
                                    </Button>
                                </div>
                                <div className="container __overview flex flex-column justify-center align-center bg-dark-cosmos-300" data-type="full-bleed">
                                    <div className="image-box">
                                        <Img
                                            src={data.images?.[0]?.image_url}
                                            alt={data.images?.[0]?.name || "default"}
                                            className="card-img"
                                            defaultSrc={`${import.meta.env.VITE_CLOUDFRONT_URL}/assets/logo/moonkeyeu-logo.svg`}
                                        />
                                    </div>
                                    <div className="overview-info-wrapper container flex flex-column justify-center padding-2" data-type="full-bleed">
                                        <div className="article-detail-container">
                                            <h3 className="astronaut-name">{checkValue(data?.name)}</h3>
                                            <h5 className="astronaut-nationality">{checkValue(agency?.name)}</h5>
                                            <div className="panel-body">
                                                <hr className="hr-7-xs"/>
                                                <div className="panel-info-wrapper">
                                                    <div className="panel-info-container">
                                                        <div className="detail-wrapper fs-small-200 clr-star-300 padding-block-2 padding-inline-1">
                                                            <p className="info-panel-row">Nationality</p>
                                                            <p className="info-panel-row">
                                                                {checkValue(data.nationality?.length > 0 ? data.nationality[0]?.nationality_name : null)}
                                                            </p>
                                                        </div>
                                                        <div className="detail-wrapper fs-small-200 clr-star-300 padding-block-2 padding-inline-1">
                                                            <p className="info-panel-row">Date Of Birth</p>
                                                            <p className="info-panel-row">{checkValue(data?.date_of_birth)}</p>
                                                        </div>
                                                    </div>
                                                    <div className="panel-info-container">
                                                        <div className="detail-wrapper fs-small-200 clr-star-300 padding-block-2 padding-inline-1">
                                                            <p className="info-panel-row">Status</p>
                                                            <p className="info-panel-row">{checkValue(data?.status)}</p>
                                                        </div>
                                                        <div className="detail-wrapper fs-small-200 clr-star-300 padding-block-2 padding-inline-1">
                                                            <p className="info-panel-row">type</p>
                                                            <p className="info-panel-row">{checkValue(agency?.type)}</p>
                                                        </div>
                                                    </div>
                                                </div>
                                                <hr className="hr-7-xs"/>
                                            </div>
                                            <div className="container flex justify-space-evenly align-center padding-block-2" data-type="full-bleed" data-overflow="visible">
                                                { instagram ? (
                                                    <div className="instagram">
                                                        <LinkButton
                                                            className="btn-transparent btn-instragram"
                                                            to={instagram}
                                                            isExternal={true}
                                                        >
                                                            <FontAwesomeIcon icon={faInstagram} />
                                                        </LinkButton>
                                                    </div>
                                                ) : (
                                                    <Tooltip message="No Instagram Available">
                                                        <div className="instagram">
                                                            <LinkButton
                                                                className="btn-transparent btn-instragram"
                                                                isExternal={true}
                                                                disabled={true}
                                                                >
                                                                 <FontAwesomeIcon icon={faInstagram} />
                                                             </LinkButton>
                                                        </div>
                                                    </Tooltip>
                                                )}
                                                 { twitter ? (
                                                    <div className="x-twitter">
                                                        <LinkButton
                                                            className="btn-transparent btn-x-twitter"
                                                            to={twitter}
                                                            isExternal={true}
                                                        >
                                                        <FontAwesomeIcon icon={faXTwitter} />
                                                        </LinkButton>
                                                    </div>
                                                ) : (
                                                    <Tooltip message="No Twitter Available">
                                                        <div className="x-twitter">
                                                            <LinkButton
                                                                className="btn-transparent btn-x-twitter"
                                                                isExternal={true}
                                                                disabled={true}
                                                                >
                                                        <FontAwesomeIcon icon={faXTwitter} />
                                                             </LinkButton>
                                                        </div>
                                                    </Tooltip>
                                                )}
                                                 { wiki ? (
                                                    <div className="wiki">
                                                        <LinkButton
                                                            className="btn-wiki"
                                                            to={wiki}
                                                            isExternal={true}
                                                        >
                                                        <FontAwesomeIcon icon={faWikipediaW} />
                                                        </LinkButton>
                                                    </div>
                                                ) : (
                                                    <Tooltip message="No Wiki Available">
                                                        <div className="wiki">
                                                            <LinkButton
                                                                className="btn-transparent btn-wiki"
                                                                isExternal={true}
                                                                disabled={true}
                                                                >
                                                        <FontAwesomeIcon icon={faWikipediaW} />
                                                             </LinkButton>
                                                        </div>
                                                    </Tooltip>
                                                )}
                                                { data?.info_url ? (
                                                    <div className="agency">
                                                        <LinkButton
                                                            className="btn-transparent btn-agency"
                                                            to={data?.info_url}
                                                            isExternal={true}
                                                        >
                                                             <FontAwesomeIcon icon={faBuilding} />
                                                        </LinkButton>
                                                    </div>
                                                ) : (
                                                    <Tooltip message="No agency available">
                                                        <div className="agency">
                                                            <LinkButton
                                                                className="btn-agency"
                                                                isExternal={true}
                                                                disabled={true}>
                                                                <FontAwesomeIcon icon={faBuilding} />
                                                             </LinkButton>
                                                        </div>
                                                    </Tooltip>
                                                )}
                                                <div>
                                                    <Tooltip message={copied ? "Copied!" :"Copied to clipboard!"}>
                                                        <Button className="btn-transparent" onClick={handleShare} disabled={copied}>
                                                            <FontAwesomeIcon icon={faShareFromSquare} />
                                                        </Button>
                                                    </Tooltip>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div className="article-info-container container flex flex-column" data-type="full-bleed">
                                    <section className="biography-section">
                                        <div className="heading-box">
                                            <FontAwesomeIcon icon={faBook} />
                                            <h2>Astronaut Biography</h2>
                                        </div>
                                        <hr className="hr-7-sm bg-hr-600" />
                                        <div className="info-box">
                                            <p>{data?.bio}</p>
                                        </div>
                                    </section>
                                    <Launch launches={launches} navUrl={'/launches/'}/>
                                    <div className="padding-block-end-4">
                                        <hr className="hr-6-md"/>
                                    </div>
                                </div>
                            </div>
                        </div>
                </section>
            </SkeletonLoader>
        </>
    )
}
export default Astronaut;