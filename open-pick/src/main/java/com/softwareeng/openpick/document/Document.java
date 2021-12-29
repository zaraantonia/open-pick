package com.softwareeng.openpick.document;

import com.softwareeng.openpick.user.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "documents")
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 512,unique=true)
    private String title;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User owner;
//
//    @OneToOne
//    @JoinColumn(name = "project_id", referencedColumnName = "id")
//    private Project project;

    @Column (nullable = false)
    private long size;

    @Column(name = "upload_time")
    private Date uploadTime;

    public Document() {
    }

    public Document(Integer id, String title, long size) {
        this.id = id;
        this.title = title;
        this.size = size;
    }


    private byte[] content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
