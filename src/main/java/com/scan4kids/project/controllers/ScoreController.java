package com.scan4kids.project.controllers;


import com.scan4kids.project.daos.ScoresRepository;
import com.scan4kids.project.daos.SubmissionsRepository;
import com.scan4kids.project.models.Score;
import com.scan4kids.project.models.Submission;
import com.scan4kids.project.models.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ScoreController {

    private SubmissionsRepository submissionsDao;
    private ScoresRepository scoresDao;

    public ScoreController(SubmissionsRepository submissionsDao, ScoresRepository scoresDao) {
        this.submissionsDao = submissionsDao;
        this.scoresDao = scoresDao;
    }

    @GetMapping("/submissions/{id}/score")
    public String showScoreForm(Model model, @PathVariable long id) {
        Submission submissionToScore = submissionsDao.getOne(id);
        model.addAttribute("submission", submissionToScore);
        model.addAttribute("score", new Score());
        return "scores/scores";
    }

    @PostMapping("/submissions/{id}/score")
    public String addScore(@ModelAttribute Score score, Model model, @ModelAttribute Submission submission) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        score.setJudge(currentUser);
        score.setSubmission(submission);
        model.addAttribute("score", score);
        model.addAttribute("submission", submission);

        score.setTotalScore(score.getAnalysis() + score.getCoding() + score.getCreativity() + score.getDataManagement() + score.getProfessionalism() + score.getVisualization());

        scoresDao.save(score);

        return "redirect:/dashboard";

    }


}
