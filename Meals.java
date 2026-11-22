import java.util.ArrayList;

public class Meals implements java.io.Serializable {
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
        if (servings <= 0) {
            this.servings = 1;
        } else {
            this.servings = servings;
        }
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

    //Return name of ingredient removed, 
    //or null if ingredient was not found
    public String removeIngredient(Ingredients ingredient) {
        if (ingredients.remove(ingredient)) {
            calories -= ingredient.getCalories();
            protein -= ingredient.getProtein();
            carbs -= ingredient.getCarbs();
            fat -= ingredient.getFat();
            sugar -= ingredient.getSugar();
            return ingredient.getName();
        }
        return null;
    }

    public String macrosPerServing() {
        return name + " -- Calories: " + (calories / servings) 
            + ", Protein: " + (protein / servings) 
            + ", Carbs: " + (carbs / servings) 
            + ", Fat: " + (fat / servings) 
            + ", Sugar: " + (sugar / servings);
    }

    public void changeIngredientValue(Ingredients ingredient, String val, int newValue) {
        int index = ingredients.indexOf(ingredient);
        if (index < 0) {
            return;
        }
        switch (val.toLowerCase()) {
            case "calories" -> {
                int oldCalories = ingredients.get(index).getCalories();
                try {
                    ingredients.get(index).setCalories(newValue);
                } catch (IllegalArgumentException e) {
                    IO.println("Invalid calorie value. It cannot be negative. No changes were made to the ingredient.");
                    // If the new value is negative, do not change the ingredient's value
                    return;
                }
                calories -= oldCalories;
                calories += ingredients.get(index).getCalories();
            }
            case "protein" -> {
                int oldProtein = ingredients.get(index).getProtein();
                try {
                    ingredients.get(index).setProtein(newValue);
                } catch (IllegalArgumentException e) {
                    IO.println("Invalid protein value. It cannot be negative. No changes were made to the ingredient.");
                    // If the new value is negative, do not change the ingredient's value
                    return;
                }
                protein -= oldProtein;
                protein += ingredients.get(index).getProtein();
            }
            case "carbs" -> {
                int oldCarbs = ingredients.get(index).getCarbs();
                try {
                    ingredients.get(index).setCarbs(newValue);
                } catch (IllegalArgumentException e) {
                    IO.println("Invalid carb value. It cannot be negative. No changes were made to the ingredient.");
                    // If the new value is negative, do not change the ingredient's value
                    return;
                }
                carbs -= oldCarbs;
                carbs += ingredients.get(index).getCarbs();
            }
            case "fat" -> {
                int oldFat = ingredients.get(index).getFat();
                try {
                    ingredients.get(index).setFat(newValue);
                } catch (IllegalArgumentException e) {
                    IO.println("Invalid fat value. It cannot be negative. No changes were made to the ingredient.");
                    // If the new value is negative, do not change the ingredient's value
                    return;
                }
                fat -= oldFat;
                fat += ingredients.get(index).getFat();
            }
            case "sugar" -> {
                int oldSugar = ingredients.get(index).getSugar();
                try {
                    ingredients.get(index).setSugar(newValue);
                } catch (IllegalArgumentException e) {
                    IO.println("Invalid sugar value. It cannot be negative. No changes were made to the ingredient.");
                    // If the new value is negative, do not change the ingredient's value
                    return;
                }
                sugar -= oldSugar;
                sugar += ingredients.get(index).getSugar();
            }
            default -> {
                IO.println("Invalid macro type. No changes were made to the ingredient.");
                // Invalid value type, do nothing
            }

        }
    }
}