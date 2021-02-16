package com.treelevel.app.training.repository;

import com.treelevel.app.training.model.Program;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgramRepository extends JpaRepository<Program, Long> {}
