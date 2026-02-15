package com.catodev.onechallengeforum.repository;

import com.catodev.onechallengeforum.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
