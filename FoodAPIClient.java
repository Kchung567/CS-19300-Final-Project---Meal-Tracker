import java.net.http.HttpClient;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.net.URLEncoder;
import java.io.IOException;
import java.lang.InterruptedException;
import java.nio.file.Files;
import java.nio.file.Path;

// For Json Parsing
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class FoodAPIClient {
    private static String apiKey = System.getenv("FDC_API_KEY");
    

    //given an item (ingredient name) return a url to search for it
    public static String searchItem(String item) {
        String query = URLEncoder.encode(item, StandardCharsets.UTF_8);

        String url = "https://api.nal.usda.gov/fdc/v1/foods/search?query=" 
            + query 
            + "&api_key="
            + apiKey;
        
            return url;
    }

    public static Ingredients searchFDCID(String id) {
        String url = "https://api.nal.usda.gov/fdc/v1/food/" + id + "?api_key=" + apiKey;

        HttpClient client = HttpClient.newHttpClient();

        HttpResponse<String> response = null;

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .GET()
            .build();
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e){
            IO.println("Response took too long");
        }

        if (response.statusCode() == 200) {
            // Parsing the Json file pulled from the Food api
            // First grabs entire file as Json object
            // then gets name of ingredient
            // then grabs it's nutrition label
            // and then uses that to search for macros
            JsonObject root = JsonParser.parseString(response.body()).getAsJsonObject();
            String name = root.get("description").getAsString();
            JsonObject nutrients = root.getAsJsonObject("labelNutrients");
            
            // Calories
            JsonObject caloriesObject = nutrients.getAsJsonObject("calories");
            double calories = caloriesObject.get("value").getAsDouble();
            int roundedCalories = (int) Math.round(calories);

            // Protein
            JsonObject proteinsObject = nutrients.getAsJsonObject("protein");
            double proteins = proteinsObject.get("value").getAsDouble();
            int roundedProteins = (int) Math.round(proteins);

            // Carbs
            JsonObject carbsObject = nutrients.getAsJsonObject("carbohydrates");
            double carbs = carbsObject.get("value").getAsDouble();
            int roundedCarbs = (int) Math.round(carbs);

            // Fats
            JsonObject fatsObject = nutrients.getAsJsonObject("fat");
            double fats = fatsObject.get("value").getAsDouble();
            int roundedFats = (int) Math.round(fats);

            // Sugar
            JsonObject sugarsObject = nutrients.getAsJsonObject("sugars");
            double sugars = sugarsObject.get("value").getAsDouble();
            int roundedSugars = (int) Math.round(sugars);

            return new Ingredients(name, roundedCalories, roundedProteins, roundedCarbs, roundedFats, roundedSugars);
        }

        return null;
    }

    public static void main(String[] args) {
        
        Ingredients ingredient = searchFDCID("2012128");
        IO.println(ingredient.toString());
    }
}
