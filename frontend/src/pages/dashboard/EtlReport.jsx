import React, {useEffect, useState} from "react";
import { useAuth } from "@/context/AuthProvider.jsx";
import toast from "react-hot-toast";
import { useSimpleQuery } from "@/services/queries.jsx";
import BasicTable from "@/components/table/BasicTable.jsx";
import { DateTime } from "luxon";
import { Button } from "@/components/button/Button.jsx";
import { useModal } from "@/context/ModalProvider.jsx";
import {getCoreRowModel, getPaginationRowModel, getSortedRowModel, useReactTable} from "@tanstack/react-table";
import Heading from "@/components/utils/Heading.jsx";
import TablePagination from "@/components/pagination/TablePagination.jsx";
import SpinnerLoader from "@/components/loader/SpinnerLoader.jsx";
import JsonLdGeneric from "@/components/seo/jsonld/JsonLdGeneric.jsx";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {faChevronLeft, faCircleExclamation} from '@fortawesome/free-solid-svg-icons';

function EtlReport() {
    const baseUrl = import.meta.env.VITE_BACKEND_BASE_URL;
    const { openModal } = useModal();
    const { user } = useAuth();
    const [sorting, setSorting] = useState([]);


    const columns = [
        {
            header: "ID",
            accessorKey: "jobExecutionId",
        },
        {
            header: "Task",
            accessorKey: "jobName",
        },
        {
            header: "StartTime",
            accessorKey: "startTime",
            cell: (info) =>
                info.getValue()
                    ? DateTime.fromISO(info.getValue()).toFormat("MMMM dd, yyyy - hh:mm:ss a")
                    : "-",
        },
        {
            header: "EndTime",
            accessorKey: "endTime",
            cell: (info) =>
                info.getValue()
                    ? DateTime.fromISO(info.getValue()).toFormat("MMMM dd, yyyy - hh:mm:ss a")
                    : "-",
        },
        {
            header: "Status",
            accessorKey: "status",
        },
        {
            header: "Actions",
            accessorKey: "actions",
            cell: ({ row }) => (
                <div>
                    {row.original.exitMessage ? (
                        <Button
                            className="btn-transparent"
                            onClick={() =>
                                openModal("exceptionMessage", {
                                    title: "Exception Message",
                                    details: [row.original.exitMessage],
                                    confirmLabel: undefined,
                                    cancelLabel: "Exit",
                                    confirmFn: undefined,
                                    hasConfirmBtn: false
                                }, "prompt")
                            }
                        >
                            <FontAwesomeIcon icon={faCircleExclamation} style={{ color: "var(--clr-warning-300)" }} />
                        </Button>
                    ) : (
                        "-"
                    )}
                </div>
            ),
        },
    ];

    const queryData = useSimpleQuery({
        url: `${baseUrl}/dashboard/report/tasks`,
        cacheKey: "etl-report",
        queryOptions: {
            enabled: !!user,
            retry: 1,
        },
        options: { withCredentials: false, Bearer: true },
    });

    useEffect(() => {
        if (queryData.isError) {
            toast.error("Oops! Something went wrong, try again later.");
        }
    }, [queryData.isError]);


    const table = useReactTable({
        data: queryData?.data?.data ?? [],
        columns,
        getCoreRowModel: getCoreRowModel(),
        getPaginationRowModel: getPaginationRowModel(),
        getSortedRowModel: getSortedRowModel(),
        initialState: {
            pagination: { pageSize: 12 },
        },
        state: {
            sorting: sorting
        },
        onSortingChange: setSorting,
    });

    return (
        <>
            <Heading
                title="ETL Tasks"
                description="Track the progress and review detailed logs of all ETL tasks executed by the ETL API."
            />
            <JsonLdGeneric
                type="CollectionPage"
                title="ETL Tasks"
                description="Track the progress and review detailed logs of all ETL tasks executed by the ETL API."
            />
            <TablePagination table={table} />
            <section className="members-section">
                <div className="container padding-inline-5 rounded-sm overlay" data-type="wide">
                    {!(queryData.isPending || queryData.isFetching) &&
                        <div className="flex justify-start margin-block-4">
                            <Button className="btn-transparent" onClick={() => window.history.back()}>
                                <FontAwesomeIcon icon={faChevronLeft} /> Back
                            </Button>
                        </div>
                    }
                    {(queryData.isPending || queryData.isFetching) ? <SpinnerLoader/> : <BasicTable table={table} />}
                </div>
            </section>
            <TablePagination table={table} />
        </>
    );
}

export default EtlReport;
