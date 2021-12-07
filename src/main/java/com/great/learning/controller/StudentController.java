package com.great.learning.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.great.learning.model.Student;
import com.great.learning.service.*;

@Controller
@RequestMapping("/students")
public class StudentController {

	@Autowired
	private StudentService studService;

	@RequestMapping("/list")
	public String listStudents(Model theModel) {

		System.out.println("Inside the StudentController -> ");

		// get the students from db;
		List<Student> theStudents = studService.findAll();

		// add to the spring model
		theModel.addAttribute("Students", theStudents);

		return "list-students";
	}

	@RequestMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {
		// create model attribute to bind form data.
		Student theStudent = new Student();
		theModel.addAttribute("Student", theStudent);

		return "Student-form";
	}

	@RequestMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("studentId") int theId, Model theModel) {

		// get the Student from the service
		Student theStudent = studService.findById(theId);

		// set Student as a model attribute to pre-populate the form
		theModel.addAttribute("Student", theStudent);

		// send over to our form
		return "Student-form";
	}

	@PostMapping("/save")
	public String saveBook(@RequestParam("id") int id, @RequestParam("name") String name,
			@RequestParam("department") String department, @RequestParam("country") String country) {

		System.out.println("Trying to save with Id :" + id);

		Student theStudent;
		if (id != 0) {
			// Update Operation
			theStudent = studService.findById(id);

			// put updated values to the student object found from database.
			theStudent.setName(name);
			theStudent.setDepartment(department);
			theStudent.setCountry(country);

		} else {
			// Create Operation
			theStudent = new Student(name, department, country);
		}

		studService.save(theStudent);
		return "redirect:/students/list";
	}

	@DeleteMapping("/delete")
	public String delete(@RequestParam("studentId") int theId) {
		// delete the book
		studService.deleteById(theId);
		return "redirect:/students/list";
	}

//	@GetMapping("/search")
//	public String search(@RequestParam("name") String name, @RequestParam("author") String author, Model theModel) {
//
//		if (name.trim().isEmpty() && author.trim().isEmpty())
//			return "redirect:/books/list";
//		else {
//			List<Book> theBooks = bookService.searchBy(author, name);
//			theModel.addAttribute("Books", theBooks);
//			return "list-books";
//		}
//	}
}
