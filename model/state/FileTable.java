package model.state;

import java.io.BufferedReader;

public interface FileTable {
    boolean isOpen(String filename);

    void addOpenFile(String filename, BufferedReader bufferReader);

    BufferedReader getOpenFile(String filename);

    void closeFile(String filename);
}
