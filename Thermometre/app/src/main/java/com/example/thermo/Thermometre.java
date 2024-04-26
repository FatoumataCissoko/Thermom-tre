 package com.example.thermo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class Thermometre extends View {

    //Attributs(3)
    private float temp;
    private int thermometerColor = Color.RED;

    public Thermometre(Context context) { // Constructeur par defaut
        super(context);
        this.temp=23.0f;
    }

    public Thermometre(Context context, float temp) {// Constructeur avec parametre
        super(context);
        this.temp=temp;
    }
    //Methodes(2): Tem et Unit
    public void setTemp(float temp) {
        this.temp=temp;
        invalidate(); // Demander à la vue de se redessiner
    }

    private String unit;

    public void setUnit(String unit) {
        this.unit = unit;
        invalidate(); // Demander à la vue de se redessiner
    }
    //Les unites de mesure(Celsius, Fahrenheit, Kelvin)

    // Méthode pour convertir Celsius en Fahrenheit
    public float CelsiusToFahrenheit(float celsius) {

        return (celsius * 9 / 5) + 32;
    }

    // Méthode pour convertir Celsius en Kelvin
    public float CelsiusToKelvin(float celsius) {

        return celsius + 273.15f;
    }

    // Méthode pour dessiner l'échelle Celsius
    private void DrawEchelleCelsius(Canvas c) {
        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setTextSize(20);

        float startX = getWidth() * 0.8f;//Verticale
        float startY = getHeight() * 0.1f;//Horizontal

        float interval = (getHeight() * 0.8f) / 10;

        for (int i = 0; i <= 10; i++) {
            float yPos = startY + interval * (10 - i);
            int tempValue = i * 10;

            // Ajout de condition pour ne dessiner que les graduations jusqu'à 100
            if (tempValue <= 100) {
                c.drawText(String.valueOf(tempValue), startX, yPos, paint);

                // Dessine la ligne de graduation
                c.drawLine(startX - 20, yPos, startX + 20, yPos, paint);
            }
        }
    }

    // Méthode pour dessiner l'échelle Kelvin
    private void DrawEchelleKelvin(Canvas c) {
        Paint paint = new Paint();
        paint.setColor(Color.GRAY);
        paint.setTextSize(20);

        float startX = getWidth() * 0.8f;
        float startY = getHeight() * 0.1f;

        float interval = (getHeight() * 0.8f) / 100;

        for (int i = 0; i <= 100; i++) {
            float yPos = startY + interval * (10 - i);
            c.drawText(String.valueOf(i * 10 + 273.15f), startX, yPos, paint);

            // Dessine la ligne de graduation
            c.drawLine(startX - 20, yPos, startX + 20, yPos, paint);
        }
    }


    // Méthode pour dessiner l'échelle Fahrenheit
    private void DrawEchelleFahrenheit(Canvas c) {
        // Dessiner l'échelle Fahrenheit
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setTextSize(20);

        float startX = getWidth() * 0.8f;
        float startY = getHeight() * 0.1f;

        float interval = (getHeight() * 0.8f) / 100;

        for (int i = 0; i <= 100; i++) {
            float yPos = startY + interval * (10 - i);
            c.drawText(String.valueOf(i * 10 + 32), startX, yPos, paint);

            // Dessine la ligne de graduation
            c.drawLine(startX - 20, yPos, startX + 20, yPos, paint);
        }
    }

    //Pour definir la couleur du thermometre
    public  void  setColorThermometre(int color){
        thermometerColor= color;
        invalidate();// Demander à la vue de se redessiner avec la nouvelle couleur
    }

    // Méthode appelée lors du dessin de la vue
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Crayon
        Paint p = new Paint();

        //Fixation de la couleur
        //p.setColor(Color.RED);

        // Dessine la ligne thermometre
        float lineHeight = (temp / 100.0f) * (this.getHeight() - 120.0f); // Longueur de la ligne en fonction de la température

        // Variation de couleur du bleu au rouge en fonction de la température
        int color = getColorForTemperature(temp);
        p.setColor(color);

        // Dessine la ligne thermometre
        p.setStrokeWidth(10);
        canvas.drawLine((this.getWidth() / 2.0f), this.getHeight() - 60.0f - lineHeight, (this.getWidth() / 2.0f), this.getHeight() - 60.0f, p);


        // Dessine la boule de mercure
        p.setColor(Color.RED); // Couleur rouge pour la boule de mercure
        canvas.drawCircle((this.getWidth()/2.0f),this.getHeight()-60.0f,60.0f,p);

        // Dessine les graduations
        int numGraduations = 11; // Nombre de graduations
        float graduationSpacing = (this.getHeight() - 120.0f) / (numGraduations ); // Espacement entre chaque graduation
        float startX = (this.getWidth() / 2.0f) - 20; // Début des graduations
        float endX = (this.getWidth() / 2.0f) + 20; // Fin des graduations
        float y = this.getHeight() - 60.0f - graduationSpacing; // Position de départ des graduations
        int graduationValue = 0;
        for (int i = 0; i <= numGraduations; i++) {
            canvas.drawLine(startX, y, endX, y, p);
            // Dessiner le texte pour les graduations
            p.setTextSize(30);
            canvas.drawText(Integer.toString(graduationValue), this.getWidth() / 2.0f + 30, y + 10, p);
            graduationValue += 10; // Incrémenter la valeur de graduation
            y -= graduationSpacing;
        }


        // Dessine l'échelle en fonction de l'unité
        if ("C".equals(unit)) {
            DrawEchelleCelsius(canvas);
        } else if ("F".equals(unit)) {
            DrawEchelleFahrenheit(canvas);
        } else if ("K".equals(unit)) {
            DrawEchelleKelvin(canvas);
        }

        // Mettre a jour ou rafraichir1
        invalidate();
    }

    private int getColorForTemperature(float temp) {
        // Définir les limites de couleur pour la température
        float blueThermo = 0.0f;
        float redThermo = 100.0f;

        // Calculer l'interpolation linéaire de couleur en fonction de la température
        float ColorGradua = (temp - blueThermo) / (redThermo - blueThermo);
        int blue = (int) (255 * (1 - ColorGradua));
        int red = (int) (255 * ColorGradua);
        int gris = (int) (128 - Math.abs(ColorGradua - 0.5) * 256);

        // Combiner les composantes de couleur pour obtenir la couleur finale
        return Color.rgb(red, gris , blue);
    }





}
