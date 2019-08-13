package com.codegym.service;

import com.codegym.model.Department;
import com.codegym.model.Staff;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StaffService {
    Iterable<Staff>findAllByDepart(Department department);
    Page<Staff> findAll(Pageable pageable);
    Page<Staff>finAllByName(String name,Pageable pageable);
    Staff findById(Long id);
    void save(Staff staff);
    void remove(Long id);
}
