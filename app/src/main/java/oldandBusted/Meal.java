package oldandBusted;

import android.media.Image;

import java.util.GregorianCalendar;

/**
 * Created by normen on 03.11.17.
 */

public class Meal {

    private oldandBusted.Food food;
    private GregorianCalendar time;
    private Image foodImage;

    public Meal() {
        this.food = new oldandBusted.Food();
        this.time = new GregorianCalendar();
        this.foodImage = null;
    }

    public Meal(oldandBusted.Food food, Image foodImage) {
        this.food = food;
        this.foodImage = foodImage;
        this.time = new GregorianCalendar();
    }

    public oldandBusted.Food getFood() {
        return food;
    }

    public void setFood(oldandBusted.Food food) {
        this.food = food;
    }

    public GregorianCalendar getTime() {
        return time;
    }

    public void setTime(GregorianCalendar time) {
        this.time = time;
    }

    public Image getFoodImage() {
        return foodImage;
    }

    public void setFoodImage(Image foodImage) {
        this.foodImage = foodImage;
    }
}
