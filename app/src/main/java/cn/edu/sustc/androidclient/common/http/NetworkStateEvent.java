package cn.edu.sustc.androidclient.common.http;

public class NetworkStateEvent {
    private boolean connected;
    public NetworkStateEvent(boolean connected){
        this.connected = connected;
    }

    public boolean isConnected() {
        return connected;
    }
}
