package com.example.tutor_inteligente.components;

import android.util.JsonReader;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiRest {
        @GET("emocion_cara/")
        Call<String> getEmocionRostro();

        @GET("emocion_texto/me gusto")
        Call<String> getEmocionTexto();
}
