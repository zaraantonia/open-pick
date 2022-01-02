package com.softwareeng.openpick.project;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO users_projects (user_id, projects_id) VALUES (:userId, :projectId)", nativeQuery = true)
    void saveInUsersProjects(@Param("projectId") Integer projectId, @Param("userId") Integer userId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM users_projects WHERE projects_id = :projectId", nativeQuery = true)
    void deleteProjectsFromUserProjects(@Param("projectId") Integer projectId);
}
