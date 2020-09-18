package com.java_school.scheduler.repo;

import com.java_school.scheduler.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepo extends JpaRepository<Session, Integer> {
}

