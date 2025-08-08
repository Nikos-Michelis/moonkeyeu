import React from "react";
import {Button} from "@/components/button/Button.jsx";
import {useForm} from "react-hook-form";
import {useNavigate, useParams} from "react-router-dom";
import ErrorBox from "@/components/utils/ErrorBox.jsx";
import ScrollToTop from "@/components/utils/ScrollToTop.jsx";
import {useCreateMutation} from "@/services/mutations.jsx";
import Head from "@/components/seo/Head.jsx";
import JsonLdGeneric from "@/components/seo/jsonld/JsonLdGeneric.jsx";
import PasswordField from "@/components/utils/PasswordField.jsx";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faArrowRight, faSpinner} from "@fortawesome/free-solid-svg-icons";
const ResetPassword = () =>{
    const baseUrl = import.meta.env.VITE_BACKEND_BASE_URL;
    const { token } = useParams();
    const navigate = useNavigate();

    const {
        register,
        handleSubmit,
        reset,
        watch,
        formState: {errors}
    } = useForm({ mode: 'onChange' });

    const changePasswordMutation =
        useCreateMutation({
            successMessage: "You have successfully changed your password!",
        });
    const onSubmit = (data) => {
        changePasswordMutation.mutate(
            { url: `${baseUrl}/auth/reset-password`, data: data, options: { withCredentials: true } },
            {
                onSuccess: () => {
                    reset();
                    navigate("/launches");
                }
            }
        );
    };

    return(
        <>
            <Head
                title="Reset Password"
                description="Reset your password to regain access to your MoonkeyEU account."
            />
            <JsonLdGeneric
                title="Reset Password"
                description="Reset your password to regain access to your MoonkeyEU account."
            />
            <ScrollToTop behavior="auto" />
            <section className="reset-password-section">
                <div className="container flex justify-center" data-height="full" data-type="medium" data-spacing="none">
                    <div className="container container--light-overlay flex flex-column align-center  padding-inline-8 padding-block-10" data-type="fixed-inherit" data-spacing="none">
                        <div className="container container--form margin-5" data-type="narrow">
                            <div className="form-content">
                                <h2>Reset Password</h2>
                                {(errors && Object.keys(errors).length > 0 || changePasswordMutation.error) && <ErrorBox errors={errors} apiError={changePasswordMutation.error?.response?.data}/>}
                                <form onSubmit={handleSubmit(onSubmit)}>
                                    <input type="hidden" {...register('token')} value={token}/>
                                    <PasswordField
                                        label="New Password"
                                        name="newPassword"
                                        errors={errors}
                                        register={register}
                                        rules={{
                                            required: 'New Password is required.',
                                            pattern:{value:/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/g,
                                                message:'New Password should be at least 8 characters and include, ' +
                                                    'at least 1 UPPERCASE letter, 1 number, 1 special character!'
                                            }
                                        }}
                                    />
                                    <PasswordField
                                        label="Confirm Password"
                                        name="confirmPassword"
                                        errors={errors}
                                        register={register}
                                        rules={{
                                            required: 'Please confirm your password.',
                                            validate: value => value === watch('newPassword') || 'Passwords do not match.'
                                        }}
                                    />
                                    <div className="container flex justify-center">
                                        <Button
                                            className="btn btn--primary btn--big"
                                            type="submit"
                                            disabled={changePasswordMutation.isPending}>
                                            {changePasswordMutation.isPending
                                                ? <FontAwesomeIcon icon={faSpinner} spin />
                                                : <FontAwesomeIcon icon={faArrowRight} />} Continue
                                        </Button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </>
    );
}
export default ResetPassword;