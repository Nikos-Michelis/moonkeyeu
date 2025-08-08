import { useEffect, useState } from "react";
import {Button} from "@/components/button/Button.jsx";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faChevronUp } from '@fortawesome/free-solid-svg-icons';

const GoTop = ({ scrollUp = () => window.scrollTo({ top: 0, behavior: "smooth" }) }) => {
    const [showGoTop, setShowGoTop] = useState(false);

    const handleVisibleButton = () => {
        const position = window.scrollY;
        if (position > 50) {
            setShowGoTop(true);
        } else {
            setShowGoTop(false);
        }
    };

    useEffect(() => {
        window.addEventListener("scroll", handleVisibleButton);

        return () => {
            window.removeEventListener("scroll", handleVisibleButton);
        };
    }, []);

    return (
        <>
            {showGoTop && (
                <div className="goTopBtn" onClick={scrollUp}>
                    <Button className="btn-goTop">
                        <FontAwesomeIcon icon={faChevronUp} className="btn-goTop__text" />
                    </Button>
                </div>
            )}
        </>
    );
};

export default GoTop;
