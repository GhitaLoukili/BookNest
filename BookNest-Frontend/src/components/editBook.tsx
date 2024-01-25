import { useState } from 'react'
import { useMutation, useQueryClient } from '@tanstack/react-query'
import Dialog from '@mui/material/Dialog'
import DialogActions from '@mui/material/DialogActions'
import DialogTitle from '@mui/material/DialogTitle'
import { Book, BookResponse, BookEntry } from '../types'
import BookDialogContent from './bookDialogContent'
import { updateBook } from '../api/bookapi'
import Button from '@mui/material/Button'
import IconButton from '@mui/material/IconButton'
import EditIcon from '@mui/icons-material/Edit'
import Tooltip from '@mui/material/Tooltip'



type FormProps = {
 bookInfos: BookResponse;
}

function EditBook({ bookInfos }: FormProps) {
    const queryClient= useQueryClient()
    const [open, setOpen] = useState(false)
    const [book, setBook] = useState<Book>({
        name: '',
        author: '',
        publisher: '',
        genre: '',
        topic:'',
        releaseYear: 0,
        price: 0
    })

    const { mutate } = useMutation(updateBook,{
        onSuccess: () =>{
            queryClient.invalidateQueries(["books"])
        },
        onError: (err) => {
            console.error(err)
        }
    })

    const handleClickOpen = () => {
        setBook({
            name: bookInfos.name,
            author: bookInfos.author,
            publisher: bookInfos.publisher,
            genre: bookInfos.genre,
            topic: bookInfos.topic,
            releaseYear: bookInfos.releaseYear,
            price: bookInfos.price
        })
        setOpen(true)
    }
            
    const handleClose = () => {
        setOpen(false)
    }
            
    const handleSave = () => {
        const url = bookInfos._links.self.href
        const bookEntry: BookEntry = {book, url}
        mutate(bookEntry)
        setBook({ name: '', author: '', publisher: '',genre:'', topic:'',releaseYear: 0, price: 0 })
        setOpen(false)
    }

    const handleChange = (event : React.ChangeEvent<HTMLInputElement>) =>{
        setBook({...book, [event.target.name]: event.target.value})
    }

    return(
    <>
        <Tooltip title="Edit book">
            <IconButton aria-label="edit" size="small"
                onClick={handleClickOpen}>
                <EditIcon fontSize= "small" />
            </IconButton>
        </Tooltip>
        <Dialog open={open} onClose={handleClose}>
            <DialogTitle>Edit book</DialogTitle>
            <BookDialogContent book={book} handleChange={handleChange} />
            <DialogActions>
                <Button variant='outlined' onClick={handleClose}>Cancel</Button>
                <Button variant='outlined' onClick={handleSave}>Save</Button>
            </DialogActions>
        </Dialog>
    </>
    )
}
export default EditBook