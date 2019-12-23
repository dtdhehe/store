package com.example.store.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.store.entity.User;
import com.example.store.service.OrderService;
import com.example.store.service.UserService;
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
import java.util.*;

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
    @Autowired
    private UserService userService;

    /**
     * 查询销售金额/会员
     * @param queryType
     * @return
     */
    @GetMapping("/amount")
    public ResultVO queryAmount(@RequestParam("queryType") String queryType){
        //返回对象
        Map<String,Object> resultMap = new HashMap<>(8);
        if (StringUtils.isEmpty(queryType)){
            return ResultUtils.failed("查询类型不能为空");
        }
        //当前销售人员
        User salesUser = (User)SecurityUtils.getSubject().getPrincipal();
        //昨天
        QueryWrapper<Map> yesterdayWrapper = new QueryWrapper<>();
        yesterdayWrapper.eq("valid_flag",ConstantUtils.ACTIVE);
        String yesterday = DateUtils.getYesterday();
        yesterdayWrapper.likeRight("create_time",yesterday);
        if (ConstantUtils.SALES_AMOUNT.equals(queryType)) {
            //查询销售金额
            yesterdayWrapper.eq("sales_id",salesUser.getId());
            resultMap.put("yesterday",orderService.queryAmount(yesterdayWrapper));
        }else if (ConstantUtils.SALES_USER.equals(queryType)){
            //查询会员人数
            yesterdayWrapper.eq("user_type",ConstantUtils.CUSTOMER);
            resultMap.put("yesterday",userService.queryAmount(yesterdayWrapper));
        }
        //今天
        QueryWrapper<Map> todayWrapper = new QueryWrapper<>();
        todayWrapper.eq("valid_flag",ConstantUtils.ACTIVE);
        String today = DateUtils.formatDate(new Date(),"yyyyMMdd");
        todayWrapper.likeRight("create_time",today);
        if (ConstantUtils.SALES_AMOUNT.equals(queryType)) {
            //查询销售金额
            todayWrapper.eq("sales_id",salesUser.getId());
            resultMap.put("today",orderService.queryAmount(todayWrapper));
        }else if (ConstantUtils.SALES_USER.equals(queryType)){
            //查询会员人数
            todayWrapper.eq("user_type",ConstantUtils.CUSTOMER);
            resultMap.put("today",userService.queryAmount(todayWrapper));
        }
        //本周
        QueryWrapper<Map> weekWrapper = new QueryWrapper<>();
        weekWrapper.eq("valid_flag",ConstantUtils.ACTIVE);
        //周一
        String monday = DateUtils.getMonday();
        weekWrapper.ge("SUBSTR(create_time,1,8)",monday);
        //周日
        String sunday = DateUtils.getSunday();
        weekWrapper.le("SUBSTR(create_time,1,8)",sunday);
        if (ConstantUtils.SALES_AMOUNT.equals(queryType)) {
            //查询销售金额
            weekWrapper.eq("sales_id",salesUser.getId());
            resultMap.put("week",orderService.queryAmount(weekWrapper));
        }else if (ConstantUtils.SALES_USER.equals(queryType)){
            //查询会员人数
            weekWrapper.eq("user_type",ConstantUtils.CUSTOMER);
            resultMap.put("week",userService.queryAmount(weekWrapper));
        }
        //本月
        QueryWrapper<Map> monthWrapper = new QueryWrapper<>();
        monthWrapper.eq("valid_flag",ConstantUtils.ACTIVE);
        String firstDay = DateUtils.getFirstDayOfMonth();
        monthWrapper.ge("SUBSTR(create_time,1,8)",firstDay);
        String lastDay = DateUtils.getEndDayOfMonth();
        monthWrapper.le("SUBSTR(create_time,1,8)",lastDay);
        if (ConstantUtils.SALES_AMOUNT.equals(queryType)) {
            //查询销售金额
            monthWrapper.eq("sales_id",salesUser.getId());
            resultMap.put("month",orderService.queryAmount(monthWrapper));
        }else if (ConstantUtils.SALES_USER.equals(queryType)){
            //查询会员人数
            monthWrapper.eq("user_type",ConstantUtils.CUSTOMER);
            resultMap.put("month",userService.queryAmount(monthWrapper));
        }
        return ResultUtils.success("查询成功",resultMap);
    }

    /**
     * 查询折线图
     * @return
     */
    @GetMapping("/echarts")
    public ResultVO queryEcharts(){
        List<Map<String,Object>> resultList = new ArrayList<>();
        String today = DateUtils.formatDate(new Date(),"yyyyMMdd");
        //查最近七天
        for (int i=0;i<7;i++){
            Map<String,Object> resultMap = new HashMap<>(8);
            if (i != 0){
                today = DateUtils.getOffsetDayFromDate(today,-1);
            }
            QueryWrapper<Map> amountWrapper = new QueryWrapper<>();
            amountWrapper.eq("valid_flag",ConstantUtils.ACTIVE);
            amountWrapper.likeRight("create_time",today);
            Map amountMap = orderService.queryAmount(amountWrapper);
            amountWrapper.eq("user_type",ConstantUtils.CUSTOMER);
            Map userMap = userService.queryAmount(amountWrapper);
            resultMap.put("date",DateUtils.convertDateToViewType(today,"date"));
            resultMap.put("amount",(amountMap != null)?amountMap.get("total"):0);
            resultMap.put("num",(amountMap != null)?amountMap.get("num"):0);
            resultMap.put("userNum",userMap.get("total"));
            resultList.add(resultMap);
        }
        return ResultUtils.success("查询成功",resultList);
    }

}
