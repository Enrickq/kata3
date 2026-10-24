package software.ulpgc.katas;

import com.google.gson.JsonParser;
import org.junit.jupiter.api.Test;
import software.ulpgc.katas.adapters.CsvLibroParser;
import software.ulpgc.katas.adapters.CsvReader;
import software.ulpgc.katas.adapters.JsonLibroWriter;
import software.ulpgc.katas.domain.model.ResultadoParseo;

import java.io.InputStreamReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class JsonLibroWriterTest {
    @Test
    void salidaJsonCoincideConEjemploEsperadoYSePuedeParsear() throws Exception {
        var lector = new CsvReader();
        var parser = new CsvLibroParser();
        ResultadoParseo resultado;
        try (var stream = getClass().getResourceAsStream("/libros.csv");
             var entrada = new InputStreamReader(stream, StandardCharsets.UTF_8)) {
            resultado = parser.parse(lector.read(entrada));
        }
        var destino = new StringWriter();
        new JsonLibroWriter().write(resultado, destino);
        String esperado;
        try (var stream = getClass().getResourceAsStream("/libros-esperados.json")) {
            esperado = new String(stream.readAllBytes(), StandardCharsets.UTF_8);
        }
        assertThat(JsonParser.parseString(destino.toString()))
                .isEqualTo(JsonParser.parseString(esperado));
    }

    @Test
    void serializaDatasetVacio() {
        var destino = new StringWriter();
        new JsonLibroWriter().write(new ResultadoParseo(List.of(), 0), destino);
        var json = JsonParser.parseString(destino.toString()).getAsJsonObject();
        assertThat(json.getAsJsonArray("libros").size()).isZero();
        assertThat(json.getAsJsonObject("resumen").get("validos").getAsInt()).isZero();
        assertThat(json.getAsJsonObject("resumen").get("totalProcesados").getAsInt()).isZero();
    }
}
