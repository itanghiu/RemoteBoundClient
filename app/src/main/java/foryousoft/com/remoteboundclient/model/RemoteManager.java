package foryousoft.com.remoteboundclient.model;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

/**
 * Created by I-Tang HIU  on 15/02/2016.
 */
public class RemoteManager {

    public static final int SEND_MESSAGE = 10;
    public static final String REMOTE_SERVICE_ACTION = "com.foryousoft.REMOTE_SERVICE";
    public static final String REMOTE_SERVICE_PACKAGE_PATH = "foryousoft.com.remoteboundservice";
    public static String TEXT_MESSAGE_KEY = "TEXT_MESSAGE_KEY";
    public static String COUNT_KEY = "COUNT_KEY";
    public static String UPPERCASE_KEY = "UPPERCASE_KEY";

    private String TAG = getClass().getName();
    private static RemoteManager instance ;
    private Messenger messenger = null;
    private boolean isBound = false;
    private ServiceConnection connection = new RemoteServiceConnection();
    private Context context;

    private RemoteManager(Context context) {
        this.context = context;
    }

    public static RemoteManager build(Context context) {

        if (instance != null) return instance;
        instance = new RemoteManager(context);
        return instance;
    }

    public static RemoteManager get() {

        if (instance == null) throw new RuntimeException("Error. RemoteManager.build() method has to be called first before calling RemoteManager.get().");
        return instance;
    }

    public boolean bindToService() {

        Intent intent = new Intent(REMOTE_SERVICE_ACTION);
        intent.setClassName(REMOTE_SERVICE_PACKAGE_PATH, REMOTE_SERVICE_PACKAGE_PATH + ".engine.RemoteService");
        boolean bound = context.bindService(intent, connection, Context.BIND_AUTO_CREATE);
        Log.d(TAG, "bound: " + bound);
        return bound;
    }

    public void sendMessage(String text, int action) {

        try {
            Message msg = Message.obtain(null, SEND_MESSAGE, 0, 0);
            msg.replyTo = new Messenger(new ServerResponseHandler(context));
            msg.what = action;
            Bundle bundle = msg.getData();
            bundle.putString(TEXT_MESSAGE_KEY, text);
            messenger.send(msg);
        }
        catch (RemoteException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void unbind() {

        if (isBound) { // Unbind from the service
            context.unbindService(connection);
            isBound = false;
        }
    }

    private class RemoteServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName component, IBinder binder) {

            messenger = new Messenger(binder);
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName component) {

            messenger = null;
            isBound = false;
        }
    }
}
