import { Book } from '../types'
import DialogContent from '@mui/material/DialogContent'
import TextField from '@mui/material/TextField'
import Stack from '@mui/material/Stack'



type DialogFormProps = {
 book: Book;
 handleChange: (event: React.ChangeEvent<HTMLInputElement>) =>
 void
}
function BookDialogContent({ book, handleChange }: DialogFormProps) {
 return (
    <DialogContent>
        <Stack spacing={2} mt={1}>
            <TextField label="Name" name="name"
            value={book.name} onChange={handleChange}/>
            <TextField label="Author" name="author"
            value={book.author} onChange={handleChange}/>
            <TextField label="Publisher" name="publisher"
            value={book.publisher} onChange={handleChange}/>
            <TextField label="Genre" name="genre"
            value={book.genre} onChange={handleChange}/>
            <TextField label="Topic" name="topic"
            value={book.topic} onChange={handleChange}/>
            <TextField label="Release Year" name="releaseYear"
            value={book.releaseYear} onChange={handleChange}/>
            <TextField label="Price" name="price"
            value={book.price} onChange={handleChange}/>
        </Stack>
        </DialogContent>

)}
 

export default BookDialogContent