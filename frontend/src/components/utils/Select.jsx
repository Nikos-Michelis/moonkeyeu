import React, { useState } from "react";
import SelectElement from "@/components/utils/html/SelectElement.jsx";
import FloatingLabel from "@/components/utils/html/FloatingLabel.jsx";

const Select = (
    {
        label,
        register,
        name,
        rules,
        options,
        errors = null,
        defaultValue = "",
        disabled, ...props
    }) => {
    const [isActive, setIsActive] = useState(!!defaultValue);

    const handleFocus = () => setIsActive(true);
    const handleBlur = (e) => setIsActive(!!e.target.value);

    const errorMessage = errors ? errors[name]?.message : null;

    return (
        <FloatingLabel label={label} isActive={isActive}>
            <SelectElement
                {...props}
                name={name}
                register={register}
                rules={rules}
                options={options}
                onFocus={handleFocus}
                onBlur={handleBlur}
                disabled={disabled}
                defaultValue={defaultValue}
            />
            {errorMessage && <p className="error-message">{errorMessage}</p>}
        </FloatingLabel>
    );
};

export default Select;
