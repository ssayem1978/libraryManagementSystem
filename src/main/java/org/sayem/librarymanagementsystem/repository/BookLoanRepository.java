package org.sayem.librarymanagementsystem.repository;

import org.sayem.librarymanagementsystem.entity.BookLoan;
import org.sayem.librarymanagementsystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookLoanRepository extends JpaRepository<BookLoan, Long> {
    List<BookLoan> findByUser(User user);

    @Query("select bl from BookLoan bl where bl.returnInitialized is true and bl.returnInitializedDate is not null and bl.returnAcceptDate is null")
    List<BookLoan> findAllReturnInitializedBooks();

    @Query("SELECT COUNT(*) from BookLoan bl where bl.returnInitialized is false and bl.returnInitializedDate is null and bl.returnAcceptDate is null")
    int getTotalBookLoans();

    @Query("SELECT COUNT(*) from BookLoan bl where bl.returnInitialized is true and bl.returnInitializedDate is not null and bl.returnAcceptDate is null")
    int getTotalInitializedBookLoans();
}
