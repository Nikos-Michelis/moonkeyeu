import React, {useEffect, useRef, useState} from "react";
import PropTypes from "prop-types";
import {useLaunchFilters} from "@/hooks/paging-filtering/useLaunchFilters.jsx";
import {useDebounce} from "@/hooks/util/useDebounce.jsx";
import {Button} from "@/components/button/Button.jsx";
import CustomSelect from "@/components/utils/CustomSelect.jsx";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {faArrowsRotate, faFilter, faSearch} from '@fortawesome/free-solid-svg-icons';
import {useClickOutside} from "@/hooks/util/useClickOutside.jsx";

LaunchFiltering.propTypes = {
    location: PropTypes.number,
    launcher: PropTypes.number,
    agency: PropTypes.number,
    upcoming: PropTypes.bool,
    rocketConfing: PropTypes.number,
    spacecraftConf: PropTypes.number,
    astronaut: PropTypes.number,
    search: PropTypes.string,
    limit: PropTypes.number,
};

function LaunchFiltering({filters, searchPlaceHolder, isPending, isFetching, isError }) {
    const [showOptions, setShowOptions] = useState(false);
    const [searchValue, setSearchValue] = useState('');
    const [selectedOption, setSelectedOption] = useState('');
    const optionsRef = useRef(null);
    const triggerRef = useRef(null);
    const maxLimit = 50;
    const sheduled =
        [
            { id: "true", name: "Upcoming" },
            { id: "false", name: "Previous" }
        ];
    const limitOptions =
        [
            { id: 12, name: "Limit 12" },
            { id: 24, name: "Limit 24" },
            { id: 50, name: "Limit 50" }
        ];
    const orderingOptions =
        [
            { id: "asc", name: "Asc" },
            { id: "desc", name: "Desc" }
        ];
    const {
        search,
        location,
        launcher,
        agency,
        upcoming,
        rocketConfig,
        spacecraftConfig,
        astronaut,
        limit,
        ordering,
        setFilters,
        resetFilters,
    } = useLaunchFilters();
    const [localSearch, setLocalSearch] = useState(search);
    const debounceSearch = useDebounce(localSearch);

    useEffect(() => {
        setFilters({ search: debounceSearch});
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
                        value={localSearch || ""}
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
                    <div className="flex justify-center">
                        <Button
                            ref={triggerRef}
                            className="btn btn-overlay"
                            onClick={() => toggleOptions(true)}
                            disabled={isFetching || isPending || isError}
                        >
                            <FontAwesomeIcon icon={faFilter} />
                        </Button>
                    </div>
                    <CustomSelect
                        options={limitOptions || []}
                        field="limit"
                        placeholder={`Limit ${limit <= maxLimit ? limit : maxLimit }`}
                        setFilters={setFilters}
                        selectedOption={selectedOption}
                        setSelectedOption={setSelectedOption}
                        defaultValue={Number(limit)}
                        isSearchable={false}
                        btnClassName="select-btn-small"
                        dropDownClassName="ctn-medium"
                    />
                </div>
                <div ref={optionsRef} className={`toolbar-option height-fade ${ showOptions ? "show md" : ""}`}>
                    <div className="padding-8">
                        <div className="select-option-container">
                            <div className="select-option">
                                <CustomSelect
                                    options={sheduled || []}
                                    field="upcoming"
                                    setFilters={setFilters}
                                    placeholder="Upcoming/Past"
                                    searchValue={searchValue}
                                    setSearchValue={setSearchValue}
                                    selectedOption={selectedOption}
                                    setSelectedOption={setSelectedOption}
                                    defaultValue={upcoming}
                                    isSearchable={false}
                                />
                            </div>
                            <div className="select-option">
                                <CustomSelect
                                    options={filters?.data?.locations || []}
                                    field="location"
                                    placeholder={"Location"}
                                    setFilters={setFilters}
                                    searchValue={searchValue}
                                    setSearchValue={setSearchValue}
                                    selectedOption={selectedOption}
                                    setSelectedOption={setSelectedOption}
                                    defaultValue={Number(location)}
                                />
                            </div>
                            <div className="select-option">
                                <CustomSelect
                                    options={filters?.data?.launchers || []}
                                    field="launcher"
                                    setFilters={setFilters}
                                    placeholder="Launcher"
                                    searchValue={searchValue}
                                    setSearchValue={setSearchValue}
                                    selectedOption={selectedOption}
                                    setSelectedOption={setSelectedOption}
                                    defaultValue={Number(launcher)}
                                />
                            </div>
                            <div className="select-option">
                                <CustomSelect
                                    options={filters?.data?.agencies || []}
                                    field="agency"
                                    setFilters={setFilters}
                                    placeholder="Agency"
                                    searchValue={searchValue}
                                    setSearchValue={setSearchValue}
                                    selectedOption={selectedOption}
                                    setSelectedOption={setSelectedOption}
                                    defaultValue={Number(agency)}
                                />
                            </div>
                            <div className="select-option">
                                <CustomSelect
                                    options={filters?.data?.rocket_configurations || []}
                                    field="rocketConfig"
                                    setFilters={setFilters}
                                    placeholder="Rocket"
                                    searchValue={searchValue}
                                    setSearchValue={setSearchValue}
                                    selectedOption={selectedOption}
                                    setSelectedOption={setSelectedOption}
                                    defaultValue={Number(rocketConfig)}
                                />
                            </div>
                            <div className="select-option">
                                <CustomSelect
                                    options={filters?.data?.spacecraft_configurations || []}
                                    field="spacecraftConfig"
                                    setFilters={setFilters}
                                    placeholder="Spacecraft"
                                    searchValue={searchValue}
                                    setSearchValue={setSearchValue}
                                    selectedOption={selectedOption}
                                    setSelectedOption={setSelectedOption}
                                    defaultValue={Number(spacecraftConfig)}
                                />
                            </div>
                            <div className="select-option">
                                <CustomSelect
                                    options={filters?.data?.astronauts || []}
                                    field="astronaut"
                                    setFilters={setFilters}
                                    placeholder="Astronaut"
                                    searchValue={searchValue}
                                    setSearchValue={setSearchValue}
                                    selectedOption={selectedOption}
                                    setSelectedOption={setSelectedOption}
                                    defaultValue={Number(astronaut)}
                                />
                            </div>
                        </div>
                        <div className="flex flex-wrap justify-start margin-block-start-4">
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

export default LaunchFiltering;
