# server-infrastructure
Infrastructure Layer

## TEST
- Default
```shell
./gradlew -p server-infrastructure clean test
```
- TestContainers
```shell
./gradlew -p server-infrastructure clean test -Dspring.profiles.active=testcontainers
```
- mongoDB
```shell
./gradlew -p server-infrastructure clean test -Dspring.profiles.active=mongo
```

### TestContainers 사용을 위한 Colima 설치 및 설정
- 설치 : https://balhae79.tistory.com/389
- TestContainers 설정 : https://balhae79.tistory.com/390


## DB Modules

### mongoDB
- 설치
```shell
$ docker run --name local-mongodb -p 27017:27017 -d mongo:4.4
```

- 프로세스 확인
```shell
$ docker ps
CONTAINER ID   IMAGE                          COMMAND                  CREATED          STATUS                PORTS                                                                      NAMES
82fd6f4cc3b7   mongo:3.1                      "/entrypoint.sh mong…"   59 seconds ago   Up 58 seconds         27017/tcp                                                                  local-mongodb
```

- bash 실행
```shell
$ docker exec -it local-mongodb bash
root@82fd6f4cc3b7:/# mongo
```

- 간단한 명령어들
```shell
# local db를 사용함
> use local

# 데이터베이스에 person컬랙션을 만들고 내용추가
> db.person.save({name: "jeong"})

# person 에 데이터를 추가
> db.person.insert({name: "park"})

# person의 데이터를 변경하기 (park를 kim으로 변경)
> db.person.update({name:"park"}, {name:"kim"})

# person의 데이터를 삭제하기
> db.person.remove({name:"kim"})

# person의 내용을 조회
> db.person.find().pretty()

# 컬렉션의 리스트를 조회
> show collections;

# person 컬랙션의 제거
> db.person.drop();
```

### MySQL
- 설치 및 실행
```shell
$ cd docker/mysql
$ docker-compose up -d
```

## Cache Modules

### 1. redis 설치
```shell
$ docker run --name local-redis -p 6379:6379 -d redis:5.0
```
This command will do the following:

1. Pull the Redis image from the Docker hub (the place where all the public 3rd party images are stored)
2. Create and run the container and name it: local-redis
3. Route port 6379 on my pc to port 6379 inside the container. 6379 is Redis default port and can be changed

We can confirm Redis Docker is running by using docker ps:
```shell
$ docker ps
CONTAINER ID   IMAGE                          COMMAND                  CREATED          STATUS                PORTS                                                                      NAMES
3e350e018fa9   redis:5.0                      "docker-entrypoint.s…"   38 seconds ago   Up 36 seconds         0.0.0.0:6379->6379/tcp, :::6379->6379/tcp                                  my-redis
```

Connect to Redis from inside the container
```shell
$ docker exec -it local-redis sh
#
```

We can execute redis-cli (Redis command-line interface) and do some tests:
```shell
# redis-cli
127.0.0.1:6379> ping
PONG
127.0.0.1:6379> set name Joyounc
OK
127.0.0.1:6379> get name
"Joyounc"
127.0.0.1:6379>
```


