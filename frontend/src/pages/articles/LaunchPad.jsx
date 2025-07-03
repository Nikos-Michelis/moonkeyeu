import React, {useEffect} from "react";
import {useParams} from "react-router-dom";
import Launch from "@/components/article-details/Launch.jsx";
import SkeletonArticleLoader from "@/components/skeleton/SkeletonArticleLoader.jsx";
import {SkeletonLoader} from "@/components/loader/SkeletonLoader.jsx";
import {LinkButton} from "@/components/button/LinkButton.jsx";
import Tooltip from "@/components/tooltip/Tooltip.jsx";
import ScrollToTop from "@/components/utils/ScrollToTop.jsx";
import {Button} from "@/components/button/Button.jsx";
import useClipboard from "@/hooks/util/useClipboard.jsx";
import Agencies from "@/components/article-details/Agencies.jsx";
import Img from "@/components/utils/Img.jsx";
import {useParameterizedQuery} from "@/services/queries.jsx";
import Head from "@/components/seo/Head.jsx";
import JsonLdGeneric from "@/components/seo/jsonld/JsonLdGeneric.jsx";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faWikipediaW } from '@fortawesome/free-brands-svg-icons';
import {faChevronLeft, faCircleInfo, faShareFromSquare} from "@fortawesome/free-solid-svg-icons";
import {useStatePagination} from "@/hooks/paging-filtering/useStatePagination.jsx";

const LIMIT = 4;
function LaunchPad(){
    const baseUrl = `${import.meta.env.VITE_BACKEND_BASE_URL}/public/launch-pad`;
    const launchesUrl = `${import.meta.env.VITE_BACKEND_BASE_URL}/public/launches`;
    const {id} = useParams();
    const queryData = useParameterizedQuery({
        url: `${baseUrl}/${id}`,
        params: `launch-pads-${id}`,
        cacheKey: "launch-pads-article"
    });
    const pagination = useStatePagination(LIMIT);
    const launchesData = useParameterizedQuery({
        url: `${launchesUrl}?limit=${pagination.itemsPerPage}&page=${pagination.page}&upcoming=false&pad=${id}`,
        params: `pad-launches-${id}-page-${pagination.page}`,
        cacheKey: "launch-pads-article"
    });
    const { copied, copyToClipboard } = useClipboard();
    const data = queryData?.data || [];
    const launches = launchesData?.data?._embedded?.launchNormalDTOes || [];
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
    const booleanConverter = (value) => {
        return value ? value === true ? "Yes" : "No" : null;
    }

    useEffect(() => {
        const total = launchesData?.data?.page?.totalElements;
        if (total) {
            pagination.setTotalItems(total);
        }
    }, [launchesData]);

    return(
        <>
            <Head
                title={data?.name}
                description={data?.location?.description}
                image={data?.map_image}
                type="article"
            />
            <JsonLdGeneric
                title={data?.name}
                description={data?.location?.description}
                image={data?.map_image}
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
                                        src={data?.map_image}
                                        alt={data?.name || "default"}
                                        className="card-img"
                                        defaultSrc={`${import.meta.env.VITE_CLOUDFRONT_URL}/assets/logo/moonkeyeu-logo.svg`}
                                    />
                                </div>
                                <div className="overview-info-wrapper container flex flex-column justify-center padding-2" data-type="full-bleed">
                                    <div className="article-detail-container">
                                        <h3 className="launchpad-name">{data?.name}</h3>
                                        <h5>{data?.location?.name}</h5>
                                        <div className="panel-body">
                                            <hr />
                                            <div className="panel-info-wrapper">
                                                <div className="panel-info-container">
                                                    <div className="detail-wrapper fs-small-200 clr-star-300 padding-2">
                                                        <p className="info-panel-row">Active</p>
                                                        <p className="info-panel-row">{checkValue(booleanConverter(data?.active))}</p>
                                                    </div>
                                                    <div className="detail-wrapper fs-small-200 clr-star-300 padding-2">
                                                        <p className="info-panel-row">Total launches</p>
                                                        <p className="info-panel-row">{data?.total_launch_count}</p>
                                                    </div>
                                                </div>
                                                <hr/>
                                                {data?.description &&
                                                    <>
                                                        <div className="panel-info-container">
                                                            <div className="detail-wrapper clr-star-300 paddin-1">
                                                                <p className="description-panel padding-block-2">{data?.description}</p>
                                                            </div>
                                                        </div>
                                                        <hr/>
                                                    </>
                                                }
                                            </div>
                                        </div>
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
                                                        className="btn-transparent btn-wikipedia"
                                                        isExternal={true}
                                                        disabled={true}
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
                                                    to={data?.wiki_url}
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
                                                        disabled={true}
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
                            <div className="article-info-container container flex flex-column" data-type="full-bleed">
                                <section className="location-section">
                                    <div className="container flex flex-wrap justify-center align-center padding-block-8" data-type="full-bleed">
                                        <div className="flex justify-space-between margin-block-2">
                                            <p>{data?.location?.description}</p>
                                        </div>
                                    </div>
                                </section>
                                <Agencies agencies={data?.agencies}/>
                                <Launch launches={launches} navUrl={'/launches/'} pagination={pagination} hasPagination={true}/>
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
export default LaunchPad;