package software.ulpgc.katas.adapters;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

public class CsvReader {
    public List<String> read(Reader source) throws IOException {
        BufferedReader reader = new BufferedReader(source);
        return reader.lines().toList();
    }
}
