package com.mongo.service;

import java.util.List;

import com.mongo.documents.Student;

public interface StudentService {

	public Student saveStudent(Student student);
	
	public List<Student> saveStudents(List<Student> student);
	
	public String deleteAllStudents();
	
	public String deleteStudentByName(String name,String names);
	
	public Student findByStudentName(String name);
	
	public Student upateStudent(Student student);
	
	public List<Student> findAllStudents();
	
	public long updateStudentByName(String name,String email);
	
	public List<Student> findStudentByMongoTemplate(String name,String email,String departmentName);

}
