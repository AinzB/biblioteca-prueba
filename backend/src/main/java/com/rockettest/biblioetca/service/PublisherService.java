package com.rockettest.biblioetca.service;

import com.rockettest.biblioetca.mapper.PublisherMapper;
import com.rockettest.biblioetca.model.Publisher;
import com.rockettest.biblioetca.model.PublisherExample;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PublisherService {
  private final PublisherMapper mapper;

  public PublisherService(PublisherMapper mapper) {
    this.mapper = mapper;
  }

  public List<Publisher> findAll() {
    return mapper.selectByExample(null);
  }

  public List<Publisher> findByName(String name) {
    PublisherExample example = new PublisherExample();
    example.createCriteria().andNameLike("%" + name + "%");
    return mapper.selectByExample(example);
  }

  public Publisher findById(Short id) {
    return mapper.selectByPrimaryKey(id);
  }

  public Publisher create(Publisher pub) {
    mapper.insert(pub);
    return pub;
  }

  public void update(Publisher pub) {
    mapper.updateByPrimaryKeySelective(pub);
  }

  public void delete(Short id) {
    mapper.deleteByPrimaryKey(id);
  }
}
