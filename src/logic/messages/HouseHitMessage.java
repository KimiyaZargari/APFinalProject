package logic.messages;

import java.nio.ByteBuffer;

/**
 * Created by Kimiya :) on 05/07/2017.
 */
public class  HouseHitMessage extends BaseMessage {
    private int house;

    public HouseHitMessage(int house){
        this.house = house;
        serialize();
    }

    public HouseHitMessage(byte[] serialized){
        super.serialized = serialized;
        deserialize();
    }

    @Override
    protected void serialize() {
        int messageLength = 4 + 1 + 1 + 4;
        ByteBuffer byteBuffer = ByteBuffer.allocate(messageLength);
        byteBuffer.putInt(messageLength);
        byteBuffer.put(MessageTypes.PROTOCOL_VERSION);
        byteBuffer.put(MessageTypes.HOUSE_HIT);
        byteBuffer.putInt(house);
        serialized = byteBuffer.array();

    }

    @Override
    protected void deserialize() {
        ByteBuffer byteBuffer = ByteBuffer.wrap(serialized);
        byteBuffer.getInt();
        byteBuffer.get();
        byteBuffer.get();
        house = byteBuffer.getInt();

    }

    public int getHouse() {
        return house;
    }

    @Override
    public byte getMessageType() {
        return MessageTypes.HOUSE_HIT;
    }
}
