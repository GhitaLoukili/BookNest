import Dialog from '@mui/material/Dialog'
import DialogActions from '@mui/material/DialogActions'
import DialogTitle from '@mui/material/DialogTitle'
import { useState } from 'react'
import { Book } from '../types'
import { useMutation, useQueryClient } from '@tanstack/react-query'
import { addBook } from '../api/bookapi'
import BookDialogContent from './bookDialogContent'
import Button from '@mui/material/Button'


function AddBook() {
    const [open, setOpen]= useState(false)
    const [book, setBook]= useState<Book>({
        name: '',
        author: '',
        publisher: '',
        genre: '',
        topic: '',
        releaseYear: 0,
        price: 0
    })
    const queryClient= useQueryClient()

    const handleClickOpen = () => {
        setOpen(true)
    }
       
    const handleClose = () => {
        setOpen(false)
    }

    const handleChange = (event : React.ChangeEvent<HTMLInputElement>) =>{
        setBook({...book, [event.target.name]:event.target.value})
    }

    const { mutate } = useMutation(addBook, {
        onSuccess: () => {
        queryClient.invalidateQueries(["books"]);
        },
        onError: (err) => {
        console.error(err);
        },
    })

    const handleSave = () => {
        mutate(book)
        setBook({ name: '', author: '', publisher: '', genre:'',topic:'',releaseYear: 0, price: 0 })
        handleClose();
    }

    return(
    <>
        <Button variant='outlined' onClick={handleClickOpen}>New Book</Button>
        <Dialog open={open} onClose={handleClose}>
        <DialogTitle>New book</DialogTitle>
        <BookDialogContent book={book} handleChange={handleChange} />
        <DialogActions>
        <Button variant='outlined' onClick={handleClose}>Cancel</Button>
        <Button variant='outlined' onClick={handleSave}>Save</Button>
        </DialogActions>
        </Dialog>
    </>
    );
}
export default AddBook