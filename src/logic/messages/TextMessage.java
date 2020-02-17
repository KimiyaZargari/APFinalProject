package logic.messages;

import logic.messages.MessageTypes;
import logic.messages.StringMessage;

/**
 * Created by Kimiya :) on 05/07/2017.
 */
public class TextMessage extends StringMessage {
    public TextMessage(String message) {
        super(message, MessageTypes.TEXT_MESSAGE);

    }

    public TextMessage(byte[] serialized) {
        super(serialized);

    }

    @Override
    public byte getMessageType() {
        return MessageTypes.TEXT_MESSAGE;
    }
}
