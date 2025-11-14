package com.dw.scm.supplier.service.parser;

import com.dw.scm.common.exception.BusinessException;
import com.dw.scm.common.exception.ErrorCode;
import com.dw.scm.dataimport.entity.ImportJob;
import com.dw.scm.dataimport.entity.StgSupplier;
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
public class SupplierExcelParser {

    public List<StgSupplier> parse(String filePath, ImportJob job) {
        List<StgSupplier> stgSuppliers = new ArrayList<>();
        try (InputStream is = Files.newInputStream(Paths.get(filePath))) {
            Workbook workbook = WorkbookFactory.create(is);
            Sheet sheet = workbook.getSheetAt(0); // 첫 번째 시트를 가져옵니다.

            for (int i = 1; i <= sheet.getLastRowNum(); i++) { // 헤더를 제외하고 1행부터 시작
                Row row = sheet.getRow(i);
                if (row == null) continue;

                StgSupplier stgSupplier = new StgSupplier();
                stgSupplier.setImportJob(job);
                stgSupplier.setRowNo(row.getRowNum());
                stgSupplier.setSupplierCode(row.getCell(0) != null ? row.getCell(0).getStringCellValue() : null);
                stgSupplier.setSupplierName(row.getCell(1) != null ? row.getCell(1).getStringCellValue() : null);
                // ... 나머지 필드 파싱
                stgSuppliers.add(stgSupplier);
            }
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.EXCEL_PARSING_FAILED);
        }
        return stgSuppliers;
    }
}
