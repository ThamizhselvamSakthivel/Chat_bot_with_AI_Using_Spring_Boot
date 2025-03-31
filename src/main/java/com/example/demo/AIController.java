package com.example.demo;

import java.text.BreakIterator;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AIController {
 
     private final ChatClient chatClient;

      public AIController(ChatClient chatClient) {
          this.chatClient = chatClient;
      }

      @PostMapping("/api/chat")
      public ResponseEntity<String> chat(@RequestBody String userInput) {
          String response = chatClient.call(userInput);
          return ResponseEntity.ok(beautifyText(response));
      }

      public static String beautifyText(String text) {
        BreakIterator iterator = BreakIterator.getSentenceInstance();
        iterator.setText(text);

        StringBuilder formattedText = new StringBuilder();
        int start = iterator.first();

        for (int end = iterator.next(); end != BreakIterator.DONE; start = end, end = iterator.next()) {
            formattedText.append(text.substring(start, end).trim()).append("\n\n");
        }

        return formattedText.toString();
    }

    @PostMapping("/api//clear")
    public String clearChat() {
        chatClient.clearChat();
        return "Chat history cleared.";
    }

}
