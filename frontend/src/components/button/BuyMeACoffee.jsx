import {LinkButton} from "@/components/button/LinkButton.jsx";

const BuyMeACoffee = () => {
    return(
        <section className="donation-section">
            <div className="flex justify-center align-center margin-block-4">
                <LinkButton className="btn-transparent" isExternal={true} to="https://buymeacoffee.com/moonkeyeu">
                    <img
                        className="btn-donation"
                        src={`${import.meta.env.VITE_CLOUDFRONT_URL}/assets/yellow-button.png`}
                        alt="Buy Me a Coffee Widget"
                    />
                </LinkButton>
            </div>
        </section>
    );
}

export default BuyMeACoffee;