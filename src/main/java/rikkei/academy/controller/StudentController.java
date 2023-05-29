package rikkei.academy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import rikkei.academy.model.Student;
import rikkei.academy.service.IStudentService;

import java.util.List;

@Controller
@RequestMapping(value = {"/", "/student"})
public class StudentController {
    @Autowired
    private IStudentService studentService;

    @GetMapping
    public String showListStudent(Model model) {
        List<Student> studentList = studentService.findAll();
        model.addAttribute("listStudent", studentList);
//        model.addAttribute("message", "<button>OK CON GA DEN</button>");
        return "student/list";
    }

    @GetMapping("/{id}")
    public String detailStudent(@PathVariable Long id, Model model) {
        Student student = studentService.findById(id);
        model.addAttribute("student", student);
        return "student/detail";
    }

    @GetMapping("/create")
    public String showFormCreate(Model model) {
        Student student = new Student();
        model.addAttribute("student", student);
        return "student/create";
    }

    //Cách 1 : Dùng @RequestParam
//    @PostMapping("/create")
//    public String actionCreate(@RequestParam("name") String name,
//                               @RequestParam("address") String address,
//                               @RequestParam("age") int age) {
//        Long id = 0L;
//        if(studentService.findAll().size()==0){
//            id = 1L;
//        } else {
//            id = studentService.findAll().get(studentService.findAll().size()-1).getId()+1;
//        }
//        Student student = new Student(id,name,address,age);
//        studentService.save(student);
//        System.out.println("name --->" + name);
//        return "redirect:/";
//    }
    //Cách 2:
    @PostMapping("/create")
    public String actionCreate(Student student) {
        Long id = 0L;
        if (studentService.findAll().size() == 0) {
            id = 1L;
        } else {
            id = studentService.findAll().get(studentService.findAll().size() - 1).getId() + 1;
        }
        student.setId(id);
        studentService.save(student);
        System.out.println("student ----->" + student);
        return "redirect:/";
    }
    @GetMapping("/edit/{id}")
    public String showFormEdit(@PathVariable Long id,Model model){
        Student student = studentService.findById(id);
        model.addAttribute("student",student);
        return "student/edit";
    }
    @GetMapping("/delete/{id}")
    public String showFormDelete(@PathVariable Long id,Model model){
        Student student = studentService.findById(id);
        System.out.println("student tren ___>"+student);
        model.addAttribute("student",student);
        return "student/delete";
    }
    @PostMapping("/edit")
    public String actionEdit(Student student){
        System.out.println("student------>" + student);
        studentService.save(student);
        return "redirect:/";
    }
    @PostMapping("/delete")
    public String actionDelete(Student student){
        System.out.println("student------>" + student);
        studentService.deleteById(student.getId());
        return "redirect:/";
    }
}
