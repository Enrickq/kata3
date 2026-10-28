package software.ulpgc.katas;

import org.junit.jupiter.api.Test;
import software.ulpgc.katas.adapters.CsvLibroParser;
import software.ulpgc.katas.adapters.CsvReader;
import software.ulpgc.katas.domain.model.Libro;

import java.io.InputStreamReader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CsvLibroParserTest {
    private final CsvReader reader = new CsvReader();
    private final CsvLibroParser parser = new CsvLibroParser();

    @Test
    void transformaRegistrosYDescartaInvalidos() throws Exception {
        var contenido = "titulo,autor,anio\nUno,Ana,2001\n,Sin título,2020\nDos,Luis,error\n";
        var resultado = parser.parse(reader.read(new StringReader(contenido)));
        assertThat(resultado.libros()).containsExactly(new Libro("Uno", "Ana", 2001));
        assertThat(resultado.descartados()).isEqualTo(2);
    }

    @Test
    void datasetVacio() throws Exception {
        var resultado = parser.parse(reader.read(new StringReader("")));
        assertThat(resultado.libros()).isEmpty();
        assertThat(resultado.descartados()).isZero();
    }

    @Test
    void cabeceraIncorrecta() {
        assertThatThrownBy(() -> parser.parse(List.of("nombre,autor,anio", "Uno,Ana,2001")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Cabecera CSV no válida");
    }

    @Test
    void aceptaEntradaDesdeFicheroODatosEnMemoria() throws Exception {
        String texto;
        try (var stream = getClass().getResourceAsStream("/libros.csv")) {
            assertThat(stream).isNotNull();
            texto = new String(stream.readAllBytes(), StandardCharsets.UTF_8);
        }
        var memoria = parser.parse(reader.read(new StringReader(texto)));
        try (var stream = getClass().getResourceAsStream("/libros.csv");
             var entrada = new InputStreamReader(stream, StandardCharsets.UTF_8)) {
            var recurso = parser.parse(reader.read(entrada));
            assertThat(recurso).isEqualTo(memoria);
        }
    }

    @Test
    void descartaLibroConAnioCero() {
        var resultado = parser.parse(
                List.of("titulo,autor,anio", "Clean code, Robert C. Martin, 0")
        );

        assertThat(resultado.libros()).isEmpty();
        assertThat(resultado.descartados()).isEqualTo(1);
    }
}
