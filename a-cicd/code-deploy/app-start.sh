#!/bin/bash

# NGINX 설정에서 서비스 URL을 가져옴
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

# JAR 파일 경로 설정
REPOSITORY=/home/ec2-user/everyonesgarden
JAR_NAME=$(ls -tr $REPOSITORY/*.jar | tail -n 1)
echo "> JAR NAME: $JAR_NAME"

# JAR 파일 실행 권한 추가
chmod +x $JAR_NAME

# API_RUN_SCRIPT 파일 경로 설정
API_RUN_SCRIPT=${REPOSITORY}/api-run.sh

# API_RUN_SCRIPT 파일 생성 및 실행 내용 작성
echo "nohup java -jar -Dspring.profiles.active=${IDLE_PROFILE} ${JAR_NAME} 1> /dev/null 2>&1 &" > ${API_RUN_SCRIPT}
chmod 755 ${API_RUN_SCRIPT}

# API_RUN_SCRIPT 실행
${API_RUN_SCRIPT}

# 헬스체크
echo "> Jar 파일 실행 후 헬스체크"
echo "> IDLE_PORT: $IDLE_PORT"
echo "> curl -s http://localhost:$IDLE_PORT/profile "
sleep 10

for RETRY_COUNT in {1..10}
do
  RESPONSE=$(curl -s http://localhost:${IDLE_PORT}/profile)
  UP_COUNT=$(echo ${RESPONSE} | grep 'prod' | wc -l)
  if [ ${UP_COUNT} -ge 1 ]; then
    echo "> Health check 성공"
    break
  else
    echo "> 헬스체크 실패"
    echo "> 헬스체크: ${RESPONSE}"
  fi

  if [ ${RETRY_COUNT} -eq 10]; then
    echo "> Health check 실패."
    echo "> 엔진엑스에 연결하지 않고 배포를 종료합니다."
    exit 1
  fi

  echo "> 헬스체크 실패. 10초 후 재시도"
  sleep 10
done

# Nginx 포트 전환
echo "> Port 전환"
echo "set \$service_url http://127.0.0.1:${IDLE_PORT};" | sudo tee /etc/nginx/conf.d/service-url.inc
echo "> Nginx Reload"
sudo service nginx reload

# 이전 포트 서비스 종료
echo "이전 포트 죽이기"
KILL_PID=$(lsof -ti tcp:${KILL_PORT})

if [ ! -z "$KILL_PID" ]; then
  echo "> 포트 ${KILL_PORT}에서 실행 중인 서비스 종료"
  kill $KILL_PID
else
  echo "> 포트 ${KILL_PORT}에 실행 중인 서비스가 없습니다."
fi
