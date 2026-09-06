package com.hutch9910.chessboardPatterns.web;

import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class SystemTrayManager {

    private final ConfigurableApplicationContext context;

    public SystemTrayManager(ConfigurableApplicationContext context) {
        this.context = context;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onStartup() {

        if (!SystemTray.isSupported()) {
            return;
        }

        try {

            Image image = Toolkit.getDefaultToolkit()
                    .getImage(getClass().getResource("/static/trayicon.png"));

            PopupMenu popup = new PopupMenu();

            MenuItem stopItem = new MenuItem("Stop Application");

            stopItem.addActionListener(e -> {
                context.close();
                System.exit(0);
            });

            popup.add(stopItem);

            TrayIcon trayIcon =
                    new TrayIcon(image, "Chessboard Patterns", popup);

            trayIcon.setImageAutoSize(true);

            SystemTray.getSystemTray().add(trayIcon);

            trayIcon.displayMessage(
                    "Chessboard Patterns",
                    "Application started on localhost:8080",
                    TrayIcon.MessageType.INFO);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}