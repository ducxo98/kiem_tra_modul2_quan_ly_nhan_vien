package com.codegym.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "department")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nameDepartment;

    @OneToMany(targetEntity = Staff.class, fetch = FetchType.EAGER, mappedBy = "department")
    private List<Staff>staffList;


    public List<Staff> getStaffList() {
        return staffList;
    }

    public void setStaffList(List<Staff> staffList) {
        this.staffList = staffList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameDepartment() {
        return nameDepartment;
    }

    public void setNameDepartment(String nameDepartment) {
        this.nameDepartment = nameDepartment;
    }

    public Department(String nameDepartment) {
        this.nameDepartment = nameDepartment;
    }

    public Department() {
    }
}
