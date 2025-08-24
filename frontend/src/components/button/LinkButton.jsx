import PropTypes from 'prop-types';
import { Link } from 'react-router-dom';

export function LinkButton(
    {
        children,
        to,
        disabled = false,
        isExternal = false,
        target = "_blank",
        rel = "noopener noreferrer",
        className = "btn btn--primary",
        onClick
    }) {
    return isExternal ? (
        <a
            href={to}
            className={`${className}${disabled ? " btn--disabled" : ""}`}
            target={target}
            rel={rel}
            onClick={disabled ? (e) => e.preventDefault() : onClick}
        >
            {children}
        </a>
    ) : (
        <Link
            to={to}
            className={`${className}${disabled ? " btn--disabled" : ""}`}
            onClick={disabled ? (e) => e.preventDefault() : undefined}
        >
            {children}
        </Link>
    );
}

LinkButton.propTypes = {
    children: PropTypes.node,
    disabled: PropTypes.bool,
    isExternal: PropTypes.bool,
    target: PropTypes.string,
    rel: PropTypes.string,
    className: PropTypes.string,
};

