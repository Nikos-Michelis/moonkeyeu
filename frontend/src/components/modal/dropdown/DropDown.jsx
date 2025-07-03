import React, { useRef} from "react";
import {Button} from "@/components/button/Button.jsx";
const Dropdown = ({status, menus, className, style }) => {
    const dropdownRef = useRef(null);
    const handleItemClick = (item) => {
        if (item.onClick) {
            item.onClick();
        }
    };

    return (
        <div className={`dropdown bg-star-100 padding-4 rounded-md ${className}`} style={{ ...style }} ref={ dropdownRef }>
            {menus.map((menu) => (
                <div className="container" data-spacing="none">
                    {menu.items.map((item, index) => (
                        <Button
                            key={index}
                            href={item.href || "#"}
                            className={`menu-item flex align-center btn-transparent padding-1 rounded-md fs-small-300 fw-bold'${item.className || ""}`}
                            onClick={(e) => {
                                handleItemClick(item);
                            }}
                            disabled={status?.isPending}
                        >
                            {item.leftIcon && <span className="icon-button">{item.leftIcon}</span>}
                            {item.label}
                            {item.rightIcon && <span className="icon-right">{item.rightIcon}</span>}
                        </Button>
                    ))}
                </div>
            ))}
        </div>
    );
};

export default Dropdown;
