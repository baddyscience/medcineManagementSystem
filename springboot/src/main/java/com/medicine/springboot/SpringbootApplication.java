package com.medicine.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.awt.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;

@SpringBootApplication
public class SpringbootApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(SpringbootApplication.class);

        // 添加监听器监听环境准备事件
        application.addListeners(new ApplicationListener<ApplicationEnvironmentPreparedEvent>() {
            @Override
            public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
                ConfigurableEnvironment environment = event.getEnvironment();
                int port = environment.getProperty("server.port", Integer.class, 8080);
                port = findAvailablePort(port);
                System.out.println("Using port: " + port);

                // 动态设置端口
                environment.getPropertySources().addFirst(new MapPropertySource("customPort",
                        Collections.singletonMap("server.port", port)));
            }
        });

        // 添加监听器监听应用准备就绪事件
        application.addListeners(new ApplicationListener<ApplicationReadyEvent>() {
            @Override
            public void onApplicationEvent(ApplicationReadyEvent event) {
                ConfigurableApplicationContext context = event.getApplicationContext();
                int port = context.getEnvironment().getProperty("server.port", Integer.class, 8080);
                openBrowser("http://localhost:" + port);
            }
        });

        application.run(args);
    }

    private static int findAvailablePort(int startPort) {
        int port = startPort;
        while (!isPortAvailable(port)) {
            System.out.println("Port " + port + " is not available, trying " + (port + 1));
            port++;
        }
        return port;
    }

    private static boolean isPortAvailable(int port) {
        try (ServerSocket socket = new ServerSocket(port)) {
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private static void openBrowser(String url) {
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                desktop.browse(new URI(url));
            } else {
                String os = System.getProperty("os.name").toLowerCase();
                Runtime runtime = Runtime.getRuntime();
                if (os.contains("win")) {
                    runtime.exec(new String[]{"cmd", "/c", "start", url});
                } else if (os.contains("mac")) {
                    runtime.exec(new String[]{"open", url});
                } else if (os.contains("nix") || os.contains("nux")) {
                    runtime.exec(new String[]{"xdg-open", url});
                } else {
                    System.err.println("Unsupported operating system.");
                }
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

}
