package oldandBusted;

import android.media.Image;

import java.util.GregorianCalendar;

/**
 * Created by normen on 02.11.17.
 */

public class MarkerRepresentation {

    private Image mealImage;
    private GregorianCalendar time;
    private String meal;

    public MarkerRepresentation(){

        mealImage = null;
        time = new GregorianCalendar();
        meal = "Burger";

    }

    public MarkerRepresentation(Image image,String meal){
        this.mealImage = image;
        this.meal = meal;
        this.time = new GregorianCalendar();
    }

    public Image getMealImage() {
        return mealImage;
    }

    public void setMealImage(Image mealImage) {
        this.mealImage = mealImage;
    }

    public String getMeal() {
        return meal;
    }

    public void setMeal(String meal) {
        this.meal = meal;
    }

    public GregorianCalendar getTime() {
        return time;
    }
}
