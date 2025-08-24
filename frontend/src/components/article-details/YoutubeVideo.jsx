import {YouTubeEmbed} from "@/components/api/youtube-window/YouTubeEmbed.jsx";
import React, { useState } from "react";
import VideoSelector from "@/components/utils/VideoSelector.jsx";

const YoutubeVideo = ({recommendedVideo, videos}) =>{
    const [video, setVideo] = useState(recommendedVideo);
    return(
        <section className="video-section">
            <div className="flex flex-column margin-block-4">
                <VideoSelector
                    options={videos || []}
                    setVideo={setVideo}
                    placeholder="Watch Livestream"
                    defaultValue={video?.priority}
                />
            </div>
            <YouTubeEmbed videoUrl={video ? video?.videoUrl : recommendedVideo?.videoUrl}></YouTubeEmbed>
        </section>
    )
}
export default YoutubeVideo;