import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faCircleExclamation} from "@fortawesome/free-solid-svg-icons";

/**
 * ErrorBox Component
 *
 * Displays validation error messages from both the backend API and the form validation logic
 * (e.g., using `react-hook-form` or similar).
 *
 * @param {Object} props
 * @param {Object} props.errors - Validation errors from the form hook (e.g., useForm).
 * @param {Object} [props.apiError] - Error object returned from the backend API.
 * @param {Array} [props.apiError.validationErrors] - Optional list of validation error messages from the API.
 *
 * @returns {JSX.Element} A component that renders a list of error messages.
 */

const ErrorBox = ({ errors, apiError }) => {
    const formErrorMessages = Object.values(errors).map(error => error.message);
    const apiValidationErrorMessages = apiError?.validationErrors || [];
    const renderMessages = (messages) =>
        messages.map((message, index) => (
            <li key={index}>
                <span className="response-error-message">{message}</span>
            </li>
        ));

    return (
            <div className="response-error-wrapper">
                <div className="response-error-container">
                    <div className="error-circle">
                        <FontAwesomeIcon icon={faCircleExclamation} />
                    </div>
                    <ul>
                        {renderMessages(formErrorMessages)}
                        {apiValidationErrorMessages.flatMap((error, index) =>
                            Object.entries(error).map(([field, errorMessage]) => (
                                <li key={`${field}-${index}`}>
                                    <span className="response-error-message">{errorMessage}</span>
                                </li>
                            ))
                        )}
                    </ul>
                </div>
            </div>
    );

};

export default ErrorBox;
