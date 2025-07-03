const SelectElement = ({ name, register, rules, options, onFocus, onBlur, disabled, defaultValue, ...props }) => {
    return (
        <select
            {...props}
            {...(register ? register(name, rules) : {})}
            name={name}
            onFocus={onFocus}
            onBlur={onBlur}
            disabled={disabled}
            defaultValue={defaultValue}
        >
            <option value="" disabled></option>
            {options.map((option) => (
                <option key={option.value} value={option.value}>
                    {option.label}
                </option>
            ))}
        </select>
    );
};

export default SelectElement;
