# 공지사항 관리

#### 공지사항을 관리하고 조회/등록 할 수 있는 REST API 서비스입니다.

## 실행 방법
```bash
# 프로젝트 빌드
./gradlew build

# 애플리케이션 실행
./gradlew bootRun
```

## 테스트 실행
```bash
# 전체 테스트 실행
./gradlew test

# API 문서 생성
./gradlew asciidoctor
```
## API 문서
- API 문서는 실행 후 http://localhost:8080/docs/api-guide.html 에서 확인 가능합니다.
- Spring REST Docs를 통해 테스트 기반의 정확한 문서화 제공

### 기술 스택
- Java 17
- Spring Boot
- Spring Data JPA
- Spring REST Docs
- JUnit 5

### 핵심 기능
- 공지사항 CRUD 작업
- 첨부파일 관리
- 조회수 관리
- 기간 설정 및 검증


### 시스템 아키텍처 설명

### 아키텍처 개요

본 시스템은 클린 아키텍처 원칙을 따르며, 계층을 명확히 분리하여 유지보수성과 테스트 용이성을 높였습니다.

### 패키지 구조

#### Adapter Layer (`adapter`)
- **controller**: REST API 엔드포인트 구현
- **payload**: API Request/Response DTO 정의

#### Application Layer (`application`)
- **query**: 유스케이스 요청 데이터 정의
- **command**: 유스케이스 입력 데이터 정의
- **service**: 유스케이스 구현체
- **usecase**: 유스케이스 인터페이스 정의

#### Domain Layer (`domain`)
- **dto**: 계층 간 데이터 전달 객체
- **entity**: 핵심 도메인 모델
- **exception**: 도메인 예외 정의

#### Infrastructure Layer (`infrastructure`)
- **file**: 파일 처리 관련 구현
- **persistence**: 데이터 영속성 처리

