import React from "react";
import {Button} from "@/components/button/Button.jsx";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faAngleLeft, faAngleRight, faAnglesLeft, faAnglesRight} from "@fortawesome/free-solid-svg-icons";

const FIRST_PAGE = 1;
const StatePagination = ({ pagination, isPending, isFetching}) => {
    return (
        <div className="flex justify-center align-center fs-small-200">
            { pagination?.page !== FIRST_PAGE &&
                <Button
                    className="btn btn-primary no-ellipsis"
                    disabled={pagination?.page === FIRST_PAGE || (isPending || isFetching)}
                    onClick={pagination?.firstPage}
                >
                    <FontAwesomeIcon icon={faAnglesLeft} />
                </Button>
            }
             <Button
                className="btn btn-primary no-ellipsis"
                disabled={pagination?.page === FIRST_PAGE || (isPending || isFetching)}
                onClick={pagination?.prevPage}
            >
                <FontAwesomeIcon icon={faAngleLeft} />
            </Button>

            <span className="paging-text flex flex-wrap justify-center margin-inline-4">
                <div className="flex">
                    Page
                    <span className="margin-inline-1"> {pagination?.page} </span>
                    /
                    <span className="margin-inline-1"> {pagination?.totalPages} </span>
                </div>
                <div className="flex">
                    of
                    <span className="margin-inline-1"> {pagination?.totalItems} </span>
                    items
                </div>
            </span>
            <Button
                className="btn btn-primary no-ellipsis"
                disabled={pagination?.page === pagination?.totalPages || (isPending || isFetching)}
                onClick={pagination?.nextPage}
            >
                <FontAwesomeIcon icon={faAngleRight} />
            </Button>
            { pagination?.page !== pagination?.totalPages &&
                <Button
                    className="btn btn-primary no-ellipsis"
                    disabled={pagination?.page === pagination?.totalPages || (isPending || isFetching)}
                    onClick={pagination?.lastPage}
                >
                    <FontAwesomeIcon icon={faAnglesRight} />
                </Button>
            }
        </div>
    );
}
export default StatePagination;