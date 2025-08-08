import React from "react";
import PropTypes from "prop-types";

export const Button = React.forwardRef(
    ({ children, onClick, type = "button", disabled = false, className = "btn btn--primary" }, ref) => {
        return (
            <button
                ref={ref}
                type={type}
                className={`${className}${disabled ? " btn--disabled" : ""}`}
                onClick={onClick}
                disabled={disabled}
            >
                {children}
            </button>
        );
    }
);

Button.displayName = "Button";

Button.propTypes = {
    onClick: PropTypes.func,
    type: PropTypes.oneOf(["submit", "button"]),
    disabled: PropTypes.bool,
    children: PropTypes.node,
    className: PropTypes.string,
};
