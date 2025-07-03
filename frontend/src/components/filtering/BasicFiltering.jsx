import PropTypes from "prop-types";
import React, {useEffect, useState} from "react";
import {useDebounce} from "@/hooks/util/useDebounce.jsx";
import {useBasicFilters} from "@/hooks/paging-filtering/useBasicFilters.jsx";
import {Button} from "@/components/button/Button.jsx";
import CustomSelect from "@/components/utils/CustomSelect.jsx";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSearch, faArrowsRotate } from '@fortawesome/free-solid-svg-icons';

BasicFiltering.propTypes = {
    limit: PropTypes.number,
    search: PropTypes.string,
    ordering: PropTypes.string

};

function BasicFiltering({defaultFilters, searchPlaceHolder, field}) {
    const [selectedOption, setSelectedOption] = useState('');
    const limitOptions =
        [
            {id: 12, name: "Limit 12"},
            {id: 24, name: "Limit 24"},
            {id: 50, name: "Limit 50"}
        ];
    const orderingOptions =
        [
            {id: field ? field : "asc", name: "Asc"},
            {id: field ? `-${field}` : "desc", name: "Desc"}
        ]

    const {
        search,
        limit,
        ordering,
        setFilters,
        resetFilters,
    } = useBasicFilters(defaultFilters);
    const [locaSearch, setLocalSearch] = useState(search);
    const debounceSearch = useDebounce(locaSearch);

    useEffect(() => {
        setFilters({search: debounceSearch});
    }, [debounceSearch]);
    const handleReset = () => {
        setSelectedOption('');
        resetFilters();
        setLocalSearch('');
    };

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
                        dropDownClassName="ctn-medium"
                    />
                    <Button  className="btn btn-overlay" onClick={handleReset}>
                        <FontAwesomeIcon icon={faArrowsRotate} />
                    </Button>
                    <CustomSelect
                        options={orderingOptions || []}
                        field="ordering"
                        placeholder={selectedOption}
                        setFilters={setFilters}
                        selectedOption={selectedOption}
                        setSelectedOption={setSelectedOption}
                        defaultValue={ordering}
                        isSearchable={false}
                        btnClassName="select-btn-small"
                        dropDownClassName="ctn-medium"
                    />
                </div>
            </div>
        </section>
    );
}

export default BasicFiltering;