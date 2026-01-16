# Build stage
FROM maven:3.9.6-eclipse-temurin-11 AS build
WORKDIR /compiler
COPY . .
RUN mvn -q clean package -DskipTests

# Run stage
FROM eclipse-temurin:11-jre-jammy
WORKDIR /compiler

# 실행에 필요한 패키지 설치
# - bash: entrypoint.sh가 /usr/bin/env bash 를 쓰므로 필요
# - docker.io: 컨테이너 내부에서 docker 커맨드 사용 시 필요(호스트 소켓 마운트 전제)
# - ca-certificates: HTTPS 통신 시 기본 권장
RUN apt-get update \
 && apt-get install -y --no-install-recommends bash docker.io ca-certificates \
 && rm -rf /var/lib/apt/lists/*

# 앱 파일 복사
COPY --from=build /compiler/target/*.jar /compiler/compiler.jar
COPY executions /executions
COPY entrypoint.sh /compiler/entrypoint.sh

# Windows CRLF 방지 + 실행권한 부여
RUN sed -i 's/\r$//' /compiler/entrypoint.sh \
 && chmod +x /compiler/entrypoint.sh

EXPOSE 8082
ENTRYPOINT ["/compiler/entrypoint.sh"]
