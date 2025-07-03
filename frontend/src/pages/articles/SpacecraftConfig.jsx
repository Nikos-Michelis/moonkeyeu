import React from "react";
import {useParams} from "react-router-dom";
import SkeletonArticleLoader from "@/components/skeleton/SkeletonArticleLoader.jsx";
import {SkeletonLoader} from "@/components/loader/SkeletonLoader.jsx";
import {LinkButton} from "@/components/button/LinkButton.jsx";
import Tooltip from "@/components/tooltip/Tooltip.jsx";
import ScrollToTop from "@/components/utils/ScrollToTop.jsx";
import useClipboard from "@/hooks/util/useClipboard.jsx";
import {Button} from "@/components/button/Button.jsx";
import Img from "@/components/utils/Img.jsx";
import {useParameterizedQuery} from "@/services/queries.jsx";
import Head from "@/components/seo/Head.jsx";
import JsonLdGeneric from "@/components/seo/jsonld/JsonLdGeneric.jsx";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {
    faHourglass,
    faTimeline,
    faAlignLeft,
    faCircleInfo,
    faShareFromSquare,
    faChevronLeft
} from '@fortawesome/free-solid-svg-icons';
import { faWikipediaW } from '@fortawesome/free-brands-svg-icons';

function SpacecraftConfig(){
    const baseUrl = `${import.meta.env.VITE_BACKEND_BASE_URL}/public/spacecraft`;
    const {id} = useParams();
    const queryData = useParameterizedQuery({
        url: `${baseUrl}/${id}`,
        params: `spacecraft-${id}`,
        cacheKey: "spacecraft-article"
    });
    const data = queryData?.data || [];
    const { copied, copyToClipboard } = useClipboard();
    const contentConfig = {
        component: SkeletonArticleLoader,
        count: 1
    };
    const handleShare = () => {
        copyToClipboard(window.location.href)
    };
    const checkValue = (value, metric= "") => {
        return (value ? `${value} ${metric}` : "―");
    }
    const booleanConverter = (value) => {
        return value ? value === true ? "Yes" : "No" : null;
    }

    return(
        <>
            <Head
                title={data?.name}
                description={data?.details}
                image={data?.images?.[0]?.image_url}
                alt={data?.images?.[0]?.name}
                type="article"
            />
            <JsonLdGeneric
                title={data?.name}
                description={data?.details}
                image={data?.images?.[0]?.image_url}
                alt={data?.images?.[0]?.name }
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
                                        <div className="panel-body">
                                            <h3>{checkValue(data?.name)}</h3>
                                            <h5>{checkValue(data?.agency?.name)}</h5>
                                            <hr className="hr-7-xs"/>
                                            <div className="panel-info-wrapper">
                                                <div className="panel-info-container">
                                                    <div className="detail-wrapper fs-small-200 padding-1 clr-star-300">
                                                        <p className="info-panel-row">Type</p>
                                                        <p className="info-panel-row">{checkValue(data?.type)}</p>
                                                    </div>
                                                    <div className="detail-wrapper fs-small-200 padding-1 clr-star-300">
                                                        <p className="info-panel-row">In Use</p>
                                                        <p className="info-panel-row">{checkValue(booleanConverter(data?.in_use))}</p>
                                                    </div>
                                                </div>
                                                <div className="panel-info-container">
                                                    <div className="detail-wrapper fs-small-200 padding-1 clr-star-300">
                                                        <p className="info-panel-row">Height</p>
                                                        <p className="info-panel-row">{checkValue(data?.height)}</p>
                                                    </div>
                                                    <div className="detail-wrapper fs-small-200 padding-1 clr-star-300">
                                                        <p className="info-panel-row">Diameter</p>
                                                        <p className="info-panel-row">{checkValue(data?.diameter)}</p>
                                                    </div>
                                                </div>
                                            </div>
                                            <hr className="hr-7-xs"/>
                                            <div className="panel-info-wrapper">
                                                <div className="panel-info-container">
                                                    <div className="detail-wrapper fs-small-200 padding-1 clr-star-300">
                                                        <p className="info-panel-row">Payload Capacity</p>
                                                        <p className="info-panel-row">{checkValue(data?.payload_capacity, "kg")}</p>
                                                    </div>
                                                    <div className="detail-wrapper fs-small-200 padding-1 clr-star-300">
                                                        <p className="info-panel-row">Maiden Flight</p>
                                                        <p className="info-panel-row">{checkValue(data?.maiden_flight)}</p>
                                                    </div>
                                                </div>
                                                <div className="panel-info-container">
                                                    <div className="detail-wrapper fs-small-200 padding-1 clr-star-300">
                                                        <p className="info-panel-row">Crew Capacity</p>
                                                        <p className="info-panel-row">{checkValue(data?.crew_capacity)}</p>
                                                    </div>
                                                    <div className="detail-wrapper fs-small-200 padding-1 clr-star-300">
                                                        <p className="info-panel-row">Human Rated</p>
                                                        <p className="info-panel-row">{checkValue(booleanConverter(data?.human_rated))}</p>
                                                    </div>
                                                </div>
                                            </div>
                                            <hr className="hr-7-xs"/>
                                        </div>
                                        <div className="container flex justify-space-evenly align-center padding-block-2" data-type="full-bleed" data-overflow="visible">
                                            { data?.info_url ? (
                                                <div className="info">
                                                    <LinkButton
                                                        className="btn-transparent btn-instragram"
                                                        to={data.info_url}
                                                        isExternal={true}
                                                    >
                                                        <FontAwesomeIcon icon={faCircleInfo} />
                                                    </LinkButton>
                                                </div>
                                            ) : (
                                                <Tooltip message="No Info Available">
                                                    <div className="info">
                                                        <LinkButton
                                                            className="btn-transparent btn-instragram"
                                                            isExternal={true}
                                                        >
                                                            <FontAwesomeIcon icon={faCircleInfo} />
                                                        </LinkButton>
                                                    </div>
                                                </Tooltip>
                                            )}
                                            { data?.wiki_url ? (
                                                <div className="wiki">
                                                    <LinkButton
                                                        className="btn-transparent btn-wiki"
                                                        to={data.wiki_url}
                                                        isExternal={true}
                                                    >
                                                        <FontAwesomeIcon icon={faWikipediaW} />
                                                    </LinkButton>
                                                </div>
                                            ) : (
                                                <Tooltip message="No Wiki Available">
                                                    <div className="wiki">
                                                        <LinkButton
                                                            className="btn-transparent btn-wikipedia"
                                                            isExternal={true}
                                                        >
                                                            <FontAwesomeIcon icon={faWikipediaW} />
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
                                <section className="text-section">
                                    <div className="info-box">
                                        <FontAwesomeIcon icon={faHourglass} />
                                        <span>Flight Life</span>
                                        <p>{data?.flight_life}</p>
                                    </div>
                                     <div className="info-box">
                                         <FontAwesomeIcon icon={faAlignLeft} />
                                        <span>Description</span>
                                        <p>{data?.details}</p>
                                    </div>
                                     <div className="info-box">
                                         <FontAwesomeIcon icon={faTimeline} />
                                        <span>History</span>
                                        <p>{data?.history}</p>
                                    </div>
                                    <div className="padding-block-4">
                                        <hr className="hr-6-md"/>
                                    </div>
                                </section>
                            </div>
                        </div>
                    </div>
                </section>
            </SkeletonLoader>
        </>
    )
}
export default SpacecraftConfig;