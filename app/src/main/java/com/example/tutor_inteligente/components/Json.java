package com.example.tutor_inteligente.components;

import android.content.Context;
import android.util.JsonReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Json {

    private Context context;
    private ArrayList niveles = new ArrayList();

    public Json(Context context) {
        this.context = context;
        createLevels();
    }

    private void createLevels() {
        try {
            JsonReader reader = new JsonReader(new InputStreamReader(context.getAssets().open("ejercicios.json"), StandardCharsets.UTF_8));
            reader.beginArray();
            while (reader.hasNext()) {
                niveles.add(createLevel(reader));
            }
            reader.endArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Object createLevel(JsonReader reader) throws IOException {
        int id = 0, dificultad = 0;
        String descripcion = null, tipo = null, comprobacionSalida = null, comprobacionCodigo = null;
        String ayuda = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            switch (name) {
                case "Id":
                    id = reader.nextInt();
                    break;
                case "descripcion":
                    descripcion = reader.nextString();
                    break;
                case "tipo":
                    tipo = reader.nextString();
                    break;
                case "dificultad":
                    dificultad = reader.nextInt();
                    break;
                case "comprobacionSalida":
                    comprobacionSalida = reader.nextString();
                    break;
                case "comprobacionCodigo":
                    comprobacionCodigo = reader.nextString();
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }
        reader.endObject();
        return new Nivel(id, descripcion, tipo, dificultad, comprobacionSalida, comprobacionCodigo,
                ayuda);
    }

    public Nivel getLevel(Integer numNivel) {
        return (Nivel) niveles.get(numNivel - 1);
    }

}
