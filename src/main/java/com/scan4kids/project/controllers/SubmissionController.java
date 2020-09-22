package com.scan4kids.project.controllers;

import com.scan4kids.project.daos.ScoresRepository;
import com.scan4kids.project.daos.SubmissionsRepository;
import com.scan4kids.project.daos.UsersRepository;
import com.scan4kids.project.models.Submission;
import com.scan4kids.project.models.User;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

@Controller
public class SubmissionController {

//    @Value("${file-upload-path}")
//    private String uploadPath;

    private SubmissionsRepository submissionsDao;
    private UsersRepository usersDao;
    private ScoresRepository scoresDao;

    public SubmissionController(SubmissionsRepository submissionsDao, UsersRepository usersDao, ScoresRepository scoresDao) {
        this.submissionsDao = submissionsDao;
        this.usersDao = usersDao;
        this.scoresDao = scoresDao;
    }

//    @GetMapping("/submissions/create")
//    public String showCreateForm(Model model) {
//        model.addAttribute("submission", new Submission());
//        return "users/dashboard";
//    }
//
//
//    @PostMapping("/submissions/create")
//    public String createSubmission(@Valid Submission createdSubmission, Errors validation, Model m, @RequestParam(name = "file") MultipartFile uploadedFile){
//        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//        if (validation.hasErrors()) {
//            m.addAttribute("errors", validation);
//            System.out.println(validation.getAllErrors());
//            m.addAttribute("submission", createdSubmission);
//            return "users/dashboard";
//        }
//
//        // Files handle
//        uploadFileHandler(createdSubmission, m, uploadedFile);
//
//        createdSubmission.setOwner(currentUser);
//        submissionsDao.save(createdSubmission);
//
//        return "redirect:/dashboard";
//    }
//
//
//    private void uploadFileHandler(@Valid Submission createdSubmission, Model m, @RequestParam(name = "file") MultipartFile uploadedFile) {
//        if(!uploadedFile.getOriginalFilename().isEmpty()){
//
//            String filename = uploadedFile.getOriginalFilename().replace(" ", "_").toLowerCase();
//            String filepath = Paths.get(uploadPath, filename).toString();
//            File destinationFile = new File(filepath);
//
//            // Try to save it in the server
//            try {
//                uploadedFile.transferTo(destinationFile);
//                m.addAttribute("message", "File successfully uploaded!");
//            } catch (IOException e) {
//                e.printStackTrace();
//                m.addAttribute("message", "Oops! Something went wrong! " + e);
//            }
//
//            //Save it in the DB
//            createdSubmission.setSubmission_file(filename);
//        }
//    }

    @PostMapping("/upload/db")
    public String uploadToDB(@RequestParam("file") MultipartFile file) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Submission submission = new Submission();
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        submission.setFileName(fileName);
        submission.setOwner(currentUser);
        try {
            submission.setFile(file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        submissionsDao.save(submission);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/files/download/")
                .path(fileName).path("/db")
                .toUriString();
        return "submissions/success-page";
    }

    @GetMapping("/download/{id}/db")
    public ResponseEntity<?> downloadFromDB(@PathVariable long id) {
        Submission submission = submissionsDao.findSubmissionsById(id);
        return ResponseEntity.ok()
//                .contentType(MediaType.parseMediaType())
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + submission.getFileName() + "\"")
                .body(submission.getFile());
    }




}
