#!/usr/bin/env bash
set -euo pipefail

profiles=""

addProfile() {
  if [ -z "$profiles" ]; then
    profiles="$1"
  else
    profiles="$profiles,$1"
  fi
}

if [ "${ENABLE_KAFKA_MODE:-}" = "true" ]; then addProfile "kafka"; fi
if [ "${ENABLE_RABBITMQ_MODE:-}" = "true" ]; then addProfile "rabbitmq"; fi
if [ "${LOGSTASH_LOGGING:-}" = "true" ]; then addProfile "logstash"; fi
if [ "${ROLLING_FILE_LOGGING:-}" = "true" ]; then addProfile "rollingFile"; fi

# executions가 있을 때만 이동 (없으면 mv가 실패해서 컨테이너가 바로 죽음)
if [ -d "/executions" ]; then
  rm -rf /compiler/executions
  mv /executions /compiler/executions
fi

echo "Starting the compiler with the following profiles: $profiles"

JAR="/compiler/compiler.jar"

if [ -n "$profiles" ]; then
  exec java -Dspring.profiles.active="$profiles" -jar "$JAR"
else
  exec java -jar "$JAR"
fi
