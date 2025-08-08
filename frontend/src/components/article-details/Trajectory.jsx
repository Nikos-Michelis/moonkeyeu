import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faChartLine } from '@fortawesome/free-solid-svg-icons';

const Trajectory = ({flightclub_url}) => {
    return(
        <section className="trajectory-section">
            <div className="article__heading-box">
                <FontAwesomeIcon icon={faChartLine} />
                <h2>Telemetry</h2>
            </div>
            <hr className="hr-100-sm bg-hr-600" />
                <div className="article__info-box">
                    <>
                        {flightclub_url !== null ?

                            <p>View comprehensive details including the rocket’s trajectory, velocity, altitude, thrust, and more at
                            <a href={flightclub_url} target="_blank" rel="noopener noreferrer">
                                <span> FlightClub.io</span>
                            </a>
                        </p>
                        :
                        <p>Trajectory is not available. Check back for updates.</p>
                        }
                    </>
                </div>
        </section>
    );
};
export default Trajectory