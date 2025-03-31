package com.example.demo;

import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class ChatClient {
    private final OllamaChatModel chatModel;
    private final List<Message> chatHistory = new ArrayList<>(); 

    public ChatClient(OllamaChatModel chatModel) {
        this.chatModel = chatModel;
    }

    public synchronized String call(String userInput) {
        // Add user message to chat history
        UserMessage userMessage = new UserMessage(userInput);
        chatHistory.add(userMessage);

        // ✅ FIX: Pass List<Message> to Prompt (not List<UserMessage>)
        Prompt chatPrompt = new Prompt(chatHistory);

        // Call Ollama model
        ChatResponse response = chatModel.call(chatPrompt);

        // Extract assistant's message
        AssistantMessage assistantMessage = (AssistantMessage) response.getResults().get(0).getOutput();

        // Store AI response in chat history
        chatHistory.add(assistantMessage);

        // Return AI response text
        return assistantMessage.getText();
    }

    public synchronized void clearChat() {
        chatHistory.clear();
    }
}
