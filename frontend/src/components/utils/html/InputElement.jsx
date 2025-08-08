import React from "react";
const InputElement = ({ name, register, rules, onFocus, onBlur, disabled, ...props }) => {
    return (
        <input
            {...props}
            {...(register ? register(name, rules) : {})}
            name={name}
            autoComplete={name}
            onFocus={onFocus}
            onBlur={onBlur}
            disabled={disabled}
        />
    );
};

export default InputElement;
