package com.softwareeng.openpick.project;

import org.hibernate.annotations.SQLUpdate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public interface ProjectRepository extends JpaRepository<Project,Integer> {

    @Query(value = "SELECT projects_id FROM users_projects WHERE user_id = :userId", nativeQuery = true)
    public List<Integer> findProjectsIdByUserId(@Param("userId") Integer userId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE users_projects SET user_id = :newId WHERE user_id = :oldId", nativeQuery = true)
    public void updateUserId(@Param("oldId") Integer oldId, @Param("newId")Integer newId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE projects SET user_id = :newId WHERE user_id = :oldId", nativeQuery = true)
    public void updateUserIdInProject(Integer oldId, Integer newId);
}
