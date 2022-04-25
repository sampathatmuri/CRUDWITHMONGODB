package com.mongo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mongo.documents.Student;
import com.mongo.repository.StudentRepository;

@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentRepository repo;

	public StudentServiceImpl(StudentRepository repo) {
		super();
		this.repo = repo;
	}

	public Student saveStudent(Student student) {
		return repo.save(student);
	}

	@Transactional
	public List<Student> saveStudents(List<Student> student) {
		return repo.saveAll(student);
	}

	public String deleteAllStudents() {
		if (repo.findAll().isEmpty()) {
			return "FAILURE";
		}
		repo.deleteAll();
		return "SUCCESS";
	}

	public String deleteStudentByName(String name, String names) {
		if (repo.findByName(name) != null) {
			repo.deleteByNameAndDepartmentDepartmentName(name, names);
			return "SUCCESS";
		}
		return "FAILURE";

	}

	public Student findByStudentName(String name) {
		return repo.findByName(name);
	}

	public Student upateStudent(Student student) {
		return repo.save(student);
	}

	public List<Student> findAllStudents() {

		return repo.findAllExcludeId();
	}

	public long updateStudentByName(String name, String email) {
		return repo.update(name, email);
	}

	public List<Student> findStudentByMongoTemplate(String name, String email, String departmentName) {
		return repo.findStudentByNameAndEmailAndDepartmentName(name, email, departmentName);
	}

}
