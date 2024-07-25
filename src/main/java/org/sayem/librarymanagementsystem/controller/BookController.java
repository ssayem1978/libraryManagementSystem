package org.sayem.librarymanagementsystem.controller;


import org.sayem.librarymanagementsystem.entity.Book;
import org.sayem.librarymanagementsystem.entity.BookLoan;
import org.sayem.librarymanagementsystem.entity.Category;
import org.sayem.librarymanagementsystem.service.BookLoanService;
import org.sayem.librarymanagementsystem.service.BookService;
import org.sayem.librarymanagementsystem.service.CategoryService;
import org.sayem.librarymanagementsystem.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final CategoryService categoryService;
    private final BookService bookService;
    private final BookLoanService bookLoanService;
    private final ImageService imageService;


    @GetMapping("/admin/books")
    public String books(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        List<Book> books = bookService.getBooks();
        model.addAttribute("books", books);
        model.addAttribute("username", username);
        return "book/books";
    }

    @GetMapping("/admin/books/add")
    public String showAddBookForm(Model model) {
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        model.addAttribute("book", new Book());
        return "book/add-book";
    }

    @PostMapping("/admin/books/add")
    public String addBook(@ModelAttribute Book book, BindingResult result, @RequestParam("coverImage2") MultipartFile coverImage, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.getAllCategories());
            return "book/add-book";
        }
        String imageUniqueFileName = imageService.uploadImage(coverImage);
        book.setCoverImage(imageUniqueFileName);
        bookService.addBook(book);
        return "redirect:/admin/books";
    }


    @GetMapping("/admin/books/edit/{id}")
    public String showEditBookForm(@PathVariable Long id, Model model) {
        Book book = bookService.getBookById(id);
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("book", book);
        model.addAttribute("categories", categories);
        return "book/edit-book";
    }

    @PostMapping("/admin/books/edit/{id}")
    public String editBook(@PathVariable Long id, @ModelAttribute Book book, @RequestParam("coverImage2") MultipartFile coverImage) {
        bookService.updateBook(id, book, coverImage);
        return "redirect:/admin/books";
    }

    @GetMapping("/admin/books/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return "redirect:/admin/books";
    }

    @GetMapping("/user/borrowing")
    public String getUserBorrowingBooks(Model model) {
        List<BookLoan> books = bookLoanService.getBorrowingBooks();
        model.addAttribute("books", books);
        return "book/borrowing-books";
    }

    @GetMapping("/user/books/return/{id}")
    public String initializeReturn(@PathVariable Long id, Model model) {
        bookLoanService.returnInitialize(id);
        List<BookLoan> books = bookLoanService.getBorrowingBooks();
        model.addAttribute("books", books);
        return "book/borrowing-books";
    }
}
