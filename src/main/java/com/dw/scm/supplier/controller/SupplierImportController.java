package com.dw.scm.supplier.controller;

import com.dw.scm.common.ApiResponse;
import com.dw.scm.dataimport.entity.ImportJob;
import com.dw.scm.supplier.service.SupplierImportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * 공급자 데이터 가져오기 관련 API를 제공하는 컨트롤러입니다.
 */
@Tag(name = "Supplier Import", description = "공급자 데이터 가져오기 API")
@RestController
@RequestMapping("/api/v1/suppliers/import")
@RequiredArgsConstructor
public class SupplierImportController {

    private final SupplierImportService supplierImportService;

    /**
     * 엑셀 파일을 업로드하여 공급자 데이터를 비동기적으로 가져오는 작업을 시작합니다.
     *
     * @param file 엑셀 형식의 공급자 데이터 파일
     * @return 생성된 작업 ID를 포함한 API 응답
     */
    @Operation(summary = "공급자 데이터 엑셀 가져오기", description = "엑셀 파일을 통해 다수의 공급자 데이터를 비동기적으로 시스템에 등록합니다.")
    @PostMapping
    public ResponseEntity<ApiResponse<Map<String, Object>>> importSuppliers(@RequestParam("file") MultipartFile file) {
        // 서비스 레이어에 작업 시작 요청
        ImportJob job = supplierImportService.startImport(file);

        // 즉시 반환할 응답 데이터 생성
        Map<String, Object> responseData = Map.of(
            "message", "공급업체 데이터 가져오기 작업이 시작되었습니다.",
            "jobId", job.getId()
        );

        // 202 Accepted 상태와 함께 성공 응답 반환
        return new ResponseEntity<>(ApiResponse.success(responseData), HttpStatus.ACCEPTED);
    }
}
