package com.example.d10s.gema;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by User_8 on 27/09/2015.
 */
public class AltaUsuario extends AsyncTask <String,String,String> {
    public static String result = null;
    Context context;


    public AltaUsuario(Context context){
        this.context=context;
    }

    @Override
    protected String doInBackground(String[] args){

        try{
            HttpURLConnection con = (HttpURLConnection) new URL("http://"+getURL()+":8080/HelloWorldWeb/AltaUsuario").openConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            DataOutputStream salida = new DataOutputStream(con.getOutputStream());
            salida.write(("TipoUsuario=" + URLEncoder.encode(args[0], "utf8") +
                    "&Mail=" + URLEncoder.encode(args[1], "utf8") + "&Nombre=" + URLEncoder.encode(args[2], "utf8") +
                    "&Psswd=" + URLEncoder.encode(args[3], "utf8")).getBytes());
            salida.flush();
            DataInputStream entrada = new DataInputStream(con.getInputStream());
            byte[] chunR = new byte[512];
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int bytesLeidos;
            while((bytesLeidos=entrada.read(chunR)) != -1)
                baos.write(chunR,0,bytesLeidos);
            result = baos.toString();
            baos.close();
            con.disconnect();
        }catch (IOException e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(String result)
    {
        if("Correcto".equals(result))
            new ActivityLauncher(context).launchLogueo();
        else
            Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
    }

    private String getURL()
    {
        return "192.168.1.123";
    }
}
