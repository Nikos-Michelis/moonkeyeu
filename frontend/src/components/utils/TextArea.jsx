import { useState } from "react";
import FloatingLabel from "@/components/utils/html/FloatingLabel.jsx";
import TextAreaElement from "@/components/utils/html/TextAreaElement.jsx";

const TextArea = (
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
            <TextAreaElement
                {...props}
                name={name}
                register={register}
                rules={rules}
                options={options}
                onFocus={handleFocus}
                onBlur={handleBlur}
                disabled={disabled}
            />
            {errorMessage && <p className="error-message">{errorMessage}</p>}
        </FloatingLabel>
    );
};

export default TextArea;
