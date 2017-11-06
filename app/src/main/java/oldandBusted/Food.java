package oldandBusted;

/**
 * Created by normen on 03.11.17.
 */

public class Food {

    private String foodName;
    private String snippet;
    private String description;
    private String effectDescription;
    private int effect = 0; //0-2  0 bad, 1 neutral, 2 good


    public Food(String foodName, String snippet, String description) {
        this.foodName = foodName;
        this.snippet = snippet;
        this.description = description;
    }

    //default value
    public Food() {
        this.foodName = "Burger";
        this.snippet = "verry delicious food";
        this.description = "Burger with potatoes";

    }

    public String getFoodName() {
        return foodName;
    }


    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getEffect() {
        return effect;
    }

    public void setEffect(int effect) {
        this.effect = effect;
    }

    public String getEffectDescription() {
        return effectDescription;
    }

    public void setEffectDescription(String effectDescription) {
        this.effectDescription = effectDescription;
    }
}
