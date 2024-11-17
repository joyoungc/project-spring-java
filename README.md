# project-spring-java
자바 스프링 기반 프로젝트를 위한 boilerplate 코드

## 1. Application Architecture

### Domain
#### server-domain
- DDD의 그 Domain Layer로 기술에 독립적인 POJO로 개발
- 프로젝트를 위한 공통 Business 코드
- Utility, Extension, Constants, Entity, VO(Enum 포함), Aggregate를 포함하며 프로젝트 도메인의 비즈니스 룰을 정의
- POJO로 구현하기 때문에 Spring의 @Component, @Service 등 사용하지 않음
- gradle에 어떤 dependency도 포함하지 않음 

### Application
- domain의 요소를 사용하여 시스템이 가지는 기능/사례를 정의한 집합
- 구성 요소
    - Input Port (use case)
    - Output Port (port)
    - logging, exception
- 외부에서 시스템을 가동하기 위한 기술이 무엇인지 아무것도 알 필요가 없고 알아서도 안됨
- POJO로 개발하고 다른 영역에서 Spring framework를 사용해 DI를 제어
  - 각 기능(usecase) 들에 대하여 @Service를 사용하여 Service로 정의
- 의존성은 domain 에 대해서만 가짐

### Adapter (input/output)
#### server-infrastructure
- Secondary/Driven Adapters
- Persistence 및 External System
- infrastructure 의 Configuration, 데이터 관련 공통 라이브러리 및 DB Entity, Repository 가 포함됨

#### server-api
- Primary/Driving Adapters (User Interface)
- spring boot 기반 REST API 서버
 
#### server-batch (Input Adapter for worker)
- Primary/Driving Adapters (User Interface)
- spring boot 기반 BATCH Application


## 2. Dev Environment Guide

### Install Eclipse Temurin JDK 21
- https://adoptium.net/installation/
```shell
$ brew install --cask temurin21
```

### Install Docker environment
- Install docker
```shell
$ brew install docker docker-compose
```

- Install colima instead of docker desktop
```shell
$ brew install colima

To start colima now and restart at login:
brew services start colima
Or, if you don't want/need a background service you can just run:
/opt/homebrew/opt/colima/bin/colima start -f
```


### IntelliJ
> ℹ️ Written for IntelliJ IDEA 2023.2.2
#### Setting JDK
- Project Structure > Project Settings > Project
   - SDK -> Set to temurin jdk 21
- Project Structure > Project Settings > Modules
   - Dependencies -> Mudule SDK -> Set to temurin jdk 21
- Settings > Build, Execution, Deployment > Build Tools > Gradle
   - Gradle JVM -> Set to temurin jdk 21

### 로컬에서 개발 시 애플리케이션 실행 속도를 높이는 법
1. Preferences > Build, Execution, Deployment > Build Tools > Gradle
    1. Build and run using :  -> IntelliJ IDEA 로 변경
    2. Run tests using : -> IntelliJ IDEA 로 변경