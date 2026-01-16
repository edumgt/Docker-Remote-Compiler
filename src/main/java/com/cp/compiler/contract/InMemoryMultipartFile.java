package com.cp.compiler.contract;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Objects;

public class InMemoryMultipartFile implements MultipartFile {

    private final String name;
    private final String originalFilename;
    private final String contentType;
    private final byte[] content;

    public InMemoryMultipartFile(String name, String originalFilename, String contentType, byte[] content) {
        this.name = Objects.requireNonNull(name, "name");
        this.originalFilename = originalFilename;
        this.contentType = contentType;
        this.content = (content == null) ? new byte[0] : content;
    }

    @Override
    public String getName() { return name; }

    @Override
    public String getOriginalFilename() { return originalFilename; }

    @Override
    public String getContentType() { return contentType; }

    @Override
    public boolean isEmpty() { return content.length == 0; }

    @Override
    public long getSize() { return content.length; }

    @Override
    public byte[] getBytes() { return content.clone(); }

    @Override
    public InputStream getInputStream() { return new ByteArrayInputStream(content); }

    @Override
    public void transferTo(File dest) throws IOException {
        try (OutputStream os = new BufferedOutputStream(new FileOutputStream(dest))) {
            os.write(content);
        }
    }
}
