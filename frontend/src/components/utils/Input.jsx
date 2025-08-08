import React, {useState} from "react";
import InputElement from "@/components/utils/html/InputElement.jsx";
import FloatingLabel from "@/components/utils/html/FloatingLabel.jsx";

const Input = ({ label= "", register, name, rules, errors = null, defaultValue = "", disabled, ...props }) => {

    const [isActive, setIsActive] = useState(!!defaultValue);

    const handleFocus = () => setIsActive(true);
    const handleBlur = (e) => setIsActive(!!e.target.value);

    const errorMessage = errors ? errors[name]?.message : null;

    return (
        <FloatingLabel label={label} isActive={isActive}>
            <InputElement
                {...props}
                name={name}
                register={register}
                rules={rules}
                onFocus={handleFocus}
                onBlur={handleBlur}
                disabled={disabled}
            />
            {errorMessage && <p className="error-message">{errorMessage}</p>}
        </FloatingLabel>
    );
};

export default Input;
