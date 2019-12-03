package com.example.store.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.store.entity.Supplier;
import com.example.store.mapper.SupplierMapper;
import com.example.store.service.SupplierService;
import org.springframework.stereotype.Service;

/**
 * @author 罗蕾
 * @version 1.0.0
 * @date 2019/11/3 17:27
 * @description
 **/
@Service
public class SupplierServiceImpl extends ServiceImpl<SupplierMapper,Supplier> implements SupplierService {
}
