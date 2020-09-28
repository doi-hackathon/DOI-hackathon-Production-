package com.scan4kids.project.controllers;


import com.scan4kids.project.models.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ScoresController {

    @GetMapping("/score")
    public String showScoreForm() {
        return "scores/scores";
    }

//    @GetMapping("/score/{id}")
//    public String show(@PathVariable long id, Model model){
//        Event event = eventsDao.getOne(id);
//        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        List<UsersEvents> usersEvents = usersEventsDao.findAllByUserId(currentUser.getId());
//        byte attendeeCount = usersEventsDao.countByEventAndUser(event, currentUser);
//        List<UsersEvents> volunteeredEventsList = usersEventsDao.findAllVolunteeredEvents(id);
//
//        return "events/show";
//    }


}
