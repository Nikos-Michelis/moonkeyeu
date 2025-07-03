import Input from "@/components/utils/Input.jsx";
import React, {useState} from "react";
import {faEye, faEyeSlash} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";

const PasswordField = (
    {
        label = "Password",
        name = "password",
        errors,
        register,
        rules = {
            required: 'Password is required.',
            pattern:{value:/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/g,
                message:'Password should be at least 8 characters and include, ' +
                    'at least 1 UPPERCASE letter, 1 number, 1 special character!'}
        }
    }) => {
    const [type, setType] = useState('password');
    const [icon, setIcon] = useState(faEyeSlash);

    const handleToggle = () => {
        if (type === 'password') {
            setType('text');
            setIcon(faEye);
        } else {
            setType('password');
            setIcon(faEyeSlash);
        }
    };


    return (
        <div className="input-field">
            <Input
                className={`${errors.password ? 'input-error' : ''}`}
                label={label}
                name={name}
                type={type}
                register={register}
                rules={rules}
                errors={errors}
            />
            <span className="visibility" onClick={handleToggle}>
                <FontAwesomeIcon icon={icon} />
            </span>
        </div>
    )
}
export default PasswordField;