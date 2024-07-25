package org.sayem.librarymanagementsystem.repository;

import org.sayem.librarymanagementsystem.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Page<Book> findAllByCategoryId(Long category, Pageable bookPage);

    @Query("select count(*) from Book")
    int getTotalBooks();
}
