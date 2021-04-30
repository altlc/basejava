package com.basejava.webapp.storage.serializer;

import com.basejava.webapp.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface Serializer {
    void doPutFile(Resume r, OutputStream os) throws IOException;

    Resume doGetFile(InputStream is) throws IOException;
}
