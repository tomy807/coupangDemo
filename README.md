# 쿠팡 클론 API 설계

## 프로젝트 구성
### SERVER
1. spec : t2.micro(AWS)
2. OS: Ubuntu 20.04
3. IP: 13.124.22.35
4. Domain: myfirstsite1104.shop
5. Backend-Language: Spring
6. Webserver: nginx

### DB
1. spec: MySQL Community(RDS)
2. version: 8.0.27

## 프로젝트 설치 과정
### 1. AWS EC2 인스턴스 생성
1. AMI 선택 → Ubuntu 20.04(프리티어 사용 가능 선택)
2. 인스턴스 유형 선택 → 프리티어 사용 가능으로 선택
3. 인스턴스 구성 → default 값으로
4. 스토리지 추가 → default 값으로
5. 태그 추가 → 넘어가도 ok
6. 보안 그룹 구성 → default 값으로 (SSH)
7. 키페어 생성 → 키페어로 내가 만든 인스턴스 접속
8. 원격으로 작동시키기 위해 MobaXterm과 연결

### 2. RDS 인스턴스 생성
서버에 과부하를 줄이기 위해 서버와 분리하여 디비 구축
1. AWS RDS 서비스 선택 후 데이터 베이스 생성 클릭
2. 표준 생성
3. MySQL 8.0.27 선택
4. 프리티어 선택
5. 마스터 이름, 마스터 암호 작성
6. VPC 보안그룹 → 새로 생성
7. 데이터 베이스 인증 - >암호 인증
8. 데이터 베이스 생성
9. 원격으로 작동시키기 위해 DataGrip과 연결

### 3. 서버에 Nginx,PHP 설치
    nginx 설치
    sudo apt update
    sudo apt install curl gnupg2 ca-certificates lsb-release
    sudo apt install nginx
    sudo apt install php-fpm php-mysql
    sudo service nginx start

### 4. Domain 구입,적용
1. DNS 설정
    1. 타입: A 호스트: www 값/위치: {EC2 공개Ip}
    2. 타입: A 호스트: @ 값/위치: {EC2 공개Ip}
    3. 타입: CNAME	호스트: dev 값/위치: myfirstsite1104.shop.
    4. 타입: CNAME	호스트: prod 값/위치: myfirstsite1104.shop.
2. https 설정

공개ip로 접속시 도메인으로 연결, 80포트(Http)로 연결시 443포트(Https)로 접속

/etc/nginx/sites-available/default 설정

    server {
        listen 443 ssl default_server;
        listen [::]:443 ssl default_server;
            
        ssl_certificate /etc/letsencrypt/live/www.myfirstsite1104.shop/fullchain.pem;
        ssl_certificate_key /etc/letsencrypt/live/www.myfirstsite1104.shop/privkey.pem;
            
        ssl_certificate /etc/letsencrypt/live/myfirstsite1104.shop/fullchain.pem;
        ssl_certificate_key /etc/letsencrypt/live/myfirstsite1104.shop/privkey.pem;
        
        root /var/www/api-server-spring-boot-final;
        
        index index.html index.htm index.nginx-debian.html;

        server_name myfirstsite1104.shop www.myfirstsite1104.shop;

        location / {
            proxy_pass http://127.0.0.1:9000;
        }
    }

    server {
        listen 80;
        listen [::]:80;

        server_name 13.124.22.35;

        root /var/www/html;
        index index.html;

        location / {
                return 301 https://www.myfirstsite1104.shop$request_uri;
        }
    }

    server {
        root /var/www/html/prod;
        index index.php index.html index.htm index.nginx-debian.html;
    
        server_name prod.myfirstsite1104.shop;
    
        location / {
                try_files $uri $uri/ =404;
        }

        listen 443 ssl; # managed by Certbot
        ssl_certificate /etc/letsencrypt/live/prod.myfirstsite1104.shop/fullchain.pem; # managed by Certbot
        ssl_certificate_key /etc/letsencrypt/live/prod.myfirstsite1104.shop/privkey.pem; # managed by Certbot
        include /etc/letsencrypt/options-ssl-nginx.conf; # managed by Certbot
        ssl_dhparam /etc/letsencrypt/ssl-dhparams.pem; # managed by Certbot
    }

    server {
        root /var/www/html/dev;
        index index.php index.html index.htm index.nginx-debian.html;
        
        server_name dev.myfirstsite1104.shop;

        location / {
                try_files $uri $uri/ =404;
        }

        listen 443 ssl; # managed by Certbot
        ssl_certificate /etc/letsencrypt/live/dev.myfirstsite1104.shop/fullchain.pem; # managed by Certbot
        ssl_certificate_key /etc/letsencrypt/live/dev.myfirstsite1104.shop/privkey.pem; # managed by Certbot
        include /etc/letsencrypt/options-ssl-nginx.conf; # managed by Certbot
        ssl_dhparam /etc/letsencrypt/ssl-dhparams.pem; # managed by Certbot
    }

    server {
        if ($host = prod.myfirstsite1104.shop) {
        return 301 https://$host$request_uri;
        } 

        server_name prod.myfirstsite1104.shop;
        listen 80;
        return 404; # managed by Certbot
    }

    server {
        if ($host = dev.myfirstsite1104.shop) {
        return 301 https://$host$request_uri;
        }



        server_name dev.myfirstsite1104.shop;
        listen 80;
        return 404;
    }   

## 쿠팡 ERD 설계후 API 구현

