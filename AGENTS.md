# 에이전트 가이드

## 안내 문서와 기준

- 작업 전에 [README.md](README.md)의 프로젝트 구조, 개발 환경, 빌드·실행·테스트 안내를 읽는다.
- 버전과 의존성은 루트 및 각 모듈의 `build.gradle`, Gradle Wrapper 설정을 기준으로 확인한다.
- 환경, 명령어, 모듈 구조의 공통 설명은 README.md에서 관리한다.
- 이 파일에는 에이전트의 변경 규칙과 검증 의무를 유지한다.
- 실행 방법이나 프로젝트 구성을 변경하면 README.md도 함께 갱신한다.

## 아키텍처와 Bean 등록

- README.md의 모듈 의존성 방향을 유지한다. 도메인 규칙은 `backend-domain`에 둔다.
- `backend-domain`에 Spring 의존성이나 Spring 애노테이션을 추가하지 않는다.
- Use Case는 Input Port, 영속성 및 외부 시스템 연동은 Output Port와 Adapter로 분리한다.
- 기존 애플리케이션 서비스의 `@Service` 등록은 유지한다.
  새 서비스도 현재 `ApplicationConfig`의 input 패키지 스캔 방식에 맞춘다.
- 모듈 전체를 대상으로 컴포넌트 스캔 범위를 임의로 넓히거나 중복 Bean 등록을 추가하지 않는다.
- 영속성 Adapter는 기존 `@PersistenceAdapter` 방식을 따른다.
- application과 infrastructure는 라이브러리 모듈로 유지한다. 실행용 `bootJar`를 활성화하지 않는다.

## 도메인과 API 변경

- `Member`, `Order`, `Product`의 기존 값 객체와 정책을 재사용한다.
- `ResponseCode`, `CommonError`, `CommonResponse`를 사용하여 기존 응답 형식을 유지한다.
- 예외 처리는 `GlobalExceptionHandler`에 맞춘다.
  유효성 검증 오류는 400, NOT_FOUND 애플리케이션 오류는 404, 그 외 애플리케이션 오류는 500이다.
- DTO / Entity 변환에는 기존 MapStruct 및 Lombok 바인딩 구성을 사용한다.
- 한국어·영어·일본어 메시지 구조와 기존 검증·오류 메시지 키를 유지한다.

## 코드 생성과 의존성

- 테이블 정의 또는 생성된 테이블에 의존하는 코드를 변경하면 먼저 다음을 실행한다.

  ```shell
  ./gradlew :backend-infrastructure:generateJooq
  ```

- jOOQ와 QueryDSL 생성물을 직접 수정하거나 커밋하지 않는다.
- Spring Boot가 관리하는 라이브러리에 버전을 임의로 고정하지 않는다.
  버전 재정의가 필요하면 호환성을 확인하고 이유를 설명한다.

## 검증

- 코드 변경 후 영향을 받는 모듈의 테스트를 실행한다.
- 의존성, 빌드 설정, 여러 모듈에 걸친 변경은 `./gradlew build`로 전체 검증한다.
- infrastructure의 `testFixtures`를 변경하면 API 모듈의 테스트도 검증한다.
- 외부 HTTP 연동의 일반 테스트는 WireMock을 사용하고 실제 공개 API의 가용성에 의존하지 않도록 한다.
- Docker 및 별도 서비스가 필요한 테스트는 README.md의 환경 조건을 확인한다.
- 실패한 테스트를 통과시키기 위해 검증을 삭제하거나 비활성화하지 않는다.
- 실행하지 못했거나 건너뛴 테스트와 그 이유를 결과에 명시한다.
