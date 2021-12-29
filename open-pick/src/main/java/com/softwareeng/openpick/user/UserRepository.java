package com.softwareeng.openpick.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    public Long countById(Integer id);

    @Query("SELECT u FROM User u WHERE u.username = :username")
    public User getUserByUsername(@Param("username") String username);

    @Transactional
    @Modifying
    @Query(value = "UPDATE users SET id = :newId WHERE id = :oldId", nativeQuery = true)
    public void updateUserId(Integer oldId, Integer newId);
}
