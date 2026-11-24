// Ingredients is a simple helper class that just holds macros of ingredients
// Needs to implement Serializable, this is what allows the object to get written into a file
// Must implement serializable because its used in meals.java, which gets written into a file
public class Ingredients implements java.io.Serializable {
    // Fields
    private int calories;
    private int protein;
    private int carbs;
    private int fat;
    private int sugar;
    private String name;

    // Constructor
    public Ingredients(String name, int calories, int protein, int carbs, int fat, int sugar){
        this.name = name;
        this.calories = calories;
        this.protein = protein;
        this.carbs = carbs;
        this.fat = fat;
        this.sugar = sugar;
    }

    // getters and setters for fields
    public String getName() {
        return name;
    }

    public int getCalories() {
        return calories;
    }
    
    public int getProtein() {
        return protein;
    }

    public int getCarbs() {
        return carbs;
    }

    public int getFat() {
        return fat;
    }

    public int getSugar() {
        return sugar;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Makes sure all values are valid (0 or above)
    public void setCalories(int calories) {
        if (calories < 0) {
            throw new IllegalArgumentException("Calorie value cannot be negative.");
        }
        this.calories = calories;
    }

    public void setProtein(int protein) {
        if (protein < 0) {
            throw new IllegalArgumentException("Protein value cannot be negative.");
        }
        this.protein = protein;
    }

    public void setCarbs(int carbs) {
        if (carbs < 0) {
            throw new IllegalArgumentException("Carbohydrate value cannot be negative.");
        }
        this.carbs = carbs;
    }

    public void setFat(int fat) {
        if (fat < 0) {
            throw new IllegalArgumentException("Fat value cannot be negative.");
        }
        this.fat = fat;
    }

    public void setSugar(int sugar) {
        if (sugar < 0) {
            throw new IllegalArgumentException("Sugar value cannot be negative.");
        }
        this.sugar = sugar;
    }
}