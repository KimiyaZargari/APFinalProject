package logic.messages;

import logic.messages.BooleanMessage;
import logic.messages.MessageTypes;

/**
 * Created by Kimiya :) on 04/07/2017.
 */
public class OpponentReadyMessage extends BooleanMessage {

    public OpponentReadyMessage(boolean isReady) {
        super(isReady, MessageTypes.OPPONENT_READY);
    }

    public OpponentReadyMessage(byte[] serialized) {
        super(serialized);
    }

    @Override
    public byte getMessageType() {

        return MessageTypes.OPPONENT_READY;
    }

}
