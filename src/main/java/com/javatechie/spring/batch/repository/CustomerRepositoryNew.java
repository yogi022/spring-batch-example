package com.javatechie.spring.batch.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javatechie.spring.batch.entity.CustomerNew;

public interface CustomerRepositoryNew  extends JpaRepository<CustomerNew,String> {
}
