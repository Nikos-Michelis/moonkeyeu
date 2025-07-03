import {Button} from "@/components/button/Button.jsx";
import React from "react";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {faBackwardFast, faArrowLeft, faForwardFast, faArrowRight} from '@fortawesome/free-solid-svg-icons';

const TablePagination = ({table}) => {
    return (
        <section className="pagination-section">
            <div className="pagination-container margin-block-10">
                <div className="backward-arrows">
                    {table.getCanPreviousPage() && (
                        <Button className="btn btn-overlay" onClick={() => table.setPageIndex(0)}>
                            <FontAwesomeIcon icon={faBackwardFast} />
                        </Button>
                    )}
                    <Button className="btn btn-overlay" onClick={() => table.previousPage()} disabled={!table.getCanPreviousPage()}>
                        <FontAwesomeIcon icon={faArrowLeft} /> Prev
                    </Button>
                </div>
                <span className="paging-text">
                    Page
                    <span> {table.getState().pagination.pageIndex + 1} </span>
                    /
                    <span> {table.getPageCount()} </span>
                    of
                    <span> {table.getPrePaginationRowModel().rows.length} </span>
                    items
                </span>
                <div className="forward-arrows">
                    <Button className="btn btn-overlay" onClick={() => table.nextPage()} disabled={!table.getCanNextPage()}>
                        Next <FontAwesomeIcon icon={faArrowRight} />
                    </Button>
                    {table.getCanNextPage() && (
                        <Button className="btn btn-overlay" onClick={() => table.setPageIndex(table.getPageCount() - 1)}>
                            <FontAwesomeIcon icon={faForwardFast} />
                        </Button>
                    )}
                </div>
            </div>
        </section>
    );
}

export default TablePagination;
