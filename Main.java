import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.io.EOFException;

public class Main {
    // save file name as variable to avoid hardcoding
    // meals get saved to Meals.txt
    // also create meals ArrayList to store meals in memory
    private static final String MEALS_FILE = "Meals.txt";
    private static ArrayList<Meals> meals = new ArrayList<>();
    public static void main(String[] args) {
        // welcome message
        IO.println("Welcome to the Meal Tracker!");
        // read in meals from Meals.txt and store them in meals arraylist
        loadMeals();
        while (true) {
            // main menu
            IO.println("\nPlease choose an option:");
            IO.println("1. Add a meal");
            IO.println("2. View meals");
            IO.println("3. Update a meal");
            IO.println("4. Delete a meal");
            IO.println("5. Exit");
            // read in user choice
            String choice = IO.readln();
            switch (choice) {
                case "1" -> addMeal(); // call addMeal method to add a meal
                case "2" -> viewMeals(); // call viewMeals method to view meals
                case "3" -> updateMeal(); // call updateMeal method to update a meal
                case "4" -> {
                    Meals deletedMeal = deleteMeal(); // call deleteMeal method to delete a meal
                    //prints out deleted meal's macros per serving if it was found and deleted
                    if (deletedMeal != null) {
                        IO.println("Deleted meal: " + deletedMeal.macrosPerServing());
                    }
                }
                case "5" -> {
                    saveMeals(); // call saveMeals method to save meals to Meals.txt
                    //exit message and exits the program
                    IO.println("Exiting the Meal Tracker. Goodbye!");
                    return;
                }
                // if user input is invalid, print error message and loop back to main menu
                default -> IO.println("Invalid choice. Please try again."); 
            }
        }
    }

