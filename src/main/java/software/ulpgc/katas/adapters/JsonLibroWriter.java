package software.ulpgc.katas.adapters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import software.ulpgc.katas.domain.model.ResultadoParseo;

import java.io.Writer;

public class JsonLibroWriter {
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public void write(ResultadoParseo resultado, Writer destino) {
        var documento = new DocumentoJson(
                resultado.libros(),
                new DocumentoJson.Resumen(resultado.libros().size(), resultado.descartados())
        );
        gson.toJson(documento, destino);
    }
}
