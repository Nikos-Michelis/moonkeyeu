import {
    Mesh,
    Color,
    Group,
    DoubleSide,
    RingGeometry,
    TorusGeometry,
    TextureLoader,
    ShaderMaterial,
    SRGBColorSpace,
    AdditiveBlending,
    MeshPhongMaterial,
    MeshBasicMaterial,
    IcosahedronGeometry,
} from "three";
import * as THREE from "three";

export class Planet {
    group;
    loader;
    animate;
    planetGroup;
    planetGeometry;

    constructor({
                    orbitSpeed = 1,
                    orbitRadius = 1,
                    orbitRotationDirection = "clockwise",

                    planetSize = 1,
                    planetAngle = 0,
                    planetRotationSpeed = 1,
                    planetRotationDirection = "clockwise",
                    planetTexture,

                    rimHex = 0x0088ff,
                    facingHex = 0x000000,
                    atmosphere,
                    isStar = false,
                    ring = null,
                } = {}) {
        this.orbitSpeed = orbitSpeed;
        this.orbitRadius = orbitRadius;
        this.orbitRotationDirection = orbitRotationDirection;

        this.planetSize = planetSize;
        this.planetAngle = planetAngle;
        this.planetTexture = planetTexture;
        this.planetRotationSpeed = planetRotationSpeed;
        this.planetRotationDirection = planetRotationDirection;

        this.atmosphere = atmosphere;
        this.isStar = isStar;
        this.ring = ring;

        this.group = new Group();
        this.planetGroup = new Group();
        this.loader = new TextureLoader();
        this.planetGeometry = new IcosahedronGeometry(this.planetSize, 12);

        this.addRing();
        this.addAtmosphere();
        this.createPlanet();
        this.createGlow(rimHex, facingHex);

        this.animate = this.createAnimateFunction();
         this.animate();
    }

    createPlanet() {
        const planetMaterial = this.loadTextures();
        planetMaterial.colorSpace = SRGBColorSpace;
        const planetMesh = new Mesh(this.planetGeometry, planetMaterial);
        this.planetGroup.add(planetMesh);
        this.planetGroup.rotation.z = (-this.planetAngle * Math.PI) / 180;
        this.group.add(this.planetGroup);
    }

    loadTextures(){
        const dayTex = this.loader.load(this.planetTexture?.day);
        const nightTex = this.loader.load(this.planetTexture?.night);
        if (!dayTex && !nightTex) console.error("Failed to load planet textures.");
        if (!this.isStar) {
            return  new THREE.ShaderMaterial({
                uniforms: {
                    dayMap: {value: dayTex},
                    nightMap: {value: nightTex},
                    lightDirection: {value: new THREE.Vector3(1, 0, 1).normalize()}
                },
                vertexShader: `
                    varying vec3 vNormal;
                    varying vec2 vUv;
                    varying vec3 vSunDirection;
                
                    uniform vec3 sunPosition;
                
                    void main() {
                      vUv = uv;
                      vec4 worldPosition = modelMatrix * vec4(position, 1.0);
                      vNormal = normalize(modelMatrix * vec4(normal, 0.0)).xyz;
                      vSunDirection = normalize(sunPosition - worldPosition.xyz);
                      gl_Position = projectionMatrix * modelViewMatrix * vec4(position, 1.0);
                    }
                  `,
                fragmentShader: `
                    uniform sampler2D dayMap;
                    uniform sampler2D nightMap;
                    uniform vec3 lightDirection;
    
                    varying vec2 vUv;
                    varying vec3 vNormal;
    
                    void main() {
                        // dot = amount of light (1 = full day, 0 = full night)
                        float light = max(dot(normalize(vNormal), lightDirection), 0.1);
    
                        vec3 dayColor   = texture2D(dayMap, vUv).rgb;
                        vec3 nightColor = texture2D(nightMap, vUv).rgb;
    
                        // mix: if light=1 → day, if light=0 → night
                        vec3 color = mix(nightColor, dayColor, light);
    
                        gl_FragColor = vec4(color, 1.0);
                    }
                `
            });
        }
        return new THREE.MeshBasicMaterial({
            map: dayTex,
        });
    }

