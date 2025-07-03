import React from "react";
import PropTypes from "prop-types";
import {Button} from "@/components/button/Button.jsx";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import { faAngleRight, faAnglesRight, faAngleLeft, faAnglesLeft } from "@fortawesome/free-solid-svg-icons";

const FIRST_PAGE = 1;

Pagination.propTypes = {
    page: PropTypes.number.isRequired,
    nextPage: PropTypes.func.isRequired,
    previousPage: PropTypes.func.isRequired,
    totalElements: PropTypes.number.isRequired,
    totalPages: PropTypes.number.isRequired,
};
export default function Pagination({ page, totalPages, totalElements, nextPage, previousPage, firstPage, lastPage, isPending, isFetching }) {
    return (
        <section className="pagination-section">
            <div className="pagination-container margin-block-12">
                <div className="backward-arrows">
                    { page !== FIRST_PAGE &&
                    <Button className="btn btn-overlay" disabled={page === FIRST_PAGE || (isPending || isFetching)} onClick={firstPage}>
                        <FontAwesomeIcon icon={faAnglesLeft} />
                    </Button> }
                    <Button className="btn btn-overlay" disabled={page === FIRST_PAGE || (isPending || isFetching)} onClick={previousPage}>
                        <FontAwesomeIcon icon={faAngleLeft} /> Prev
                    </Button>
                </div>
                <span className="paging-text">
                    Page
                    <span> {page} </span>
                    /
                    <span> {totalPages} </span>
                    of
                    <span> {totalElements} </span>
                    items
                </span>
                <div className="forward-arrows">
                    <Button className="btn btn-overlay" disabled={page === totalPages || (isPending || isFetching)} onClick={nextPage}>
                        Next <FontAwesomeIcon icon={faAngleRight} />
                    </Button>
                    { page !== totalPages &&
                    <Button className="btn btn-overlay" disabled={page === totalPages || (isPending || isFetching)} onClick={lastPage}>
                        <FontAwesomeIcon icon={faAnglesRight} />
                    </Button> }
                </div>
            </div>
        </section>
    );
}

