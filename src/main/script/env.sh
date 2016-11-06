#!/bin/bash

PID_FILE=schedule.pid

# define JAVA_HOME location
# JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk1.8.0_20.jdk/Contents/Home



SCHEDULE_OPTS="-server -Xmx512m -Xms512m"
SCHEDULE_DEBUG_OPTS="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005"
