import * as THREE from "three";
import { OrbitControls } from "three/examples/jsm/controls/OrbitControls";
import Stats from "three/examples/jsm/libs/stats.module";

export default class SceneInit {
    constructor(canvas, fov = 36, camera, scene, stats, controls, renderer) {
        this.canvas = canvas;
        this.fov = fov;
        this.scene = scene;
        this.stats = stats;
        this.camera = camera;
        this.controls = controls;
        this.renderer = renderer;
    }

    initScene() {
        this.camera = new THREE.PerspectiveCamera(
            this.fov,
            1,
            1,
            1000
        );
        this.camera.position.z = 70;
        this.scene = new THREE.Scene();
        const container = this.canvas.parentElement;
        this.renderer = new THREE.WebGLRenderer({
            canvas: this.canvas,
            antialias: false,
        });
        this.renderer.setSize(container.clientWidth, container.clientHeight);
        this.controls = new OrbitControls(this.camera, this.renderer.domElement);
        this.controls.minDistance = 10;
        this.controls.maxDistance = 100;
        this.stats = Stats();
        container.appendChild(this.stats.dom);
        window.addEventListener("resize", () => this.onWindowResize(), false);
    }

    animate() {
        window.requestAnimationFrame(this.animate.bind(this));
        this.render();
        this.stats.update();
    }

    render() {
        //this.updateScene();
        this.renderer.render(this.scene, this.camera);
    }

    updateScene() {
        this.scene.traverse((obj) => {
            if (obj.userData.isAtmosphere) {
                obj.rotation.y += 0.0008;
            }
        });
    }

    onWindowResize() {
        const container = this.canvas.parentElement;
        this.camera.aspect = container.clientWidth / container.clientHeight;
        this.camera.updateProjectionMatrix();
        this.renderer.setSize(container.clientWidth, container.clientHeight);
    }

    dispose() {
        if (this.controls) {
            this.controls.dispose();
        }

        if (this.renderer) {
            this.renderer.dispose();
        }

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