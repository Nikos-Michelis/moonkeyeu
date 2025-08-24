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
        <section className="toolbar">
            <div className="toolbar__container margin-block-start-12 margin-block-end-8 margin-inline-8">
                <div className="search flex justify-center">
                    <input type="hidden" name="action" value="search" />
                    <input
                        className="search__searchbar box-shadow-light"
                        value={localSearch || ""}
                        type="text"
                        name="search"
                        placeholder={searchPlaceHolder}
                        onChange={(e) => setLocalSearch(e.target.value)}
                    />
                    <div className="search__btn-search box-shadow-light">
                        <FontAwesomeIcon icon={faSearch} />
                    </div>
                </div>
                <div className="toolbar__tools">
                    <div className="flex justify-center">
                        <Button
                            ref={triggerRef}
                            className="btn btn--overlay"
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
                        btnClassName="select__btn select__btn--small"
                        dropDownClassName="select__content--medium"
                    />
                </div>
                <div ref={optionsRef} className={`toolbar__wrapper height-fade ${ showOptions ? "show md" : ""}`}>
                    <div className="toolbar__options padding-block-start-8 padding-inline-6">
                        <div className="toolbar__option">
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
                        <div className="toolbar__option">
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
                        <div className="toolbar__option">
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
                        <div className="toolbar__option">
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
                        <div className="toolbar__option">
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
                        <div className="toolbar__option">
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
                        <div className="toolbar__option">
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
                    <div className="toolbar__actions flex flex-wrap justify-start padding-4">
                        <Button
                            className="btn btn--primary btn--small"
                            onClick={ handleReset }>
                            <FontAwesomeIcon icon={faArrowsRotate} />
                        </Button>
                    </div>
                </div>
            </div>
        </section>
    );
}

export default LaunchFiltering;
