package model.state;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MapFileTable implements FileTable{
    private Map<String, BufferedReader> fileTable = new HashMap<String, BufferedReader>();
    @Override
    public boolean isOpen(String filename) {
        return fileTable.containsKey(filename);
    }

    @Override
    public void addOpenFile(String filename, BufferedReader bufferReader) {
        fileTable.put(filename, bufferReader);
    }

    @Override
    public BufferedReader getOpenFile(String filename) {
        return fileTable.get(filename);
    }

    @Override
    public void closeFile(String filename) {
        try {
            fileTable.remove(filename).close();
        } catch (IOException e) {
            throw new NotFoundException("file not found");
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("FileTable:\n");
        for  (Map.Entry<String, BufferedReader> entry : fileTable.entrySet()) {
            sb.append(entry.getKey()).append('\n');
        }
        sb.append("\n");
        return sb.toString();
    }
}
