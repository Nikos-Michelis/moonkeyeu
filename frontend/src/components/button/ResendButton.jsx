import React, { useState, useEffect } from "react";
import {Button} from "@/components/button/Button.jsx";
import {useSimpleTimer} from "@/hooks/timer/useSimpleTimer.jsx";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {faRotateRight, faSpinner} from '@fortawesome/free-solid-svg-icons';

const ResendButton = ({ handleOtpResend, otpToken, status, delay }) => {
    const [millis, setMillis] = useState(0);
    const handleResendClick = () => {
        handleOtpResend(otpToken);
    };
    const { seconds } = useSimpleTimer(millis, 1000);
    const isDisabled = seconds > 0;
    useEffect(() => {
        if(delay > 0 && status.isError) {
            setMillis(delay);
        }
        return () => setMillis(0);
    }, [delay]);

    return (
        <Button
            className="btn btn--primary btn--small btn-resend bg-secondary-300"
            type="button"
            onClick={handleResendClick}
            disabled={isDisabled || status.isPending}
        >
            {status.isPending ? (
                <>
                    <FontAwesomeIcon icon={faSpinner} spin /> Resend
                </>
            ) : isDisabled ? (
                `Resend in ${seconds}s`
            ) : (
                <>
                    <FontAwesomeIcon icon={faRotateRight} /> Resend
                </>
            )}
        </Button>
    );
};

export default ResendButton;


