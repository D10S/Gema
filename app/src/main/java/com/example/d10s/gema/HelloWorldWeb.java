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
 * Created by Sala9 on 09/09/2015.
 */
public class HelloWorldWeb extends AsyncTask<String,String,String>{

    Context context;
    public HelloWorldWeb(Context context){
        this.context = context;
    }

    @Override
    protected String doInBackground(String[] args)
        {
            String result = null;
            try{
                HttpURLConnection con = (HttpURLConnection) new URL("http://"+getURL()+":8080/HelloWorldWeb/AltaProducto/").openConnection();
                con.setDoOutput(true);
                con.setRequestMethod("POST");
                DataOutputStream salida = new DataOutputStream(con.getOutputStream());
                salida.write(("idProducto=" + URLEncoder.encode(args[0], "utf8") +
                        "&ususario=" + URLEncoder.encode(args[1], "utf8")).getBytes());
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
            return null;
        }
    @Override
    protected void onPostExecute(String result){

        if("Correcto".equals(result))
            new ActivityLauncher(context).LaunchSucces();
        else
            Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
    }

    private String getURL()
    {
        return "192.168.1.123";
    }

}
