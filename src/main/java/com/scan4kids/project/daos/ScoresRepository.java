package com.scan4kids.project.daos;

import com.scan4kids.project.models.Score;
import com.scan4kids.project.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScoresRepository extends JpaRepository<Score, Long> {

    List<Score> findAllByJudge(User judge);

}
