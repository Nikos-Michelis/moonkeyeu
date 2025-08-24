import React, {useEffect} from 'react';
import Tooltip from "@/components/tooltip/Tooltip.jsx";
import {Button} from "@/components/button/Button.jsx";
import useClipboard from "@/hooks/util/useClipboard.jsx";
import Img from "@/components/utils/Img.jsx";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faCircleInfo, faShareFromSquare} from "@fortawesome/free-solid-svg-icons";
import {LinkButton} from "@/components/button/LinkButton.jsx";
import SceneInit from "@/components/utils/solar-system/SceneInit.js";
import * as THREE from "three";
import Planet from "@/components/utils/solar-system/Planet.js";

const PlanetCard = ({id ,name ,radius ,tilt ,rotation ,orbit ,distance ,moons, ring_in_radius,
                        ring_out_radius ,info , img_atmosphere, img_day, img_night, img_ring ,isStar ,hasRing }) => {
    const tooltipInfoMessage = id ? "" : "No Info Available";
    const canvasRef = React.useRef(null);

    const { copied, copyToClipboard } = useClipboard();
    const handleShare = () => {
        const url = window.location.origin + window.location.pathname + "/" + id;
        copyToClipboard(url);
    };
    useEffect( () => {
        if (!canvasRef.current) return;

        let sceneInit = new SceneInit(canvasRef?.current);
        sceneInit.initScene();
        sceneInit.animate();

        sceneInit.scene.background = new THREE.TextureLoader().load("stars.jpg");

        const sunLight = new THREE.DirectionalLight(0xFDFFD3, 1);
        sunLight.position.set(50, 0, 50);
        sunLight.castShadow = true;
        sceneInit.scene.add(sunLight);

        let ambient = new THREE.AmbientLight(0x222222, 6);
        sceneInit.scene.add(ambient);
        const sunGeometry =
            new Planet(8, 0, tilt,
                {day: img_day, night: img_night}, img_atmosphere,
                {innerRadius: ring_in_radius, outerRadius: ring_out_radius, texture: img_ring}, isStar);
        const sunMesh = sunGeometry.getMesh();
        const solarSystem = new THREE.Group();
        solarSystem.add(sunMesh);

        sceneInit.scene.add(solarSystem);

        const EARTH_YEAR = 2 * Math.PI * (1 / 60) * (1 / 60);
        const animate = () => {
            sunMesh.rotation.y += EARTH_YEAR;
            requestAnimationFrame(animate);
        };
        animate();
        return () => {
            sceneInit.dispose();
        };

    }, []);
    return (
        <article className="portrait-card portrait-card__container portrait-card__container--medium">
            <div className="portrait-card__media">
                <canvas ref={canvasRef} className="portrait-card__canvas" />
            </div>
            <div className="portrait-card__info portrait-card__info--medium flex flex-column justify-space-evenly margin-block-start-1 margin-inline-2">
                <div className="panel">
                    <h4 className="panel__title">{name}</h4>
                    <hr/>
                    <div className="panel__wrapper">
                        <div className="panel__container panel__container--col">
                            <div className="panel__detail-box fs-small-100 padding-1">
                                <p className="panel__text">Type</p>
                                <p className="panel__text">Planet</p>
                            </div>
                            <div className="panel__detail-box fs-small-100 padding-1">
                                <p className="panel__text">Radius</p>
                                <p className="panel__text">12.742 km</p>
                            </div>
                        </div>
                        <div className="panel__container panel__container--col">
                            <div className="panel__detail-box fs-small-100 padding-1">
                                <p className="panel__text">Mass</p>
                                <p className="panel__text">5.97 × 10²⁴ kg</p>
                            </div>
                            <div className="panel__detail-box fs-small-100 padding-1">
                                <p className="panel__text">Gravity</p>
                                <p className="panel__text">9.81 m/s²</p>
                            </div>
                        </div>
                        <div className="panel__container panel__container--col">
                            <div className="panel__detail-box fs-small-100 padding-1">
                                <p className="panel__text">Rotation</p>
                                <p className="panel__text">24 hours</p>
                            </div>
                            <div className="panel__detail-box fs-small-100 padding-1">
                                <p className="panel__text">Atmosphere</p>
                                <p className="panel__text">Yes</p>
                            </div>
                        </div>
                    </div>
                    <hr/>
                </div>
            </div>
            <div className="portrait-card__actions flex flex-wrap justify-center margin-block-4">
                <div className="portrait-card__action">
                    <Button className="portrait-card__button btn btn--primary" to={id}>
                        <FontAwesomeIcon icon={faCircleInfo} /> INFO
                    </Button>
                </div>
                <div className="portrait-card__action">
                    <Tooltip copied={copied} message={copied ? "Copied!" :"Copied to clipboard!"}>
                        <Button
                            className="btn btn--primary"
                            disabled={copied}
                        >
                            <FontAwesomeIcon icon={faShareFromSquare} /> SHARE
                        </Button>
                    </Tooltip>
                </div>
            </div>
        </article>
    );
};

export default PlanetCard;
