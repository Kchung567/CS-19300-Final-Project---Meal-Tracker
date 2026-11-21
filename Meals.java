public class Meals {
    private String name;
    private int calories;
    private int protein;
    private int carbs;
    private int fat;
    private int sugar;
    private int servings;
    private ArrayList<Ingredients> ingredients;

    public Meals(String name, int servings) {
        this.name = name;
        calories = 0;
        protein = 0;
        carbs = 0;
        fat = 0;
        sugar = 0;
        this.servings = servings;
        this.ingredients = new ArrayList<>();
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

    public int getServings() {
        return servings;
    }

    public ArrayList<Ingredients> getIngredients() {
        return ingredients;
    }

    public void addIngredient(Ingredients ingredient) {
        ingredients.add(ingredient);
        calories += ingredient.getCalories();
        protein += ingredient.getProtein();
        carbs += ingredient.getCarbs();
        fat += ingredient.getFat();
        sugar += ingredient.getSugar();
    }

    public void removeIngredient(Ingredients ingredient) {
        if (ingredients.remove(ingredient)) {
            calories -= ingredient.getCalories();
            protein -= ingredient.getProtein();
            carbs -= ingredient.getCarbs();
            fat -= ingredient.getFat();
            sugar -= ingredient.getSugar();
        }
    }

    public void changeIngredientValue(Ingredients ingredient, String val, int newValue) {
        int index = ingredients.indexOf(ingredient);
        if (index < 0) {
            return;
        }
        switch (val) {
            case "calories" -> {
                calories -= ingredient.getCalories();
                ingredient.setCalories(newValue);
                calories += ingredient.getCalories();
            }
            case "protein" -> {
                protein -= ingredient.getProtein();
                ingredient.setProtein(newValue);
                protein += ingredient.getProtein();
            }
            case "carbs" -> {
                carbs -= ingredient.getCarbs();
                ingredient.setCarbs(newValue);
                carbs += ingredient.getCarbs();
            }
            case "fat" -> {
                fat -= ingredient.getFat();
                ingredient.setFat(newValue);
                fat += ingredient.getFat();
            }
            case "sugar" -> {
                sugar -= ingredient.getSugar();
                ingredient.setSugar(newValue);
                sugar += ingredient.getSugar();
            }
        }
    }
}