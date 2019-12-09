package com.example.store.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.store.entity.Level;
import com.example.store.mapper.LevelMapper;
import com.example.store.service.LevelService;
import org.springframework.stereotype.Service;

/**
 * @author 罗蕾
 * @version 1.0.0
 * @date 2019/11/7 17:47
 * @description
 **/
@Service
public class LevelServiceImpl extends ServiceImpl<LevelMapper,Level> implements LevelService {
}
