package com.wangjunneil.schedule;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

/**
 * Created by Jame on 2018/2/28.
 */
public class FtpFileList {
    private static final Logger LOG = LoggerFactory.getLogger(FtpFileList.class);

    //ftp服务器地址
    public static  String hostname = "10.10.201.98";
    //ftp服务器端口号默认为21
    public static Integer port = 22;
    //ftp登录账号
    public static String username = "ngpuser";
    //ftp登录密码
    public static String password = "ngpuser";

    public FTPClient ftpClient = null;

    public static void main(String[] args) {
        listFileNames(hostname, port, username, password, "E://james.jpg");
    }

    private static List<String> listFileNames(String host, int port, String username, final String password, String dir) {
        List<String> list = new ArrayList<String>();
        ChannelSftp sftp = null;
        Channel channel = null;
        Session sshSession = null;
        try {
            JSch jsch = new JSch();
            jsch.getSession(username, host, port);
            sshSession = jsch.getSession(username, host, port);
            sshSession.setPassword(password);
            Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking", "no");
            sshSession.setConfig(sshConfig);
            sshSession.connect();
            LOG.debug("Session connected!");
            channel = sshSession.openChannel("sftp");
            channel.connect();
            LOG.debug("Channel connected!");
            sftp = (ChannelSftp) channel;
            Vector<?> vector = sftp.ls(dir);
            for (Object item:vector) {
                ChannelSftp.LsEntry entry = (ChannelSftp.LsEntry) item;
                System.out.println(entry.getFilename());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeChannel(sftp);
            closeChannel(channel);
            closeSession(sshSession);
        }
        return list;
    }

    private static void closeChannel(Channel channel) {
        if (channel != null) {
            if (channel.isConnected()) {
                channel.disconnect();
            }
        }
    }

    private static void closeSession(Session session) {
        if (session != null) {
            if (session.isConnected()) {
                session.disconnect();
            }
        }
    }
}

