package com.hainguyen.carrental.repository;

import com.hainguyen.carrental.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<User, Long> {
}
