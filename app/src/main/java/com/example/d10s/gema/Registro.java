package com.example.d10s.gema;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by D10S on 09/09/2015.
 */
public class Registro extends Activity {
    public int RESULT_LOAD_IMG=1;
    public ImageView imgView;
    public CheckBox vendedor;
    public EditText escuela;
    public EditText nombre;
    public EditText fecha;
    public EditText correo;
    public EditText contra;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro);

        escuela = (EditText) findViewById(R.id.editText4);
        nombre = (EditText) findViewById(R.id.editText3);
        fecha = (EditText) findViewById(R.id.editText5);
        correo = (EditText) findViewById(R.id.editText6);
        contra = (EditText) findViewById(R.id.editText7);
        vendedor = (CheckBox) findViewById(R.id.checkBox);
    }

    public void vamos(View view){
        String esc = escuela.getText().toString().trim();
        String nom = nombre.getText().toString().trim();
        String fec = fecha.getText().toString().trim();
        String cor = correo.getText().toString().trim();
        String con = contra.getText().toString().trim();


        if(!esc.isEmpty() && !nom.isEmpty() && !fec.isEmpty() && !cor.isEmpty() && !con.isEmpty())
        {
            if(vendedor.isChecked()){
                new AltaUsuario(Registro.this).execute("vendedor", cor, nom, con);
                if(AltaUsuario.result != null)
                {
                    Intent holi = new Intent(this, HelloVendedor.class);
                    startActivity(holi);
                }
            }
            else if (!vendedor.isChecked())
            {
                new AltaUsuario(Registro.this).execute("comprador", cor, nom, con);
                if(AltaUsuario.result != null)
                {
                    Intent holo = new Intent(this, HelloCliente.class);
                    startActivity(holo);
                }

            }
        }
        else
            Toast.makeText(this, "Faltan algunos datos ;)", Toast.LENGTH_SHORT).show();
    }

    public void cargar(View view){
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK && null != data)
            {
                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
                imgView = (ImageView) findViewById(R.id.imageView);
                imgView.destroyDrawingCache();
                imgView.setMaxHeight(50);
                imgView.setMaxWidth(50);
                imgView.setBackground(null);
                imgView.setImageBitmap(BitmapFactory.decodeFile(imgDecodableString));

            } else
            {
                Toast.makeText(this, "No seleccionaste una Imagen", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e)
        {
            Toast.makeText(this, "Algo salio mal", Toast.LENGTH_LONG).show();
        }

    }
}
