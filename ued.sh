#!/bin/bash

function start() {
        JAVA_OPTS="$JAVA_OPTS -server -Xmx2048m -Xms2048m -XX:PermSize=128M -XX:MaxPermSize=128m"
        JAVA_OPTS="$JAVA_OPTS -verbose:gc -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xloggc:/root/ued/logs/ued.log -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/root/ued/logs/ued.hprof"
        export JAVA_OPTS
        nohup java $JAVA_OPTS -jar /root/ued/build/libs/ued-1.0-SNAPSHOT.jar  --spring.config.location=classpath:/application.properties,file:/root/ued.properties > /dev/null &
}

function stop() {
        process=`ps -ef | grep 'ued' | grep -v "grep"|grep -v "restart"|grep -v "stop"| wc -l`
        if [[ 0 != $process ]];then
                ps -ef | grep 'ued' | grep -v "grep"|grep -v "stop"|grep -v "restart"| awk '{print $2}' | xargs kill -15
        fi
        sleep 10
        if [[ 0 != $process ]];then
                ps -ef | grep 'ued' | grep -v "grep"|grep -v "stop"|grep -v "restart"| awk '{print $2}' | xargs kill -9
        fi
}

function restart() {
        stop
        sleep 1
        start
}

function status() {
        ps -ef | grep 'developer-center' | grep -v grep| awk '{print $2}'

}

function help() {
    echo "$0 |start|stop|restart|status|"
}

if [ "$1" == "" ]; then
    help
elif [ "$1" == "stop" ];then
    stop
elif [ "$1" == "start" ];then
    start
elif [ "$1" == "restart" ];then
    restart
elif [ "$1" == "status" ];then
    status
else
    help
fi