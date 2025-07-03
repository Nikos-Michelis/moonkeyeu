import React, {useEffect} from "react";
import {useParams} from "react-router-dom";
import SkeletonArticleLoader from "@/components/skeleton/SkeletonArticleLoader.jsx";
import {SkeletonLoader} from "@/components/loader/SkeletonLoader.jsx";
import {LinkButton} from "@/components/button/LinkButton.jsx";
import Tooltip from "@/components/tooltip/Tooltip.jsx";
import ScrollToTop from "@/components/utils/ScrollToTop.jsx";
import {Button} from "@/components/button/Button.jsx";
import useClipboard from "@/hooks/util/useClipboard.jsx";
import Img from "@/components/utils/Img.jsx";
import {useParameterizedQuery} from "@/services/queries.jsx";
import Head from "@/components/seo/Head.jsx";
import JsonLdGeneric from "@/components/seo/jsonld/JsonLdGeneric.jsx";
import RocketConfig from "@/components/article-details/RocketConfig.jsx";
import SpacecraftConfig from "@/components/article-details/SpacecraftConfig.jsx";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {faCircleInfo, faAlignLeft, faChevronLeft, faShareFromSquare, faRocket} from '@fortawesome/free-solid-svg-icons';
import {useStatePagination} from "@/hooks/paging-filtering/useStatePagination.jsx";
import UpcomingLaunch from "@/components/article-details/UpcomingLaunch.jsx";

const LIMIT = 4;
function Agency(){
    const baseUrl = `${import.meta.env.VITE_BACKEND_BASE_URL}/public/agency`;
    const rocketsUrl = `${import.meta.env.VITE_BACKEND_BASE_URL}/public/rockets`;
    const {id} = useParams();
    const queryData = useParameterizedQuery({
        url: `${baseUrl}/${id}`,
        params: `agency-${id}`,
        cacheKey: "agency-article"
    });
    const pagination = useStatePagination(LIMIT);
    const rocketConfigQuery = useParameterizedQuery({
        url: `${rocketsUrl}?limit=${pagination?.itemsPerPage}&page=${pagination?.page}&agency=${id}`,
        params: `rocketConfig-${id}-page-${pagination?.page}`,
        cacheKey: "rocketConfig"
    });
    const { copied, copyToClipboard } = useClipboard();
    const data = queryData?.data || [];
    const country = data?.country || [];
    const rocketConfig = rocketConfigQuery?.data?._embedded?.rocketConfigSummarizedDTOes;

    const contentConfig = {
        component: SkeletonArticleLoader,
        count: 1
    };
    const handleShare = () => {
        copyToClipboard(window.location.href)
    };
    const checkValue = (value) => { return (value ? value : "―"); }

    useEffect(() => {
        const total = rocketConfigQuery?.data?.page?.totalElements;
        if (total) {
            pagination.setTotalItems(total);
        }
    }, [rocketConfigQuery]);

    return(
        <>
            <Head
                title={data?.name}
                description={data?.description}
                image={data?.images?.[0]?.image_url}
                alt={data?.images?.[0]?.name }
                type="article"
            />
            <JsonLdGeneric
                title={data?.name}
                description={data?.description}
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
                                        src={data?.images?.[0]?.image_url}
                                        alt={data?.images?.[0]?.name || "default"}
                                        className="card-img scale-down-img"
                                        defaultSrc={`${import.meta.env.VITE_CLOUDFRONT_URL}/assets/logo/moonkeyeu-logo.svg`}
                                    />
                                </div>
                                <div className="overview-info-wrapper container flex flex-column justify-center padding-2" data-type="full-bleed">
                                    <div className="article-detail-container">
                                        <div className="panel-body">
                                            <h3 className="agency-name">{data?.name}</h3>
                                            <hr className="hr-7-sm" />
                                            <div className="panel-info-wrapper">
                                                <div className="panel-info-container">
                                                    <div className="detail-wrapper fs-small-200 padding-1 clr-star-300">
                                                        <p className="info-panel-row">Type</p>
                                                        <p className="info-panel-row">{data?.type}</p>
                                                    </div>
                                                    <div className="detail-wrapper fs-small-200 padding-1 clr-star-300">
                                                        <p className="info-panel-row">Country</p>
                                                        <p className="info-panel-row">{country?.[0]?.alpha_3_code}</p>
                                                    </div>
                                                </div>
                                                <div className="panel-info-container">
                                                    <div className="detail-wrapper fs-small-200 padding-1 clr-star-300">
                                                        <p className="info-panel-row">Spacecraft</p>
                                                        <p className="info-panel-row">{checkValue(data?.Spacecraft)}</p>
                                                    </div>
                                                    <div className="detail-wrapper fs-small-200 padding-1 clr-star-300">
                                                        <p className="info-panel-row">Launchers</p>
                                                        <p className="info-panel-row">{checkValue(data?.Launchers)}</p>
                                                    </div>
                                                </div>
                                                <div className="panel-info-container">
                                                    <div className="detail-wrapper fs-small-200 padding-1 clr-star-300">
                                                        <p className="info-panel-row">Administrator</p>
                                                        <p className="info-panel-row">{checkValue(data?.administrator)}</p>
                                                    </div>
                                                </div>
                                            </div>
                                            <hr className="hr-7-sm" />
                                        </div>
                                    </div>
                                    <div className="container flex justify-space-evenly align-center padding-block-2" data-type="full-bleed" data-overflow="visible">
                                        { data?.info_url ? (
                                            <div className="info">
                                                <LinkButton
                                                    className="btn-transparent btn-instragram"
                                                    to={data?.info_url}
                                                    isExternal={true}
                                                >
                                                    <FontAwesomeIcon icon={faCircleInfo} />
                                                </LinkButton>
                                            </div>
                                        ) : (
                                            <Tooltip message="No Info Available">
                                                <div className="info">
                                                    <LinkButton
                                                        className="btn-transparent btn-wikipedia"
                                                        isExternal={true}
                                                        disabled={true}
                                                    >
                                                        <FontAwesomeIcon icon={faCircleInfo} />
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
                            <div className="article-info-container container flex flex-column" data-type="full-bleed">
                                <section className="agency-section">
                                    <div className="heading-box">
                                        <FontAwesomeIcon icon={faAlignLeft} />
                                        <h2>Agency Description</h2>
                                    </div>
                                    <hr className="hr-7-sm bg-hr-600" />
                                    <div className="info-box">
                                        <p>{data?.description}</p>
                                    </div>
                                </section>
                                {data?.upcoming_launch && <UpcomingLaunch launch={data?.upcoming_launch}/>}
                                {rocketConfig?.length > 0 &&
                                    <RocketConfig
                                        queryData={rocketConfigQuery}
                                        pagination={pagination}
                                    />
                                }
                                {data?.spacecraft_configurations?.length > 0 &&
                                    <SpacecraftConfig
                                        spacecraftConfigs={data?.spacecraft_configurations}
                                    />
                                }
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
export default Agency;