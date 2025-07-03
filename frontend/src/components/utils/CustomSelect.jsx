import React, {useState, useRef} from "react";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {faAngleDown, faAngleUp, faMagnifyingGlass} from '@fortawesome/free-solid-svg-icons';
import {useClickOutside} from "@/hooks/util/useClickOutside.jsx";
const CustomSelect = (
    {
        options = [],
        setFilters,
        placeholder,
        field,
        searchValue,
        selectedOption,
        setSelectedOption,
        setSearchValue,
        defaultValue,
        isSearchable = true,
        btnClassName = "",
        dropDownClassName = "",

    }) => {
    const [isOpen, setIsOpen] = useState(false);
    const dropdownRef = useRef(null);

    const preSelected = options?.find?.((item) => item.id === defaultValue)?.name || null;
    const handleOnSelect = (value) => {
        if (!value) return;
        setFilters({[field]: value.id });
        setSelectedOption((prev) => ({ ...prev, [field]: value.name }));
        setIsOpen(false);
    };

    const handleOnSearch = (e) => {
        setSearchValue((prev) => ({ ...prev, [field]: e.target.value }));
    };

    const filteredOptions = options.filter(option =>
        option.name.toLowerCase().includes(searchValue?.[field]?.toLowerCase() || "")
    );

    const handleClickOutside = (event) => {
        if (dropdownRef.current && !dropdownRef.current.contains(event.target)) {
            setIsOpen(false);
        }
    };

    useClickOutside({ modalRef: dropdownRef, handler: handleClickOutside });

    return (
        <div className="select-wrapper" ref={dropdownRef}>
            <div className={`select-btn ${btnClassName}`} onClick={() => setIsOpen(!isOpen)}>
                <span>{selectedOption?.[field] || preSelected || placeholder}</span>
                <FontAwesomeIcon icon={isOpen ? faAngleUp : faAngleDown} />
            </div>
            {isOpen && (
                <div className={`content ${dropDownClassName}`}>
                    {isSearchable && (
                        <div className="search">
                            <FontAwesomeIcon icon={faMagnifyingGlass} />
                            <input
                                className="filter-search-input"
                                type="text"
                                placeholder="Search"
                                value={searchValue?.[field] || ""}
                                onChange={handleOnSearch}
                            />
                        </div>
                    )}
                    <ul className="options">
                        {filteredOptions.length > 0 ? (
                            filteredOptions.map((option) => (
                                <li className={`${defaultValue === option.id? "selected" : ""}`} key={option.id} onClick={() => handleOnSelect(option)}>
                                    {option.name}
                                </li>
                            ))
                        ) : (
                            <li>No results found</li>
                        )}
                    </ul>
                </div>
            )}
        </div>
    );
};

export default CustomSelect;
