#!/bin/bash

PRG="$0"

while [ -h "$PRG" ]; do
	ls=`ls -ld "$PRG"`
	link=`expr "$ls" : '.*-> \(.*\)$'`
	if expr "$link" : '/.*' > /dev/null; then
		PRG="$link"
	else
		PRG=`dirname "$PRG"`/"$link"
	fi
done

PRGDIR=`dirname "$PRG"`

[ -z "$SCHEDULE_HOME" ] && SCHEDULE_HOME=`cd "$PRGDIR/.." >/dev/null; pwd`

if [ -r "$SCHEDULE_HOME/bin/env.sh" ] ; then
	. "$SCHEDULE_HOME/bin/env.sh"
fi

if [ -z "$JAVA_HOME" ] ; then
	echo "The JAVA_HOME environment variable is needed to run this program."
	exit 1
fi

if [ -z "$SCHEDULE_TEMP" ] ; then
	SCHEDULE_TEMP="$SCHEDULE_HOME"/temp
fi

if [ -z "$SCHEDULE_CONF" ] ; then
	SCHEDULE_CONF="$SCHEDULE_HOME"/conf
fi

if [ -z "$SCHEDULE_LIBS" ] ; then
	SCHEDULE_LIBS="$SCHEDULE_HOME"/libs
fi

# ------------------------------------------------------------------ function

start_server() {
    cow_say
    exec "$JAVA_HOME/bin/java" $SCHEDULE_OPTS \
    		-DSCHEDULE.HOME="$SCHEDULE_HOME" \
    		-DSCHEDULE.CONF="$SCHEDULE_CONF" \
    		-DSCHEDULE.LIBS="$SCHEDULE_LIBS" \
    		-DSCHEDULE.TEMP="$SCHEDULE_TEMP" \
    		-jar "$SCHEDULE_HOME/bin/run.jar" start
    	sleep 3
    	PID=`ps -ef | grep -i run.jar | grep -v "grep" | awk '{print $2}'`
        echo $PID > "$SCHEDULE_HOME/logs/$PID_FILE"
}

stop_server() {
    exec "$JAVA_HOME/bin/java" \
    		-DSCHEDULE.HOME="$SCHEDULE_HOME" \
    		-DSCHEDULE.CONF="$SCHEDULE_CONF" \
    		-DSCHEDULE.LIBS="$SCHEDULE_LIBS" \
    		-DSCHEDULE.TEMP="$SCHEDULE_TEMP" \
    		-jar "$SCHEDULE_HOME/bin/run.jar" stop
    sleep 3
    rm -rf "$SCHEDULE_HOME/logs/$PID_FILE"
}

debug_server() {
    cow_say
    exec "$JAVA_HOME/bin/java" $SCHEDULE_OPTS $SCHEDULE_DEBUG_OPTS \
    		-DSCHEDULE.HOME="$SCHEDULE_HOME" \
    		-DSCHEDULE.CONF="$SCHEDULE_CONF" \
    		-DSCHEDULE.LIBS="$SCHEDULE_LIBS" \
    		-DSCHEDULE.TEMP="$SCHEDULE_TEMP" \
    		-jar "$SCHEDULE_HOME/bin/run.jar" start
    sleep 3
    PID=`ps -ef | grep -i run.jar | grep -v "grep" | awk '{print $2}'`
    echo $PID > "$SCHEDULE_HOME/logs/$PID_FILE"
}

show_usage() {
    echo "Usage: ctl.sh ( commands .... )"
    echo "commands:"
    echo "  start       start schedule server in daemon"
    echo "  stop        immediate shutdown schedule server"
    echo "  debug       start schedule server in debug mode"
    echo "  help        print help document"
}

cow_say() {
    echo " ___________________ "
    echo "< Fight for freedom >"
    echo " ------------------- "
    echo "       \   ,__,"
    echo "        \  (oo)____"
    echo "           (__)    )\\"
    echo "              ||--|| *"
    echo
    echo "       =[ Schedule center v1.0.0-dev-d5085f6f0d919224f8390bebe2b21d059da82daf]"
    echo "+ -- --=[ See http://www.ziyanfoods.com for more technical sharing.         ]"
    echo "+ -- --=[                Created by ziyan on 2016-0804                   ]"
    echo ""
}

if [ "$1" = "start" ] ; then
    start_server
elif [ "$1" = "stop" ] ; then
    stop_server
elif [ "$1" == "debug" ] ; then
    debug_server
elif [ "$1" == "status" ] ; then
    PID=`ps -ef | grep -i run.jar | grep -v "grep" | awk '{print $2}'`
    if [ ! $PID ] ; then
        echo "schedule server not running."
    else
        echo "schedule server already running."
    fi
elif [ "$1" = "restart" ] ; then
    PID=`ps -ef | grep -i run.jar | grep -v "grep" | awk '{print $2}'`
    if [ ! $PID ] ; then
        echo "schedule server not running."
		start_server
    else
        stop_server
        start_server
    fi
elif [ "$1" = "help" ] ; then
    show_usage
else
    show_usage
fi
