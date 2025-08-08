import {YouTubeEmbed} from "@/components/api/youtube-window/YouTubeEmbed.jsx";
import React from "react";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faYoutube } from '@fortawesome/free-brands-svg-icons';

const YoutubeVideo = ({videoUrl}) =>{
    return(
        <section className="video-section">
            <div className="article__heading-box">
                <FontAwesomeIcon icon={faYoutube} />
                <h2>Launch Video</h2>
            </div>
            <hr className="hr-100-sm bg-hr-600" />
            <YouTubeEmbed videoUrl={videoUrl}></YouTubeEmbed>
        </section>
    )
}
export default YoutubeVideo;