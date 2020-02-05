package org.debugroom.mynavi.sample.cloudformation.backend.domain.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import org.debugroom.mynavi.sample.cloudformation.backend.domain.model.jpa.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
