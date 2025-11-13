import React, { createContext, useContext, useRef, useEffect, useState } from "react";
import * as THREE from "three";
import SceneInit from "@/components/utils/solar-system/SceneInit.js";

const SceneContext = createContext(null);

export const SceneProvider = ({ children }) => {
    const canvasRef = useRef(null);
    const [scene, setScene] = useState(null);

    useEffect(() => {
        if (!canvasRef.current) return;

        const s = new SceneInit(canvasRef.current);
        console.log(s)
        s.initScene();
        s.animate();

        // Example setup
        s.scene.background = new THREE.TextureLoader().load("stars.jpg");
        const sunLight = new THREE.DirectionalLight(0xFDFFD3, 1);
        sunLight.position.set(50, 0, 50);
        s.scene.add(sunLight);

        const ambient = new THREE.AmbientLight(0x222222, 6);
        s.scene.add(ambient);

        setScene(s.scene);

        return () => s.dispose();
    }, []);

    const addObject = (obj) => scene?.add(obj);
    const removeObject = (obj) => scene?.remove(obj);

    return (
        <SceneContext.Provider value={{ scene, addObject, removeObject, canvasRef }}>
            {children}
        </SceneContext.Provider>
    );
};

export const useScene = () => {
    const context = useContext(SceneContext);
    if (!context) throw new Error("useScene must be used within a SceneProvider");
    return context;
};
