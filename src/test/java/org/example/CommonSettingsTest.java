package org.example;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.ServerSocket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

class CommonSettingsTest {
    private static final String file = "settings.ini";

    @Test
    void testGetPortFromFile() throws IOException {
        ServerSocket server = new ServerSocket(CommonSettings.getPortFromFile(file));

    }

    @Test
    void testGetDateAndTime() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(new Date());

    }

    @Test
    void testLog() {
        Path file = Path.of("File.log");
        boolean isRegularExecutableFile = Files.isRegularFile(file) &
                Files.isReadable(file) & Files.isExecutable(file);
    }
}