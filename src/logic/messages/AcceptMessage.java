package logic.messages;

/**
 * Created by Kimiya :) on 04/07/2017.
 */
public class AcceptMessage extends BooleanMessage {

    public AcceptMessage(boolean isAccepted) {
        super(isAccepted, MessageTypes.ACCEPT_REJECT);
    }

    public AcceptMessage(byte[] serialized) {
       super(serialized);
    }


    @Override
    public byte getMessageType() {
        return MessageTypes.ACCEPT_REJECT;
    }
}
