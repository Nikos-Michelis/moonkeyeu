import React, { useEffect, useState } from "react";
import { useAuth } from "@/context/AuthProvider.jsx";
import toast from "react-hot-toast";
import { useSimpleQuery } from "@/services/queries.jsx";
import BasicTable from "@/components/table/BasicTable.jsx";
import { DateTime } from "luxon";
import { Button } from "@/components/button/Button.jsx";
import Heading from "@/components/utils/Heading.jsx";
import TablePagination from "@/components/pagination/TablePagination.jsx";
import {
    getCoreRowModel,
    getPaginationRowModel,
    getSortedRowModel,
    useReactTable
} from "@tanstack/react-table";
import SpinnerLoader from "@/components/loader/SpinnerLoader.jsx";
import JsonLdGeneric from "@/components/seo/jsonld/JsonLdGeneric.jsx";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faChevronLeft} from "@fortawesome/free-solid-svg-icons";

function MembersReport() {
    const baseUrl = import.meta.env.VITE_BACKEND_BASE_URL;
    const { user } = useAuth();
    const [sorting, setSorting] = useState([]);

    const columns = [
        {
            header: "ID",
            accessorKey: "id",
        },
        {
            header: "Username",
            accessorKey: "username",
        },
        {
            header: "Email",
            accessorKey: "email",
        },
         {
            header: "Roles",
            accessorKey: "role",
        },

        {
            header: "Enabled",
            accessorKey: "enabled",
            cell: ({ row }) => (row.original.enabled ? "Yes" : "No")

        },
        {
            header: "Locked",
            accessorKey: "accountNonLocked",
            cell: ({ row }) => (!row.original.accountNonLocked ? "Yes" : "No")

        },
        {
            header: "Registered",
            accessorKey: "createdAt",
            cell: (info) =>
                info.getValue()
                    ? DateTime.fromISO(info.getValue()).toFormat("dd-MM-yyyy")
                    : "-",
        },
    ];

    const queryData = useSimpleQuery({
        url: `${baseUrl}/dashboard/report/members`,
        cacheKey: "members",
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
                title="Members"
                description="Manage and view all registered members from your dashboard."
            />
            <JsonLdGeneric
                type="CollectionPage"
                title="Members"
                description="Manage and view all registered members from your dashboard."
            />
            <TablePagination table={table} />
            <section className="members-section">
                <div className="container container--light-overlay padding-5 rounded-sm" data-type="wide">
                    {!(queryData.isPending || queryData.isFetching) &&
                        <div className="flex justify-start margin-block-4">
                            <Button className="btn--transparent" onClick={() => window.history.back()}>
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

export default MembersReport;
