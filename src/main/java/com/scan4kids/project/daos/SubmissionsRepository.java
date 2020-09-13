package com.scan4kids.project.daos;

import com.scan4kids.project.models.Submission;
import com.scan4kids.project.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubmissionsRepository extends JpaRepository<Submission, Long> {

    Submission findSubmissionsByOwner(User owner);

}
