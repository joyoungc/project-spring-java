# server-data

## DB Modules

### mongoDB
설치 
```shell
$ docker run --name local-mongodb -p 27017:27017 -d mongo:4.4
```

프로세스 확인
```shell
$ docker ps
CONTAINER ID   IMAGE                          COMMAND                  CREATED          STATUS                PORTS                                                                      NAMES
82fd6f4cc3b7   mongo:3.1                      "/entrypoint.sh mong…"   59 seconds ago   Up 58 seconds         27017/tcp                                                                  local-mongodb
```

bash 실행
```shell
$ docker exec -it local-mongodb bash
root@82fd6f4cc3b7:/# mongo
```

간단한 명령어들
```shell
// local db를 사용함
> use local

// 데이터베이스에 person컬랙션을 만들고 내용추가
> db.person.save({name: "jeong"})

//person 에 데이터를 추가
> db.person.insert({name: "park"})

//person의 데이터를 변경하기 (park를 kim으로 변경)
> db.person.update({name:"park"}, {name:"kim"})

//person의 데이터를 삭제하기
> db.person.remove({name:"kim"})

//person의 내용을 조회
> db.person.find().pretty()

//컬렉션의 리스트를 조회
> show collections;

//person 컬랙션의 제거
> db.person.drop();
```