    // method to save meals to Meals.txt
    private static void saveMeals() {
        // uses ObjectOutputStream to write the meals ArrayList to Meals.txt
        // try-with-resources to automatically close the stream
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(MEALS_FILE))) {
            for (Meals meal : meals) {
                oos.writeObject(meal);
            }
            IO.println("Meals saved successfully!");
        } catch (IOException e) {
            IO.println("Error saving meals: " + e.getMessage());
        }
    }

    // method to delete a meal from the meals ArrayList
    // returns the deleted meal if it was found and deleted, otherwise returns null
    private static Meals deleteMeal() {
        // prompt user for meal name to delete and saves name to mealName variable
        String mealName = IO.readln("Enter the name of the meal to delete:\n");
        // loop through meals ArrayList to find meal with matching name (case-insensitive)
        for (int i = 0; i < meals.size(); i++) {
            Meals meal = meals.get(i);
            if (meal.getName().equalsIgnoreCase(mealName)) {
                // if meal was found, remove it and return it
                Meals removedMeal = meals.remove(i);
                IO.println("Meal deleted successfully!");
                return removedMeal;
            }
        }
        // if meal was not found, tell user and return null
        IO.println("Meal not found.");
        return null;
    }


    // method to update a meal in the meals ArrayList
    // prompts user for meal name to update, then prompts for new values for meal name,
    // servings, and ingredients. If user presses enter without entering a value, the current value is kept.
    // If user enters a new value, the meal is updated with the new value.
    private static void updateMeal() {
        String mealName = IO.readln("Enter the name of the meal to update:\n");
        // Find the meal by name (case-insensitive)
        boolean mealFound = false;
        Meals mealToUpdate = null;
        for (Meals meal : meals) {
            // Check if the meal name matches (case-insensitive)
            if (meal.getName().equalsIgnoreCase(mealName)) {
                mealFound = true;
                mealToUpdate = meal;
                // if meal is found, ask to change name
                IO.println("Enter new meal name (or press Enter to keep current):");
                String newName = IO.readln();
                if (!newName.isEmpty()) {
                    meal.setName(newName);
                } else {
                    IO.println("Keeping current name");
                }
                
                // Ask to change servings
                IO.println("Enter new number of servings (or press Enter to keep current):");
                try {
                    int servingsInput = Integer.parseInt(IO.readln());
                    if (servingsInput > 0) {
                        meal.setServings(servingsInput);
                    } else {
                        IO.println("Keeping current servings");
                    }
                } catch (NumberFormatException e) {
                    IO.println("Keeping current servings");
                } 
                

                // allows user to add, remove, or update ingredients within their meals
                String choice = IO.readln("Would you like to (or press Enter to keep current):\n1. Add an Ingredient\n2. Remove an Ingredient\n3. Update an Ingredient\n");
                switch (choice) {
                    case "1" -> gatherMacrosAndAddIngredients(meal); // calls helper method to add Ingredients
                    case "2" -> {
                        // asks user for ingredient they want to remove
                        // loops through the ingredients list and tracks whether ingredient was found or not

                        //prints out ingredients for user to see
                        IO.println("Ingredients:");
                        for (Ingredients ingredient : meal.getIngredients()) {
                            IO.println(ingredient.getName());
                        }
                        if (meal.getIngredients().size() == 0) {
                            IO.println("No Ingredients");
                        } else {
                            String ingredientToRemove = IO.readln("Enter Ingredient you want to Remove\n");
                            boolean ingredientFound = false;
                            for (int i = 0; i < meal.getIngredients().size(); i++) {
                                if (meal.getIngredients().get(i).getName().equalsIgnoreCase(ingredientToRemove)) {
                                    ingredientFound = true;
                                    // if found, use meal's remove ingredient method to properly remove the ingredient
                                    meal.removeIngredient(meal.getIngredients().get(i));
                                    IO.println("Ingredient Removed: " + ingredientToRemove);
                                    break;
                                }
                                if (!ingredientFound) {
                                    IO.println("Could Not Find Ingredient");
                                }
                            }
                        }
                        
                    } case "3" -> {
                        // Ask to update ingredients
                        String ingredientName = IO.readln("Enter ingredient you want to update (or type 'done' to finish):\n");
                        while (!ingredientName.equalsIgnoreCase("done")) {
                            // while loop to keep asking for ingredient updates until user types 'done'
                            // boolean to track if the ingredient was found
                            boolean ingredientFound = false;
                            for (Ingredients ingredient : meal.getIngredients()) {
                                // Check if the ingredient name matches (case-insensitive)
                                if (ingredient.getName().equalsIgnoreCase(ingredientName)) {
                                    // If the ingredient is found, ask for new values for each macro
                                    ingredientFound = true;
                                    // This try-catch block is repeated for all macros (calories, protein, carbs, fat, sugar) to handle invalid input and negative values
                                    // Repeat use because each macro is tracked the same way, but with a different name
                                    // Try-catch block used for parsing string to int, that way theres no crash for bad input
                                    try {
                                        // Ask user for new calorie value, if they press enter, keep current value
                                        String caloriesInput = IO.readln("Enter new calories (or press Enter to keep current):\n");
                                        if (!caloriesInput.isEmpty()) {
                                            //read in new calorie value and check if it is negative, if it is negative, do not change the ingredient's value
                                            int newCalories = Integer.parseInt(caloriesInput);
                                            if (newCalories >= 0) {
                                                // use meal's changeIngredientValue method to update the ingredient's calories
                                                // will also use this method to update the other macros as well
                                                meal.changeIngredientValue(ingredient, "calories", newCalories);
                                            } else {
                                                IO.println("Calorie value cannot be negative. Keeping current value.");
                                            }
                                        }
                                    } catch (NumberFormatException e) {
                                        IO.println("Invalid input for calories. Keeping current value.");
                                    }

                                    try {
                                        String proteinInput = IO.readln("Enter new protein (or press Enter to keep current):\n");
                                        if (!proteinInput.isEmpty()) {
                                            int newProtein = Integer.parseInt(proteinInput);
                                            if (newProtein >= 0) {
                                                meal.changeIngredientValue(ingredient, "protein", newProtein);
                                            } else {
                                                IO.println("Protein value cannot be negative. Keeping current value.");
                                            }
                                        }
                                    } catch (NumberFormatException e) {
                                        IO.println("Invalid input for protein. Keeping current value.");
                                    }
                                

                                    try {
                                        String carbsInput = IO.readln("Enter new carbs (or press Enter to keep current):\n");
                                        if (!carbsInput.isEmpty()) {
                                            int newCarbs = Integer.parseInt(carbsInput);
                                            if (newCarbs >= 0) {
                                                meal.changeIngredientValue(ingredient, "carbs", newCarbs);
                                            } else {
                                                IO.println("Carbs value cannot be negative. Keeping current value.");
                                            }
                                        }
                                    } catch (NumberFormatException e) {
                                        IO.println("Invalid input for carbs. Keeping current value.");
                                    }

                                    try {
                                        String fatInput = IO.readln("Enter new fat (or press Enter to keep current):\n");
                                        if (!fatInput.isEmpty()) {
                                            int newFat = Integer.parseInt(fatInput);
                                            if (newFat >= 0) {
                                                meal.changeIngredientValue(ingredient, "fat", newFat);
                                            } else {
                                                IO.println("Fat value cannot be negative. Keeping current value.");
                                            }
                                        }
                                    } catch (NumberFormatException e) {
                                        IO.println("Invalid input for fat. Keeping current value.");
                                    }

                                    try {
                                        String sugarInput = IO.readln("Enter new sugar (or press Enter to keep current):\n");
                                        if (!sugarInput.isEmpty()) {
                                            int newSugar = Integer.parseInt(sugarInput);
                                            if (newSugar >= 0) {
                                                meal.changeIngredientValue(ingredient, "sugar", newSugar);
                                            } else {
                                                IO.println("Sugar value cannot be negative. Keeping current value.");
                                            }
                                        }
                                    } catch (NumberFormatException e) {
                                        IO.println("Invalid input for sugar. Keeping current value.");
                                    }
                                    break; // Exit the loop after updating the ingredient
                                }
                            }
                            if (!ingredientFound) {
                                IO.println("Ingredient not found in the meal.");
                            }
                            ingredientName = IO.readln("Enter another ingredient to update (or type 'done' to finish):\n");
                        }
                    } default -> IO.println("Enter a valid option");
                }
                
            }
        }
        if (!mealFound) {
            IO.println("Meal not found.");
        } else {
            IO.println("Meal updated successfully!");
            IO.println(mealToUpdate.macrosPerServing());
        }
    }

    // method to view all meals in the meals arraylist
    private static void viewMeals() {
        // if list is empty, prints no meals available
        // if not, loop through list and print out the macros per serving
        if (meals.isEmpty()) {
            IO.println("No meals available.");
        } else {
            for (int i = 0; i < meals.size(); i++) {
                Meals meal = meals.get(i);
                IO.println(meal.macrosPerServing());
            }
        }   
    }


    // Method to load meals into Meals.txt
    private static void loadMeals() {
        // try-catch block used to open and close input stream automatically
        // this will write objects into a chosen file, in this case Meals.txt
        // when no more meals, it throws an exception, so catch the exception to know we wrote all the meals
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(MEALS_FILE))) {
            // loop until exception thrown
            // readObject() knows where every object starts and ends, so just read every object and then add it
            // all objects must get casted to the desired class, in this case, Meals
            while (true) {
                Meals meal = (Meals) ois.readObject();
                meals.add(meal);
            }
        } catch (EOFException e) {
            // End of file reached, no more meals to read
        } catch (IOException e) {
            IO.println("Error loading meals: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            IO.println("Error loading meals: Class not found.");
        }
    }

    // Method to add Meals
    private static void addMeal() {
        // boolean to track whether input was valid or not
        // used for every while loop in addMeal()
        boolean validInput = false;

        // prompts user for a meal name
        // if meal name is empty or just spaces, asks for a new name
        String mealName = IO.readln("Enter meal name:\n");
        while (mealName.trim().isEmpty()) {
            IO.println("Meal name cannot be empty. Please try again.");
            mealName = IO.readln("Enter meal name:\n");
        }
        
        // create servings variable for later use
        int servings = 0;
        while (!validInput) {
            // try-catch block for parsing ints
            try {
                servings = Integer.parseInt(IO.readln("Enter number of servings:\n"));
                // servings can not be 0 or negative
                // repeats until user inputs a valid serving (1 or greater)
                if (servings <= 0) {
                    IO.println("Number of servings must be greater than 0. Please try again.");
                } else {
                    validInput = true;
                }
            } catch (NumberFormatException e) {
                // catches whether user puts in something other than an integer
                IO.println("Invalid input for number of servings. Please enter a valid integer.");
            }
        }

        // creates a new meal using the name and servings found earlier and adds it to arraylist
        Meals meal = new Meals(mealName, servings);
        meals.add(meal);
        gatherMacrosAndAddIngredients(meal);

        IO.println("Meal added successfully!");
    }

    private static void gatherMacrosAndAddIngredients(Meals meal) {
        boolean validInput = false;
        boolean manual = false;
        boolean loop = true;
        IO.println("Enter Ingredients for the meal (type 'done' when finished):");
        if (System.getenv("FDC_API_KEY") == null) {
            IO.println("No API Key, must manually input ingredients");
            manual = true;
        } else {
            while(loop) {
                String choice = IO.readln("Would you like to search or manually input ingredients? (Type Manual or Search)\n");
                switch (choice.toLowerCase()) {
                    case "manual" -> {
                        manual = true;
                        loop = false;
                    }
                    case "search" -> {
                        String ingredient = IO.readln("What is the name of your Ingredient?\n");
                        while (true) {
                            if (ingredient.strip().isEmpty()) {
                                ingredient = IO.readln("Please enter a proper Ingredient name. What is the name of your Ingredient?\n");
                            } else {
                                break;
                            }
                        }
                        String[] results = FoodAPIClient.searchItem(ingredient);

                        if (results == null || results.length == 0) {
                            IO.println("No ingredients found. Switching to manual entry");
                            manual = true;
                            loop = false;
                            break;
                        }

                        for (int i = 0; i < results.length; i++) {
                            IO.println((i + 1) + ". " + results[i]);
                        }

                        int ingredientChoice = -1;
                        while (true) {
                            try {
                                ingredientChoice = Integer.parseInt(IO.readln("Select which ingredient matches the best (1-" + results.length + ")\n"));
                            } catch (NumberFormatException e) {
                                IO.println("Please enter a valid integer");
                            }
                            if (ingredientChoice - 1 >= 0 && ingredientChoice - 1 <= results.length - 1) {
                                int fdcIdIndex = results[ingredientChoice - 1].indexOf("ID: ");
                                String fdcId = results[ingredientChoice - 1].substring(fdcIdIndex + 4);
                                Ingredients userIngredient = FoodAPIClient.searchFDCID(fdcId);
                                IO.println("Ingredient added: " + userIngredient.toString());
                                meal.addIngredient(userIngredient);
                                break;
                            } else {
                                IO.println("Please enter a number in between 1 and " + results.length);
                            }
                        }
                        String again = IO.readln("Would you like to enter another ingredient? (y/n)\n");
                        while (!again.equalsIgnoreCase("y") && !again.equalsIgnoreCase("n")) {
                            again = IO.readln("Please enter a proper choice\n");
                        }
                        if (again.toLowerCase().equals("y")) {
                            loop = true;
                        } else {
                            loop = false;
                        }
                    }
                    default -> IO.println("Please enter a proper choice\n");
                }
            }
        }

        // Will continue asking for ingredients until user says they are done
        while (manual) {
            String ingredientName = IO.readln("Enter ingredient name (or 'done' to finish):\n");
            if (ingredientName.equalsIgnoreCase("done")) {
                break;
            }

            // variables to store macros
            int calories = 0;
            int protein = 0;
            int carbs = 0;
            int fat = 0;
            int sugar = 0;

            // Repeatedly asks for calories of the ingredient until user inputs a proper value (0 or greater)
            // Try-catch block used because strings are getting parsed
            // This while loop is repeated for every macro because they are all tracked the same, just with different names
            validInput = false;
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
            //creates new ingredient and then adds it to the meal
            Ingredients ingredient = new Ingredients(ingredientName, calories, protein, carbs, fat, sugar);
            meal.addIngredient(ingredient);
        }
    }
}
