async function sendMessage() {
    const userInput = document.getElementById("user-input").value;
    if (!userInput.trim()) return;

    appendMessage("You", userInput);

    try {
        const response = await fetch("/api/chat", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(userInput)
        });

        const aiResponse = await response.text();
        appendMessage("Alex", aiResponse);
    } catch (error) {
        appendMessage("Alex", "Error: Unable to connect to AI.");
    }

    document.getElementById("user-input").value = "";
}

async function clearChat() {
    try {
        await fetch("/api/chat/clear", { method: "POST" });
        document.getElementById("chat-box").innerHTML = ""; // Clear UI chat history
    } catch (error) {
        console.error("Error clearing chat:", error);
    }
}

function appendMessage(sender, message) {
    const chatBox = document.getElementById("chat-box");
    const messageDiv = document.createElement("div");
    messageDiv.innerHTML = `<strong>${sender}:</strong> ${message}`;
    chatBox.appendChild(messageDiv);
    chatBox.scrollTop = chatBox.scrollHeight;
}
