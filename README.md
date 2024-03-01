# project-spring-java
자바 스프링 기반 프로젝트를 위한 boilerplate 코드

## 1. Application Architecture

### Application Layer
#### server-api
- spring boot 기반 REST API 서버
#### server-batch
- spring boot 기반 BATCH Application

### Domain Layer
#### server-domain
- 프로젝트를 위한 공통 Business 코드

### Infrastructure Layer
#### server-infrastructure
- 프로젝트를 위한 공통 Configuration
- 데이터 관련 공통 라이브러리 및 Entity, Repository


## 2. Dev Environment Guide

### Install Eclipse Temurin JDK 21
- https://adoptium.net/installation/
```shell
$ brew install --cask temurin21
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