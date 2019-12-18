package com.example.store.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.store.entity.Level;
import com.example.store.service.LevelService;
import com.example.store.util.ConstantUtils;
import com.example.store.util.ResultUtils;
import com.example.store.vo.ResultVO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 罗蕾
 * @version 1.0.0
 * @date 2019/11/7 17:48
 * @description
 **/
@Api(tags = "会员等级")
@RestController
@RequestMapping("/level")
public class LevelController {

    @Autowired
    private LevelService levelService;

    /**
     * 修改等级分数
     * @param map
     * @return
     */
    @PutMapping("/updateLevel")
    public ResultVO updateLevel(@RequestBody Map<String,Object> map){
        try {
            for (String key : map.keySet()){
                QueryWrapper<Level> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("level_name",key);
                queryWrapper.eq("valid_flag",ConstantUtils.ACTIVE);
                Level dbLevel = levelService.getOne(queryWrapper);
                if (dbLevel == null){
                    return ResultUtils.failed("未查询到该会员等级");
                }
                dbLevel.setLevelPoint(new BigDecimal((Integer) map.get(key)));
                boolean flag = levelService.saveOrUpdate(dbLevel);
                if (!flag){
                    return ResultUtils.failed("修改失败");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtils.failed("修改失败");
        }
        return ResultUtils.success("修改成功");
    }

    @GetMapping("/queryLevel")
    public ResultVO queryLevel(){
        QueryWrapper<Level> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("valid_flag",ConstantUtils.ACTIVE);
        List<Level> levelList = levelService.list(queryWrapper);
        Map<String,Object> resultMap = new HashMap<>(16);
        for (Level item : levelList){
            Map<String,Object> itemMap = new HashMap<>(8);
            itemMap.put("levelPoint",item.getLevelPoint());
            itemMap.put("discount",item.getDiscount());
            resultMap.put(item.getLevelName(),itemMap);
        }
        return ResultUtils.success("查询成功",resultMap);
    }

}
