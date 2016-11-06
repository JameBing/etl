package com.wangjunneil.schedule.startup;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * <code>BootStartup</code>为引导启动对象,初始化系统目录结构以及类加载器
 *
 * @author <a href="mailto:wangjunneil@gmail.com">jun.wang</a>
 */
public final class BootStartup {

    /**
     * 后台主程序定义
     */
    private static final String DAEMON_MAIN_CLASS = "com.wangjunneil.schedule.bootstrap.ScheduleMain";

    /**
     * 引导启动对象定义
     */
    private static BootStartup startup = null;

    /**
     * 类加载器定义
     */
    private ClassLoader commonLoader = null;

    /**
     * 后台对象定义
     */
    private Object daemon = null;

    // ------------------------------------------------------------------------------------------------ construct method

    /**
     * 私有构造方法
     */
    private BootStartup() {  }

    // ------------------------------------------------------------------------------------------------ public method

    /**
     * 初始化系统,设置系统目录,创建系统类加载器以及实例化启动类
     *
     * @throws Exception 初始化异常
     */
    public void init() throws Exception {
        initSysConstruct();         // 初始化系统结构
        initSysClassLoaders();      // 初始化类加载器

        // 设置当前主线程类加载器
        Thread.currentThread().setContextClassLoader(commonLoader);

        Class startupClass = commonLoader.loadClass(DAEMON_MAIN_CLASS);
        Object startupInstance = startupClass.newInstance();
        daemon = startupInstance;
    }

    private void initSysConstruct() throws Exception {
        setScheduleHome();
        setScheduleConf();
        setScheduleLibs();
        setScheduleTemp();
    }

    private void setScheduleHome() throws Exception {
        String scheduleHome = System.getProperty("SCHEDULE.HOME");
        if (scheduleHome != null && !"".equals(scheduleHome))
            return;
        File bootstrapJar = new File(System.getProperty("user.dir"), "run.jar");
        if (bootstrapJar.exists())
            System.setProperty("SCHEDULE.HOME", (new File(System.getProperty("user.dir"), "..")).getCanonicalPath());
        else
            System.setProperty("SCHEDULE.HOME", System.getProperty("user.dir"));
    }

    private void setScheduleConf() {
        String scheduleConf = System.getProperty("SCHEDULE.CONF");
        if (scheduleConf != null && !"".equals(scheduleConf))
            return;
        System.setProperty("SCHEDULE.CONF", System.getProperty("SCHEDULE.HOME") + "/conf");
    }

    private void setScheduleLibs() {
        String scheduleLibs = System.getProperty("SCHEDULE.LIBS");
        if (scheduleLibs != null && !"".equals(scheduleLibs))
            return;
        System.setProperty("SCHEDULE.LIBS", System.getProperty("SCHEDULE.HOME") + "/libs");
    }

    private void setScheduleTemp() {
        String scheduleTemp = System.getProperty("SCHEDULE.TEMP");
        if (scheduleTemp == null || "".equals(scheduleTemp)) {
            scheduleTemp = System.getProperty("SCHEDULE.HOME") + "/temp";
        }
        System.setProperty("java.io.tmpdir", scheduleTemp);
    }

    private void initSysClassLoaders() {
        try {
            Set<URL> set = new LinkedHashSet<URL>();
            String scheduleConf = System.getProperty("SCHEDULE.CONF");
            String scheduleLibs = System.getProperty("SCHEDULE.LIBS");

            File conf = new File(scheduleConf);
            if (!conf.exists())
                throw new ExceptionInInitializerError("system can not be found [conf] directory");
            conf = conf.getCanonicalFile();
            set.add(conf.toURI().toURL());

            File libs = new File(scheduleLibs);
            if (!libs.exists())
                throw new ExceptionInInitializerError("system can not be found [libs] directory");
            libs = libs.getCanonicalFile();
            for (String fileName : libs.list()) {
                if (!fileName.endsWith(".jar")) continue;
                File lib = new File(libs, fileName);
                lib = lib.getCanonicalFile();
                set.add(lib.toURI().toURL());
            }

            URL[] array = set.toArray(new URL[set.size()]);
            commonLoader = new StandardLoader(array);
            if( commonLoader == null ) {
                commonLoader = this.getClass().getClassLoader();
            }
        } catch (Exception t) {
            throw new ExceptionInInitializerError("initial system classloader failure");
        }
    }

    /**
     * 启动后台程序
     *
     * @throws Exception 反射异常
     */
    public void start() throws Exception {
        if (daemon == null) init();

        Method method = daemon.getClass().getMethod("start", (Class [] )null);
        method.invoke(daemon, (Object [])null);
    }

    /**
     * 停止后台程序
     *
     * @throws Exception 反射异常
     */
    public void stop() throws Exception {
        Method method = daemon.getClass().getMethod("stop", (Class [] ) null);
        method.invoke(daemon, (Object [] ) null);

    }

    // -------------------------------------------------------------------------------------------------- main method

    /**
     * Main 方法入口
     *
     * @param args 命令行参数被处理
     */
    public static void main(String[] args) {
        if (startup == null) {
            startup = new BootStartup();
            try {
                startup.init();
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        }

        try {
            String command = "start";
            if (args.length > 0) {
                command = args[args.length - 1];
            }

            if ("start".equals(command)) {
                startup.start();
            } else if ("stop".equals(command)) {
                startup.stop();
            } else {
                System.err.println("the command [" + command + "] can not be found.");
            }
        } catch (Throwable th) {
            th.printStackTrace();
            System.exit(1);
        }
    }

}
