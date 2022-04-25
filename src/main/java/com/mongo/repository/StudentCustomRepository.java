package com.mongo.repository;

import java.util.List;

import com.mongo.documents.Student;

public interface StudentCustomRepository {

	public long update(String name,String email);
	public List<Student> findStudentByNameAndEmailAndDepartmentName(String name,String email,String departmentName);
}
