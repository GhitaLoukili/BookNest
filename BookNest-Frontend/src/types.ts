export type BookResponse = {
    name: string
    author: string
    publisher: string
    genre: string
    topic: string
    releaseYear: number
    price: number
    _links: {
    self: {
    href: string
    },
    book: {
    href: string
    },
    owner: {
    href: string
    }
    }
}

export type Book = {
    name: string
    author: string
    publisher: string
    genre: string
    topic: string
    releaseYear: number
    price: number
}

export type BookEntry = {
    book: Book;
    url: string;
}