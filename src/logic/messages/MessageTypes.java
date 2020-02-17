package logic.messages;

/**
 * Created by Kimiya :) on 03/07/2017.
 */
public class MessageTypes {



    /**
     * Version of communication protocol
     */
    public static final byte PROTOCOL_VERSION = 1;

    public static final byte SEND_REQUEST = 1;
    public static final byte ACCEPT_REJECT = 2;
    public static final byte OPPONENT_READY = 3;
    public static final byte OPPONENT_LEFT = 4;
    public static final byte TEXT_MESSAGE = 5;
    public static final byte HOUSE_HIT = 6;
    public static final byte IS_SHIP = 7;
    public static final byte Server_name = 8;
    public static final byte CANCEL_REQUEST = 9;
}
