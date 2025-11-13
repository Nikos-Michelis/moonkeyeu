import * as THREE from "three";

export default class Planet {
    constructor(radius, positionX, tilt, textures, atmosphere, ring, isStar = false) {
        this.radius = radius;
        this.positionX = positionX;
        this.tilt = tilt;
        this.textures = textures;
        this.atmosphere = atmosphere;
        this.ring = ring;
        this.isStar = isStar;
    }

    getMesh() {
        if (this.mesh === undefined || this.mesh === null) {
            const geometry = new THREE.SphereGeometry(this.radius);
            let material = this.loadTextures();
            this.mesh = new THREE.Mesh(geometry, material);
            this.mesh.position.x = this.positionX;
            this.mesh.rotation.z = this.tilt * Math.PI / 180;
            this.mesh.userData.isPlanet = true;
            this.addAtmosphere();
            this.addRing();
        }
        return this.mesh;
    }

    addAtmosphere(){
        if(this.atmosphere){
            const atmosphereGeo = new THREE.SphereGeometry(this.radius + 0.1, 32, 20);
            const texture = new THREE.TextureLoader().load(this.atmosphere);
            const atmosphereMaterial = new THREE.MeshPhongMaterial({
                map:texture,
                transparent: true,
                opacity: 0.45,
                depthWrite: false,
                blending: THREE.AdditiveBlending,
                side: THREE.DoubleSide
            });
            const atmosphereMesh = new THREE.Mesh(atmosphereGeo, atmosphereMaterial);
            atmosphereMesh.rotation.z = 0.41;
            atmosphereMesh.userData.isAtmosphere = true;
            this.mesh.add(atmosphereMesh);
        }
    }

    addRing() {
        if (this.ring) {
            const RingGeo = new THREE.RingGeometry(this.ring.innerRadius, this.ring.outerRadius,30);
            const texture = new THREE.TextureLoader().load(this.ring.texture);

            const RingMat = new THREE.MeshStandardMaterial({
                map: texture,
                side: THREE.DoubleSide
            });
            const ring = new THREE.Mesh(RingGeo, RingMat);
            ring.position.x = this.positionX;
            ring.rotation.x = -0.5 * Math.PI;

            ring.rotation.y = -this.tilt;
            ring.userData.isRing = true;
            this.mesh.add(ring);
        }
    }

    loadTextures(){
        const dayTex = new THREE.TextureLoader().load(this.textures?.day);
        const nightTex = new THREE.TextureLoader().load(this.textures?.night);

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
}