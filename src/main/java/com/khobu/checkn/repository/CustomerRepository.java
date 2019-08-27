package com.khobu.checkn.repository;


import com.khobu.checkn.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("SELECT COUNT(c.id) FROM Customer c where c.active = true")
    public Integer getTotalCustomerCount();

}
