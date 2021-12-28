package com.softwareeng.openpick.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    public Long countById(Integer id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE users SET id = :newId WHERE id = :oldId", nativeQuery = true)
    public void updateUserId(Integer oldId, Integer newId);
}
