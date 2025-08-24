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
        <div className={`${className} bg-star-100 padding-4 rounded-md`} style={{ ...style }} ref={ dropdownRef }>
            {menus.map((menu) => (
                <div className="container" data-spacing="none" key={menu.name}>
                    {menu.items.map((item, index) => (
                        <Button
                            key={index}
                            href={item.href || "#"}
                            className={`dropdown__menu-item flex align-center btn--transparent padding-1 rounded-md fs-small-300 fw-bold'${item.className || ""}`}
                            onClick={(e) => {
                                handleItemClick(item);
                            }}
                            disabled={status?.isPending}
                        >
                            {item.leftIcon && <span className="dropdown__icon">{item.leftIcon}</span>}
                            {item.label}
                            {item.rightIcon && <span className="dropdown__icon">{item.rightIcon}</span>}
                        </Button>
                    ))}
                </div>
            ))}
        </div>
    );
};

export default Dropdown;
