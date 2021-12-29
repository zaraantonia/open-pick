package com.softwareeng.openpick.document;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document,Integer> {

    @Query("SELECT new Document (d.id, d.title, d.size) from Document d ORDER BY d.uploadTime DESC")
    @Override
    List<Document> findAll();
}
