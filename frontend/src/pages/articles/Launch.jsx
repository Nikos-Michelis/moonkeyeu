import CountdownTimer from "@/components/timers/CountdownTimer.jsx";
import Trajectory from "@/components/article-details/Trajectory.jsx";
import Mission from "@/components/article-details/Mission.jsx";
import Location from "@/components/article-details/Locations.jsx";
import Rocket from "@/components/article-details/Rocket.jsx";
import Agency from "@/components/article-details/Agency.jsx";
import {useParams} from "react-router-dom";
import {DateTime} from "luxon";
import Boosters from "@/components/article-details/Boosters.jsx";
import Crew from "@/components/article-details/Crew.jsx";
import Spacecraft from "@/components/article-details/Spacecraft.jsx";
import RelatedNews from "@/components/article-details/RelatedNews.jsx";
import SkeletonArticleLoader from "@/components/skeleton/SkeletonArticleLoader.jsx";
import {SkeletonLoader} from "@/components/loader/SkeletonLoader.jsx";
import YoutubeVideo from "@/components/article-details/YoutubeVideo.jsx";
import ScrollToTop from "@/components/utils/ScrollToTop.jsx";
import {Button} from "@/components/button/Button.jsx";
import useComparator from "@/hooks/util/useComparator.jsx";
import Img from "@/components/utils/Img.jsx";
import React from "react";
import {useParameterizedQuery} from "@/services/queries.jsx";
import Head from "@/components/seo/Head.jsx";
import Tooltip from "@/components/tooltip/Tooltip.jsx";
import useClipboard from "@/hooks/util/useClipboard.jsx";
import JsonLdEvent from "@/components/seo/jsonld/JsonLdEvent.jsx";
import RelatedPrograms from "@/components/article-details/RelatedPrograms.jsx";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faTriangleExclamation, faChevronLeft, faShareFromSquare, faLocationDot, faCalendarDays } from '@fortawesome/free-solid-svg-icons';

function Launch(){
    const baseUrl = `${import.meta.env.VITE_BACKEND_BASE_URL}/public/launch`;
    const { id} = useParams();
    const { data: newsData } = useParameterizedQuery({
        url: `${import.meta.env.VITE_NEWS_API_URL}&launch=${id}}`,
        params: `launch-${id}`,
        cacheKey: `latest-news`
    });
    const queryData = useParameterizedQuery({
        url: `${baseUrl}/${id}`,
        params: `launch-${id}`,
        cacheKey: 'launch-article'
    });
    const data = queryData?.data || [];
    const launcherStage = data.rocket?.launcher_stage || [];
    const spacecraftStage = data.rocket?.spacecraft_stage || [];
    const programs = data?.programs || [];
    const newsArticles = newsData?.results || [];
    const rocketConfig = data.rocket?.configuration || {};
    const zonedDateTime = DateTime.fromISO(data.net).setZone(DateTime.local().zoneName);
    const formattedZonedDateTime = zonedDateTime.invalid === null? zonedDateTime.toFormat('MMMM dd, yyyy - hh:mm a ZZZZ') : null;
    const { copied, copyToClipboard } = useClipboard();
    const orderedVideo = useComparator(
        data?.video_urls?.filter(video =>
            video.videoUrl.includes("youtube.com") || video.videoUrl.includes("youtu.be")
        ), (a, b) => a.priority > b.priority);
    const contentConfig = {
        component: SkeletonArticleLoader,
        count: 1
    };
    const handleShare = () => {
        copyToClipboard(window.location.href)
    };
    return(
        <>
            <Head
                title={data?.fullname}
                description={data?.mission?.description}
                image={data?.image?.image_url}
                alt={data?.image?.name}
                type="article"

            />
            <JsonLdEvent
                title={data?.fullname}
                description={data?.description}
                image={data?.image?.image_url}
                startDate={data?.window_start}
                endDate={data?.window_end}
                location={data?.pad}
                agency={data?.launch_provider?.name}
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
                                    <Button
                                        className="btn-transparent"
                                        onClick={() => window.history.back()}
                                    >
                                        <FontAwesomeIcon icon={faChevronLeft} /> Back
                                    </Button>
                                </div>
                                <div className="container __overview flex flex-column justify-center align-center bg-dark-cosmos-300" data-type="full-bleed">
                                    {zonedDateTime.invalid === null && zonedDateTime > Date.now() ? (
                                        <CountdownTimer net={zonedDateTime} timerStyle="article"/>
                                    ) : null }
                                    <div className="image-box">
                                        <Img
                                            src={data.rocket?.configuration?.image?.image_url}
                                            alt={data.rocket?.configuration?.image?.name || "default"}
                                            className="card-img"
                                            defaultSrc={`${import.meta.env.VITE_CLOUDFRONT_URL}/assets/logo/moonkeyeu-logo.svg`}
                                        />
                                    </div>
                                    <div className="overview-info-wrapper container flex flex-column justify-center padding-2" data-type="full-bleed">
                                        <div className="article-detail-container">
                                            <h3 className="rocket-name">{data?.fullname}</h3>
                                            <h5 className="rocket-manufacturer">{data.launch_provider?.name}</h5>
                                            <hr className="hr-7-xs"/>
                                            <div className="article-detail-box launch-status">
                                                <FontAwesomeIcon icon={faTriangleExclamation} />
                                                <p className="status-text">{data?.status}</p>
                                            </div>
                                            <div className="article-detail-box launch-schedule">
                                                <FontAwesomeIcon icon={faCalendarDays} />
                                                <p className="launch-date">{formattedZonedDateTime}</p>
                                            </div>
                                            <div className="article-detail-box launch-location">
                                                <FontAwesomeIcon icon={faLocationDot} />
                                                <p className="launch-location">{data.pad?.location?.name}</p>
                                            </div>
                                            <hr className="hr-7-xs"/>
                                            <div className="container flex justify-space-evenly align-center padding-block-2" data-type="full-bleed" data-overflow="visible">
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
                                    {orderedVideo?.videoUrl && <YoutubeVideo videoUrl={orderedVideo?.videoUrl} />}
                                    <Trajectory flightclub_url={data.flightclub_url}/>
                                    <Agency launchProvider={data.launch_provider}/>
                                    {data?.mission &&
                                        <Mission
                                            mission={data.mission || {}}
                                            launchCost={rocketConfig.launch_cost}
                                            missionPatches={data.mission_patches || []}
                                        />
                                    }
                                    {spacecraftStage.length > 0 &&
                                        <Spacecraft stage={spacecraftStage[0] || []}/>
                                    }
                                    {spacecraftStage[0] &&
                                        spacecraftStage[0]?.crew?.length > 0 &&
                                        <Crew key={data.rocket.id} crew={spacecraftStage[0].crew || []}/>
                                    }
                                    <Location pad={data?.pad}/>
                                    {rocketConfig && <Rocket {...rocketConfig}/>}
                                    {launcherStage.length > 0 && <Boosters stage={launcherStage} />}
                                    {programs.length > 0 && <RelatedPrograms programs={programs} />}
                                    {newsArticles.length > 0 && <RelatedNews articles={newsArticles}/>}
                                    <section className="last-updated-section">
                                        <div className="flex flex-wrap justify-center align-center padding-block-start-8">
                                            <p>Last Updated: {formattedZonedDateTime}</p>
                                        </div>
                                    </section>
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
export default Launch;