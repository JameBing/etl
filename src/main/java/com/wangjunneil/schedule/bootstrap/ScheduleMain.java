package com.wangjunneil.schedule.bootstrap;


import org.apache.log4j.Logger;
import org.eclipse.jetty.security.HashLoginService;
import org.eclipse.jetty.server.*;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.webapp.WebAppContext;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


/**
 *
 * Created by wangjun on 7/23/16.
 */
public class ScheduleMain {

    private static Logger log = Logger.getLogger(ScheduleMain.class.getName());

    private static final Properties props = new Properties();

    public ScheduleMain() {
        try {
            props.load(this.getClass().getClassLoader().getResourceAsStream("server.properties"));
            log.info("load configuration file server.properties");
        } catch (IOException e) {
            throw new ExceptionInInitializerError("");
        }
    }

    public void start() {
        startHook();
        startJetty();
        registerShutdownHook();

        //tmall listener
        // TmallMessageListener.startListener();
    }

    public void stop() {
        new HookSocket().stop();
    }

    private void startHook() {
        new Thread(new HookSocket()).start();

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                log.info("shutdown server");
            }
        });
    }

    private void startJetty() {
        QueuedThreadPool threadPool = getThreadPool();
        Server server = new Server(threadPool);
        server.setDumpAfterStart(false);
        server.setDumpBeforeStop(false);
        server.setStopAtShutdown(true);
        ServerConnector http = getServerConnector(server);
        server.addConnector(http);

        WebAppContext root = new WebAppContext();
        root.setContextPath("/");
        root.setResourceBase(getScheduleHome() + "/server");
        root.setDescriptor(getScheduleHome() + "/server/WEB-INF/web.xml");
        root.setParentLoaderPriority(true);
        server.setHandler(root);

        authorizeConfig(server);
        requestLogConfig(server);

        try {
            server.join();
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private QueuedThreadPool getThreadPool() throws NumberFormatException {
        int min = Integer.parseInt(props.getProperty("server.threads.min"));
        int max = Integer.parseInt(props.getProperty("server.threads.max"));
        int idleTimeout = Integer.parseInt(props.getProperty("server.threads.idleTimeout"));
        int stopTimeout = Integer.parseInt(props.getProperty("server.threads.stopTimeout"));
        boolean detailedDump = Boolean.parseBoolean(props.getProperty("server.threads.enable.detailedDump"));

        QueuedThreadPool threadPool = new QueuedThreadPool();
        threadPool.setName("HTTP-SERVER-THREAD");
        threadPool.setMinThreads(min);
        threadPool.setMaxThreads(max);
        threadPool.setIdleTimeout(idleTimeout);
        threadPool.setStopTimeout(stopTimeout);
        threadPool.setDetailedDump(detailedDump);
        return threadPool;
    }

    private ServerConnector getServerConnector(Server server) {
        int port = Integer.parseInt(props.getProperty("server.http.port"));
        int idleTimeout = Integer.parseInt(props.getProperty("server.http.idleTimeout"));

        HttpConfiguration http_config = new HttpConfiguration();
        http_config.setSecureScheme("https");
        http_config.setSecurePort(8443);
        http_config.setOutputBufferSize(32768);
        http_config.setRequestHeaderSize(8192);
        http_config.setResponseHeaderSize(8192);
        http_config.setSendServerVersion(true);
        http_config.setSendDateHeader(false);
        ServerConnector http = new ServerConnector(server, new HttpConnectionFactory(http_config));
        http.setPort(port);
        http.setIdleTimeout(idleTimeout);
        return http;
    }

    private void authorizeConfig(Server server) {
        HashLoginService loginService = new HashLoginService("JCGRealm");
        String secretFile = System.getProperty("secretFile");
        secretFile = secretFile == null ? getScheduleHome() + "/conf/authorize.properties" : secretFile;
        loginService.setConfig(secretFile);
        loginService.setHotReload(false);
        server.addBean(loginService);
    }

    public void requestLogConfig(Server server) {
        Boolean enableRequestLog = false;
        String flag = System.getProperty("enableRequestLog");
        if (System.getProperty("enableRequestLog") == null) {
            enableRequestLog = Boolean.parseBoolean(props.getProperty("server.enable.requestLog"));
        } else {
            enableRequestLog = Boolean.parseBoolean(flag);
        }

        if (enableRequestLog) {
            NCSARequestLog requestLog = new NCSARequestLog(getScheduleHome() + "/logs/request_yyyy_mm_dd.log");
            requestLog.setAppend(true);
            requestLog.setExtended(false);
            requestLog.setLogTimeZone("GMT");
            requestLog.setLogLatency(true);
            server.setRequestLog(requestLog);
        }
    }

    private void registerShutdownHook() {
//        Signal.handle(new Signal("INT"), new SignalHandler() {
//            @Override
//            public void handle(Signal signal) {
//                log.info("name=" + signal.getName() + ", number=" + signal.getNumber());
//            }
//        });
//        Signal.handle(new Signal("TERM"), new SignalHandler() {
//            @Override
//            public void handle(Signal signal) {
//                log.info("name=" + signal.getName() + ", number=" + signal.getNumber());
//            }
//        });
//        Signal.handle(new Signal("HUP"), new SignalHandler() {
//            @Override
//            public void handle(Signal signal) {
//                log.info("name=" + signal.getName() + ", number=" + signal.getNumber());
//            }
//        });
    }

    private void process(String args[]) throws Exception {
        String command = "start";
        if (args.length > 1)
            command = args[args.length - 1];

        if ("start".equals(command)) {
            start();
        } else if ("stop".equals(command)) {
            stop();
        }
    }

    private String getScheduleHome() {
        String scheduleHome = System.getProperty("SCHEDULE.HOME");
        if (scheduleHome == null || "".equals(scheduleHome)) {
            scheduleHome = System.getProperty("user.dir") + "/schedule-center/src/main";
            System.setProperty("SCHEDULE.HOME", scheduleHome);
        }
        return scheduleHome;
    }

    public static void main(String[] args) {
        try {
            (new ScheduleMain()).process(args);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

}
