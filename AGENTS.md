# 에이전트 가이드

## 개발 환경

| 항목 | 기준 |
| --- | --- |
| Java | JDK 21 |
| Gradle | Wrapper 8.5 (`./gradlew`) |
| Spring Boot | 3.3.1 |
| 데이터베이스 | MySQL, H2, Redis/Spring Cache |

## 자주 쓰는 명령어

```bash
# 전체 검증 및 테스트
./gradlew build
./gradlew test

# 특정 모듈 검증
./gradlew :backend-api:test
./gradlew :backend-api:build
./gradlew :backend-infrastructure:build

# 단일 테스트 클래스 또는 메서드
./gradlew :backend-api:test --tests 'io.joyoungc.api.product.ProductControllerTest'
./gradlew :backend-api:test --tests '...Class.method'

# 정리 및 실행 가능한 JAR 생성
./gradlew clean
./gradlew :<module>:bootJar

# 애플리케이션 실행
./gradlew :backend-api:bootRun
./gradlew :backend-batch:bootRun
```

## 데이터베이스 및 코드 생성

```bash
# 로컬 MySQL 시작
docker compose -f docker/mysql/docker-compose.yml up -d

# jOOQ 코드 생성
./gradlew :backend-infrastructure:generateJooq
```

| 항목 | 값 |
| --- | --- |
| MySQL 포트 | `3306` |
| 데이터베이스 | `app-db` |
| 사용자 / 비밀번호 | `user` / `user` |
| jOOQ 입력 | `backend-infrastructure/src/main/resources/sql/schema.sql` |
| jOOQ 출력 | `backend-infrastructure/build/generated/jooq/main` (무시되는 생성물) |

테이블을 변경하거나 테이블에 의존하는 코드를 수정할 때는 `generateJooq`를 먼저 실행한다.

## 모듈 구조

```text
backend-domain          순수 도메인 모델, 프로젝트 의존성 없음
    |
    +-- backend-application       domain 의존
    |
    +-- backend-infrastructure    domain, application 의존
    |
    +-- backend-api               domain, application, infrastructure 의존
    |
    +-- backend-batch             domain, application, infrastructure 의존
```

이 프로젝트는 DDD와 헥사고날 아키텍처(Ports and Adapters)를 사용한다.

| 역할 | 위치 |
| --- | --- |
| Domain business rule | `backend-domain` |
| Input port / Use case | `backend-application/src/main/java/io/joyoungc/application/input` |
| Output port / Repository interface | `backend-application/src/main/java/io/joyoungc/application/output` |
| Primary/Input adapter, Controller | `backend-api/src/main/java/io/joyoungc/api` |
| Secondary/Output adapter, Persistence | `backend-infrastructure/src/main/java/io/joyoungc/infrastructure/persistence` |

`backend-domain`은 Spring 의존성과 애노테이션이 없는 순수 POJO 계층으로 유지한다.

## 애플리케이션 wiring

```text
ServerApiApplication
ServerBatchApplication
        |
        +-- @Import ApplicationConfig
        |       +-- scans io.joyoungc.application.input
        |
        +-- @Import PersistenceConfig
                +-- scans io.joyoungc.infrastructure.persistence
```

- API 진입점: `io.joyoungc.api.ServerApiApplication`
- Batch 진입점: `io.joyoungc.batch.ServerBatchApplication`
- 모듈 전체를 대상으로 한 광범위한 컴포넌트 스캔을 가정하지 않는다.
- UseCase 구현에 `@Service`를 임의로 추가하지 말고 현재 input 패키지 스캔 방식을 따른다.
- `@PersistenceAdapter`는 영속성 계층 컴포넌트에 사용하는 사용자 정의 Spring stereotype이다.

## 도메인 및 API 규칙

- 핵심 도메인 객체: `Member`, `Order`, `Product`
- 주요 값 객체/정책: `Address`, `Grade`, `OrderStatus`, `DiscountPolicy`
- 구조화된 오류 처리: `ResponseCode`, `CommonError`
- 전역 예외 처리: `GlobalExceptionHandler`의 `@RestControllerAdvice`
- 표준 응답: 오류 코드가 포함된 `CommonResponse`
- HTTP 매핑: `NOT_FOUND`는 404, 그 외 애플리케이션 예외는 500
- DTO/Entity 변환: 기존 MapStruct 및 Lombok 바인딩 구성 사용
- 메시지: 한국어, 영어, 일본어 다국어 구조와 기존 검증/오류 메시지 키 유지

## 테스트

| 대상 | 실행/도구 | 주의사항 |
| --- | --- | --- |
| API 통합 테스트 | `BaseServerApiIntegrationTest`, JUnit 5, REST Assured | 랜덤 포트, `test` 프로파일, Mongo 자동 설정 제외 |
| API 외부 연동 | WireMock, Instancio | infrastructure의 공유 test fixture 사용 |
| Infrastructure DB | Testcontainers MySQL | Docker 필요, `application-testcontainers.yml` 사용 |
| Infrastructure 외부 연동 | Testcontainers, WireMock | 프로파일을 Gradle 시스템 프로퍼티로 전달 가능 |

API 테스트의 `test` 프로파일은 `infra-develop`을 그룹으로 포함한다. Infrastructure 테스트에서 프로파일을 지정하려면 다음을 사용한다.

```bash
./gradlew :backend-infrastructure:test -Dspring.profiles.active=<profile>
```

Infrastructure의 `testFixtures` 코드를 변경하면 API 모듈의 테스트 컴파일에도 영향을 줄 수 있다.
