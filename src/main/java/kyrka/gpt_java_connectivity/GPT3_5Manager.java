
package kyrka.gpt_java_connectivity;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author kyrka
 */
public class GPT3_5Manager {

    private static final String API_KEY ="REPLACE WITH YOUR API KEY";
    private static final String API_URL = "https://api.openai.com/v1/chat/completions";
    private String answer;

    public GPT3_5Manager () {
    }

    public String generateText ( List<String> messages, String userMessage, int maxTokens, double temperature ) throws IOException {
        OkHttpClient client = new OkHttpClient.Builder ()
                .readTimeout ( 120, TimeUnit.SECONDS ) 
                .writeTimeout ( 120, TimeUnit.SECONDS ) 
                .connectTimeout ( 120, TimeUnit.SECONDS ) 
                .build ();
        Gson gson = new Gson ();

        List<Map<String, String>> messageObjects = new ArrayList<> ();
        for ( String message : messages ) {
            String role = message.startsWith ( "User:" ) ? "user" : "assistant";
            String content = message.substring ( message.indexOf ( " " ) + 1 );
            Map<String, String> messageObject = new HashMap<> ();
            messageObject.put ( "role", role );
            messageObject.put ( "content", content );
            messageObjects.add ( messageObject );
        }
        Map<String, String> userInput = new HashMap<> ();
        userInput.put ( "role", "user" );
        userInput.put ( "content", userMessage );
        messageObjects.add ( userInput );

        Map<String, Object> jsonBody = new HashMap<> ();
        jsonBody.put ( "model", "gpt-3.5-turbo" );
        jsonBody.put ( "messages", messageObjects );
        jsonBody.put ( "temperature", temperature );
        jsonBody.put ( "max_tokens", maxTokens );

        String jsonString = gson.toJson ( jsonBody );
        okhttp3.RequestBody requestBody = okhttp3.RequestBody.create ( MediaType.parse ( "application/json" ), jsonString );
        System.out.println ( "User send: " + userMessage ); // testing the user message 
        Request request = new Request.Builder ()
                .url ( API_URL )
                .addHeader ( "Content-Type", "application/json" )
                .addHeader ( "Authorization", "Bearer " + API_KEY )
                .post ( requestBody )  
                .build ();

        // Execute the request
        try ( Response response = client.newCall ( request ).execute () ) {
            if ( !response.isSuccessful () ) {
                // Return a custom error message when the API call fails
                return "I'm sorry, I couldn't process your request. Please try again with a different input.";
            }

            // Parse the response
            String responseBodyJson = response.body ().string ();
            ApiResponse apiResponse = gson.fromJson ( responseBodyJson, ApiResponse.class );
            System.out.println ( "API Response: " + responseBodyJson ); // testing the API response 
            // Return the generated text
            String aiResponse = apiResponse.choices.get ( 0 ).message.content.trim ();
            messages.add ( "AI: " + aiResponse );
            answer = aiResponse;
            return aiResponse;
        } catch (java.net.SocketTimeoutException e) {
        // Show an info message when a request timeout occurs
        javax.swing.JOptionPane.showMessageDialog(null, "The request has timed out. Please try again later.", "Request Timeout", javax.swing.JOptionPane.INFORMATION_MESSAGE);
        return "I'm sorry, I couldn't process your request due to a timeout. Please try again later.";
    }
    }

    public String getAnswer () {
        return answer;
    }

    static class ApiResponse {

        List<Choice> choices;

        static class Choice {

            Message message;

            static class Message {

                String role;
                String content;
            }
        }
    }
}
