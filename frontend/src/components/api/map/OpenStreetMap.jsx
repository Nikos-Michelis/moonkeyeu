import React, {useEffect, useRef, useState} from 'react';
import { MapContainer, TileLayer, Marker, Popup } from 'react-leaflet';
import { Icon } from 'leaflet';
import 'leaflet/dist/leaflet.css';
import {LinkButton} from "@/components/button/LinkButton.jsx";
import Tooltip from "@/components/tooltip/Tooltip.jsx";
import SpinnerLoader from "@/components/loader/SpinnerLoader.jsx";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {faCircleInfo, faLocationDot, faSearch} from '@fortawesome/free-solid-svg-icons';
import { faWikipediaW } from '@fortawesome/free-brands-svg-icons';
import SearchPopUp from "@/components/modal/SearchPopUp.jsx";
import ModalButton from "@/components/button/ModalButton.jsx";
import {useDebounce} from "@/hooks/util/useDebounce.jsx";

const OpenStreetMap = (
    {
        locations,
        isPending,
        isFetching,
    }
) => {
    const data = locations?.data;
    const defaultCenter = [45, 50];
    const [searchValue, setSearchValue] = useState('');
    const debounceSearch = useDebounce(searchValue);
    const [open, setOpen] = useState(false)
    const markerRefs = useRef({});

    useEffect(() => {
        const match = data?.pads?.find(loc =>
            (
                loc?.location?.name
            )?.toLowerCase().includes(debounceSearch.toLowerCase())
        );

        if (match && markerRefs.current[match.id]) {
            const marker = markerRefs.current[match.id];
            marker.openPopup();
        }
    }, [debounceSearch]);


    return (
            <div className="map-container">
                <SearchPopUp setSearchValue={setSearchValue} value={searchValue}/>
                <div className="leaflet-bottom leaflet-left">
                    <div className="location-dot-wrapper leaflet-control-attribution margin-block-end-6 margin-inline-start-2">
                          <span style={{ color: '#5fdb9b' }}>
                            <FontAwesomeIcon icon={faLocationDot} style={{ color: '#5fdb9b' }} /> Active Pads<span>-</span>{data?.active}
                          </span>
                                                <span style={{ color: '#bd4646' }}>
                            <FontAwesomeIcon icon={faLocationDot} style={{ color: '#bd4646' }} /> Non-Active Pads<span>-</span>{data?.inactive}
                          </span>
                    </div>
                </div>

                <MapContainer center={defaultCenter} zoom={2} style={{ height: '100%', width: '100%' }} zoomControl={false}>
                    <TileLayer
                        url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                        attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
                    />
                    {(isPending || isFetching) ?
                        <div>
                            <div className="flex justify-center">
                                <SpinnerLoader styles={{height:"full", container:"map", type:"fixed-inherit"}}/>
                            </div>
                        </div>
                        :
                        data?.pads?.map((location) => (
                            <Marker
                                key={location.id}
                                ref={(ref) => {
                                    if (ref) markerRefs.current[location.id] = ref;
                                }}
                                position={[location.latitude, location.longitude]}
                                icon={new Icon({
                                    iconUrl: location?.active
                                        ? `${import.meta.env.VITE_CLOUDFRONT_URL}/assets/icons/marker/location-dot-solid-green.svg`
                                        : `${import.meta.env.VITE_CLOUDFRONT_URL}/assets/icons/marker/location-dot-solid-red.svg` ,
                                    iconSize: [18, 31],
                                    iconAnchor: [12, 41],
                                    popupAnchor: [1, -34],
                                    shadowAnchor: [4, 62],
                                    shadowSize: [41, 41],
                                })}
                                title={location.name}
                                eventHandlers={{
                                    /*mouseover: (e) => {
                                        e.target.openPopup();
                                    },*/
                                    click: (e) =>{
                                        e.target.openPopup();
                                    }

                                }}
                            >
                                <Popup>
                                    <div className="leaflet-popup-container">
                                        <div className="card-img-box">
                                            {location.map_image?
                                                    <img
                                                        loading="lazy"
                                                        src={location.map_image}
                                                        alt={location.name}
                                                        className="card-img"
                                                    />
                                                    :
                                                    <img
                                                        key={`default`}
                                                        src={`${import.meta.env.VITE_CLOUDFRONT_URL}/assets/logo/moonkeyeu-logo.svg`}
                                                        alt="default"
                                                        className="card-img"
                                                    />
                                            }

                                        </div>
                                        <div className="map-info-container">
                                            <h3>{location?.name}</h3>
                                            <span>{location.location?.name}</span>
                                        </div>
                                        <div className="flex justify-center">
                                            {location.id ? (
                                                <div className="info">
                                                    <LinkButton className="btn btn-primary" to={location.id.toString()} >
                                                        <FontAwesomeIcon icon={faCircleInfo} />
                                                    </LinkButton>
                                                </div>
                                            ) : (
                                                <Tooltip message="No Info Available">
                                                    <div className="info">
                                                        <LinkButton className="">
                                                            <FontAwesomeIcon icon={faCircleInfo} />
                                                        </LinkButton>
                                                    </div>
                                                </Tooltip>
                                            )}
                                            { location.wiki_url ? (
                                                <div className="wiki">
                                                    <LinkButton
                                                        className="btn btn-primary"
                                                        to={location.wiki_url}
                                                        isExternal={true}
                                                    >
                                                        <FontAwesomeIcon icon={faWikipediaW} />
                                                    </LinkButton>
                                                </div>
                                            ) : (
                                                <Tooltip message="No Wiki Available">
                                                    <div className="wiki">
                                                        <LinkButton
                                                            className="btn btn-primary"
                                                            isExternal={true}
                                                        >
                                                            <FontAwesomeIcon icon={faWikipediaW} />
                                                        </LinkButton>
                                                    </div>
                                                </Tooltip>
                                            )}
                                        </div>
                                    </div>
                                </Popup>
                            </Marker>
                        ))
                    }
                </MapContainer>
            </div>
    );
};

export default OpenStreetMap;
