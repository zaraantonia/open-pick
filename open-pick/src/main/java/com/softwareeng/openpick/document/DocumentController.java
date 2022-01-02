package com.softwareeng.openpick.document;

import com.softwareeng.openpick.exception.NotFoundException;
import com.softwareeng.openpick.project.ProjectService;
import com.softwareeng.openpick.user.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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

    @Autowired
    private ProjectService projectService;

    @GetMapping("/documents")
    public String showAllDocuments(Model model){
        List<Document> listDocs = repo.findAll();
        model.addAttribute("listDocs",listDocs);
        return "documents";
    }

    @PostMapping("/upload/{project_id}")
    public String uploadFile(@RequestParam("document") MultipartFile multipartFile, @PathVariable("project_id") Integer projectId, RedirectAttributes redirectAttributes) throws IOException {
        String filename = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        Document document = new Document();
        document.setContent(multipartFile.getBytes());
        document.setSize(multipartFile.getSize());
        document.setUploadTime(new Date());
        document.setTitle(filename);
        try {
            document.setProject(projectService.get(projectId));
            repo.save(document);
            redirectAttributes.addFlashAttribute("message","The file has been uploaded successfully.");
        } catch (NotFoundException ignored){}
        return "redirect:/projects";
    }

    @GetMapping("/download")
    public void downloadFile(@Param("id") Integer id, HttpServletResponse response ) throws Exception {
        Optional<Document> result = repo.findById(id);
        if (!result.isPresent()) {
            throw new Exception("Could not find document with ID:" + id);
        }
        Document document = result.get();
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=" + document.getTitle();

        response.setHeader(headerKey, headerValue);

        ServletOutputStream outputStream = response.getOutputStream();

        outputStream.write(document.getContent());
        outputStream.close();
    }

    @GetMapping("/delete/{id}")
    public String deleteDocument(@PathVariable("id") Integer id, RedirectAttributes ra) throws UserNotFoundException {
        Long count = repo.countById(id);
        if(count == null | count == 0){
            throw new UserNotFoundException("Could not find document.");
        }
        repo.deleteById(id);
        ra.addFlashAttribute("message","File has been deleted succesfully");
        return "redirect:/projects";
    }

    @RequestMapping(value = "/uploadEdit/{oldId}", method = RequestMethod.POST)
    public String editDocument(@PathVariable("oldId") Integer oldId, @RequestParam("document") MultipartFile multipartFile, RedirectAttributes redirectAttributes) {
        try {
            Optional<Document> result =repo.findById(oldId);
            if(!result.isPresent()){
                throw new Exception("Could not find document with ID:"+oldId);
            }
            Document document = result.get();
            String filename = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            document.setContent(multipartFile.getBytes());
            document.setSize(multipartFile.getSize());
            document.setUploadTime(new Date());
            document.setTitle(filename);
            repo.save(document);
            redirectAttributes.addFlashAttribute("message","The file has been edited successfully.");
            return "redirect:/projects";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "The .");
            return "redirect:/projects";
        }
    }

    @GetMapping("/edit/{id}")
    public String editDoc(@PathVariable("id") Integer id,Model model) {
        model.addAttribute("oldId", id.toString());
        return "doc_form";
    }

}