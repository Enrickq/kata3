package software.ulpgc.katas.adapters;

import software.ulpgc.katas.domain.model.Libro;
import software.ulpgc.katas.domain.model.ResultadoParseo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CsvLibroParser {
    public ResultadoParseo parse(List<String> lines) {
        if (lines.isEmpty()) return new ResultadoParseo(List.of(), 0);
        if (!lines.getFirst().strip().equals("titulo,autor,anio")) {
            throw new IllegalArgumentException("Cabecera CSV no válida");
        }

        List<Libro> libros = new ArrayList<>();
        int descartados = 0;
        for (String line : lines.subList(1, lines.size())) {
            Optional<Libro> libro = parseLibro(line);
            if (libro.isPresent()) libros.add(libro.get());
            else descartados++;
        }
        return new ResultadoParseo(List.copyOf(libros), descartados);
    }

    private Optional<Libro> parseLibro(String line) {
        String[] fields = line.split(",", -1);
        if (!valid(fields)) return Optional.empty();
        try {
            int anio = Integer.parseInt(fields[2].strip());
            if (anio <= 0) return Optional.empty();
            return Optional.of(new Libro(fields[0].strip(), fields[1].strip(), anio));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    private boolean valid(String[] fields) {
        return fields.length == 3 &&
                !fields[0].isBlank() &&
                !fields[1].isBlank() &&
                !fields[2].isBlank();
    }
}
