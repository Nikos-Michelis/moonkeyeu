import * as THREE from "three";
import { OrbitControls } from "three/examples/jsm/controls/OrbitControls";
import Stats from "three/examples/jsm/libs/stats.module";
import {TextureLoader} from "three";

export default class SceneInit {
    constructor({ canvas, fov = 36 } = {}) {
        this.canvas = canvas;
        this.fov = fov;
        this.loader = new TextureLoader();
    }

    initScene() {
        this.camera = new THREE.PerspectiveCamera(
            this.fov,
            this.canvas.innerWidth,
            this.canvas.innerHeight,
            1000
        );

        this.camera.position.set(
            70 * Math.cos(Math.PI / 6),
            30 * Math.sin(Math.PI / 6),
            40
        );

        this.scene = new THREE.Scene();
        const container = this.canvas.parentElement;

        this.renderer = new THREE.WebGLRenderer({
            canvas: this.canvas,
            antialias: false
        });

        this.renderer.setSize(container.clientWidth, container.clientHeight);

        this.controls = new OrbitControls(this.camera, this.renderer.domElement);
        this.controls.minDistance = 10;
        this.controls.maxDistance = 100;

        this.stats = Stats();
        container.appendChild(this.stats.dom);

        this.addStarField();
        this.addSunLight();
        this.addAmbient();

        window.addEventListener("resize", () => this.onWindowResize(), false);
    }

    animate() {
        window.requestAnimationFrame(this.animate.bind(this));
        this.render();
        this.stats.update();
    }

    addStarField() {
        this.scene.background = this.loader.load("stars.jpg");
    }

    addSunLight() {
        const sunLight = new THREE.PointLight(0xffff99, 1000);
        sunLight.position.set(50, 0, 50);
        sunLight.castShadow = true;
        this.scene.add(sunLight);
    }

    addAmbient() {
        let ambient = new THREE.AmbientLight(0x222222, 6);
        this.scene.add(ambient);
    }

    render() {
        this.renderer.render(this.scene, this.camera);
    }

    onWindowResize() {
        const container = this.canvas.parentElement;
        this.camera.aspect = container.clientWidth / container.clientHeight;
        this.camera.updateProjectionMatrix();
        this.renderer.setSize(container.clientWidth, container.clientHeight);
    }

    dispose() {
        if (this.controls) this.controls.dispose();
        if (this.renderer) this.renderer.dispose();

        this.scene.traverse((obj) => {
            if (obj.geometry) obj.geometry.dispose();

            if (obj.material) {
                if (Array.isArray(obj.material)) {
                    obj.material.forEach((m) => m.dispose());
                } else {
                    obj.material.dispose();
                }
            }
        });

        window.removeEventListener("resize", this.onWindowResize);
    }
}
