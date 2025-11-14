# SCM - Supply Chain Management System

## 1. 프로젝트 개요

본 프로젝트는 공급망 관리(SCM)를 위한 백엔드 시스템입니다. Spring Boot를 기반으로 구축되었으며, 공급자, 제품, 구매 주문, 입고 등 SCM의 핵심 도메인을 효율적으로 관리하는 것을 목표로 합니다.

주요 기능으로는 마스터 데이터 관리, 엑셀 파일을 통한 대용량 데이터 비동기 가져오기, AI 챗봇을 통한 데이터 분석 및 리포팅 등이 포함됩니다.

## 2. 기술 스택

- **언어**: Java 21
- **프레임워크**: Spring Boot 3.x
- **데이터베이스**: MySQL, H2 (테스트용)
- **데이터 액세스**: Spring Data JPA (Hibernate)
- **API 문서화**: SpringDoc (Swagger UI)
- **빌드 도구**: Gradle
- **라이브러리**:
  - Lombok: 보일러플레이트 코드 감소
  - Apache POI: 엑셀 파일 파싱
  - Spring Web, Spring Validation 등

## 3. 아키텍처

### 3.1. 모듈형 패키지 구조

본 프로젝트는 전통적인 계층형 아키텍처를 따르면서, **기능(Feature) 중심의 모듈형 패키지 구조**를 채택하여 코드의 응집도를 높이고 결합도를 낮추었습니다.

```
com.dw.scm
├── config            # ⚙️ Swagger 등 전역 설정
├── common            # 📦 전역 공통 코드 (BaseEntity, ApiResponse, GlobalExceptionHandler 등)
│   └── exception
├── aichat            # 🤖 AI 챗봇 관련 기능 모듈
│   ├── entity
│   └── repository
├── dataimport        # 📥 데이터 가져오기(Excel 등) 공통 기능 모듈
│   ├── entity
│   ├── exception
│   ├── repository
│   └── service
├── product           # 🏭 제품 마스터 관리 모듈
│   ├── entity
│   └── repository
├── production        # 🛠️ 생산 사이클 관리 모듈
│   ├── entity
│   └── repository
├── purchaseorder     # 🛒 구매 주문 관리 모듈
│   ├── entity
│   └── repository
├── receipt           # 🚚 입고 관리 모듈
│   ├── entity
│   └── repository
└── supplier          # 👤 공급자 관리 모듈
    ├── controller
    ├── entity
    ├── repository
    └── service
        ├── parser
        └── validator
```

- **`{module-name}`**: 각 기능 도메인을 나타내는 최상위 패키지입니다. (예: `supplier`, `product`)
- **`controller`**: API 엔드포인트를 정의하고 HTTP 요청을 처리합니다.
- **`service`**: 해당 모듈의 핵심 비즈니스 로직을 수행합니다.
- **`repository`**: Spring Data JPA를 사용하여 데이터베이스 액세스를 담당합니다.
- **`entity`**: 해당 모듈의 핵심인 JPA Entity, 도메인 Enum 등을 포함합니다.
- **`parser`, `validator`**: 특정 서비스 내에서 복잡한 책임을 분리하기 위한 하위 패키지입니다.

### 3.2. 표준 API 응답

모든 API 응답은 `common/ApiResponse.java` 래퍼 클래스를 통해 일관된 형식으로 제공됩니다.

- **성공 시**:
  ```json
  {
      "success": true,
      "data": { ... }
  }
  ```
- **실패 시**:
  ```json
  {
      "success": false,
      "error": {
          "status": 400,
          "code": "C002",
          "message": "유효하지 않은 입력 값입니다."
      }
  }
  ```

### 3.3. 전역 예외 처리

`common/exception/GlobalExceptionHandler.java`가 `@RestControllerAdvice`를 사용하여 애플리케이션 전역의 예외를 처리합니다. `BusinessException`, `MethodArgumentNotValidException` 등 다양한 예외를 처리하여 일관된 `ApiResponse`를 반환합니다.

## 4. API 문서 (Swagger)

`springdoc-openapi` 라이브러리를 사용하여 API 문서를 자동으로 생성하고 시각화합니다.

- **설정**: `config/SwaggerConfig.java`에서 `OpenAPI` Bean을 직접 설정하여 API 정보, 서버 정보, JWT 인증 방식 등을 중앙에서 관리합니다.
- **인증**: Swagger UI 우측 상단의 'Authorize' 버튼을 통해 JWT 토큰을 등록하면, 인증이 필요한 API를 간편하게 테스트할 수 있습니다.
- **접속 URL**:
  - **Swagger UI**: `http://localhost:8080/swagger-ui.html`
  - **API Docs (JSON)**: `http://localhost:8080/v3/api-docs`

## 5. 주요 기능

### 5.1. 공급자 데이터 비동기 가져오기
- **API**: `POST /api/v1/suppliers/import`
- **설명**: 엑셀 파일을 업로드하면, 시스템은 요청을 즉시 반환하고 백그라운드에서 비동기적으로 데이터를 처리합니다.
- **프로세스**:
  1. **파일 업로드 및 작업 생성**: 사용자가 파일을 업로드하면 `ImportJob`이 생성되고 작업 ID가 반환됩니다.
  2. **파싱**: (비동기) Apache POI를 사용하여 엑셀 데이터를 `StgSupplier` 스테이징 테이블로 파싱합니다.
  3. **검증**: (비동기) `SupplierValidator`를 통해 데이터의 정합성(필수값, 형식 등)을 검증합니다.
  4. **데이터 반영**: (비동기) 검증을 통과한 데이터를 실제 `Supplier` 마스터 테이블에 반영(UPSERT)합니다.
  5. **결과 기록**: 각 단계의 결과와 최종 성공/실패 여부는 `ImportJob` 테이블에 기록됩니다.

### 5.2. 핵심 도메인 관리
- **공급자 (`Supplier`)**: 공급자 정보 관리
- **제품 (`Product`)**: 원자재, 완제품 등 제품 정보 관리
- **구매 주문 (`PurchaseOrder`, `PurchaseOrderLine`)**: 구매 주문 생성 및 라인별 상세 관리
- **입고 (`Receipt`, `ReceiptLine`)**: 구매 주문에 따른 입고 처리

### 5.3. AI 챗봇 (기반 마련)
- AI 챗봇과의 대화 세션(`AiChatSession`), 메시지(`AiChatMessage`), 첨부파일(`AiChatAttachment`)을 관리하기 위한 도메인 모델이 준비되어 있습니다.

## 6. 실행 방법

### 6.1. 사전 요구사항
- JDK 21
- Gradle 8.x
- MySQL 데이터베이스

### 6.2. 데이터베이스 설정
1. MySQL에 `scm` 스키마를 생성합니다.
   ```sql
   CREATE DATABASE scm CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
   ```
2. `src/main/resources/application.yaml` 파일의 `datasource` 설정을 확인합니다.
3. 데이터베이스 접속 정보(`username`, `password`)는 보안을 위해 환경 변수 또는 외부 설정 파일을 통해 주입하는 것을 권장합니다.

### 6.3. 빌드 및 실행
프로젝트 루트 디렉토리에서 다음 명령어를 실행합니다.
```bash
# Gradle을 사용하여 애플리케이션 빌드 및 실행
./gradlew bootRun
```
애플리케이션이 시작되면 `localhost:8080`에서 실행됩니다. API 문서는 `http://localhost:8080/swagger-ui.html`에서 확인할 수 있습니다.
```