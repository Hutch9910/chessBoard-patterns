package com.hutch9910.chessboardPatterns.web;

import java.awt.Desktop;
import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class BrowserLauncher {

    @Value("${local.server.port:${server.port:8080}}")
    private int port;

    @EventListener(ApplicationReadyEvent.class)
    public void openBrowser() {
        try {
            Desktop.getDesktop().browse(
                    new URI("http://localhost:" + port));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}