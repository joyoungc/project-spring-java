# project
자바 기반 프로젝트를 위한 샘플 코드

## Config Modules

### redis 설치
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