package com.sky.service;

import com.sky.entity.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

public interface CategoryService {
    List<Category> list(Integer type);
}
