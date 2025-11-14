package com.dw.scm.dataimport.service;

import com.dw.scm.dataimport.entity.ImportJob;
import com.dw.scm.dataimport.entity.ImportStatus;
import com.dw.scm.dataimport.entity.StgSupplier;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;

/**
 * 데이터 가져오기 작업(ImportJob)의 생명주기를 관리하는 서비스 인터페이스입니다.
 */
public interface ImportJobService {

    /**
     * 업로드된 파일 정보를 기반으로 새로운 가져오기 작업을 생성하고 초기 상태로 저장합니다.
     *
     * @param file     업로드된 원본 파일
     * @param filePath 서버에 저장된 파일의 경로
     * @return 생성된 ImportJob 엔티티
     */
    ImportJob createNewJob(MultipartFile file, Path filePath);

    /**
     * ID를 사용하여 특정 가져오기 작업을 조회합니다.
     *
     * @param jobId 조회할 작업의 ID
     * @return 조회된 ImportJob 엔티티, 없으면 null
     */
    ImportJob getJob(Long jobId);

    /**
     * 가져오기 작업의 현재 상태를 업데이트합니다. (예: PARSING, VALIDATING 등)
     *
     * @param job    상태를 업데이트할 ImportJob
     * @param status 새로운 상태 값
     */
    void updateStatus(ImportJob job, ImportStatus status);

    /**
     * 데이터 처리(파싱, 검증)가 완료된 후, 전체 행 수, 성공/실패 행 수 등을 계산하여 작업 결과를 업데이트합니다.
     *
     * @param job          결과를 업데이트할 ImportJob
     * @param stgSuppliers 처리된 스테이징 데이터 목록
     */
    void updateJobStatus(ImportJob job, List<StgSupplier> stgSuppliers);

    /**
     * 처리 과정에서 예외가 발생했을 때, 작업을 실패 상태로 변경하고 에러 메시지를 기록합니다.
     *
     * @param job 실패 처리할 ImportJob
     * @param e   발생한 예외
     */
    void failJob(ImportJob job, Exception e);
}
