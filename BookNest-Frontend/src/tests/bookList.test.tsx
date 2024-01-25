import { QueryClient, QueryClientProvider } from '@tanstack/react-query'
import { render, screen, waitFor } from '@testing-library/react'
import { describe, expect, test } from 'vitest'
import '@testing-library/jest-dom/vitest'
import userEvent from '@testing-library/user-event'
import Booklist from '../components/bookList'


const queryClient = new QueryClient({
    defaultOptions: {
        queries: {
            retry: false,
        },
    },
})

const wrapper = ({children } : { children: React.ReactNode }) => (
    <QueryClientProvider client = {queryClient}>{children}</QueryClientProvider>)

describe("Booklist tests", () => {
    test("component renders", () => {
    render(<Booklist />, {wrapper})
    expect(screen.getByText(/Loading/i)).toBeInTheDocument()
    })

    test("Books are fetched", async () => {
        render(<Booklist />, { wrapper })
        await waitFor(() => screen.getByText(/New Book/i))
        expect(screen.getByText(/The Butterfly Room/i)).toBeInTheDocument()
        })

    test("Open new baook modal", async () => {
        render(<Booklist />, { wrapper })
        await waitFor(() => screen.getByText(/New Book/i))
        await userEvent.click(screen.getByText(/New Book/i))
        expect(screen.getByRole('button', { name: 'Save' })).toBeInTheDocument()

    })
})