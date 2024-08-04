#!/bin/bash

service_url=$(grep "set \$service_url" /etc/nginx/conf.d/service-url.inc | cut -d ':' -f 3)

# 포트 번호에 따라 IDLE_PROFILE 값을 설정
if [ "$service_url" == "8081;" ]; then
    IDLE_PROFILE=prod2
    IDLE_PORT=8082
    KILL_PORT=8081
elif [ "$service_url" == "8082;" ]; then
    IDLE_PROFILE=prod1
    IDLE_PORT=8081
    KILL_PORT=8082
else
    echo "알 수 없는 포트 번호입니다."
    exit 1
fi
echo "배포 후 실행 될 프로필과 포트: $IDLE_PROFILE, $IDLE_PORT"

REPOSITORY=/home/ec2-user/everyonesgarden
JAR_NAME=$(ls -tr $REPOSITORY/*.jar | tail -n 1)
echo "> JAR NAME: $JAR_NAME"

echo "> $JAR_NAME 에 실행권한 추가"

chmod +x $JAR_NAME

echo "> $JAR_NAME 실행"

nohup java -jar \
-Dspring.profiles.active=$IDLE_PROFILE \
-Dspring.config.location=classpath:application.yml
$JAR_NAME > $REPOSITORY/nohup.out 2>&1 &

echo "> Jar 파일 실행 후 헬스체크"
echo "> IDLE_PORT: $IDLE_PORT"
echo "> curl -s http://localhost:$IDLE_PORT/profile "
sleep 10

for RETRY_COUNT in {1..10}
do
  RESPONSE=$(curl -s http://localhost:${IDLE_PORT}/profile)
  UP_COUNT=$(echo ${RESPONSE} | grep 'prod' | wc -l)
  if [ ${UP_COUNT} -ge 1 ]
  then # $up_count >= 1 ("prod" 문자열이 있는지 검증)
    echo "> Health check 성공"
    break
  else
    echo "헬스체크 실패"
    echo"헬스체크: ${RESPONSE}"
  fi
