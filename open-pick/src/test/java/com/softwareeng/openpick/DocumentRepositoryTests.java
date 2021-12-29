package com.softwareeng.openpick;

import com.softwareeng.openpick.document.Document;
import com.softwareeng.openpick.document.DocumentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DocumentRepositoryTests {

    @Autowired
    private DocumentRepository repo;
    @Autowired
    private TestEntityManager entityManager;


    @Test
    @Rollback(false)
    void testInsertProject() throws IOException {
        File file = new File("C:\\Users\\Doroteea\\Downloads\\WebSecurityConfig.java");
        Document document = new Document();
        document.setTitle(file.getName());

        byte[] bytes = Files.readAllBytes(file.toPath());
        document.setContent(bytes);
        long fileSize = bytes.length;
        document.setSize(fileSize);
        document.setUploadTime(new Date());

        Document savedDoc = repo.save(document);
        Document existDoc = entityManager.find(Document.class,savedDoc.getId());

        assertThat(existDoc.getSize()).isEqualTo(fileSize);

    }
}