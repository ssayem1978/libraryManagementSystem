package org.sayem.librarymanagementsystem.repository;

import org.sayem.librarymanagementsystem.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    String GET_CATEGORY_COUNT = "SELECT COUNT(*) FROM Category";


    @Query(GET_CATEGORY_COUNT)
    int getTotalCategories();
}
