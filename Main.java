import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Main {
    private static final String MEALS_FILE = "Meals.txt";
    private ArrayList<Meals> meals = new ArrayList<>();
    public static void main(String[] args) {
        IO.println("Welcome to the Meal Tracker!");
        try (BufferedReader reader = new BufferedReader(new java.io.InputStreamReader(System.in));
             PrintWriter writer = new PrintWriter(new FileOutputStream(MEALS_FILE))) {
            Main main = new Main();
            main.loadMeals();
            while (true) {
                IO.println("\nPlease choose an option:");
                IO.println("1. Add a meal");
                IO.println("2. View meals");
                IO.println("3. Update a meal");
                IO.println("4. Delete a meal");
                IO.println("5. Exit");
                String choice = reader.readLine();
                switch (choice) {
                    case "1" -> main.addMeal(reader);
                    case "2" -> main.viewMeals();
                    case "3" -> main.updateMeal(reader);
                    case "4" -> main.deleteMeal(reader);
                    case "5" -> {
                        main.saveMeals();
                        IO.println("Exiting the Meal Tracker. Goodbye!");
                        return;
                    }
                    default -> IO.println("Invalid choice. Please try again.");
                }
            }
        } catch (IOException e) {
            IO.println("An error occurred: " + e.getMessage());
        }
    }

    private void viewMeals() {
        if (meals.isEmpty()) {
            IO.println("No meals available.");
            return;
        }
        for (int i = 0; i < meals.size(); i++) {
            Meals meal = meals.get(i);
            IO.println(meal.macrosPerServing());
        }
    }

    private void loadMeals() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(MEALS_FILE))) {
            while (true) {
                Meals meal = (Meals) ois.readObject();
                meals.add(meal);
            }
        } catch (IOException | ClassNotFoundException e) {
            meals = new ArrayList<>();
        }
    }

    private void addMeal(BufferedReader reader) {
        String mealName = IO.readln("Enter meal name:\n");
        int servings = Integer.parseInt(IO.readln("Enter number of servings:\n"));
        Meals meal = new Meals(mealName, servings);
        meals.add(meal);
        IO.println("Enter Ingredints for the meal (type 'done' when finished):");
        while (true) {
            String ingredientName = IO.readln("Enter ingredient name (or 'done' to finish):\n");
            if (ingredientName.equalsIgnoreCase("done")) {
                break;
            }

            int calories = 0;
            int protein = 0;
            int carbs = 0;
            int fat = 0;
            int sugar = 0;

            boolean validInput = false;
            while (!validInput) {
                try {
                    calories = Integer.parseInt(IO.readln("Enter calories:\n"));
                    if (calories < 0) {
                        IO.println("Calorie value cannot be negative. Please try again.");
                    } else {
                        validInput = true;
                    }
                } catch (NumberFormatException e) {
                    IO.println("Invalid input for calories. Please enter a valid integer.");
                    continue;
                }
            }

            validInput = false;
            while (!validInput) {
                try {
                    protein = Integer.parseInt(IO.readln("Enter protein:\n"));
                    if (protein < 0) {
                        IO.println("Protein value cannot be negative. Please try again.");
                    } else {
                        validInput = true;
                    }
                } catch (NumberFormatException e) {
                    IO.println("Invalid input for protein. Please enter a valid integer.");
                    continue;
                }
            }
            
            validInput = false;
            while (!validInput) {
                try {
                    carbs = Integer.parseInt(IO.readln("Enter carbs:\n"));
                    if (carbs < 0) {
                        IO.println("Carbs value cannot be negative. Please try again.");
                    } else {
                        validInput = true;
                    }
                } catch (NumberFormatException e) {
                    IO.println("Invalid input for carbs. Please enter a valid integer.");
                    continue;
                }
            }
            
            validInput = false;
            while (!validInput) {
                try {
                    fat = Integer.parseInt(IO.readln("Enter fat:\n"));
                    if (fat < 0) {
                        IO.println("Fat value cannot be negative. Please try again.");
                    } else {
                        validInput = true;
                    }
                } catch (NumberFormatException e) {
                    IO.println("Invalid input for fat. Please enter a valid integer.");
                    continue;
                }
            }
            
            validInput = false;
            while (!validInput) {
                try {
                    sugar = Integer.parseInt(IO.readln("Enter sugar:\n"));
                    if (sugar < 0) {
                        IO.println("Sugar value cannot be negative. Please try again.");
                    } else {
                        validInput = true;
                    }
                } catch (NumberFormatException e) {
                    IO.println("Invalid input for sugar. Please enter a valid integer.");
                    continue;
                }
            }
            Ingredients ingredient = new Ingredients(ingredientName, calories, protein, carbs, fat, sugar);
            meal.addIngredient(ingredient);
        }
        IO.println("Meal added successfully!");
    }
}
