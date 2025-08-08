import React from "react";

const Tooltip = ({message, children, show = false }) => {
    return (
        <>
            {!show && (
                <div className="tooltip">
                    {children}
                    <div className="tooltip__text">{message}</div>
                </div>
            )}
            {show && children}
        </>
    );
};
export default Tooltip;
