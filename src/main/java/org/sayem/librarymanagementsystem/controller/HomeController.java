package org.sayem.librarymanagementsystem.controller;


import org.sayem.librarymanagementsystem.entity.Book;
import org.sayem.librarymanagementsystem.entity.BookLoan;
import org.sayem.librarymanagementsystem.entity.Category;
import org.sayem.librarymanagementsystem.service.BookLoanService;
import org.sayem.librarymanagementsystem.service.BookService;
import lombok.RequiredArgsConstructor;
import org.sayem.librarymanagementsystem.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private BookService bookService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BookLoanService bookLoanService;

    @RequestMapping({"/books", "/home", "/"})
    public String home(Model model,
                       @RequestParam(defaultValue = "0") int page,
                       @RequestParam(required = false) Long category) {
        List<Category> categories = categoryService.getAllCategories();
        Page<Book> books;
        if (category != null) {
            List<Sort.Order> sorts = List.of(Sort.Order.desc("id"));
            books = bookService.findByCategory(category, PageRequest.of(page, 5, Sort.by(sorts)));
        } else {
            books = bookService.getBooks(PageRequest.of(page, 5));
        }
        model.addAttribute("categories", categories);
        model.addAttribute("books", books);
        model.addAttribute("page", books);
        model.addAttribute("selectedCategory", category);
        return "home";
    }

    @GetMapping("/books/details/{id}")
    public String bookDetails(@PathVariable Long id, Model model) {
        Book book = bookService.getBookById(id);
        model.addAttribute("book", book);
        return "book/book-details";
    }

    @PostMapping("/books/borrow/{id}")
    public String borrowBook(@PathVariable Long id,
                             @RequestParam("returnDate") LocalDate returnDate,
                             @RequestParam("message") String message) {
        bookLoanService.borrowBook(id, returnDate, message);
        return "redirect:/books/details/" + id;
    }

    @GetMapping("/admin/books/lend")
    public String lendsBook(Model model) {
        List<BookLoan> books = bookLoanService.getLendBooks();
        model.addAttribute("books", books);
        return "book/lend-books";
    }

    @GetMapping("/admin/books/lend/{id}")
    public String acceptReturn(@PathVariable Long id, Model model) {
        bookLoanService.acceptReturn(id);
        List<BookLoan> books = bookLoanService.getLendBooks();
        model.addAttribute("books", books);
        return "book/lend-books";
    }
}
