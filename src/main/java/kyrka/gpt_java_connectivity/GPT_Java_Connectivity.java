
package kyrka.gpt_java_connectivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author kyrka
 * Use the runLegacyModel ()  to produce unexpected results 
 */
public class GPT_Java_Connectivity {

    public static void main ( String[] args ) throws IOException {
        // The unexpected results stem from the legacy model.
        runLegacyModel () ;
        // The default works perfect, w/o producing unexpected results
        runDefaultModel ();
    }

    public static void runLegacyModel () {
        Scanner in = new Scanner ( System.in );
        GPT3Manager gptLegacy = new GPT3Manager ();
        String prompt;

        System.out.println ( "Hi I am GPT Legacy Model. Ask me anything " );
        prompt = in.nextLine ();

        gptLegacy.setQuestion ( prompt );
        System.out.println ( gptLegacy.getAnswer () );
    }

    public static void runDefaultModel () throws IOException {
        Scanner in = new Scanner ( System.in );
        GPT3_5Manager gptDefault = new GPT3_5Manager ();
        
        // User's prompt
        String prompt;
        //This list contains the user's prompts, which can be used for conversational chat purposes. 
        List<String> chatHistory = new ArrayList<> ();
        
        System.out.println ( "Hi I am GPT Legacy Model. Ask me anything " );
        prompt = in.nextLine ();
        
        gptDefault.generateText ( chatHistory, prompt, 2000, 0.7 );
        System.out.println ( gptDefault.getAnswer () );
    }
}
