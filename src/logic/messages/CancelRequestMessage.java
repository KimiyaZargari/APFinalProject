package logic.messages;

/**
 * Created by Kimiya :) on 10/07/2017.
 */
public class CancelRequestMessage extends StringMessage {
    public CancelRequestMessage(String ip) {
        super(ip, MessageTypes.CANCEL_REQUEST);
    }

    public CancelRequestMessage(byte[] serialized) {
        super(serialized);
    }

    @Override
    public byte getMessageType() {
        return MessageTypes.CANCEL_REQUEST;
    }
}
