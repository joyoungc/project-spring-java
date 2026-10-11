# project-spring-java

Java와 Spring Boot 기반의 REST API 및 배치 애플리케이션을 위한 보일러플레이트입니다.
Member, Order, Product 예제로 DDD와 헥사고날 아키텍처(Ports and Adapters)를 구성합니다.

## 문서 관리

- 이 문서: 프로젝트 구조, 개발 환경, 빌드·실행·테스트 안내
- [AGENTS.md](AGENTS.md): 에이전트의 코드 변경 및 검증 규칙
- 버전과 의존성의 실제 기준: [build.gradle](build.gradle), 각 모듈의 `build.gradle`,
  [Gradle Wrapper 설정](gradle/wrapper/gradle-wrapper.properties)

버전, 실행 방법, 모듈 구성이 바뀌면 이 문서를 갱신합니다.
AGENTS.md에서는 공통 안내를 반복하지 않고 해당 절을 참조합니다.

## 개발 환경

| 항목 | 현재 구성 |
| --- | --- |
| Java | JDK 21 |
| Spring Boot | 3.5.16 |
| Gradle | Wrapper 8.5 (`./gradlew`) |
| 기본 개발 DB | H2 인메모리 |
| 선택 연동 | MySQL, MongoDB, Redis |

Gradle은 별도로 설치하지 않고 저장소의 Wrapper를 사용합니다.
`java -version`과 `./gradlew --version`에서 JDK 21 사용 여부를 확인합니다.

### IntelliJ IDEA

- Project SDK와 Module SDK를 JDK 21로 설정합니다.
- Gradle 설정에서 저장소의 Wrapper를 사용하고 Gradle JVM도 JDK 21로 지정합니다.
- 빌드와 테스트는 Gradle로 실행하여 코드 생성 및 프로젝트 설정을 동일하게 적용합니다.
- IntelliJ 빌드로 전환한다면 Lombok·MapStruct·QueryDSL 애노테이션 처리와 jOOQ 생성 소스 설정을 확인합니다.

## 프로젝트 구조

| 모듈 | 역할 | 다른 프로젝트 모듈에 대한 의존성 |
| --- | --- | --- |
| `backend-domain` | 도메인 모델, 값 객체, 비즈니스 규칙 및 공통 응답·오류 모델 | 없음 |
| `backend-application` | Input Port / Use Case, Output Port, 애플리케이션 서비스 | domain |
| `backend-infrastructure` | 영속성 및 외부 시스템 연동, DB Entity / Repository | domain, application |
| `backend-api` | REST API를 제공하는 Input Adapter | domain, application, infrastructure |
| `backend-batch` | Spring Batch 작업을 실행하는 Input Adapter | domain, application, infrastructure |

`backend-domain`은 Spring 의존성과 Spring 애노테이션이 없는 POJO 계층입니다.
Lombok과 공통 라이브러리, 테스트 의존성은 사용합니다.

`backend-application`은 다른 프로젝트 모듈 중 domain에만 의존하지만,
Spring Context / Transaction / Spring Data Commons도 사용합니다.
현재 `MemberService`, `ProductService`는 `@Service`로 등록됩니다.
영속성 구현과 외부 시스템 호출은 Output Port를 통해 분리합니다.

### 애플리케이션 구성

- API 진입점: `io.joyoungc.api.ServerApiApplication`
- Batch 진입점: `io.joyoungc.batch.ServerBatchApplication`
- 두 진입점은 `ApplicationConfig`와 `PersistenceConfig`를 `@Import`합니다.
- `ApplicationConfig`는 `io.joyoungc.application.input`을 스캔합니다.
- `PersistenceConfig`는 `io.joyoungc.infrastructure.persistence`를 스캔합니다.
- 영속성 구현은 사용자 정의 stereotype인 `@PersistenceAdapter`를 사용합니다.
- application과 infrastructure는 실행 진입점이 없는 라이브러리 JAR 모듈입니다.

## 빌드와 실행

저장소 루트에서 실행합니다.

```shell
# 전체 모듈 빌드와 테스트
./gradlew build

# 테스트만 실행
./gradlew test

# API 실행: 기본 프로파일에 infra-develop이 포함됨
./gradlew :backend-api:bootRun

# Batch 실행: H2 개발 환경을 명시적으로 활성화
./gradlew :backend-batch:bootRun --args='--spring.profiles.active=infra-develop'

# 실행 가능한 JAR 생성
./gradlew :backend-api:bootJar :backend-batch:bootJar
```

