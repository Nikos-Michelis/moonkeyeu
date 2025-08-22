import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faChartLine} from "@fortawesome/free-solid-svg-icons";

const WeatherConcerns = ({weather_concerns}) => {
    return(
        <section className="trajectory-section">
            <div className="article__heading-box">
                <FontAwesomeIcon icon={faChartLine} />
                <h2>Weather</h2>
            </div>
            <hr className="hr-100-sm bg-hr-600" />
            <div className="flex flex-column">
                <div className="article__info-box">
                    <>
                        {weather_concerns !== null ?

                            <p>{weather_concerns}</p>
                            :
                            <p>Weather concerns is not available. Check back for updates.</p>
                        }
                    </>
                </div>
                <div className="flex justify-end clr-dark-cosmos-300 margin-block-start-4">
                    <small>Weather data powered by <a href="https://openweathermap.org/" target="_blank">OpenWeather</a></small>
                </div>
            </div>
        </section>
    );
};
export default WeatherConcerns