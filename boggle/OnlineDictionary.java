package boggle;

import java.util.HashMap;

/**
 * The Dictionary class for the first Assignment in CSC207, Fall 2022
 * The Dictionary will contain lists of words that are acceptable for Boggle 
 */
public class OnlineDictionary {

    /**
     * set of legal words for Boggle
     *  we will load the words depeding on the users preferences.
     *  but once a word is loaded, we want to temporariyl keep the word until the round is ended.
     */
    private HashMap<String, String> fetchedWords;

    private int wordCount;

    /**
     * Class constructor
     *
     * This initializes the Online Dictionary.
     */
    public OnlineDictionary() {
        this.wordCount = 0;
        this.fetchedWords = new HashMap<String, String>();

    }

    /* 
     * Checks to see the meaning of the provided word and adds the meaning to the dictionary
     *
     * @param word  The word to check
     * @return  The meaning of the word found
     */
    public String fetchWordMeaning(String word) {
        // Check if the word is already in the dictionary
        // fetch the meanig of the word
        // Create a neat value object to hold the URL


//        URL url = new URL("https://api.nasa.gov/planetary/apod?api_key=DEMO_KEY");
//
//// Open a connection(?) on the URL(??) and cast the response(???)
//        HttpURLConnection connection = null;
//        try {
//            connection = (HttpURLConnection) url.openConnection();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//// Now it's "open", we can set the request method, headers etc.
//        connection.setRequestProperty("accept", "application/json");
//
//// This line makes the request
//        InputStream responseStream = null;
//        try {
//            responseStream = connection.getInputStream();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//// Manually converting the response body InputStream to APOD using Jackson
//        ObjectMapper mapper = new ObjectMapper();
//        APOD apod = mapper.readValue(responseStream, APOD.class);
//
//// Finally we have the response
//        System.out.println(apod.title);
//




        // Fake example transaction ID: 3YC00XQKNVMZ
//        String url = "https://api.dictionaryapi.dev/api/v2/entries/en/" + word;
//        Header headerKey = new Header("X-Kount-Api-Key", "<!--Actual Kount RIS/API Key goes here-->");
//
//        // Create an instance of HttpClient.
//        HttpClient client = new HttpClient();
//
//        // Create a method instance.
//        GetMethod method = new GetMethod(url);
//
//        // Provide custom retry handler is necessary
//        method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
//                new DefaultHttpMethodRetryHandler(3, false));
//        method.addRequestHeader(headerKey);
//
//        try {
//            // Execute the method.
//            int statusCode = client.executeMethod(method);
//
//            if (statusCode != HttpStatus.SC_OK) {
//                System.err.println("Method failed: " + method.getStatusLine());
//            }
//
//
//            // Read the response body.
//            byte[] responseBody = method.getResponseBody();
//
//            // Deal with the response.
//            // Use caution: ensure correct character encoding and is not binary data
//            System.out.println(new String(responseBody));
//
//        } catch (HttpException e) {
//            System.err.println("Fatal protocol violation: " + e.getMessage());
//            e.printStackTrace();
//        } catch (IOException e) {
//            System.err.println("Fatal transport error: " + e.getMessage());
//            e.printStackTrace();
//        } finally {
//            // Release the connection.
//            method.releaseConnection();
//        }
//    }
        //add the meaning of the word
        // increase the word count
        // return the meaning of the word
 throw new UnsupportedOperationException();
    }

    /*
     * Checks the number of successful API calls made. In other words, the number of words in the dictionary
     *
     *
     * @return  number of successful API calls
     */
    public int getWordCount() {
        return this.wordCount;

    }


}
