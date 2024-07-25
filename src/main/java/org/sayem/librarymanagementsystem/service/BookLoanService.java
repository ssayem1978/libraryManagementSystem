package org.sayem.librarymanagementsystem.service;

import org.sayem.librarymanagementsystem.entity.BookLoan;

import java.time.LocalDate;
import java.util.List;

public interface BookLoanService {

    void borrowBook(Long id, LocalDate returnDate, String message);

    List<BookLoan> getBorrowingBooks();

    void returnInitialize(Long id);

    List<BookLoan> getLendBooks();

    void acceptReturn(Long id);
}
