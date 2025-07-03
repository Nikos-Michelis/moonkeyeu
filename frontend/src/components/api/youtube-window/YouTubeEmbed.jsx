import PropTypes from 'prop-types';

export function YouTubeEmbed({ videoUrl, width = "560", height = "315" }) {
    const videoId = videoUrl?.split("v=")[1]?.split("&")[0];

    if (!videoId) {
        return <div>Invalid video URL</div>;
    }

    return (
        <div className="youtube-embed">
            <iframe
                width={width}
                height={height}
                src={`https://www.youtube.com/embed/${videoId}`}
                frameBorder="0"
                allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture"
                allowFullScreen
                title="YouTube video"
            ></iframe>
        </div>
    );
}

YouTubeEmbed.propTypes = {
    videoUrl: PropTypes.string.isRequired,
    width: PropTypes.string,
    height: PropTypes.string,
};
