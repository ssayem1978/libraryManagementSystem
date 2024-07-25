package org.sayem.librarymanagementsystem.service.impl;

import org.sayem.librarymanagementsystem.entity.Book;
import org.sayem.librarymanagementsystem.entity.BookLoan;
import org.sayem.librarymanagementsystem.repository.BookLoanRepository;
import org.sayem.librarymanagementsystem.repository.BookRepository;
import org.sayem.librarymanagementsystem.service.BookService;
import org.sayem.librarymanagementsystem.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final ImageService imageService;

    @Override
    public void addBook(Book book) {
        bookRepository.save(book);
    }

    @Override
    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Couldn't find book'"));
    }

    @Override
    public void updateBook(Long id, Book updatedBook, MultipartFile coverImage) {
        Book existingBook = getBookById(id);

        if (!coverImage.isEmpty()) {
            imageService.deleteImage(existingBook.getCoverImage());
            updatedBook.setCoverImage(imageService.uploadImage(coverImage));
        } else {
            updatedBook.setCoverImage(existingBook.getCoverImage());
        }
        existingBook.setTitle(updatedBook.getTitle());
        existingBook.setAuthor(updatedBook.getAuthor());
        existingBook.setIsbn(updatedBook.getIsbn());
        existingBook.setCategory(updatedBook.getCategory());
        existingBook.setCoverImage(updatedBook.getCoverImage());
        bookRepository.save(existingBook);
    }

    @Transactional
    @Override
    public void deleteBook(Long id) {
        Book book = getBookById(id);
        imageService.deleteImage(book.getCoverImage());
        bookRepository.delete(book);
    }

    @Override
    public Page<Book> getBooks(Pageable bookPage) {
        return bookRepository.findAll(bookPage);
    }

    @Override
    public Page<Book> findByCategory(Long category, Pageable bookPage) {
        return bookRepository.findAllByCategoryId(category, bookPage);
    }

}
