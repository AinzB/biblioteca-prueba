package com.rockettest.biblioetca.service;

import com.rockettest.biblioetca.mapper.AuthorMapper;
import com.rockettest.biblioetca.model.Author;
import com.rockettest.biblioetca.model.AuthorExample;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AuthorService {
  private final AuthorMapper mapper;

  public AuthorService(AuthorMapper mapper) {
    this.mapper = mapper;
  }

  public List<Author> findAll() {
    return mapper.selectByExampleWithBLOBs(new AuthorExample());
  }

  public List<Author> findByName(String name) {
    AuthorExample example = new AuthorExample();
    example.createCriteria().andNameLike("%" + name + "%");
    return mapper.selectByExampleWithBLOBs(example);
  }

  public Author findById(Short id) {
    return mapper.selectByPrimaryKey(id);
  }

  public Author create(Author author) {
    mapper.insert(author);
    return author;
  }

  public void update(Author author) {
    mapper.updateByPrimaryKeySelective(author);
  }

  public void delete(Short id) {
    mapper.deleteByPrimaryKey(id);
  }

  
}
