
package kyrka.gpt_java_connectivity;

/**
 *
 * @author kyrka
 */
public class APIKey {

    private String apiKey = "REPLACE WITH YOUR API KEY";

    public APIKey ( String apiKey ) {
        this.apiKey = apiKey;
    }

    public APIKey () {
    }

    public String getApiKey () {
        return apiKey;
    }

    public void setApiKey ( String apiKey ) {
        this.apiKey = apiKey;
    }

}
