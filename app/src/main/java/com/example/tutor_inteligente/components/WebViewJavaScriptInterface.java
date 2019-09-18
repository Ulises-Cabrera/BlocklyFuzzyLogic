package com.example.tutor_inteligente.components;

import android.text.TextUtils;
import android.util.Log;
import android.webkit.JavascriptInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class WebViewJavaScriptInterface {

    private final static String TAG = WebViewJavaScriptInterface.class.getName();
    //private GameFragment libgdxFragment;
    private List<Character> ultimoCodigoGenerado;
    private CustomCallback<String> callback;
    private CustomCallback<Void> callbackTimerStoped;

    public WebViewJavaScriptInterface() {
        ultimoCodigoGenerado = new ArrayList<>();
    }

    public void generarCodigoBlockly(CustomCallback<String> callback) {
        this.callback = callback;
    }

    @JavascriptInterface
    public void codigoBlocklyGenerado(String codigoGenerado) {
        //eliminar los highlightBlock
        StringBuilder generado = new StringBuilder();
        for (String linea : codigoGenerado.split("\n")) {
            if (!linea.trim().startsWith("highlightBlock")) {
                generado.append(linea);
                generado.append("\n");
            }
        }
        callback.processResponse(generado.toString());
    }


    @JavascriptInterface
    public boolean caminarDerecha() {
        return ejecutarComando(Comando.CAMINAR_DERECHA);
    }

    @JavascriptInterface
    public boolean caminarIzquierda() {
        return ejecutarComando(Comando.CAMINAR_IZQUIERDA);
    }

    @JavascriptInterface
    public boolean disparar() {
        return ejecutarComando(Comando.DISPARAR);
    }

    @JavascriptInterface
    public boolean saltar() {
        return ejecutarComando(Comando.SALTAR);
    }

    @JavascriptInterface
    public boolean suma() {
        return ejecutarComando(Comando.SUMA);
    }

    @JavascriptInterface
    public void timerStoped() {
        if (callbackTimerStoped != null) {
            callbackTimerStoped.processResponse(null);
        }
    }

    private boolean ejecutarComando(Comando comando) {
        agregarCodigo(comando);
        Log.d(TAG, "Comando llamado: " + comando.name());

        //level.procesarComando(comando);
        //return level.recibirComandos;
        return true;
    }

    private void agregarCodigo(Comando comando) {
        switch (comando.getValue()) {
            case "izquierda":
                ultimoCodigoGenerado.add('L');
                break;
            case "derecha":
                ultimoCodigoGenerado.add('R');
                break;
            case "saltar":
                ultimoCodigoGenerado.add('J');
                break;
            case "disparar":
                ultimoCodigoGenerado.add('S');
                break;
            case "suma":
                ultimoCodigoGenerado.add('+');
        }
    }

    public String getUltimoCodigoGenerado() {
        return TextUtils.join("", ultimoCodigoGenerado);
    }

    public void addTimerStoped(CustomCallback<Void> callback) {
        callbackTimerStoped = callback;
    }

}
