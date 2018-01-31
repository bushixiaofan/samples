package com.song.samples.concurrent.httpserver;

import com.song.samples.concurrent.ThreadPool.DefaultThreadPool;
import com.song.samples.concurrent.ThreadPool.ThreadPool;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 一个基于线程池技术的简单Web服务器
 */
public class SimpleHttpServer {

    // SimpleHttpServer的根路径
    static String basePath;

    // 服务器监听端口
    static int port = 8080;

    static ServerSocket serverSocket;

    // 处理HttpRequest的线程池
    static ThreadPool<HttpRequestHandler> threadPool = new DefaultThreadPool<HttpRequestHandler>(10);


    public static String getBasePath() {
        return basePath;
    }

    public static void setBasePath(String basePath) {
        if (basePath != null && new File(basePath).exists() && new File(basePath).isDirectory()) {
            SimpleHttpServer.basePath = basePath;
        }
    }

    public static void setPort(int port) {
        if (port > 0) {
            SimpleHttpServer.port = port;
        }
    }

    public static void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        Socket socket = null;
        while ((socket = serverSocket.accept()) != null) {
            // 接收一个客户端socket，生成一个HttpRequestHandler，放入线程池中进行
            threadPool.execute(new HttpRequestHandler(socket));
        }

        serverSocket.close();
    }

    static class HttpRequestHandler implements Runnable {

        // HttpRequest负责处理socket
        private Socket socket;

        public HttpRequestHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            String line = null;
            BufferedReader br = null;
            BufferedReader reader = null;
            PrintWriter out = null;
            InputStream in = null;

            try {
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String header = reader.readLine();
                // 由相对路径计算出绝对路径
                String filePath = basePath + header.split(" ")[1];
                out = new PrintWriter(socket.getOutputStream());

                // 如果请求资源后缀为jpg或者ico，则读取资源并输出
                if (filePath.endsWith("jpg") || filePath.endsWith("ico")) {
                    in = new FileInputStream(filePath);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    int i = 0;
                    while ((i = in.read()) != -1) {
                        baos.write(i);
                    }

                    byte[] array = baos.toByteArray();
                    out.println("HTTP / 1.1 200 OK");
                    out.println("Server: Molly");
                    out.println("Content-TypeL image/jpeg");
                    out.println("Content-Length: " + array.length);
                    out.println("");
                    socket.getOutputStream().write(array, 0, array.length);
                } else {
                    br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
                    out = new PrintWriter(socket.getOutputStream());

                    out.println("HTTP / 1.1 200 OK");
                    out.println("Server: Molly");
                    out.println("Content-TypeL text/html; charset=UTF-8");
                    out.println("");
                    while ((line = br.readLine()) != null) {
                        out.println(line);
                    }
                }

                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                close(br, in, reader, out, socket);
            }
        }
    }

    private static void close(Closeable... closeables) {
        if (closeables != null) {
            for (Closeable closeable : closeables) {
                if (closeable != null) {
                    try {
                        closeable.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
    }

    public static void main(String[] args) throws IOException {
        setBasePath("/Users/songzeqi/workspace/sample_test/src/main/java/com/song/samples/concurrent/httpserver");
        start();
    }
}

