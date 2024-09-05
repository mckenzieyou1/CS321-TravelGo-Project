import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;

import java.io.*;
import java.util.*;
import java.io.*;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.awt.Image;

public class GoogleAPI {

    public static List<List<String>> content;
    public static List<String> labels;

    public static ObjectMapper mapper;
    public static ObjectReader reader;

    public GoogleAPI() {
        content = new ArrayList<>();
        labels = new ArrayList<>();
        mapper = new ObjectMapper();
        reader = mapper.reader();
    }

    // Got these httprequest templates from the TripAdvisor API

    /**
     * Makes a call to the Google Maps API with the requested information.
     * 
     * @param lat    String representing the latitude of user
     * @param lng    String representing the longitude of user
     * @param radius String representing the distance a user is willing to walk in
     *               meters
     * @param type   String representing the location's category
     * @param price  String representing the max price level
     * @return a List of Strings containing the requested information for one
     *         randomly generated place returned by the API
     */
    public List<String> makeCall(String lat, String lng, String radius, String type, String price) {
        try {
            String placeID = nearbySearch(lat, lng, radius, type, price);
            HttpResponse<String> response = placeDetails(placeID);
            return getInfo(response);
        } catch (IOException | InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            //In case no results were found.
            List<String> response = new ArrayList<>();
            response.add(e.getMessage());
            return response;
        }
        return null;
    }

    /**
     * Searches for places nearby to the given coordinate pair of type type.
     * 
     * @param lat    String representing the latitude of user
     * @param lng    String representing the longitude of user
     * @param radius String representing the distance a user is willing to walk in
     *               meters
     * @param type   String representing the location's category
     * @param price  String representing the max price level
     * @return a String representing the placeID of a random nearby location
     * @throws IOException
     * @throws InterruptedException
     * @throws Exception if there are no results
     */
    //@SuppressWarnings("unchecked")
    public static String nearbySearch(String lat, String lng, String radius, String type, String price)
            throws Exception, IOException, InterruptedException {

        // Makes a request to the Google Maps API.
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(
                        "https://maps.googleapis.com/maps/api/place/nearbysearch/json" +
                                "?location=" + lat + "%2C" + lng +
                                "&maxprice=" + price +
                                "&radius=" + radius +
                                "&type=" + type +
                                "&key=AIzaSyAHZDdxXDHR5tqWF_N4LOF7OjPJk_OLcAQ"))
                .header("accept", "application/json")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        HashMap<String, Object> map = mapper.readValue(response.body(), new TypeReference<HashMap<String, Object>>() {
        });

        // Gets results.
        List<Object> resultList = new ArrayList<>();
        resultList = (List<Object>) map.get("results");

        // Generates a number to randomly select a location returned by the API.
        Random random = new Random();
        int numResults = resultList.size();
        if (numResults > 0) {
            int randomNum = random.nextInt(numResults);
            // Retrieves the placeID of one of the generated locations.
            HashMap<String, Object> rMap = (HashMap<String, Object>) resultList.get(randomNum);
            String placeID = (String) rMap.get("place_id");
            return placeID;
        } else {
            throw new Exception("No results.");
        }

    }

    /**
     * Gets the details of a place specified by its place ID.
     * 
     * @param placeID String representing a place in the Google Maps API
     * @return a list of location details in JSON format as an HttpResponse<String>
     * @throws IOException
     * @throws InterruptedException
     */
    public static HttpResponse<String> placeDetails(String placeID) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(
                        "https://maps.googleapis.com/maps/api/place/details/json" +
                                "?fields=name%2Ceditorial_summary%2Cwebsite%2Cprice_level%2Cgeometry%2Cphotos%2Ctype%2Cformatted_address"
                                +
                                "&place_id=" + placeID +
                                "&key=AIzaSyAHZDdxXDHR5tqWF_N4LOF7OjPJk_OLcAQ"))
                .header("accept", "application/json")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        return response;
    }

    public static List<String> getInfo(HttpResponse<String> response)
            throws JsonMappingException, JsonProcessingException {
        // mapper = new ObjectMapper();
        // reader = mapper.reader();

        HashMap<String, Object> map = mapper.readValue(response.body(), new TypeReference<HashMap<String, Object>>() {
        });

        // Gets results.
        map = (HashMap<String, Object>) map.get("result");

        String name = map.get("name").toString();
        String geometry = map.get("geometry").toString();
        int latStart = geometry.indexOf("lat=") + 4; // dont need?
        int lngStart = geometry.indexOf("lng=") + 4; // dont need?
        String lat = geometry.substring(latStart, latStart + 10);
        String lng = geometry.substring(lngStart, lngStart + 10);
        String address = map.get("formatted_address").toString();
        String website = map.get("website").toString();
        String priceLevel = map.get("price_level").toString();

        //Gets the photo reference, which is needed to get the photo corresponding to the place.
        List<Object> photos = (List<Object>) map.get("photos");
        map = (HashMap<String, Object>) photos.get(0);
        String photoReference = map.get("photo_reference").toString();

        // Creates return list.
        List<String> results = new ArrayList<>();
        results.add(name);
        results.add(lat);
        results.add(lng);
        results.add(address);
        results.add(website);
        results.add(priceLevel);
        results.add(photoReference);

        return results;
    }

    /**
     * Makes an API call to get the photo from the current location.
     * @param photoReference a required parameter for the API call.
     * @return an ImageIcon object to be displayed on the GUI.
     */
    public ImageIcon getPhoto(String photoReference) {

        HttpClient client = HttpClient.newBuilder()
                .followRedirects(Redirect.ALWAYS)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(
                        "https://maps.googleapis.com/maps/api/place/photo?"
                        + "maxwidth=350"
                        + "&photo_reference=" + photoReference
                        + "&key=AIzaSyAHZDdxXDHR5tqWF_N4LOF7OjPJk_OLcAQ"))
                .header("accept", "application/json")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        try {
            HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());

            Image img = ImageIO.read(response.body());

            if (img == null) 
                throw new Exception("Image not available.");

            ImageIcon imgIcon = new ImageIcon(img);
            return imgIcon;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
        
    }

    public static void main(String[] args) {
        GoogleAPI caller = new GoogleAPI();
        // System.out.println(makeCall("48.1907461", "16.3416097", "2500", "cafe",
        // "3"));

    }
}
