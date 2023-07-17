## Run with docker
```bash
$ sudo su
$ cd /dooleaf/nginx
$ docker-compose up &
```

## TLS 인증서 갱신
```bash
# 80 port 가 listen 중이면 갱신이 안되므로 docker 로 띄워놓은 nginx container 부터 종료한다.
# 추후 docker + nginx + letsencrypt 환경에서 자동갱신 할수있도록 구성 변경이 필요하다.
$ docker stop nginx
$ docker stop lol-ground

$ sudo certbot renew

$ cd /dooleaf/nginx
$ docker-compose up &
```
