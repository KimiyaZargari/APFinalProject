package logic.messages;

/**
 * Created by Kimiya :) on 08/07/2017.
 */
public class ServerNameMessage extends StringMessage {
    public ServerNameMessage(String name) {

        super(name, MessageTypes.Server_name);
    }

    public ServerNameMessage(byte[] serialized){
        super(serialized);
    }


    @Override
    public byte getMessageType() {
        return MessageTypes.Server_name;
    }

    public String getName() {
        return super.getStr();
    }

}
