import java.util.ArrayList;

// Must implement Serializable in order to be written into a file, which is done in Main.java
public class Meals implements java.io.Serializable {
    // Fields
    private String name;
    private int calories;
    private int protein;
    private int carbs;
    private int fat;
    private int sugar;
    private int servings; // Number of servings for the meal
    private ArrayList<Ingredients> ingredients; //List of Ingredients for the meal

    // Constructor
    public Meals(String name, int servings) {
        this.name = name;
        calories = 0;
        protein = 0;
        carbs = 0;
        fat = 0;
        sugar = 0;

        // Makes sure servings are above 0
        // This is important for macros per serving because you can't divide by 0
        if (servings <= 0) {
            this.servings = 1;
        } else {
            this.servings = servings;
        }
        this.ingredients = new ArrayList<>();
    }


    // Getters and Setters for fields
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

    public void setServings(int servings) {
        if (servings <= 0) {
            this.servings = 1;
        } else {
            this.servings = servings;
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    // Method to add ingredient to the meal
    public void addIngredient(Ingredients ingredient) {
        // Adds ingredient to the ingredients list
        // adds ingredients macros to the meals overall macros
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
        // if the ingredient was found and could be removed
        // subtract it's macros from the meals overall macros, and then return what it was
        if (ingredients.remove(ingredient)) {
            calories -= ingredient.getCalories();
            protein -= ingredient.getProtein();
            carbs -= ingredient.getCarbs();
            fat -= ingredient.getFat();
            sugar -= ingredient.getSugar();
            return ingredient.getName();
        }
        // if not found, return null
        return null;
    }

    // Works like to string
    // returns a string containing the meal name and its macros per serving (respective macro / servings)
    public String macrosPerServing() {
        String ingredientNames = "";
        for (Ingredients ingredient : ingredients) {
            ingredientNames += ingredient.getName() + ", ";
        }
        if (ingredientNames.isEmpty()) {
            ingredientNames = "No ingredients";
        } else {
            ingredientNames = ingredientNames.substring(0, ingredientNames.length() - 2);
        }
        return name + " -- Calories: " + (calories / servings) 
            + ", Protein: " + (protein / servings) 
            + ", Carbs: " + (carbs / servings) 
            + ", Fat: " + (fat / servings) 
            + ", Sugar: " + (sugar / servings) 
            + "\nIngredients -- "
            + ingredientNames;
    }

    // Method to change the ingredients values
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