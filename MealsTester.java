import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

public class MealsTester {

    @Test
    public void testNewMealEmptyIngredientsList() {
        Meals meal = new Meals("Test Meal", 1);
        assertEquals(0, meal.getIngredients().size());
    }

    @Test
    public void testAddIngredient() {
        Meals meal = new Meals("Test Meal", 1);
        Ingredients ingredient1 = new Ingredients("Ingredient 1", 100, 10, 20, 5, 2);
        Ingredients ingredient2 = new Ingredients("Ingredient 2", 200, 15, 30, 10, 5);

        meal.addIngredient(ingredient1);
        meal.addIngredient(ingredient2);

        assertEquals(300, meal.getCalories());
        assertEquals(25, meal.getProtein());
        assertEquals(50, meal.getCarbs());
        assertEquals(15, meal.getFat());
        assertEquals(7, meal.getSugar());
    }

    @Test
    public void testGetIngredients() {
        Meals meal = new Meals("Test Meal", 1);
        Ingredients ingredient1 = new Ingredients("Ingredient 1", 100, 10, 20, 5, 2);
        Ingredients ingredient2 = new Ingredients("Ingredient 2", 200, 15, 30, 10, 5);

        meal.addIngredient(ingredient1);
        meal.addIngredient(ingredient2);

        assertEquals(2, meal.getIngredients().size());
        assertTrue(meal.getIngredients().contains(ingredient1));
        assertTrue(meal.getIngredients().contains(ingredient2));
    }

    
    
    @Test
    public void testRemoveIngredient() {
        Meals meal = new Meals("Test Meal", 1);
        Ingredients ingredient1 = new Ingredients("Ingredient 1", 100, 10, 20, 5, 2);
        Ingredients ingredient2 = new Ingredients("Ingredient 2", 200, 15, 30, 10, 5);

        meal.addIngredient(ingredient1);
        meal.addIngredient(ingredient2);
        String removedIngredientName = meal.removeIngredient(ingredient1);
        assertEquals("Ingredient 1", removedIngredientName);
        assertEquals(-1, meal.getIngredients().indexOf(ingredient1));
        assertEquals(1, meal.getIngredients().size());
        assertEquals(200, meal.getCalories());
        assertEquals(15, meal.getProtein());
        assertEquals(30, meal.getCarbs());
        assertEquals(10, meal.getFat());
        assertEquals(5, meal.getSugar());
    }

    @Test
    public void testRemoveNonExistentIngredient() {
        Meals meal = new Meals("Test Meal", 1);
        Ingredients ingredient1 = new Ingredients("Ingredient 1", 100, 10, 20, 5, 2);
        Ingredients ingredient2 = new Ingredients("Ingredient 2", 200, 15, 30, 10, 5);
        Ingredients ingredient3 = new Ingredients("Ingredient 3", 300, 20, 40, 15, 10);

        meal.addIngredient(ingredient1);
        meal.addIngredient(ingredient2);
        String removedIngredientName = meal.removeIngredient(ingredient3); // Attempt to remove an ingredient that was never added
        assertNull(removedIngredientName);
        assertEquals(300, meal.getCalories());
        assertEquals(25, meal.getProtein());
        assertEquals(50, meal.getCarbs());
        assertEquals(15, meal.getFat());
        assertEquals(7, meal.getSugar());
    }

    @Test
    public void testNewMealWithZeroServings() {
        Meals meal = new Meals("Test Meal", 0);
        assertEquals(1, meal.getServings(), "Servings should default to 1 if initialized with 0");
    }

    @Test
    public void testNewMealWithNegativeServings() {
        Meals meal = new Meals("Test Meal", -5);
        assertEquals(1, meal.getServings(), "Servings should default to 1 if initialized with a negative number");
    }


    @Test
    public void testMacrosPerServing() {
        Meals meal = new Meals("Test Meal", 2);
        Ingredients ingredient1 = new Ingredients("Ingredient 1", 100, 10, 20, 5, 2);
        Ingredients ingredient2 = new Ingredients("Ingredient 2", 200, 15, 30, 10, 5);

        meal.addIngredient(ingredient1);
        meal.addIngredient(ingredient2);

        assertEquals("Test Meal -- Calories: 150, Protein: 12, Carbs: 25, Fat: 7, Sugar: 3", meal.macrosPerServing());
    }

