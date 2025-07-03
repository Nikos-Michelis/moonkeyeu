import React from "react";

const Tooltip = ({message, children }) => {
    return (
        <div className="tooltip-wrapper">
            {children}
            <div className="tooltiptext">{message}</div>
        </div>
    );
};
export default Tooltip;
