import React, {useEffect} from "react";
import {useParams} from "react-router-dom";
import SkeletonArticleLoader from "@/components/skeleton/SkeletonArticleLoader.jsx";
import {SkeletonLoader} from "@/components/loader/SkeletonLoader.jsx";
import useClipboard from "@/hooks/util/useClipboard.jsx";
import ScrollToTop from "@/components/utils/ScrollToTop.jsx";
import {Button} from "@/components/button/Button.jsx";
import {useParameterizedQuery} from "@/services/queries.jsx";
import Head from "@/components/seo/Head.jsx";
import JsonLdPerson from "@/components/seo/jsonld/JsonLdPerson.jsx";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {faAtom, faBook, faChevronLeft} from '@fortawesome/free-solid-svg-icons';
import {Planet} from "@/components/utils/solar-system/Planet.js";
import SceneInit from "@/components/utils/solar-system/SceneInit.js";

function CelestialBody(){
    const baseUrl = `${import.meta.env.VITE_BACKEND_BASE_URL}/public/celestial-bodies`;
    const {id} = useParams();
    const { copied, copyToClipboard } = useClipboard();
    const canvasRef = React.useRef(null);
    const queryData
        = useParameterizedQuery({
        url: `${baseUrl}/${id}`,
        params: `celestial_body-${id}`,
        cacheKey: "celestial_body"
    });
    const data = queryData?.data || [];
    const contentConfig = {
        component: SkeletonArticleLoader,
        count: 1
    };
    const ROTATION_SPEEDS = {
        mercury: 0.001,
        venus: 0.0005,
        earth: 0.01,
        mars: 0.01,
        jupiter: 0.005,
        saturn: 0.01,
        uranus: 0.005,
        neptune: 0.005,
        pluto: 0.001,
        moon: 0.01,
        sun: 0.001
    };
    const RING_DISTANCE = {
        saturn: { ring_in_radius: 18, ring_out_radius: 29,},
        uranus: { ring_in_radius: 8, ring_out_radius: 10 },
    };
   const isStar = data?.type?.toLowerCase().includes("star");

    useEffect(() => {
        console.log(data)
    }, [data]);

    let sceneInit;
    useEffect(() => {
        if (!canvasRef.current) return;
        sceneInit = new SceneInit({canvas: canvasRef.current});
        sceneInit.initScene();
        sceneInit.animate()
    }, []);

    useEffect( () => {
        if (!sceneInit) return;

        const planetMesh = new Planet({
            orbitRotationDirection: "clockwise",
            planetSize: 18,
            planetRotationSpeed: ROTATION_SPEEDS[data?.name.toLowerCase()] || 0.01,
            planetRotationDirection: "counterclockwise",
            planetAngle: data?.tilt?.value,
            planetTexture: {day: data?.img_day, night: data?.img_night},
            rimHex: 0xffff99,
            facingHex: 0xffff99,
            atmosphere: data?.img_atmosphere,
            isStar: isStar,
            ring: {...RING_DISTANCE[data?.name.toLowerCase()] || [], texture: data?.img_ring},
        }).getPlanet();

        sceneInit.scene.add(planetMesh);

        return () => {
            sceneInit?.dispose();
        };

    }, [sceneInit]);

    const handleShare = () => {
        copyToClipboard(window.location.href)
    };

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
                celestialBodies={data?.name}
            />
            <ScrollToTop behavior="auto" />
            <SkeletonLoader
                isFetching={queryData.isFetching}
                isPending={queryData.isFetching}
                isError={queryData.isError}
                contentConfig={contentConfig}>
                <section className="article">
                    <div className="container flex justify-center" data-type="wide" data-spacing="none">
                        <div className="container container--light-overlay article__content flex flex-column align-center" data-type="fixed" data-spacing="none">
                            <div className="container flex justify-start padding-block-start-7 padding-block-end-2">
                                <Button className="btn--transparent" onClick={() => window.history.back()}>
                                    <FontAwesomeIcon icon={faChevronLeft} /> Back
                                </Button>
                            </div>
                            <div className="container article__overview flex flex-column justify-center align-center bg-dark-cosmos-300" data-type="full-bleed">
                                <div className="article__image-box">
                                    <canvas ref={canvasRef} className="article__canvas" />
                                </div>
                            </div>
                            <div className="article__info-container container flex flex-column" data-type="full-bleed">
                                <section className="celestialbody-section">
                                    <div className="article__heading-box">
                                        <FontAwesomeIcon icon={faAtom} />
                                        <h2>{data?.name}</h2>
                                    </div>
                                    <hr className="hr-100-sm bg-hr-600" />
                                    <div className="panel">
                                        <div className="panel__wrapper">
                                            <div className="panel__container">
                                                <div className="panel__detail-box fs-small-100 padding-1">
                                                    <p className="panel__text">Type</p>
                                                    <p className="panel__text">{data?.type}</p>
                                                </div>
                                                <div className="panel__detail-box fs-small-100 padding-1">
                                                    <p className="panel__text">Diameter</p>
                                                    <p className="panel__text">{data?.diameter?.readable}</p>
                                                </div>
                                            </div>
                                            <div className="panel__container">
                                                <div className="panel__detail-box fs-small-100 padding-1">
                                                    <p className="panel__text">Mass</p>
                                                    <p className="panel__text">{data?.mass?.readable}</p>
                                                </div>
                                                <div className="panel__detail-box fs-small-100 padding-1">
                                                    <p className="panel__text">Gravity</p>
                                                    <p className="panel__text">{data?.gravity?.readable}</p>
                                                </div>
                                            </div>
                                            <div className="panel__container">
                                                <div className="panel__detail-box fs-small-100 padding-1">
                                                    <p className="panel__text">Rotation</p>
                                                    <p className="panel__text">{data?.rotation?.readable}</p>
                                                </div>
                                                <div className="panel__detail-box fs-small-100 padding-1">
                                                    <p className="panel__text">Atmosphere</p>
                                                    <p className="panel__text">{data?.atmosphere}</p>
                                                </div>
                                            </div>
                                            <div className="panel__container">
                                                <div className="panel__detail-box fs-small-100 padding-1">
                                                    <p className="panel__text">Moons</p>
                                                    <p className="panel__text">{data?.moons}</p>
                                                </div>
                                                <div className="panel__detail-box fs-small-100 padding-1">
                                                    <p className="panel__text">Orbit</p>
                                                    <p className="panel__text">{data?.orbit?.readable}</p>
                                                </div>
                                            </div>
                                            <div className="panel__container">
                                                <div className="panel__detail-box fs-small-100 padding-1">
                                                    <p className="panel__text">Titl</p>
                                                    <p className="panel__text">{data?.tilt?.readable}</p>
                                                </div>

                                            </div>
                                        </div>
                                        <hr/>
                                    </div>
                                    <div className="margin-block-end-12">
                                        <p>{data?.description}</p>
                                    </div>
                                </section>
                                <div className="padding-block-end-4">
                                    <hr className="hr-90-md"/>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>
            </SkeletonLoader>
        </>
    )
}
export default CelestialBody;