    @Test
    public void testMacrosPerServingWithZeroServings() {
        Meals meal = new Meals("Test Meal", 0);
        Ingredients ingredient1 = new Ingredients("Ingredient 1", 100, 10, 20, 5, 2);
        meal.addIngredient(ingredient1);
        assertEquals("Test Meal -- Calories: 100, Protein: 10, Carbs: 20, Fat: 5, Sugar: 2", meal.macrosPerServing(), "Macros per serving should be calculated correctly even with zero servings");
    }

    @Test
    public void testChangeIngredientValues() {
        Meals meal = new Meals("Test Meal", 1);
        Ingredients ingredient1 = new Ingredients("Ingredient 1", 100, 10, 20, 5, 2);
        meal.addIngredient(ingredient1);

        // Change the ingredient values using the changeIngredientValue method
        meal.changeIngredientValue(ingredient1, "calories", 200);
        meal.changeIngredientValue(ingredient1, "protein", 20);
        meal.changeIngredientValue(ingredient1, "carbs", 30);
        meal.changeIngredientValue(ingredient1, "fat", 10);
        meal.changeIngredientValue(ingredient1, "sugar", 5);
        assertEquals(200, meal.getCalories());
        assertEquals(20, meal.getProtein());
        assertEquals(30, meal.getCarbs());
        assertEquals(10, meal.getFat());
        assertEquals(5, meal.getSugar());
    }

    @Test
    public void testChangeIngredientValuesWithInvalidIngredient() {
        Meals meal = new Meals("Test Meal", 1);
        Ingredients ingredient1 = new Ingredients("Ingredient 1", 100, 10, 20, 5, 2);
        Ingredients ingredient2 = new Ingredients("Ingredient 2", 200, 15, 30, 10, 5);
        meal.addIngredient(ingredient1);

        // Attempt to change the values of an ingredient that is not part of the meal
        meal.changeIngredientValue(ingredient2, "calories", 300);
        meal.changeIngredientValue(ingredient2, "protein", 25);
        meal.changeIngredientValue(ingredient2, "carbs", 40);
        meal.changeIngredientValue(ingredient2, "fat", 15);
        meal.changeIngredientValue(ingredient2, "sugar", 8);

        // The meal's macros should remain unchanged because ingredient2 was not part of the meal
        assertEquals(100, meal.getCalories());
        assertEquals(10, meal.getProtein());
        assertEquals(20, meal.getCarbs());
        assertEquals(5, meal.getFat());
        assertEquals(2, meal.getSugar());
    }

    @Test
    public void testChangeIngredientValuesWithInvalidMacro() {
        Meals meal = new Meals("Test Meal", 1);
        Ingredients ingredient1 = new Ingredients("Ingredient 1", 100, 10, 20, 5, 2);
        meal.addIngredient(ingredient1);

        // Attempt to change a macro that does not exist
        meal.changeIngredientValue(ingredient1, "fiber", 10);

        // The meal's macros should remain unchanged because "fiber" is not a valid macro
        assertEquals(100, meal.getCalories());
        assertEquals(10, meal.getProtein());
        assertEquals(20, meal.getCarbs());
        assertEquals(5, meal.getFat());
        assertEquals(2, meal.getSugar());
    }

    @Test
    public void testChangeIngredientValuesWithNegativeValue() {
        Meals meal = new Meals("Test Meal", 1);
        Ingredients ingredient1 = new Ingredients("Ingredient 1", 100, 10, 20, 5, 2);
        meal.addIngredient(ingredient1);

        meal.changeIngredientValue(ingredient1, "calories", -300);
        meal.changeIngredientValue(ingredient1, "protein", -25);
        meal.changeIngredientValue(ingredient1, "carbs", -40);
        meal.changeIngredientValue(ingredient1, "fat", -15);
        meal.changeIngredientValue(ingredient1, "sugar", -8);

        // The meal's macros should remain unchanged because negative values are not allowed
        assertEquals(100, meal.getCalories()); 
        assertEquals(10, meal.getProtein());
        assertEquals(20, meal.getCarbs());
        assertEquals(5, meal.getFat());
        assertEquals(2, meal.getSugar());
    }

}