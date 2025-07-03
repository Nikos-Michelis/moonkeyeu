import React, {useEffect, useRef, useState} from "react";
import PropTypes from "prop-types";
import {useDebounce} from "@/hooks/util/useDebounce.jsx";
import {useAstronautsFilters} from "@/hooks/paging-filtering/useAstronautsFilters.jsx";
import {Button} from "@/components/button/Button.jsx";
import CustomSelect from "@/components/utils/CustomSelect.jsx";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {faArrowsRotate, faFilter, faSearch} from '@fortawesome/free-solid-svg-icons';
import {useClickOutside} from "@/hooks/util/useClickOutside.jsx";

AstronautsFiltering.propTypes = {
    agency: PropTypes.number,
    status: PropTypes.number,
    nationality: PropTypes.number,
    limit: PropTypes.number,
};

function AstronautsFiltering({ filters, searchPlaceHolder, field, isLoading, isFetching, isError }) {
    const [showOptions, setShowOptions] = useState(false);
    const [searchValue, setSearchValue] = useState('');
    const [selectedOption, setSelectedOption] = useState('');
    const optionsRef = useRef(null);
    const triggerRef = useRef(null);
    const limitOptions =
        [
            { id: 12, name: "Limit 12" },
            { id: 24, name: "Limit 24" },
            { id: 50, name: "Limit 50" }
        ];
    const orderingOptions =
        [
            { id: field ? field : "asc", name: "Asc" },
            { id: field ? `-${field}` : "desc", name: "Desc" }
        ]
    const orderingFields =
        [
            { id: "lastFlight", name: "Last Flight Date" },
            { id: "firstFlight", name: "First Flight Date" },
            { id: "age", name: "Age" },
        ];
    const {
        search,
        agency,
        status,
        nationality,
        limit,
        ordering,
        setFilters,
        resetFilters,
    } = useAstronautsFilters();
    const [locaSearch, setLocalSearch] = useState(search);
    const debounceSearch = useDebounce(locaSearch);

    useEffect(() => {
        setFilters({ search: debounceSearch });
    }, [debounceSearch]);

    const toggleOptions = (isClicked) => {
        setShowOptions((prevState) => prevState !== isClicked);
    };
    const handleReset = () => {
        setSearchValue('');
        setSelectedOption('');
        resetFilters();
        setLocalSearch('');
    };

    const handleClickOutside = (event) => {
        if (optionsRef.current && !optionsRef.current.contains(event.target)) {
            setShowOptions(false);
        }
    };

    useClickOutside({
        modalRef: optionsRef,
        triggerRef: triggerRef,
        handler: handleClickOutside
    });

    return (
        <section className="item-filter-section">
            <div className="toolbar-container margin-block-start-12 margin-block-end-8 margin-inline-8">
                <div className="search-container flex justify-center">
                    <input type="hidden" name="action" value="search" />
                    <input
                        className="searchbar box-shadow-light"
                        value={locaSearch || ""}
                        type="text"
                        name="search"
                        placeholder={searchPlaceHolder}
                        onChange={(e) => setLocalSearch(e.target.value)}
                    />
                    <div className="search-button box-shadow-light">
                        <FontAwesomeIcon icon={faSearch} />
                    </div>
                </div>
                <div className="tools-container">
                    <div className="flex flex-wrap justify-center">
                        <Button
                            ref={triggerRef}
                            className="btn btn-overlay"
                            onClick={() => toggleOptions(true)}
                            disabled={isFetching || isLoading || isError}
                        >
                            <FontAwesomeIcon icon={faFilter} />
                        </Button>
                    </div>
                    <CustomSelect
                         options={limitOptions || []}
                         field="limit"
                         placeholder={`Limit ${limit}`}
                         setFilters={setFilters}
                         selectedOption={selectedOption}
                         setSelectedOption={setSelectedOption}
                         defaultValue={Number(limit)}
                         isSearchable={false}
                         btnClassName="select-btn-small"
                         dropDownClassName="ctn-medium"/>
                </div>
                <div ref={optionsRef} className={`toolbar-option height-fade ${ showOptions ? "show md" : ""}`}>
                    <div className="padding-8">
                        <div className="select-option-container">
                            <div className="select-option">
                                <CustomSelect
                                    options={filters?.data?.status || []}
                                    field="status"
                                    placeholder="Status"
                                    setFilters={setFilters}
                                    searchValue={searchValue}
                                    setSearchValue={setSearchValue}
                                    selectedOption={selectedOption}
                                    setSelectedOption={setSelectedOption}
                                    defaultValue={Number(status)}
                                />
                            </div>
                            <div className="select-option">
                                <CustomSelect
                                    options={filters?.data?.nationalities || []}
                                    field="nationality"
                                    placeholder={"Νationality"}
                                    setFilters={setFilters}
                                    searchValue={searchValue}
                                    setSearchValue={setSearchValue}
                                    selectedOption={selectedOption}
                                    setSelectedOption={setSelectedOption}
                                    defaultValue={Number(nationality)}
                                />
                            </div>
                            <div className="select-option">
                                <CustomSelect
                                    options={filters?.data?.agencies || []}
                                    field="agency"
                                    placeholder={"Agency"}
                                    setFilters={setFilters}
                                    searchValue={searchValue}
                                    setSearchValue={setSearchValue}
                                    selectedOption={selectedOption}
                                    setSelectedOption={setSelectedOption}
                                    defaultValue={Number(agency)}
                                />
                            </div>
                             <div className="select-option">
                                 <CustomSelect
                                     options={orderingOptions || []}
                                     field="ordering"
                                     placeholder={selectedOption}
                                     setFilters={setFilters}
                                     selectedOption={selectedOption}
                                     setSelectedOption={setSelectedOption}
                                     defaultValue={ordering}
                                     isSearchable={false}
                                 />
                            </div>
                             <div className="select-option">
                                 <CustomSelect
                                     options={orderingFields || []}
                                     field="field"
                                     placeholder={"OrderBy"}
                                     setFilters={setFilters}
                                     selectedOption={selectedOption}
                                     setSelectedOption={setSelectedOption}
                                     defaultValue={ordering}
                                     isSearchable={false}
                                 />
                            </div>

                        </div>
                        <div className="container flex flex-wrap justify-start margin-block-start-2">
                            <Button
                                className="btn btn-primary btn-small"
                                onClick={ handleReset }>
                                <FontAwesomeIcon icon={faArrowsRotate} />
                            </Button>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    );
}

export default AstronautsFiltering;
