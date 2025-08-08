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
        <div className={`counter ${timerStyle} ff-accent`} data-end-date={targetTimeInMillis}>
            <div className="counter__digit-box">
                <div className="counter__number"><span>T-</span></div>
            </div>
            <div className="counter__digit-box">
                <div className="counter__number"><span>{padZero(months)}</span><span>:</span></div>
                <div className="counter__label"><label>MTH</label></div>
            </div>
            <div className="counter__digit-box">
                <div className="counter__number"><span>{padZero(days)}</span><span>:</span></div>
                <div className="counter__label"><label>DAY</label></div>
            </div>
            <div className="counter__digit-box">
                <div className="counter__number"><span>{padZero(hours)}</span><span>:</span></div>
                <div className="counter__label"><label>HR</label></div>
            </div>
            <div className="counter__digit-box">
                <div className="counter__number"><span>{padZero(minutes)}</span><span>:</span></div>
                <div className="counter__label"><label>MIN</label></div>
            </div>
            <div className="counter__digit-box">
                <div className="counter__number"><span>{padZero(seconds)}</span></div>
                <div className="counter__label"><label>SEC</label></div>
            </div>
        </div>
    );
};

export default CountdownTimer;
