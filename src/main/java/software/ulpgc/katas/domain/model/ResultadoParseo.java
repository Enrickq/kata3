package software.ulpgc.katas.domain.model;

import java.util.List;

public record ResultadoParseo(List<Libro> libros, int descartados) {
}
