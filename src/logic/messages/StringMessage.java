package logic.messages;

import logic.messages.BaseMessage;
import logic.messages.MessageTypes;

import java.nio.ByteBuffer;

/**
 * Created by Kimiya :) on 05/07/2017.
 */
public abstract class StringMessage extends BaseMessage {
   private String str;
    byte messageType;

    protected StringMessage(String str, byte messageType) {
        this.str = str;
        this.messageType = messageType;
        serialize();
    }

    public StringMessage(byte[] serialized) {
        super.serialized = serialized;
        deserialize();
    }

    @Override
    protected void serialize() {
        int messageLength = 4 + 1 + 1 + 4 + str.length();
        ByteBuffer byteBuffer = ByteBuffer.allocate(messageLength);
        byteBuffer.putInt(messageLength);
        byteBuffer.put(MessageTypes.PROTOCOL_VERSION);
        byteBuffer.put(messageType);
        byteBuffer.putInt(str.length());
        byteBuffer.put(str.getBytes());
        serialized = byteBuffer.array();

    }

    @Override
    protected void deserialize() {
        ByteBuffer byteBuffer = ByteBuffer.wrap(serialized);
        byteBuffer.getInt();
        byteBuffer.get();
        byteBuffer.get();
        int textLength = byteBuffer.getInt();
        byte[] nameBytes = new byte[textLength];
        byteBuffer.get(nameBytes);
        this.str = new String(nameBytes);
    }

    public String getStr() {
        return str;
    }
}
