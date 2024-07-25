package org.sayem.librarymanagementsystem.service;

import org.sayem.librarymanagementsystem.entity.Book;
import org.sayem.librarymanagementsystem.entity.BookLoan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BookService {

    void addBook(Book book);

    List<Book> getBooks();

    Page<Book> getBooks(Pageable bookPage);

    Book getBookById(Long id);

    void updateBook(Long id, Book book, MultipartFile coverImage);

    void deleteBook(Long id);

    Page<Book> findByCategory(Long category, Pageable bookPage);

}
