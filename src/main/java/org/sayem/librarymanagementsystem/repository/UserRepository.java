package org.sayem.librarymanagementsystem.repository;

import org.sayem.librarymanagementsystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    String GET_USERS_COUNT = "select COUNT(*) from User";
    String GET_ACTIVE_USERS_COUNT = "select COUNT(*) from User u where u.isActive is true";

    User findByUsername(String username);

    @Query(GET_USERS_COUNT)
    int getTotalUsers();

    @Query(GET_ACTIVE_USERS_COUNT)
    int getTotalActiveUsers();
}