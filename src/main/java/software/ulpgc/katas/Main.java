package software.ulpgc.katas;

import software.ulpgc.katas.adapters.CsvLibroParser;
import software.ulpgc.katas.adapters.CsvReader;
import software.ulpgc.katas.adapters.JsonLibroWriter;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) throws IOException {
        Path entrada = Path.of(args.length > 0 ? args[0] : "src/main/resources/libros.csv");
        Path salida = Path.of(args.length > 1 ? args[1] : "target/salida/libros.json");
        if (!Files.isRegularFile(entrada)) {
            throw new IOException("No existe el fichero de entrada: " + entrada);
        }

        var lector = new CsvReader();
        var parser = new CsvLibroParser();
        var escritor = new JsonLibroWriter();

        try (Reader reader = Files.newBufferedReader(entrada, StandardCharsets.UTF_8)) {
            var resultado = parser.parse(lector.read(reader));
            Files.createDirectories(salida.toAbsolutePath().getParent());
            try (Writer writer = Files.newBufferedWriter(salida, StandardCharsets.UTF_8)) {
                escritor.write(resultado, writer);
            }
            System.out.printf("%d libros válidos, %d descartados. Salida: %s%n",
                    resultado.libros().size(), resultado.descartados(), salida);
        }
    }
}
