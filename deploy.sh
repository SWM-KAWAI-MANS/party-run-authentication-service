#! /bin/bash

REPOSITORY=/home/ec2-user/party-run/zip
REPOSITORY_DEPLOYMENT_GROUP_NAME=/home/ec2-user/party-run/$DEPLOYMENT_GROUP_NAME

echo "> 환경변수 불러오기"
source ~/.bashrc

echo "> 기존의 $REPOSITORY_DEPLOYMENT_GROUP_NAME/zip 폴더 삭제"
rm -r $REPOSITORY_DEPLOYMENT_GROUP_NAME

echo "> 설치된 프로젝트 폴더 이동"
mv $REPOSITORY $REPOSITORY_DEPLOYMENT_GROUP_NAME

echo "> 현재 구동 중인 party-run-authentication-service-dev 애플리케이션 pid 확인"
CURRENT_PID=$(pgrep -fl party-run-authentication-service-dev.jar | awk '{print $1}')

if [ -z "$CURRENT_PID" ]; then
  echo "> 현재 구동 중인 애플리케이션이 없으므로 종료하지 않습니다."
else
  echo "> kill -15 $CURRENT_PID"
  sudo kill -15 $CURRENT_PID
  sleep 5
fi

OLD_JAR_NAME=$(ls -tr $REPOSITORY_DEPLOYMENT_GROUP_NAME/zip/build/libs/*.jar | tail -n 1)
JAR_NAME=$REPOSITORY_DEPLOYMENT_GROUP_NAME/zip/build/libs/party-run-authentication-service-dev.jar

mv $OLD_JAR_NAME $JAR_NAME
echo "> JAR Name 변경: $OLD_JAR_NAME >> $JAR_NAME"

echo "> $JAR_NAME 에 실행권한 추가"
chmod +x $JAR_NAME

echo "> $JAR_NAME 실행 -Dspring.profiles.active=dev $JAR_NAME 추가해야함"
nohup java -jar $JAR_NAME > $REPOSITORY_DEPLOYMENT_GROUP_NAME/nohup.out 2>&1 &
