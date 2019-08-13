package com.codegym.repository;

import com.codegym.model.Department;
import com.codegym.model.Staff;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface StaffRepository extends PagingAndSortingRepository<Staff,Long> {
    Page<Staff>findByName(String name, Pageable pageable);
    Iterable<Staff>findAllByDepartment(Department department);
}
