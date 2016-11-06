package com.wangjunneil.schedule.startup;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * <code>StandardLoader</code>自定义的类加载器,用于加载特定目录或文件到类加载器中
 *
 * @author <a href="mailto:wangjunneil@gmail.com">jun.wang</a>
 */
public class StandardLoader extends URLClassLoader {

    /**
     * 使用自定义URL地址和父类加载器的构造方法
     *
     * @param urls 加载路径
     * @param classLoader 父类加载器
     */
    public StandardLoader(URL[] urls, ClassLoader classLoader) {
        super(urls, classLoader);
    }

    /**
     * 使用url地址的构造方法
     *
     * @param urls 加载路径
     */
    public StandardLoader(URL[] urls) {
        super(urls);
    }

} ///:~
