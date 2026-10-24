package software.ulpgc.katas.adapters;

import software.ulpgc.katas.domain.model.Libro;
import java.util.List;

public record DocumentoJson(List<Libro> libros, Resumen resumen) {
    public record Resumen(int validos, int descartados) {
    }
}
