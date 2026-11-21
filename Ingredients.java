public class Ingredients {
    private int calories;
    private int protein;
    private int carbs;
    private int fat;
    private int sugar;
    private String name;

    public Ingredients(String name, int calories, int protein, int carbs, int fat, int sugar) {
        this.name = name;
        this.calories = calories;
        this.protein = protein;
        this.carbs = carbs;
        this.fat = fat;
        this.sugar = sugar;
    }

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

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public void setProtein(int protein) {
        this.protein = protein;
    }

    public void setCarbs(int carbs) {
        this.carbs = carbs;
    }

    public void setFat(int fat) {
        this.fat = fat;
    }

    public void setSugar(int sugar) {
        this.sugar = sugar;
    }
}