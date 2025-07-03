import {GoogleLogin} from "@react-oauth/google";

function GoogleLoginButton({ onSuccess }) {
    return (
        <div className="margin-block-4">
            <GoogleLogin
                onSuccess={onSuccess}
                onError={() => {
                    console.log('Login Failed');
                }}
                useOneTap={false}
            />
        </div>
    );
}

export default GoogleLoginButton;

