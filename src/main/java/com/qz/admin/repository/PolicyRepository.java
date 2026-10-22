package com.qz.admin.repository;

import com.qz.admin.entity.Policy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PolicyRepository extends JpaRepository<Policy, String> {
}
