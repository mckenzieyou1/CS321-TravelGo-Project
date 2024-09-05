import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UserLocation {

    private static ObjectMapper objectMapper;
    private static String APIKey = "AIzaSyBAGQj01h9-wXPQGnIVP0WaICXcCG_joiE";
    private static Scanner scan;
    private static String address;
    private static double latitude;
    private static double longitude;

    public UserLocation(String input) {
        address = null;
        latitude = -1;
        longitude = -1;

        getUserInput(input);
    }

    public String getAddress() {
        return address;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    private static HttpResponse<String> makeHttpRequest(String input) throws Exception{
        //Reformats address to add to HTTP request.
        String[] array = input.split(" ");
        String urlAddress = String.join("+", array);

        //Makes HTTP Request to Geocode API.
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(
                        "https://maps.googleapis.com/maps/api/geocode/json?address="
                        + urlAddress
                        + "&key="
                        + APIKey))
                .header("accept", "application/json")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        return response;
    }

    /**
     * 
     * @param map HashMap containing key-value pairs from the JSON file.
     * @return A string with the formatted address.
     * @throws Exception If the initial HTTP request was unsuccessful, an exception is thrown.
     */
    private static ArrayList<Object> extractJsonValues(HashMap<String, Object> map) throws Exception{
        //Checks for any results.
        String success = (String) map.get("status");
        if (!success.equals("OK")) {
            throw new Exception("Not valid address result.");
        }

        //Extracts the array list containing all values.
        ArrayList<Object> results = (ArrayList<Object>) map.get("results");

        //Arraylist to store extracted values in.
        ArrayList<Object> list = new ArrayList<>();
        
        //Goes down the hierarchy to get the needed values.
        map = (HashMap<String, Object>) results.get(0);

        list.add(map.get("formatted_address"));

        map = (HashMap<String, Object>) map.get("geometry");
        map = (HashMap<String, Object>) map.get("location");

        //Add latitude and longitude to list.
        list.add(map.get("lat"));
        list.add(map.get("lng"));

        return list;
    }

    private static void getUserInput(String input) {
        //Getting the user's current location.
        try {
            //Connects to API to get JSON file with 
            HttpResponse<String> response = makeHttpRequest(input);

            if (response.statusCode() < 200 || response.statusCode() > 299) {
                throw new Exception("Could not load address.");
            }

            objectMapper = new ObjectMapper();

            //Create a hashmap to store the key-value pairs in the JSON file.
            HashMap<String, Object> map = objectMapper.readValue(response.body(), new TypeReference<HashMap<String, Object>>() {});

            //Gets extracted values from JSON file in following format: [Formatted address, latitude, longitude]
            ArrayList<Object> values = extractJsonValues(map);


            address = (String)values.get(0);
            latitude = (Double)values.get(1);
            longitude = (Double)values.get(2);    

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}