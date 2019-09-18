package com.example.tutor_inteligente;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.tutor_inteligente.components.CustomWebChromeClient;
import com.example.tutor_inteligente.components.Json;
import com.example.tutor_inteligente.components.WebViewJavaScriptInterface;
import com.example.tutor_inteligente.helpers.HtmlHelper;
import com.example.tutor_inteligente.ia.FuzzyLogic;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.x5.template.Theme;
import com.x5.template.providers.AndroidTemplates;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RootActivity extends AppCompatActivity implements TutorFragment.OnFragmentInteractionListener, Callback<String> {

    private WebView webView;
    private String paginaHtml;
    private FloatingActionButton btn_play;
    private WebViewJavaScriptInterface javaScriptInterface;
    private Json json;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private TutorFragment fragment;
    private FuzzyLogic jFuzzy;
    private Integer errores = 0;
    private Boolean playButton = true;
    private int nivel = 1;

    @SuppressLint({"SetJavaScriptEnabled"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_root);

        //Generar WebView (Interfaz blockly)
        HtmlHelper.theme = new Theme(new AndroidTemplates(this));
        webView = findViewById(R.id.tutor_webview);
        webView.setWebChromeClient(new CustomWebChromeClient());
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        paginaHtml = HtmlHelper.generarHtml("levels/tutorial.dt", this);
        webView.loadDataWithBaseURL("file:///android_asset/blockly/", paginaHtml, HtmlHelper.MIME, HtmlHelper.ENCODING, null);
        createFragment();

        btn_play = findViewById(R.id.btn_play);
        btn_play.setOnClickListener(view -> {
            loadExercise(nivel);
            if (playButton) {
                btn_play.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                playButton = false;
            } else {
                btn_play.setImageResource(R.drawable.ic_reload);
                errores++;
                playButton = true;
            }
            javaScriptInterface.generarCodigoBlockly(codigo -> {

                View view2 = LayoutInflater.from(RootActivity.this).inflate(R.layout.dialog_code, findViewById(android.R.id.content), false);

                TextView text_code = view2.findViewById(R.id.dialog_code_text_code);
                text_code.setText(codigo);
                text_code.setMovementMethod(new ScrollingMovementMethod());

                new AlertDialog.Builder(RootActivity.this)
                        .setView(view2)
                        .setTitle("Codigo generado")
                        .setCancelable(true)
                        .show();

                if (codigo.equals(json.getLevel(nivel).getComprobacionCodigo())) {
                    completeExercise();
                    loadExercise(nivel + 1);
                }

                /*
                //API REST
                /*
                Call<String> prueba = ApiAdapter.getApiService().getEmocionRostro();
                prueba.enqueue(this);

                Call<String> prueba2 = ApiAdapter.getApiService().getEmocionTexto();
                prueba2.enqueue(this);
                 */

            });

            webView.loadUrl("javascript:getCodeBlockly()"); //Se ejecuta funcion para obtener codigo generado
        });

        if (javaScriptInterface == null) {
            javaScriptInterface = new WebViewJavaScriptInterface();
            webView.addJavascriptInterface(javaScriptInterface, "tutor");
        }

        json = new Json(this);
        jFuzzy = new FuzzyLogic(this);
    }

    private void createFragment() {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragment = new TutorFragment();
        fragmentTransaction.add(R.id.game_fragment, fragment);
        fragmentTransaction.commit();
    }

    private void loadExercise(int numExercise) {
        fragment.setDescription_exercise(json.getLevel(numExercise).getDescripcion());
        fragment.setNumber_exercise(json.getLevel(numExercise).getId());
        fragment.setTextError(errores);
        fragment.setTextHelps(0);
        fragment.setTextTime();
    }

    private void completeExercise() {
        //Fuzzy Logic
        List listVar = new ArrayList<>();
        listVar.add(errores);
        listVar.add(fragment.getTextTime());
        listVar.add(0);
        listVar.add(json.getLevel(1).getDificultad());
        listVar.add(0);

        List<String> listName = new ArrayList<>();
        listName.add("errores");
        listName.add("tiempo");
        listName.add("ayudas");
        listName.add("categoria");
        listName.add("emocion");

        jFuzzy.setInuputVariables(listName, listVar);
        jFuzzy.evaluate();
        List vars = jFuzzy.getOutVariables();
        List rules = jFuzzy.getRules();
        jFuzzy.getGraphs(getSupportFragmentManager());

    }

    private void updateFragment() {
        fragmentTransaction.detach(fragment);
        fragmentTransaction.attach(fragment);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        String emotion = "";
        if (response.isSuccessful()) {
            emotion = response.body();
            try {
                assert emotion != null;
                JSONObject jsonObject = new JSONObject(emotion);
                emotion = (String) jsonObject.get("emocion");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Log.d("", emotion);
    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {
        Log.d("", "Error de servidor");
    }
}
