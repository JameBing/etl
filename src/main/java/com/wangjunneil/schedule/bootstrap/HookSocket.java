package com.wangjunneil.schedule.bootstrap;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * Created by wangjun on 7/23/16.
 */
public class HookSocket implements Runnable {

    public HookSocket() {

    }

    @Override
    public void run() {
        Thread.currentThread().setName("Thread-Hook-Server");

        try {
            ServerSocket serverSocket = new ServerSocket(55265);
            Socket socket = null;
            while (true) {
                socket = serverSocket.accept();
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String command = br.readLine();
                if ("stop".equalsIgnoreCase(command) || "bye".equalsIgnoreCase(command) || "quit".equalsIgnoreCase(command))
                    break;
                socket.close();
            }
            socket.close();
            serverSocket.close();
            System.exit(0); // 系统正常关闭
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        try {
            Socket socket = new Socket("localhost", 55265);
            OutputStream os = socket.getOutputStream();
            os.write("stop\n".getBytes());
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
