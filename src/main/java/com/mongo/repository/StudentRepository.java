package com.mongo.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.mongo.documents.Student;

@Repository
public interface StudentRepository extends MongoRepository<Student, String>, StudentCustomRepository {

	public void deleteByName(String name);

	public void deleteByNameAndDepartmentDepartmentName(String name, String names);

	public Student findByName(String name);

	@Query(value = "{}", fields = "{id:0}")
	public List<Student> findAllExcludeId();
}
