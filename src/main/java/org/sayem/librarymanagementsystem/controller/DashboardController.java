package org.sayem.librarymanagementsystem.controller;

import org.sayem.librarymanagementsystem.repository.BookLoanRepository;
import org.sayem.librarymanagementsystem.repository.BookRepository;
import org.sayem.librarymanagementsystem.repository.CategoryRepository;
import org.sayem.librarymanagementsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookLoanRepository bookLoanRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/admin/dashboard")
    public String getDashboard(Model model) {

        int bookCount = bookRepository.getTotalBooks();
        int userCount = userRepository.getTotalUsers();
        int activeUserCount = userRepository.getTotalActiveUsers();
        int bookLoanCount = bookLoanRepository.getTotalBookLoans();
        int bookInitLoanCount = bookLoanRepository.getTotalInitializedBookLoans();
        int categoryCount = categoryRepository.getTotalCategories();

        model.addAttribute("bookCount", bookCount);
        model.addAttribute("userCount", userCount);
        model.addAttribute("activeUserCount", activeUserCount);
        model.addAttribute("bookLoanCount", bookLoanCount);
        model.addAttribute("bookInitLoanCount", bookInitLoanCount);
        model.addAttribute("categoryCount", categoryCount);

        return "dashboard";
    }
}
