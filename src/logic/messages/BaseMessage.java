package logic.messages;

/**
 * Created by Kimiya :) on 03/07/2017.
 */
public abstract class BaseMessage {
    protected byte[] serialized;

    /**
     * Fields are stored into serial bytes in this method
     */
    protected abstract void serialize();

    /**
     * Fields are restored from serial bytes in this method
     */
    protected abstract void deserialize();

    /**
     * Return message type code
     */
    public abstract byte getMessageType();

    public byte[] getSerialized() {
        return serialized;
    }


}
