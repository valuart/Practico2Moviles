package com.example.practico2moviles;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;

public class LeerMensaje extends Service {
    private ContentResolver contenedor;
    private Uri msjRecibido;

    public LeerMensaje() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        msjRecibido = Uri.parse("content://sms");
        contenedor = getContentResolver();

        Log.d("Mensaje","Servicio Iniciado");


        while (true) {
            mostrarMensajes();
            try {
                Thread.sleep(9000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return super.onStartCommand(intent, flags, startId);
            }
        }
    }

    private void mostrarMensajes() {
        Cursor cursor = contenedor.query(msjRecibido,null,null,null,null);


        // Si no hay mensajes
        if(cursor.getCount() == 0) Log.d("Mensaje", "No hay Mensajes");

        // Si hay mensajes, muestra la info de cada mensaje
        while (cursor.moveToNext() && cursor.getPosition() < 5) {
            Log.d("Mensaje", " "+
                    "\n         Remitente: " + cursor.getString(2) +
                    "\nFecha de recepción: " + cursor.getString(4) +
                    "\n           Mensaje: " + cursor.getString(12));
        }

        cursor.close();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("Mensaje", "Servicio Destruido");
    }
}