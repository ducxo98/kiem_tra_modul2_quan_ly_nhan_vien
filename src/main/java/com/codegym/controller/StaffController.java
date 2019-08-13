package com.codegym.controller;

import com.codegym.model.Department;
import com.codegym.model.Staff;
import com.codegym.model.StaffsForm;
import com.codegym.service.DepartmentService;
import com.codegym.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Controller
@PropertySource("classpath:global_config_app.properties")
public class StaffController {
    @Autowired
    Environment env;
    @Autowired
    private StaffService staffService;
    @Autowired
    private DepartmentService departmentService;
    @ModelAttribute("departments")
    public Iterable<Department>departments(){
        return departmentService.findAll();
    }
    @GetMapping("/staffs")
    public ModelAndView listCustomers(@RequestParam("s") Optional<String>s,@PageableDefault(size=3) Pageable pageable){
        Page<Staff> staffs = staffService.findAll(pageable);
        if (s.isPresent()){
            staffs = staffService.finAllByName(s.get(),pageable);
        }else {
            staffs = staffService.findAll(pageable);
        }

        ModelAndView modelAndView = new ModelAndView("/staff/list");
        modelAndView.addObject("staffs",staffs);
        return modelAndView;
    }
    @GetMapping("/create-staff")
    public ModelAndView showCreateForm(){
        ModelAndView modelAndView = new ModelAndView("/staff/create");
        modelAndView.addObject("staff",new Staff());
        return modelAndView;
    }
    @PostMapping("/create-staff-save")
    public ModelAndView saveStaff(@ModelAttribute("staff") StaffsForm staffsForm){
        // lay ten file
        MultipartFile multipartFile = staffsForm.getAvatar();
        String fileName = multipartFile.getOriginalFilename();
        String fileUpload = env.getProperty("file_upload").toString();

        // luu file len server
        try {
            FileCopyUtils.copy(staffsForm.getAvatar().getBytes(), new File(fileUpload + fileName));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        // tham khảo: https://github.com/codegym-vn/spring-static-resources
        if (fileName.equals(" ")){
            fileName = staffService.findById(staffsForm.getId()).getAvatar();
        }

        // tao doi tuong de luu vao db
        Staff staffObject = new Staff(staffsForm.getId(),staffsForm.getName(),staffsForm.getBirthDate(),
                staffsForm.getAddress(),fileName,staffsForm.getSalary());
        staffObject.setDepartment(staffsForm.getDepartment());
        staffObject.setId(staffsForm.getId());

        staffService.save(staffObject);
        ModelAndView modelAndView = new ModelAndView("/staff/create");
        modelAndView.addObject("staff",new Staff());
        modelAndView.addObject("message","New staff created successfully");
        return modelAndView;
    }
    @GetMapping("/edit-staff/{id}")
    public ModelAndView showEditForm(@PathVariable Long id){
        Staff staff = staffService.findById(id);
        if(staff != null) {
            ModelAndView modelAndView = new ModelAndView("/staff/edit");
            modelAndView.addObject("staff", staff);
            return modelAndView;

        }else {
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }
    }

    @PostMapping("/edit-staff")
    public ModelAndView updateCustomer(@ModelAttribute("staff") Staff staff){
        staffService.save(staff);
        ModelAndView modelAndView = new ModelAndView("/staff/edit");
        modelAndView.addObject("staff", staff);
        modelAndView.addObject("message", "Staff updated successfully");
        return modelAndView;
    }
    @GetMapping("/delete-staff/{id}")
    public ModelAndView showDeleteForm(@PathVariable Long id){
        Staff staff = staffService.findById(id);
        if(staff != null) {
            ModelAndView modelAndView = new ModelAndView("/staff/delete");
            modelAndView.addObject("staff", staff);
            return modelAndView;

        }else {
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }
    }

    @PostMapping("/delete-staff")
    public String deleteCustomer(@ModelAttribute("staff") Staff staff){
        staffService.remove(staff.getId());
        return "redirect:staffs";
    }
}
