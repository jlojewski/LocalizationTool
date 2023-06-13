package loc;

public class MessageLogManager {

    private static MessageLogManager messageLogManagerInstance;

    private MessageLogManager() {
    }

    public static MessageLogManager getInstance() {
        if (messageLogManagerInstance == null) {
            messageLogManagerInstance = new MessageLogManager();
        }

        return messageLogManagerInstance;
    }


    public void printMessage(String stringToPrint) {
        System.out.println(stringToPrint);


    }



}
