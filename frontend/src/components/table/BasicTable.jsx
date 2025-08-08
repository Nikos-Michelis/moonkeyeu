import React from "react";
import { flexRender } from "@tanstack/react-table";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';

const BasicTable = ({ table }) => {
    const rows = table.getRowModel().rows;
    const asc = (
        <div className="table__sorting">
            <FontAwesomeIcon icon={faSortUp} />
        </div>
    );

    const desc = (
        <div className="table__sorting">
            <FontAwesomeIcon icon={faSortDown} />
        </div>
    );
    return (
        <div className="table">
            <div className="table__wrapper padding-4 flex flex-column justify-center">
                <table>
                    <thead>
                    {table.getHeaderGroups().map(headerGroup => (
                        <tr key={headerGroup.id}>
                            {headerGroup.headers.map(header => (
                                <th
                                    key={header.id}
                                    onClick={header.column.getToggleSortingHandler()}
                                >
                                    {header.isPlaceholder ? null : (
                                        <div>
                                            {flexRender(
                                                header.column.columnDef.header,
                                                header.getContext()
                                            )}
                                            {
                                                {
                                                    asc: asc,
                                                    desc: desc
                                                }[header.column.getIsSorted() ?? null]
                                            }
                                        </div>
                                    )}
                                </th>
                            ))}
                        </tr>
                    ))}
                    </thead>
                    <tbody>
                    {rows.length === 0 && (
                        <tr>
                            <td colSpan={table.getAllColumns().length}>No data available</td>
                        </tr>
                    )}
                    {rows.map(row => (
                        <tr key={row.id}>
                            {row.getVisibleCells().map(cell => (
                                <td key={cell.id}>
                                    {flexRender(cell.column.columnDef.cell, cell.getContext())}
                                </td>
                            ))}
                        </tr>
                    ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
};


export default BasicTable;
