package com.codegym.service.impl;

import com.codegym.model.Department;
import com.codegym.model.Staff;
import com.codegym.repository.StaffRepository;
import com.codegym.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class StaffServiceImpl implements StaffService {
    @Autowired
    private StaffRepository staffRepository;


    @Override
    public Iterable<Staff> findAllByDepart(Department department) {
        return staffRepository.findAllByDepartment(department);
    }

    @Override
    public Page<Staff> findAll(Pageable pageable) {
        return staffRepository.findAll(pageable);
    }

    @Override
    public Page<Staff> finAllByName(String name, Pageable pageable) {
        return staffRepository.findByName(name,pageable);
    }

    @Override
    public Staff findById(Long id) {
        return staffRepository.findOne(id);
    }

    @Override
    public void save(Staff staff) {
        staffRepository.save(staff);

    }

    @Override
    public void remove(Long id) {
        staffRepository.delete(id);

    }
}
