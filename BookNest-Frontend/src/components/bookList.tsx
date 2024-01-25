import { useQuery, useMutation, useQueryClient } from "@tanstack/react-query"
import { getBooks, deleteBook } from "../api/bookapi"
import { DataGrid, GridColDef, GridCellParams, GridToolbar } from '@mui/x-data-grid'
import { Snackbar } from "@mui/material"
import { useState } from "react"
import AddBook from "./addBook"
import EditBook from "./editBook"
import IconButton from '@mui/material/IconButton'
import DeleteIcon from '@mui/icons-material/Delete'
import Tooltip from '@mui/material/Tooltip'
import Button from "@mui/material/Button"
import Stack from "@mui/material/Stack"

type BookListProps = {
    logOut?: () => void
   }

function BookList({logOut}: BookListProps) {
    const [open, setOpen]=useState(false)
    const queryClient = useQueryClient()

    const { data, error, isSuccess } = useQuery({
        queryKey: ["books"],
        queryFn: getBooks
    })

    const { mutate } = useMutation(deleteBook, {
        onSuccess: () => {
            setOpen(true)
            queryClient.invalidateQueries({ queryKey: ['books'] });
        },
        onError: (err) => {
        console.error(err);
        },
       })

    const columns: GridColDef[] = [
        {field: 'name', headerName: 'Title', width: 250},
        {field: 'author', headerName: 'Author', width: 200},
        {field: 'publisher', headerName: 'Publisher', width: 200},
        {field: 'genre', headerName: 'Genre', width: 150},
        {field: 'topic', headerName: 'Topic', width: 250},
        {field: 'releaseYear', headerName: 'Release Year', width: 100},
        {field: 'price', headerName: 'Price', width: 150},
        {
            field: 'edit',
            headerName: '',
            width: 90,
            sortable: false,
            filterable: false,
            disableColumnMenu: true,
            renderCell: (params: GridCellParams) => (
            <EditBook bookInfos={params.row} />
            ),
        },
        {
            field: 'delete',
            headerName: '',
            width: 90,
            sortable: false,
            filterable: false,
            disableColumnMenu: true,
            renderCell: (params: GridCellParams) => (
                <Tooltip title="Delete book">
                    <IconButton aria-label="delete" size="small"onClick={() => {
                        if (window.confirm(`Are you sure you want to delete ${params.row.name}, by ${params.row.author}?`))
                            mutate(params.row._links.book.href)
                        }}>
                        <DeleteIcon fontSize="small" />
                    </IconButton>
                </Tooltip>
            ),
            },
       ];

    if (!isSuccess) {
        return <span>Loading...</span>
       }
       else if (error) {
        return <span>Error when fetching books...</span>
       }
       else {
        return (
            <>
            <Stack direction="row" alignItems="center" justifyContent="space-between">
                <AddBook />
                <Button onClick={logOut}>Log out</Button>
            </Stack>
            <DataGrid
            rows={data}
            columns={columns}
            disableRowSelectionOnClick={true}
            getRowId={row => row._links.self.href}
            slots={{ toolbar: GridToolbar }}
            />
            < Snackbar
            open={open}
            autoHideDuration={6000}
            onClose={() => setOpen(false)}
            message="Book deleted"
            />
            </>
        );
       }
   }
   export default BookList;