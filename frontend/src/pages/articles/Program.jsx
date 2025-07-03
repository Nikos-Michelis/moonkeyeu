import React, { useEffect } from "react";
import {useParams} from "react-router-dom";
import Launch from "@/components/article-details/Launch.jsx";
import SkeletonArticleLoader from "@/components/skeleton/SkeletonArticleLoader.jsx";
import {SkeletonLoader} from "@/components/loader/SkeletonLoader.jsx";
import {LinkButton} from "@/components/button/LinkButton.jsx";
import Tooltip from "@/components/tooltip/Tooltip.jsx";
import ScrollToTop from "@/components/utils/ScrollToTop.jsx";
import {Button} from "@/components/button/Button.jsx";
import useClipboard from "@/hooks/util/useClipboard.jsx";
import {DateTime} from "luxon";
import Agencies from "@/components/article-details/Agencies.jsx";
import Img from "@/components/utils/Img.jsx";
import {useParameterizedQuery} from "@/services/queries.jsx";
import Head from "@/components/seo/Head.jsx";
import JsonLdGeneric from "@/components/seo/jsonld/JsonLdGeneric.jsx";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faChevronLeft, faCircleInfo, faShareFromSquare } from '@fortawesome/free-solid-svg-icons';
import { faWikipediaW } from '@fortawesome/free-brands-svg-icons';
import {useStatePagination} from "@/hooks/paging-filtering/useStatePagination.jsx";

const LIMIT = 4;
function Program(){
    const programsUrl = `${import.meta.env.VITE_BACKEND_BASE_URL}/public/program`;
    const launchesUrl = `${import.meta.env.VITE_BACKEND_BASE_URL}/public/launches`;
    const { id} = useParams();
    const queryData = useParameterizedQuery({
        url: `${programsUrl}/${id}`,
        params: `program-${id}`,
        cacheKey: "program-article"
    });
    const pagination = useStatePagination(LIMIT);
    const launchesQuery = useParameterizedQuery({
        url: `${launchesUrl}?limit=${pagination.itemsPerPage}&page=${pagination.page}&upcoming=false&program=${id}`,
        params: `article-launches-${id}-page-${pagination.page}`,
        cacheKey: "article-launches"
    });
    const { copied, copyToClipboard } = useClipboard();
    const data = queryData?.data || [];
    const zonedDateTime = DateTime.fromISO(data.start_date).setZone(DateTime.local().zoneName);
    const formattedZonedDateTime = zonedDateTime.toFormat('MMMM dd, yyyy');
    const contentConfig = {
        component: SkeletonArticleLoader,
        count: 1
    };
    const handleShare = () => {
        copyToClipboard(window.location.href)
    };

    useEffect(() => {
        const total = launchesQuery?.data?.page?.totalElements;
        if (total) {
            pagination.setTotalItems(total);
        }
    }, [launchesQuery]);

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
                                        <h3 className="program-name">{data?.name}</h3>
                                        <div className="flex justify-space-between margin-block-2">
                                            <h5 className="program-type">{data?.type}</h5>
                                            <h5 className="program-name">{formattedZonedDateTime}</h5>
                                        </div>
                                        <hr className="hr-7-xs"/>
                                        <div className="article-detail-box">
                                            <p className="article-description">{data?.description}</p>
                                        </div>
                                        <hr className="hr-7-xs"/>
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
                                                        className="btn-transparent btn-instagram"
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
                                <Agencies agencies={data?.agencies}/>
                                <Launch queryData={launchesQuery} navUrl={'/launches/'} pagination={pagination} hasPagination={true}/>
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
export default Program;