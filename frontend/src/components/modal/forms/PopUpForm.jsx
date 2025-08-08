import React, { useCallback, useEffect, useReducer } from "react";
import {useModal} from "@/context/ModalProvider.jsx";
import {Button} from "@/components/button/Button.jsx";
import {useForm} from "react-hook-form";
import {useAuth} from "@/context/AuthProvider.jsx";
import ErrorBox from "@/components/utils/ErrorBox.jsx";
import ResendButton from "@/components/button/ResendButton.jsx";
import Input from "@/components/utils/Input.jsx";
import {useNavigate} from "react-router-dom";
import {useCreateMutation} from "@/services/mutations.jsx";
import PasswordField from "@/components/utils/PasswordField.jsx";
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import {faArrowLeft, faArrowRight, faRightToBracket, faSpinner, faXmark} from '@fortawesome/free-solid-svg-icons';
import GoogleLoginButton from "@/components/button/GoogleLoginButton.jsx";

const initialState = {
    formState: "login",
    apiError: null,
    otpToken: null,
    rememberMe: false
};

function formReducer(state, action) {
    switch (action.type) {
        case "SET_FORM_STATE":
            return { ...state, formState: action.payload, apiError: null };
        case "SET_API_ERROR":
            return { ...state, apiError: action.payload };
        case "SET_OTP_TOKEN":
            return { ...state, otpToken: action.payload };
        case "SET_ID_TOKEN":
            return { ...state, idToken: action.payload };
        case "SET_USERNAME":
            return { ...state, user: action.payload };
        case "RESET":
            return initialState;
        default:
            return state;
    }
}

