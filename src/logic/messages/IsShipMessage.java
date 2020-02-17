package logic.messages;

/**
 * Created by Kimiya :) on 05/07/2017.
 */
public class IsShipMessage extends BooleanMessage {
    public IsShipMessage(boolean isShip){
        super(isShip, MessageTypes.IS_SHIP);
    }

    public IsShipMessage(byte[] serialized){
        super(serialized);

    }

    @Override
    public byte getMessageType() {
        return MessageTypes.IS_SHIP;
    }
}
