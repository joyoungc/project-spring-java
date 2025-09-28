# CLAUDE.md

이 파일은 Claude Code (claude.ai/code)가 이 저장소의 코드와 작업할 때 참고할 가이드라인을 제공합니다.

## 개발 명령어

### 빌드 및 테스트
```bash
# 전체 프로젝트 빌드
./gradlew build

# 특정 모듈 빌드
./gradlew :backend-api:build
./gradlew :backend-infrastructure:build

# 테스트 실행
./gradlew test

# 특정 모듈 테스트 실행
./gradlew :backend-api:test

# 클린 빌드
./gradlew clean

# 실행 가능한 JAR 생성
./gradlew bootJar
```

### 데이터베이스 및 jOOQ
```bash
# jOOQ 클래스 생성 (backend-infrastructure 모듈)
./gradlew :backend-infrastructure:generateJooq

# Docker로 로컬 MySQL 시작
cd docker/mysql
docker-compose up -d
```

## 아키텍처 개요

이 프로젝트는 도메인 주도 설계(Domain-Driven Design) 원칙과 함께 **헥사고날 아키텍처(포트 앤 어댑터)**를 구현한 멀티 모듈 Spring Boot 애플리케이션입니다.

### 모듈 구조 및 의존성
```
backend-domain (핵심 도메인, 의존성 없음)
├── backend-application (의존: domain)
├── backend-infrastructure (의존: domain, application)
├── backend-api (의존: domain, application, infrastructure)
└── backend-batch (의존: domain, application, infrastructure)
```

### 주요 아키텍처 패턴

**헥사고날 아키텍처 구현:**
- **Input Ports**: `backend-application/src/main/java/io/joyoungc/application/input/`의 UseCase 인터페이스
- **Output Ports**: `backend-application/src/main/java/io/joyoungc/application/output/`의 Repository 인터페이스
- **Input Adapters**: `backend-api/src/main/java/io/joyoungc/api/`의 Controller
- **Output Adapters**: `backend-infrastructure/src/main/java/io/joyoungc/infrastructure/persistence/`의 Persistence Adapter

**Custom Annotations:**
- `@PersistenceAdapter`: 지속성 계층 컴포넌트를 위한 Custom Spring Stereotype

**예외 처리:**
- `GlobalExceptionHandler`의 `@RestControllerAdvice`를 통한 전역 예외 처리
- 모든 응답은 표준화된 오류 코드를 가진 `CommonResponse` 사용
- 애플리케이션 예외는 적절한 HTTP 상태 코드로 매핑 (NOT_FOUND는 404, 기타는 500)

### Domain Model 구조
- **Entity**: 핵심 비즈니스 객체 (Member, Order, Product)
- **Value Object**: Address, Grade, OrderStatus, DiscountPolicy
- **Enum**: 구조화된 오류 처리를 위한 ResponseCode, CommonError
- **순수 POJO**: Domain 계층에는 Spring Annotation 없음

### 기술 스택
- **프레임워크**: Spring Boot 3.3.1, Java 21
- **지속성**: JPA, QueryDSL, jOOQ, MySQL, H2 (테스트)
- **테스팅**: JUnit 5, TestContainers, WireMock, REST Assured, Instancio
- **매핑**: Lombok 통합과 함께 MapStruct
- **캐싱**: Redis, Spring Cache
- **데이터베이스**: MySQL (운영), H2 (개발/테스트)

### Testing 전략
- **Integration Test**: 데이터베이스 테스트를 위한 TestContainers 사용
- **REST API Test**: API 테스트를 위한 REST Assured
- **Mocking**: 외부 서비스 Mocking을 위한 WireMock
- **Test Data**: 테스트 데이터 생성을 위한 Instancio
- **Test Fixture**: testFixtures 소스 세트의 공유 테스트 유틸리티

### 주요 구현 참고사항
- Service는 직접적인 @Service Annotation이 아닌 UseCase Interface를 구현
- Port/Adapter 분리를 통한 Repository Pattern
- Lombok 바인딩과 함께 DTO/Entity 매핑을 위한 MapStruct
- Custom 오류 메시지를 포함한 포괄적인 Validation
- 다국어 메시지 지원 (한국어, 영어, 일본어)
- SQL Schema 파일로부터 jOOQ 코드 생성