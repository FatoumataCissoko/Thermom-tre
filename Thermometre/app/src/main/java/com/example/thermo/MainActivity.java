package com.example.thermo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

//va recevoir des evenements de capteur qui est relier au peripherique android
public class MainActivity extends AppCompatActivity implements SensorEventListener  {

    //Déclaration de la classe
    private SensorManager manager;
    private Sensor tempSensor;
    private Thermometre t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_main);
      //  Button btn = new Button(this);
      //  setContentView(btn);

        t = new Thermometre(this);//Instancier le thermo
        setContentView(t);//Afficher le thermo

        // Cette ligne crée un objet SensorManager en récupérant le service système associé au contexte actuel.
        this.manager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);

        // Cette ligne récupère le capteur de température ambiante par défaut à partir du SensorManager.
        this.tempSensor = this.manager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);

        // Cette ligne enregistre un écouteur (this) pour le capteur de température ambiante.
        // L'écouteur sera notifié des mises à jour du capteur avec une fréquence définie par SENSOR_DELAY_NORMAL.
        this.manager.registerListener(this, this.tempSensor, SensorManager.SENSOR_DELAY_NORMAL);


//        t.setTemp(20);
//        t.setUnit("K");



    }

    // Cette méthode est appelée lorsque les données du capteur changent.
   @Override
  public void onSensorChanged(SensorEvent sensorEvent) {

        t.setTemp(sensorEvent.values[0]);
   }

    // Cette méthode est appelée lorsque la précision du capteur change.

    @Override
 public void onAccuracyChanged(Sensor sensor, int i) {

   }
}