API의 기본 포트는 `9010`이며, Batch 설정의 서버 포트는 `9040`입니다.
생성된 JAR은 각 모듈의 `build/libs`에 위치합니다.
Batch는 `spring.batch.job.enabled=false`로 설정되어 있어 시작만으로 Job을 자동 실행하지 않습니다.

## 데이터베이스와 프로파일

`infra-develop`은 H2 인메모리 DB를 사용하며 `schema.sql`과 `common-data.sql`로 초기화합니다.
API의 `default`, `test` 프로파일 그룹에 포함되어 기본 실행과 API 테스트에 사용됩니다.
기본 H2 환경에는 Docker가 필요하지 않습니다.

| 환경 | 설정 파일 | 참고 |
| --- | --- | --- |
| H2 개발 환경 | `backend-infrastructure/src/main/resources/application-infra-develop.yml` | `jdbc:h2:mem:testdb` |
| Redis 캐시 | `backend-infrastructure/src/main/resources/application-infra-cache.yml` | `infra-cache`, 로컬 Redis `6379` |
| MongoDB | `backend-infrastructure/src/main/resources/application-infra-mongo.yml` | `infra-mongo`, 로컬 MongoDB `27017` |
| Testcontainers MySQL | `backend-infrastructure/src/test/resources/application-testcontainers.yml` | Docker 실행 필요 |

`release` 그룹은 `infra-release`를 참조하지만 해당 환경 설정 파일은 저장소에 없습니다.
배포 환경의 DB 및 연결 설정을 별도로 제공해야 합니다.

### 로컬 MySQL

Docker Desktop 또는 Colima 등 Docker 실행 환경이 준비된 상태에서 실행합니다.

```shell
docker compose -f docker/mysql/docker-compose.yml up -d
```

[docker-compose.yml](docker/mysql/docker-compose.yml)은 MySQL 8.0.38을 사용합니다.
포트는 `3306`, DB는 `app-db`, 개발용 계정은 `user` / `user`입니다.
볼륨 경로 `/Users/joyoung/Data/mysql`은 자신의 환경에 맞게 변경합니다.
MySQL 컨테이너를 시작하는 것만으로 기본 H2 데이터소스가 변경되지는 않습니다.
MySQL을 사용하려면 데이터소스 설정도 변경해야 합니다.

### jOOQ 코드 생성

```shell
./gradlew :backend-infrastructure:generateJooq
```

- 입력: `backend-infrastructure/src/main/resources/sql/schema.sql`
- 출력: `backend-infrastructure/build/generated/jooq/main`
- 현재 생성기는 DDL 파일을 읽으므로 실행 중인 MySQL 서버가 필요하지 않습니다.
- 생성물은 Git에서 제외되며 직접 수정하지 않습니다.

## 테스트

```shell
# 모듈별 테스트
./gradlew :backend-api:test
./gradlew :backend-infrastructure:test
./gradlew :backend-batch:test

# 특정 테스트 클래스
./gradlew :backend-api:test --tests 'io.joyoungc.api.product.ProductControllerTest'

# Infrastructure 테스트에 프로파일 전달
./gradlew :backend-infrastructure:test -Dspring.profiles.active=<profile>
```

| 대상 | 도구와 환경 |
| --- | --- |
| API 통합 테스트 | JUnit 5, REST Assured, 랜덤 포트, `test` 프로파일의 H2 |
| MVC 테스트 | MockMvc |
| 외부 HTTP 연동 테스트 | WireMock으로 요청과 응답 검증 |
| 영속성 테스트 | H2, 필요 시 Testcontainers MySQL 설정 사용 |
| Batch 테스트 | Spring Batch Test |

`UserApiClientTest`는 동적 포트의 WireMock 서버를 사용하므로 외부 API 서버의 가용성에 의존하지 않습니다.
WireMock은 테스트에만 사용하며 실제 애플리케이션의 외부 API 연결은 유지됩니다.
API 테스트는 infrastructure의 공유 `testFixtures`를 사용합니다.

현재 기본 테스트 실행에서는 캐시 조회 테스트, 외부 서버 호출 예제, 조건부 MongoDB 테스트가 건너뛰어집니다.
MongoDB 테스트의 실행 조건은 `spring.profiles.active=mongo`지만 설정 파일 프로파일은 `infra-mongo`여서,
해당 테스트를 활성화하려면 조건과 프로파일 설정을 먼저 맞춰야 합니다.
테스트 결과는 각 모듈의 `build/reports/tests/test/index.html`에서 확인할 수 있습니다.
