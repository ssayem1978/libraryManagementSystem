package org.sayem.librarymanagementsystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.sayem.librarymanagementsystem.entity.Book;
import org.sayem.librarymanagementsystem.entity.BookLoan;
import org.sayem.librarymanagementsystem.entity.User;
import org.sayem.librarymanagementsystem.repository.BookLoanRepository;
import org.sayem.librarymanagementsystem.repository.BookRepository;
import org.sayem.librarymanagementsystem.repository.UserRepository;
import org.sayem.librarymanagementsystem.service.BookLoanService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookLoanServiceImpl implements BookLoanService {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final BookLoanRepository bookLoanRepository;

    private final DateTimeFormatter defaultDateTimeFormatter = DateTimeFormatter.ofPattern("EEEE, MMM dd, yyyy 'at' HH:mm a");

    @Override
    public void borrowBook(Long id, LocalDate returnDate, String message) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername());

        // Find the book by ID
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        // Check if there are available copies
        if (book.getQuantity() <= 0) {
            throw new RuntimeException("No copies available");
        }

        BookLoan bookLoan = new BookLoan();
        bookLoan.setBook(book);
        bookLoan.setUser(user);
        bookLoan.setLoanDate(LocalDate.now());
        bookLoan.setReturnDate(returnDate);
        bookLoan.setMessage(message);

        bookLoanRepository.save(bookLoan);

        book.setQuantity(book.getQuantity() - 1);
        bookRepository.save(book);
    }

    @Override
    public List<BookLoan> getBorrowingBooks() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername());
        return bookLoanRepository.findByUser(user);
    }

    @Override
    public void returnInitialize(Long id) {
        BookLoan bookLoan = bookLoanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book loan not found."));
        bookLoan.setReturnInitialized(true);
        bookLoan.setReturnInitializedDate(LocalDateTime.now().format(defaultDateTimeFormatter));
        bookLoanRepository.save(bookLoan);
    }

    @Override
    public List<BookLoan> getLendBooks() {
        return bookLoanRepository.findAllReturnInitializedBooks();
    }

    @Override
    public void acceptReturn(Long id) {
        BookLoan bookLoan = bookLoanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book loan not found."));
        bookLoan.setReturnAcceptDate(LocalDateTime.now().format(defaultDateTimeFormatter));
        bookLoanRepository.save(bookLoan);

        Book book = bookRepository.findById(bookLoan.getBook().getId())
                .orElseThrow(()-> new RuntimeException("Book not found."));

        book.setQuantity(book.getQuantity()+1);
        bookRepository.save(book);
    }
}
