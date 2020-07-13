package com.scan4kids.project.controllers;

import com.scan4kids.project.daos.AlbumsRepository;
import com.scan4kids.project.daos.PhotosRepository;
import com.scan4kids.project.daos.UsersRepository;
import com.scan4kids.project.models.Album;
import com.scan4kids.project.models.Photo;
import com.scan4kids.project.models.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class PhotoController {

    @Value("${filestack.api.key}")
    private String apiKey;

    private AlbumsRepository albumsDao;
    private PhotosRepository photosDao;

    public PhotoController(AlbumsRepository albumsDao, PhotosRepository photosDao) {
        this.albumsDao = albumsDao;
        this.photosDao = photosDao;
    }

    @GetMapping("/albums/photos")
    public String viewAllPhotos(Model model) {
        List<Photo> photos = photosDao.findAll();
        model.addAttribute("photos", photos);
        model.addAttribute("noPhotosFound", photos.size() == 0);
        return "albums/photos";
    }

    @GetMapping("/albums/photos/{id}")
    public String showAPhoto(@PathVariable long id, Model model) {
        Photo photo = photosDao.getOne(id);
        model.addAttribute("photo", photo);
        model.addAttribute("photoId", id); //this is optional, just to have this attribute in case it needs to be used in the view at some point.
        return "albums/photo-show";
    }

    @GetMapping("/albums/photos/create")
    public String showCreateForm(Model model) {
        model.addAttribute("photo", new Photo());
        model.addAttribute("apiKey", apiKey);
        return "albums/photo-create";
    }

    @PostMapping("/albums/photos/create")
    public String saveCreateForm(@ModelAttribute Photo photoToAdd) {
        Album currentAlbum = albumsDao.getOne(3L); //hard-coded for now
        photoToAdd.setAlbum(currentAlbum);
        Photo photoInDB = photosDao.save(photoToAdd);
        return "redirect:/albums/photos/" + photoInDB.getId();
    }

    @GetMapping("/albums/photos/{id}/edit")
    public String showPhotoEditForm(Model model, @PathVariable long id) {
        Photo photoToEdit = photosDao.getOne(id);
        model.addAttribute("photo", photoToEdit);
        return "albums/photo-edit";
    }

    @PostMapping("/albums/photos/{id}/edit")
    public String updatePhoto(@ModelAttribute Photo photoToEdit) {
        Album currentAlbum = albumsDao.getOne(1L); //hard-coded for now
        photoToEdit.setAlbum(currentAlbum);
        photosDao.save(photoToEdit);
        return "redirect:/albums/photos/" + photoToEdit.getId();
    }

    @PostMapping("/albums/photos/{id}/delete")
    public String destroy(@PathVariable long id) {
        photosDao.deleteById(id);
        return "redirect:/albums/photos";
    }

}
