package logic.messages;

/**
 * Created by Kimiya :) on 04/07/2017.
 */
// socket.getRemoteSocketAddress().toString();
public class SendRequest extends StringMessage {



    public SendRequest(String name) {

        super(name, MessageTypes.SEND_REQUEST);
    }

    public SendRequest(byte[] serialized){
        super(serialized);
    }


    @Override
    public byte getMessageType() {
        return MessageTypes.SEND_REQUEST;
    }

    public String getName() {
        return super.getStr();
    }

}
