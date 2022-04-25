package com.mongo.controller;

import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.mongo.documents.Student;
import com.mongo.dto.StudentDto;
import com.mongo.service.StudentService;
import com.mongo.validations.StudentValidations;

@RestController
@RestControllerAdvice
public class StudentController {

	Logger log = LoggerFactory.getLogger(StudentController.class);

	@Autowired
	private StudentService service;

	private ModelMapper mapper;

	@Autowired
	private StudentValidations studentValidations;

	@PostMapping("/save")
	public ResponseEntity<Student> createStudent(@Valid @RequestBody StudentDto studentDto) {
		//studentValidations.validateSaveStudent(studentDto);
		mapper = new ModelMapper();
		Student student = mapper.map(studentDto, Student.class);
		return ResponseEntity.ok(service.saveStudent(student));
	}

	@PostMapping("/saveAll")
	public List<Student> createStudent(@RequestBody List<StudentDto> studentDto) {
		//studentValidations.validateListOfStudents(studentDto);
		mapper = new ModelMapper();
		List<Student> student = mapper.map(studentDto, new TypeToken<List<Student>>() {
		}.getType());
		return service.saveStudents(student);
	}

	@DeleteMapping("/delete")
	public String deleteAll() {
		return service.deleteAllStudents();
	}

	@DeleteMapping("/delete/{name}/{names}")
	public String deleteName(@PathVariable String name, @PathVariable String names) {
		return service.deleteStudentByName(name, names);
	}

	@GetMapping("/findByName/{name}")
	public Student findByStudentName(@PathVariable String name) {
		return service.findByStudentName(name);
	}

	@PutMapping("/update")
	public Student updateStudent(@RequestBody StudentDto studentDto) {
		mapper = new ModelMapper();
		Student student = mapper.map(studentDto, Student.class);
		return service.upateStudent(student);
	}

	@GetMapping("/findAll")
	public List<Student> findAll() {
		return service.findAllStudents();
	}

	@PutMapping("/updateByName/{name}/{email}")
	public long updateStudentEmail(@PathVariable String name, @PathVariable String email) {
		return service.updateStudentByName(name, email);
	}

	@GetMapping("/find/{name}/{email}/{departmentName}")
	public List<Student> findAlls(@PathVariable String name, @PathVariable String email,
			@PathVariable String departmentName) {
		return service.findStudentByMongoTemplate(name, email, departmentName);
	}
}
