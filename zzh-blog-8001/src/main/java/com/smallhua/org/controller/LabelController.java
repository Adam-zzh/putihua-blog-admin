package com.smallhua.org.controller;

import com.smallhua.org.common.api.CommonResult;
import com.smallhua.org.service.LabelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 〈一句话功能简述〉<br>
 * 〈标签管理〉
 *
 * @author ZZH
 * @create 2021/5/27
 * @since 1.0.0
 */
@RestController
@RequestMapping("labelController")
@Api("标签管理")
public class LabelController {

    @Autowired
    private LabelService labelService;

    @ApiOperation("发布文章-》添加标签级联查询")
    @GetMapping("labels")
    public CommonResult getAllLabels(){
        return labelService.getAllLabels();
    }

    @ApiOperation("文章列表-》右侧面板")
    @GetMapping("rightPanel")
    public CommonResult rightPanel(){
        return labelService.rightPanel();
    }

    @ApiOperation("文章列表-》右侧面板")
    @GetMapping("public/rightPanel")
    public CommonResult publicRightPanel(){
        return labelService.rightPanel();
    }
}