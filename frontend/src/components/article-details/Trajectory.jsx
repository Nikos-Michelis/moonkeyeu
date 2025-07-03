import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faChartLine } from '@fortawesome/free-solid-svg-icons';

const Trajectory = ({flightclub_url}) =>{
    return(
        <section className="trajectory-section">
            <div className="heading-box">
                <FontAwesomeIcon icon={faChartLine} />
                <h2>Telemetry</h2>
            </div>
            <hr className="hr-7-sm bg-hr-600" />
            {flightclub_url !== null ?
                <div className="info-box">
                    <p>View comprehensive details including the rocket’s trajectory, velocity, altitude, thrust, and more at
                        <a href={flightclub_url} target="_blank"  rel="noopener noreferrer">
                            <span> FlightClub.io</span>
                        </a>
                    </p>
                </div>
                :
                <div className="info-box">
                    <p>Trajectory is not available. Check back for updates.</p>
                </div>
            }

        </section>
    );
};
export default Trajectory