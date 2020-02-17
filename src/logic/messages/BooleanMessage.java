package logic.messages;

import java.nio.ByteBuffer;

/**
 * Created by Kimiya :) on 04/07/2017.
 */
public abstract class BooleanMessage extends BaseMessage {
    private byte booleanMessage;
    private byte messageType;

    protected BooleanMessage(boolean booleanMessage, byte messageType) {
        this.booleanMessage = booleanMessage ? (byte) 1 : (byte) 0;
        this.messageType = messageType;
        serialize();
    }

    protected BooleanMessage(byte[] serialized){
        super.serialized = serialized;
        deserialize();
    }

    @Override
    protected void serialize() {
        int messageLength = 4 + 1 + 1 + 1;
        ByteBuffer byteBuffer = ByteBuffer.allocate(messageLength);
        byteBuffer.putInt(messageLength);
        byteBuffer.put(MessageTypes.PROTOCOL_VERSION);
        byteBuffer.put(messageType);
        byteBuffer.put(booleanMessage);
        serialized = byteBuffer.array();
    }

    @Override
    protected void deserialize() {
        ByteBuffer byteBuffer = ByteBuffer.wrap(serialized);
        byteBuffer.getInt();
        byteBuffer.get();
        byteBuffer.get();
        this.booleanMessage = byteBuffer.get();
    }

    public byte getBooleanMessage() {
        return booleanMessage;
    }
}
