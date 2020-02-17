package logic.messages;

import logic.messages.BooleanMessage;
import logic.messages.MessageTypes;

/**
 * Created by Kimiya :) on 04/07/2017.
 */
public class OpponentLeftMessage extends BooleanMessage {
    public OpponentLeftMessage(boolean left) {

        super(left, MessageTypes.OPPONENT_LEFT);

    }

    public OpponentLeftMessage(byte[] serialized) {
        super(serialized);
    }

    @Override
    public byte getMessageType() {
        return MessageTypes.OPPONENT_LEFT;
    }
}