    createGlow(rimHex, facingHex) {
        if (!this.isStar) return;

        const uniforms = {
            color1: { value: new Color(rimHex) },
            color2: { value: new Color(facingHex) },
            fresnelBias: { value: 0.2 },
            fresnelScale: { value: 1.5 },
            fresnelPower: { value: 4.0 },
        };

        const vertexShader = `
    uniform float fresnelBias;
    uniform float fresnelScale;
    uniform float fresnelPower;

    varying float vReflectionFactor;

    void main() {
      vec4 mvPosition = modelViewMatrix * vec4( position, 1.0 );
      vec4 worldPosition = modelMatrix * vec4( position, 1.0 );

      vec3 worldNormal = normalize( mat3( modelMatrix[0].xyz, modelMatrix[1].xyz, modelMatrix[2].xyz ) * normal );

      vec3 I = worldPosition.xyz - cameraPosition;

      vReflectionFactor = fresnelBias + fresnelScale * pow( 1.0 + dot( normalize( I ), worldNormal ), fresnelPower );

      gl_Position = projectionMatrix * mvPosition;
    }
    `;

        const fragmentShader = `
      uniform vec3 color1;
      uniform vec3 color2;

      varying float vReflectionFactor;

      void main() {
        float f = clamp( vReflectionFactor, 0.0, 1.0 );
        gl_FragColor = vec4(mix(color2, color1, vec3(f)), f);
      }
    `;

        const planetGlowMaterial = new ShaderMaterial({
            uniforms,
            vertexShader,
            fragmentShader,
            transparent: true,
            blending: AdditiveBlending,
        });
        const planetGlowMesh = new Mesh(this.planetGeometry, planetGlowMaterial);
        planetGlowMesh.scale.setScalar(1.1);
        this.planetGroup.add(planetGlowMesh);
    }

    addAtmosphere(){
        if(this.atmosphere){
            const atmosphereGeo = new THREE.SphereGeometry(this.planetSize + 0.1, 32, 20);
            const atmosphereMaterial = new THREE.MeshPhongMaterial({
                map: this.loader.load(this.atmosphere),
                transparent: true,
                opacity: 0.45,
                depthWrite: false,
                blending: THREE.AdditiveBlending,
                side: THREE.DoubleSide
            });

            this.atmosphereMesh = new THREE.Mesh(atmosphereGeo, atmosphereMaterial);
            this.atmosphereMesh.rotation.z = 0.41;
            this.planetGroup.add(this.atmosphereMesh);
        }
    }

    addRing() {
        if (!this.ring) return;

        const ringsGeometry = new RingGeometry(this.ring.innerRadius, this.ring.outerRadius, 32);
        const ringsMaterial = new MeshBasicMaterial({
            side: DoubleSide,
            transparent: true,
            map: this.loader.load(this.ring.texture),
        });

        const ring = new Mesh(ringsGeometry, ringsMaterial);
        ring.rotation.x = -0.5 * Math.PI;
        ring.rotation.y = (this.planetAngle * Math.PI) / 180;
        this.group.add(ring);
    }

    createAnimateFunction() {
        return () => {
            requestAnimationFrame(this.animate);
            this.updatePlanetRotation();
            this.updateAtmosphereRotation();
        };
    }


    updateOrbitRotation() {
        if (this.orbitRotationDirection === "clockwise") {
            this.group.rotation.y -= this.orbitSpeed;
        } else if (this.orbitRotationDirection === "counterclockwise") {
            this.group.rotation.y += this.orbitSpeed;
        }
    }

    updatePlanetRotation() {
        if (this.planetRotationDirection === "clockwise") {
            this.planetGroup.rotation.y -= this.planetRotationSpeed;
        } else if (this.planetRotationDirection === "counterclockwise") {
            this.planetGroup.rotation.y += this.planetRotationSpeed;
        }
    }

    updateAtmosphereRotation(){
        if (this.atmosphereMesh) {
            this.atmosphereMesh.rotation.y += 0.0008;
        }
    }

    getPlanet() {
        return this.group;
    }
}