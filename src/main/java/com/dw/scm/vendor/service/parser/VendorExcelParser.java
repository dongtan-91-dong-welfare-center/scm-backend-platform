package com.dw.scm.vendor.service.parser;

import com.dw.scm.common.exception.BusinessException;
import com.dw.scm.common.exception.ErrorCode;
import com.dw.scm.dataimport.entity.ImportJob;
import com.dw.scm.dataimport.entity.StgVendor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Component
public class VendorExcelParser {

    public List<StgVendor> parse(String filePath, ImportJob job) {
        List<StgVendor> stgVendors = new ArrayList<>();
        try (InputStream is = Files.newInputStream(Paths.get(filePath))) {
            Workbook workbook = WorkbookFactory.create(is);
            Sheet sheet = workbook.getSheetAt(0); // 첫 번째 시트를 가져옵니다.

            for (int i = 1; i <= sheet.getLastRowNum(); i++) { // 헤더를 제외하고 1행부터 시작
                Row row = sheet.getRow(i);
                if (row == null) continue;

                StgVendor stgVendor = new StgVendor();
                stgVendor.setImportJob(job);
                stgVendor.setRowNo(row.getRowNum());
                stgVendor.setVendorCode(row.getCell(0) != null ? row.getCell(0).getStringCellValue() : null);
                stgVendor.setVendorName(row.getCell(1) != null ? row.getCell(1).getStringCellValue() : null);
                // ... 나머지 필드 파싱
                stgVendors.add(stgVendor);
            }
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.EXCEL_PARSING_FAILED);
        }
        return stgVendors;
    }
}
