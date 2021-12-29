package com.softwareeng.openpick.document;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.print.Doc;
import javax.servlet.Servlet;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Controller
public class DocumentController {

    @Autowired
    private DocumentRepository repo;

    @GetMapping("/documents")
    public String showAllDocuments(Model model){
        List<Document> listDocs = repo.findAll();
        model.addAttribute("listDocs",listDocs);
        return "documents";
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("document") MultipartFile multipartFile, RedirectAttributes redirectAttributes) throws IOException {
        String filename = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        Document document = new Document();
        document.setContent(multipartFile.getBytes());
        document.setSize(multipartFile.getSize());
        document.setUploadTime(new Date());
        document.setTitle(filename);
        repo.save(document);
        redirectAttributes.addFlashAttribute("message","The file has been uploaded successfully.");

        return "redirect:/documents";
    }

    @GetMapping("/download")
    public void downloadFile(@Param("id") Integer id, HttpServletResponse response ) throws Exception {
        Optional<Document> result =repo.findById(id);
        if(!result.isPresent()){
            throw new Exception("Could not find document with ID:"+id);
        }
        Document document = result.get();
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=" + document.getTitle();

        response.setHeader(headerKey,headerValue);

        ServletOutputStream outputStream = response.getOutputStream();

        outputStream.write(document.getContent());
        outputStream.close();
    }



//    @GetMapping("/projects/new")
//    public String showNewProjectForm(Model model){
//        //TODO new project form
//        return "project_form";
//    }
//
//    @GetMapping("/projects/{id}")
//    public String showProject(@PathVariable("id") Integer id, Model model, RedirectAttributes ra){
//        //TODO
//        return "/projects/{id}";
//    }
//
//    @GetMapping("/users/{user_id}/projects/")
//    public String showProjectsFromUser(@PathVariable("user_id") Integer id, Model model, RedirectAttributes ra){
//        //TODO
//        return "/users/{user_id}/projects/";
//    }
//
//    //@GetMapping("/users/{user_id}/projects/")
//
//    @PostMapping("/users/{user_id}/projects/save")
//    public String saveProject(Document document, RedirectAttributes ra){
//        //TODO
//        return "redirect:/projects";
//    }


}