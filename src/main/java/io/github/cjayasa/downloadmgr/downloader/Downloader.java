package io.github.cjayasa.downloadmgr.downloader;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Downloader implements Runnable{

    private String URL;

    public Downloader(String URL) {
        this.URL = URL;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public void download(String url) throws IOException {
        System.out.println("Downloading");
        URL fileUrl = new URL(url);

        HttpURLConnection httpConnection = (HttpURLConnection) fileUrl.openConnection();
        httpConnection.setRequestMethod("HEAD");
        long removeFileSize = httpConnection.getContentLengthLong();
        String type = httpConnection.getContentType();

        ReadableByteChannel readableByteChannel = Channels.newChannel(fileUrl.openStream());

        String outFile;
        Map<String, List<String>> m = httpConnection.getHeaderFields();
        if (m.get("Content-Disposition") != null) {
            //do stuff
            outFile = m.get("Content-Disposition").toString();
        } else {
            outFile = UUID.randomUUID().toString();
        }
        if (type != null) {
            outFile = outFile + "." + type.split("/")[1];
        }


        try (FileOutputStream fileOutputStream = new FileOutputStream(outFile)) {
            FileChannel fileChannel = fileOutputStream.getChannel();
            fileChannel.transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
            System.out.println("Complete: " + outFile);
        }
    }

    @Override
    public void run() {
        try {
            download(URL);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
