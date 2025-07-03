const FloatingLabel = ({ label, children, isActive }) => {
    return (
        <div className={`floating-label ${isActive ? "active" : ""}`}>
            {children}
            <label>{label}</label>
        </div>
    );
};

export default FloatingLabel;
