
package kyrka.gpt_java_connectivity;

/**
 *
 * @author kyrka
 */
import com.theokanning.openai.OpenAiService;
import com.theokanning.openai.completion.CompletionRequest;
import java.time.Duration;


public class GPT3Manager {

    OpenAiService service;
    APIKey apiKey;
    private String answer;

    public GPT3Manager () {
        apiKey = new APIKey ();

        // keep the time to 20 sec. By lowering the duration it might run out of responce time providing timeout exception
        service = new OpenAiService ( apiKey.getApiKey (), Duration.ofMillis ( 120000 ) );
         
    }

    public void setQuestion ( String question ) {
        CompletionRequest completionRequest = CompletionRequest.builder ()
                .model ( "text-davinci-003" )
                .prompt ( question )
                .temperature ( 0.7 )
                .maxTokens ( 4000 )
                .echo ( false ) // prompt the user question in response
                .build ();
        answer = service.createCompletion ( completionRequest ).getChoices ().get ( 0 ). getText ();

    }

    public String getAnswer () {
        return answer;
    }

    
}
