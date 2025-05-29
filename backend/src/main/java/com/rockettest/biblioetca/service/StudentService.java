package com.rockettest.biblioetca.service;

import com.rockettest.biblioetca.mapper.StudentMapper;
import com.rockettest.biblioetca.model.Student;
import com.rockettest.biblioetca.model.StudentExample;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StudentService {
  private final StudentMapper mapper;

  public StudentService(StudentMapper mapper) {
    this.mapper = mapper;
  }

  public List<Student> findAll() {
    return mapper.selectByExample(null);
  }

  public List<Student> searchByStudentCode(String studentCode) {
    StudentExample example = new StudentExample();
    example.createCriteria()
           .andStudentCodeLike("%" + studentCode + "%");
    return mapper.selectByExample(example);
  }

  public Student findById(Short id) {
    return mapper.selectByPrimaryKey(id);
  }

  public Student create(Student std) {
    mapper.insert(std);
    return std;
  }

  public void update(Student std) {
    mapper.updateByPrimaryKeySelective(std);
  }

  public void delete(Short id) {
    mapper.deleteByPrimaryKey(id);
  }
}
