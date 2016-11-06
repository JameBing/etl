java -cp startup.jar -agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=5005 "com.wangjunneil.schedule.startup.BootStartup"


sudo mvn clean package