const PopUpForm = () => {
    const baseUrl = import.meta.env.VITE_BACKEND_BASE_URL;
    const { modals, closeModal } = useModal();
    const navigate = useNavigate();
    const { setToken, status, error: authContextError } = useAuth();
    const modal = modals["PopUpFormModal"] || {};
    const [state, dispatch] = useReducer(formReducer, initialState);

    const {
        register,
        handleSubmit,
        reset,
        setValue,
        watch,
        formState: { errors }
    } = useForm({
        mode: "onChange",
    });

    const apiUrl = {
        register: "/auth/register",
        oAuth2Registration: "/oauth2/register/google",
        login: "/auth/authenticate",
        otpVerify: "/auth/verify-otp",
        forgotPassword: "/auth/forgot-password",
        resetPassword: ""
    }[state.formState];

    const resendMutation =
        useCreateMutation({
            successMessage: "OTP resent successfully."
        });
    const otpVerificationMutation =
        useCreateMutation({
            successMessage: undefined
        });
    const credentialsMutation =
        useCreateMutation({
            successMessage: undefined
        });
    const signInWithGoogleMutation =
        useCreateMutation({
            successMessage: undefined
        });


    const handleOtpResend = (token) => {
        resendMutation.mutate(
            { data: { token }, url: `${baseUrl}/auth/resend-otp` },
            { onSuccess: (response) => setValue("token", response.token) }
        );
    };

    const handleOtpVerification = (tokenUrl, credentials = null) => {
        otpVerificationMutation.mutate(
            { url: tokenUrl, data: credentials, options: { withCredentials: true } },
            {
                onSuccess: (response) => {
                    setToken(response?.token)
                },
                onError: (error) => {
                    dispatch({ type: "SET_API_ERROR", payload: error.response?.data });
                }
            }
        );
    };

    const handleCredentials = (url, credentials = null) => {
        credentialsMutation.mutate(
            { data: credentials, url },
            {
                onSuccess: (response) => {
                    if (response.token) {
                        dispatch({ type: "SET_OTP_TOKEN", payload: response.token });
                    }
                    handleFormView();
                },
                onError: (error) => {
                    dispatch({ type: "SET_API_ERROR", payload: error.response?.data });
                },
            }
        );
    };

    const handleOAuth2Response = useCallback((response) => {
        const { credential: idToken } = response;

        signInWithGoogleMutation.mutate(
            { url: `${baseUrl}/oauth2/login/google`, data: { idToken } },
            {
                onSuccess: (res) => {
                    if (res.token) {
                        setToken(res.token);
                        handleClose();
                    }
                    handleGoogleLoginSuccess(res, idToken);
                },
                onError: (error) => {
                    dispatch({ type: "SET_API_ERROR", payload: error.response?.data });
                },
            }
        );
    }, []);


    const handleSwitchForm = (newState) => {
        dispatch({ type: "SET_FORM_STATE", payload: newState });
        reset();
    };
    const handleGoogleLoginSuccess = (response, idToken) => {
        dispatch({ type: "SET_USERNAME", payload: {...response}});
        dispatch({ type: "SET_ID_TOKEN", payload:  idToken});
        handleSwitchForm("oAuth2Registration")
    };

    const handleFormView = () => {
        dispatch({ type: "SET_API_ERROR", payload: null });
        if (state.formState === "register" || state.formState === "login") {
            dispatch({ type: "SET_FORM_STATE", payload: "otpVerify" });
        } else if (state.formState === "forgotPassword") {
            dispatch({ type: "SET_FORM_STATE", payload: "resetPassword" });
        } else {
            handleClose();
        }
    };

    const handleClose = () => {
        dispatch({ type: "RESET" });
        closeModal("PopUpFormModal");
        reset();
    };
    const onNavigate = ()=> {
        handleClose();
        navigate("/privacy");
    }
    const onSubmit = (data) => {
        if (state.formState === "otpVerify") {
            handleOtpVerification(`${baseUrl}${apiUrl}`, data);
            return;
        } else if(state.formState === "oAuth2Registration"){
            handleOtpVerification(
                `${baseUrl}${apiUrl}`,
                {
                    confirmUsername: data?.confirmUsername,
                    email: state?.user?.email,
                    idToken: state?.idToken
                }
            );
            return;
        }
        handleCredentials(`${baseUrl}${apiUrl}`, data);
    };

    useEffect(() => {
        if (state?.user?.name) {
            setValue("confirmUsername", state?.user?.name);
        }
    }, [state?.user?.name, setValue]);

    useEffect(() => {
        if (status.isSuccess) {
            handleClose();
        }
    }, [status]);

    useEffect(() => {
        dispatch({ type: "SET_API_ERROR", payload: authContextError?.response?.data });
    }, [authContextError]);

    if (!modal.isOpen) return null

    return (
        <div className={`form-popup-container${state.formState !== "login"  ? ' show-signup' : ""}`}>
            <Button
                onClick={!signInWithGoogleMutation?.isPending ? handleClose : undefined}
                className="btn--transparent pos-absolute top-2 right-1 clr-dark-cosmos-300"
            >
                <FontAwesomeIcon icon={faXmark} />
            </Button>
            {["register", "forgotPassword"].includes(state.formState) && (
                <Button className="btn--transparent pos-absolute top-2 left-2 clr-dark-cosmos-300" onClick={() => handleSwitchForm("login")}>
                    <FontAwesomeIcon icon={faArrowLeft} />
                </Button>)
            }
            <div className={`form-box${
                (["otpVerify", "forgotPassword", "resetPassword"].includes(state.formState)) ? ' small-form' : ""} flex ${state.formState !== "login" ? "flex-column justify-center" :"flex-wrap justify-space-around"} align-center`}>
                {(state.formState === "login")  && (
                    <div className="form-details flex flex-column justify-center align-center fs-small-300">
                        <h2>Create Account</h2>
                        <p>Don’t you want to miss a single launch?</p>
                        <div className="text-center">
                            Become a member now!
                            <Button className="btn--transparent clr-dark-cosmos-300" type="button" onClick={() => handleSwitchForm("register")}>Sign-up</Button>
                        </div>
                        <GoogleLoginButton onSuccess={handleOAuth2Response} />
                    </div>)
                }
                {state.formState === "oAuth2Registration" && (
                    <div className="form-content">
                        <h2>One more step!</h2>
                        {
                            (errors && Object.keys(errors).length > 0 || state.apiError?.validationErrors) && <ErrorBox errors={errors} apiError={state.apiError}/>
                        }
                        <div className="margin-block-end-4">
                            <p>Oops! Looks like you forgot your username.</p>
                        </div>
                        <form onSubmit={handleSubmit(onSubmit)}>
                            <div className="input-field">
                                <Input
                                    className={`${errors.confirmUsername ? 'input-error' : ''}`}
                                    label="Username"
                                    name="confirmUsername"
                                    type="text"
                                    register={register}
                                    rules={{
                                        required: 'Username is required.',
                                        pattern:{
                                            value:/[A-Za-z][A-Za-z0-9_]{7,16}$/g,
                                            message:'Username must be between 7 and 16 characters long and cannot contain spaces.'
                                        }
                                    }}
                                    errors={errors}/>
                            </div>
                            <div className="flex justify-center">
                                <Button
                                    className="btn btn--primary btn--big bg-secondary-300"
                                    type="submit"
                                    disabled={otpVerificationMutation.isPending}>
                                    {(otpVerificationMutation.isPending)
                                        ? <FontAwesomeIcon icon={faSpinner} spin />
                                        : <FontAwesomeIcon icon={faArrowRight} />
                                    } Continue
                                </Button>
                            </div>
                        </form>
                    </div>)
                }
                {state.formState === "login" && (
                    <div className="form-content __login">
                        <h2>Do you already have an account?</h2>
                        {(errors && Object.keys(errors).length > 0 || state.state?.validationErrors)
                            && <ErrorBox errors={errors} apiError={state.apiError}/>}
                        <form onSubmit={handleSubmit(onSubmit)}>
                            <div className="input-field">
                                <Input
                                    className={`${errors.email ? 'input-error' : ''}`}
                                    label="Email"
                                    name="email"
                                    type="email"
                                    register={register}
                                    rules={{
                                        required: 'Email is required.',
                                        pattern:{value: /^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$/g, message:'Incorrect email.'}}}
                                    errors={errors}
                                    autoComplete="email"
                                />
                            </div>
                            <PasswordField errors={errors} register={register} />
                            <div className="flex justify-space-between align-center fs-small-100 clr-dark-cosmos-300 margin-block-end-4">
                                <div className="flex justify-center align-center">
                                    <input
                                        name="rememberMe"
                                        type="checkbox"
                                        id="checkbox-remember"
                                        disabled={credentialsMutation.isPending}
                                        {...register('rememberMe')}
                                    />
                                    <label htmlFor="checkbox-remember">
                                        <span>Remember me</span>
                                    </label>
                                </div>
                                <Button className="btn--transparent clr-dark-cosmos-300" type="button" onClick={() => handleSwitchForm("forgotPassword")}>Forgot password?</Button>
                            </div>
                            <div className="flex flex-wrap justify-center">
                                <Button className="btn btn--primary btn--big" type="submit" disabled={signInWithGoogleMutation.isPending || credentialsMutation.isPending}>
                                    {(signInWithGoogleMutation.isPending || credentialsMutation.isPending) ? <FontAwesomeIcon icon={faSpinner} spin /> : <FontAwesomeIcon icon={faRightToBracket} />} Login
                                </Button>
                            </div>
                        </form>
                    </div>)
                }
                {(state.formState === "otpVerify") && (
                    <div className="form-content">
                        {state.formState === "otpVerify" ? <h2>Account Verification</h2> : <h2>Account Activation</h2> }
                        {
                            (errors && Object.keys(errors).length > 0
                                || state.apiError?.validationErrors) && <ErrorBox errors={errors} apiError={state.apiError}/>
                        }
                        <div className="padding-block-3">
                            <p>{credentialsMutation.data? credentialsMutation?.data?.message : null}</p>
                        </div>
                        <form onSubmit={handleSubmit(onSubmit)}>
                            <input type="hidden" {...register('token')} value={state.otpToken}/>
                            <input type="hidden" {...register('rememberMe')} value={state.rememberMe}/>

                            <div className="input-field">
                                <Input
                                    className={`${errors.otp ? 'input-error' : ''}`}
                                    label="One Time Password (OTP)"
                                    name="otp"
                                    type="text"
                                    register={register}
                                    rules={{
                                        required: 'Otp code is required.',
                                        pattern:{ value: /^\d{6}$/g, message:'Otp Code should be 6 characters.'}
                                    }}
                                    errors={errors}/>
                            </div>
                            <div className="flex justify-center">
                                <Button
                                    className="btn btn--primary btn--small bg-secondary-300"
                                    type="submit"
                                    disabled={otpVerificationMutation.isPending}>
                                    {(otpVerificationMutation.isPending)
                                        ? <FontAwesomeIcon icon={faSpinner} spin />
                                        : <FontAwesomeIcon icon={faArrowRight} />
                                    } Continue
                                </Button>
                                <ResendButton
                                    handleOtpResend={handleOtpResend}
                                    otpToken={state.otpToken}
                                    status={resendMutation}
                                    delay={resendMutation?.error?.response?.data?.delay}
                                />
                            </div>
                        </form>
                    </div>)
                }
                {state.formState === "forgotPassword" && (
                    <div className="form-content">
                        <h2>Forgot Password?</h2>
                        {(errors && Object.keys(errors).length > 0 || state.apiError?.validationErrors) && <ErrorBox errors={errors} apiError={state.apiError}/>}
                        <form onSubmit={handleSubmit(onSubmit)}>
                            <div className="input-field">
                                <Input
                                    className={`${errors.email ? 'input-error' : ''}`}
                                    label="Email"
                                    name="email"
                                    type="email"
                                    register={register}
                                    rules={{
                                        required: 'Email is required.',
                                        pattern:{ value: /^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$/g, message:'Incorrect email.'}
                                    }}
                                    errors={errors}
                                    autoComplete="email"
                                />
                            </div>
                            <div className="flex justify-center">
                                <Button
                                    className="btn btn--primary btn--big"
                                    type="submit"
                                    disabled={credentialsMutation.isPending}>
                                    {credentialsMutation.isPending
                                        ? <FontAwesomeIcon icon={faSpinner} spin />
                                        : <FontAwesomeIcon icon={faArrowRight} />
                                    } Continue
                                </Button>
                            </div>
                        </form>
                    </div>)
                }
                {state.formState === "resetPassword" && (
                    <div className="form-content">
                        <h2>Forgot Password</h2>
                        <p>You will soon receive a link to reset your password via email. Don’t forget to check your inbox!</p>
                    </div>)
                }
            </div>
            {state.formState === "register" && (
                <div className="form-box register flex flex-column justify-center align-center">
                    <div className="form-content">
                        <h2>Register</h2>
                        {(errors && Object.keys(errors).length > 0 || state.apiError?.validationErrors) &&
                            <ErrorBox errors={errors} apiError={state.apiError}/>}
                        <form onSubmit={handleSubmit(onSubmit)}>
                            <div className="input-field">
                                <Input
                                    className={`${errors.username ? 'input-error' : ''}`}
                                    label="Username"
                                    name="username"
                                    type="text"
                                    register={register}
                                    rules={{
                                        required: 'Username is required.',
                                        pattern:{
                                            value:/[A-Za-z][A-Za-z0-9_]{7,16}$/g,
                                            message:'Username must be between 7 and 16 characters long and cannot contain spaces.'
                                        }
                                    }}
                                    errors={errors}
                                />
                            </div>
                            <div className="input-field">
                                <Input
                                    className={`${errors.email ? 'input-error' : ''}`}
                                    label="Email"
                                    name="email"
                                    type="email"
                                    register={register}
                                    rules={{
                                        required: 'Email is required.',
                                        pattern:{
                                            value: /^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$/g,
                                            message:'Incorrect email.'
                                        }
                                    }}
                                    errors={errors}
                                />
                            </div>
                            <PasswordField errors={errors} register={register} />
                            <PasswordField
                                label={"Repeat Password"}
                                name={"repeatPassword"}
                                errors={errors}
                                register={register}
                                rules={{
                                    required: 'Repeat Password is required.',
                                    validate: value => value === watch('password') || 'Passwords do not match.'
                                }}
                            />
                            <div className="flex align-center">
                                <input
                                    id="policy"
                                    name="policy"
                                    type="checkbox"
                                    disabled={credentialsMutation.isPending}
                                    {...register('policy', { required: 'You must agree to the privacy policy' })}
                                />
                                <label className="fs-small-200" htmlFor="policy">
                                    I agree to the <Button className="btn--transparent clr-dark-cosmos-300" onClick={onNavigate}>Privacy Policy</Button>
                                </label>
                            </div>
                            <div className="flex justify-center">
                                <Button
                                    className="btn btn--primary btn--big"
                                    type="submit"
                                    disabled={credentialsMutation.isPending}>
                                    { credentialsMutation.isPending && <FontAwesomeIcon icon={faSpinner} spin /> } Register
                                </Button>
                            </div>
                        </form>
                    </div>
                </div>)
            }
        </div>
    );
};
export default PopUpForm;