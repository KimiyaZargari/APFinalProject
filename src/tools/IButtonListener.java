package tools;

/**
 * Created by Kimiya :) on 07/07/2017.
 */
public interface IButtonListener {
    void onAcceptClicked(String ip);

    void onRejectClicked(String ip);

    void onReadyClicked();

    void onCancelClicked();

    void onHouseClicked(int house);

    void onLeaveClicked();
    void onWaitCancelClicked();

}
