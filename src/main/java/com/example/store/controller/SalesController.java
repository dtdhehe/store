package com.example.store.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.store.entity.User;
import com.example.store.service.OrderService;
import com.example.store.util.ConstantUtils;
import com.example.store.util.DateUtils;
import com.example.store.util.ResultUtils;
import com.example.store.vo.ResultVO;
import io.swagger.annotations.Api;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 罗蕾
 * @version 1.0.0
 * @date 2019/11/18 16:22
 * @description
 **/
@Api(tags = "销量")
@RestController
@RequestMapping("/sales")
public class SalesController {

    @Autowired
    private OrderService orderService;

    /**
     * 查询销售金额/会员
     * @param queryMap
     * @return
     */
    @GetMapping("/amount")
    public ResultVO queryAmount(@RequestParam Map<String,Object> queryMap){
        //返回对象
        Map resultMap = new HashMap();
        QueryWrapper<Map> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("valid_flag",ConstantUtils.ACTIVE);
        //当前销售人员
        User salesUser = (User)SecurityUtils.getSubject().getPrincipal();
        String queryType = (String) queryMap.get("queryType");
        if (StringUtils.isEmpty(queryType)){
            return ResultUtils.failed("查询类型不能为空");
        }
        if (ConstantUtils.SALES_AMOUNT.equals(queryType)){
            //查询销售金额
            queryWrapper.eq("sales_id",salesUser.getId());
            String dateType = (String) queryMap.get("dateType");
            if (ConstantUtils.YESTERDAY.equals(dateType)){
                //查询昨天
                String yesterday = DateUtils.getYesterday();
                queryWrapper.likeRight("create_time",yesterday);
            }else if (ConstantUtils.TODAY.equals(dateType)){
                //查询今天
                String today = DateUtils.formatDate(new Date(),"yyyyMMdd");
                queryWrapper.likeRight("create_time",today);
            }else if (ConstantUtils.WEEK.equals(dateType)){
                //查询本周
                //周一
                String monday = DateUtils.getMonday();
                queryWrapper.ge("SUBSTR(create_time,1,8)",monday);
                //周日
                String sunday = DateUtils.getSunday();
                queryWrapper.le("SUBSTR(create_time,1,8)",sunday);
            }else {
                //查询本月
                String firstDay = DateUtils.getFirstDayOfMonth();
                queryWrapper.ge("SUBSTR(create_time,1,8)",firstDay);
                String lastDay = DateUtils.getEndDayOfMonth();
                queryWrapper.le("SUBSTR(create_time,1,8)",lastDay);
            }
            resultMap = orderService.queryAmount(queryWrapper);
        }else if (ConstantUtils.SALES_USER.equals(queryType)){
            //查询会员人数
        }
        return ResultUtils.success("查询成功",resultMap);
    }

}
