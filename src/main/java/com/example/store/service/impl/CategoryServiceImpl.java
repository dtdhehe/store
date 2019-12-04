package com.example.store.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.store.entity.Category;
import com.example.store.mapper.CategoryMapper;
import com.example.store.service.CategoryService;
import org.springframework.stereotype.Service;

/**
 * @author 罗蕾
 * @version 1.0
 * @date 2019/11/4 22:01
 * @description
 **/
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
}
