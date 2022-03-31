package com.smallhua.org.export.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.smallhua.org.export.enums.ExcelPageEnum;
import com.smallhua.org.export.function.MybatisPlusPageQueryService;

/**
 * 〈导出excel工具类〉
 *
 */
public class EasyExcelUtils extends EasyExcel {

    /**
     * @description: 分页查询、分批次写数据，避免导出大数据量时OOM
     * auto close resource
     * @author: tanpeng
     * @date : 2020-02-28 11:42
     * @version: v1.0.0
     * @param excelWriter
     * @param sheetName
     * @param totalCount 数据总数
     * @param pageQueryService
     */
    public static void pageWrite(ExcelWriter excelWriter,
                                 String sheetName,
                                 long totalCount,
                                 MybatisPlusPageQueryService pageQueryService) {

        // default export xlsx, page size 10000, sheet max row 1000000
        int pageSize = ExcelPageEnum.XLSX.getPageSize();
        int sheetMaxRow = ExcelPageEnum.XLSX.getSheetMaxRow();

        ExcelTypeEnum excelType = excelWriter.writeContext().writeWorkbookHolder().getExcelType();
        boolean isXls =  excelType != null && ExcelTypeEnum.XLS.getValue().equals(excelType.getValue());
        if (isXls) {
            pageSize = ExcelPageEnum.XLS.getPageSize();
            sheetMaxRow = ExcelPageEnum.XLS.getSheetMaxRow();
        }

        // compute page count, sheet count
        long pageCount = (totalCount - 1) / pageSize + 1;
        long sheetCount = (totalCount - 1) / sheetMaxRow + 1;
        int currentPage = 0;

        // page write data
        WriteSheet sheet = null;
        for (int i = 0; i < sheetCount; i++) {
            sheet = EasyExcel.writerSheet(i, sheetName + i).build();
            for (int j = 0; j < (sheetMaxRow / pageSize); j++) {
                // must use ++currentPage, mybatis-plus page query current page start 1
                excelWriter.write(pageQueryService.data(++currentPage, pageSize), sheet);
                if (currentPage >= pageCount) {
                    break;
                }
            }
        }

        // close source
        excelWriter.finish();
    }

}