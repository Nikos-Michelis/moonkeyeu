import {Button} from "@/components/button/Button.jsx";
import React from "react";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {
    faAnglesLeft,
    faAngleLeft,
    faAngleRight,
    faAnglesRight
} from '@fortawesome/free-solid-svg-icons';

const TablePagination = ({table}) => {
    return (
        <section className="pagination">
            <div className="pagination__container margin-block-10">
                <div className="backward-arrows">
                    {table.getCanPreviousPage() && (
                        <Button className="btn btn--overlay" onClick={() => table.setPageIndex(0)}>
                            <FontAwesomeIcon icon={faAnglesLeft} />
                        </Button>
                    )}
                    <Button className="btn btn--overlay" onClick={() => table.previousPage()} disabled={!table.getCanPreviousPage()}>
                        <FontAwesomeIcon icon={faAngleLeft} /> Prev
                    </Button>
                </div>
                <span className="pagination__text">
                    Page
                    <span> {table.getState().pagination.pageIndex + 1} </span>
                    /
                    <span> {table.getPageCount()} </span>
                    of
                    <span> {table.getPrePaginationRowModel().rows.length} </span>
                    items
                </span>
                <div className="forward-arrows">
                    <Button className="btn btn--overlay" onClick={() => table.nextPage()} disabled={!table.getCanNextPage()}>
                        Next <FontAwesomeIcon icon={faAngleRight} />
                    </Button>
                    {table.getCanNextPage() && (
                        <Button className="btn btn--overlay" onClick={() => table.setPageIndex(table.getPageCount() - 1)}>
                            <FontAwesomeIcon icon={faAnglesRight} />
                        </Button>
                    )}
                </div>
            </div>
        </section>
    );
}

export default TablePagination;
