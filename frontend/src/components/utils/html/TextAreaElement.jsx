const TextAreaElement = ({ name, register, rules, onFocus, onBlur, disabled, ...props }) => {
    return (
        <textarea
            {...props}
            {...(register ? register(name, rules) : {})}
            name={name}
            onFocus={onFocus}
            onBlur={onBlur}
            disabled={disabled}
        />
    );
};

export default TextAreaElement;
