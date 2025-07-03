import React from 'react';
import {useCountDown} from "@/hooks/timer/useCountDown.jsx";

const CountdownTimer = ({net, timerStyle = ""}) => {
    const targetTimeInMillis = new Date(net).getTime();

    const  { months, days, hours, minutes, seconds }
        = useCountDown(targetTimeInMillis, 1000);

    const padZero = (number) => {
        return (number < 10 ? "0" : "") + number;
    }
    return (
        <div className={`${timerStyle} counter-container ff-accent`} data-end-date={targetTimeInMillis}>
            <div className="timer digit-box">
                <div className="count-down-number"><span>T-</span></div>
            </div>
            <div className="months digit-box">
                <div className="count-down-number"><span>{padZero(months)}</span><span>:</span></div>
                <div className="count-down-label"><label>MTH</label></div>
            </div>
            <div className="days digit-box">
                <div className="count-down-number"><span>{padZero(days)}</span><span>:</span></div>
                <div className="count-down-label"><label>DAY</label></div>
            </div>
            <div className="hours digit-box">
                <div className="count-down-number"><span>{padZero(hours)}</span><span>:</span></div>
                <div className="count-down-label"><label>HR</label></div>
            </div>
            <div className="minutes digit-box">
                <div className="count-down-number"><span>{padZero(minutes)}</span><span>:</span></div>
                <div className="count-down-label"><label>MIN</label></div>
            </div>
            <div className="seconds digit-box">
                <div className="count-down-number"><span>{padZero(seconds)}</span></div>
                <div className="count-down-label"><label>SEC</label></div>
            </div>
        </div>
    );
};

export default CountdownTimer;
