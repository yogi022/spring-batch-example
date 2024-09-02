package com.javatechie.spring.batch.controller;

import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javatechie.spring.batch.entity.Customer;
import com.javatechie.spring.batch.entity.CustomerNew;
import com.javatechie.spring.batch.repository.CustomerRepository;
import com.javatechie.spring.batch.repository.CustomerRepositoryNew;

@RestController
@RequestMapping("/jobs")
public class JobController {

    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private Job job;
    @Autowired
    CustomerRepositoryNew customerRepositoryNew;
    
    @Autowired
    CustomerRepository customerRepository;

    @PostMapping("/importCustomers")
    public void importCsvToDBJob() {
        JobParameters jobParameters = new JobParametersBuilder().addLong("startAt", System.currentTimeMillis()).toJobParameters();
        try {
            jobLauncher.run(job, jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
            e.printStackTrace();
        }
    }
    @PostMapping("/finaltable")
    public void transferStageToFinal() {
    	List <Customer> customer=customerRepository.findAll();
    	for(int i=0; i<customer.size();i++) {
    	CustomerNew customerNew=new CustomerNew();
    	customerNew.setContactNo(customer.get(i).getContactNo());
    	customerNew.setCountry(customer.get(i).getCountry());
    	customerNew.setDob(customer.get(i).getDob());
    	customerNew.setEmail(customer.get(i).getEmail());
    	customerNew.setFirstName(customer.get(i).getFirstName());
    	customerNew.setGender(customer.get(i).getGender());
    	customerNew.setId(customer.get(i).getId());
    	customerNew.setLastName(customer.get(i).getLastName());
    	customerRepositoryNew.save(customerNew);
    	}	
    }
}
