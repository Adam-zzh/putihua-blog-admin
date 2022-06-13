package com.smallhua.org.controller;

import com.alibaba.excel.exception.ExcelAnalysisException;
import com.smallhua.org.common.api.CommonResult;
import com.smallhua.org.common.exception.ImportExcelException;
import com.smallhua.org.excelIO.service.ExcelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Api("excel测试controller")
@RequestMapping("/test/excel")
@Slf4j
public class ExcelTestController {

    @Autowired
    private ExcelService excelService;

    @PostMapping("/importBankFlow")
    @ApiOperation(value = "导入订单", notes = "导入订单")
    public CommonResult importBankFlow( @RequestParam(value = "file",required = true) MultipartFile file){
        try {
            excelService.importExcel(file);
            log.info("[银行回款导入银行流水] companyReceiveBillController.importBankFlow success}");
        }
        catch (ImportExcelException e){
            log.warn("导入校验参数不符",e);
            return  CommonResult.failed( e.getMessage());
        }
        catch (ExcelAnalysisException e){
            log.warn("导入校验参数不符",e);
            return  CommonResult.failed( e.getMessage());
        }
        catch (Exception e){
            log.warn("银行回款导入银行流水异常",e);
            return   CommonResult.failed("系统异常，请联系管理员");
        }

        return CommonResult.success("导入成功");
    }

}