package foryousoft.com.remoteboundclient.model;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by I-Tang HIU  on 10/02/2016.
 */
public class ServerResponseHandler extends Handler {

    public static final int COUNT_ACTION = 100;
    public static final int UPPERCASE_ACTION = 101;
    private Context context;

    public ServerResponseHandler(Context context) {
        this.context = context;
    }

    @Override
    public void handleMessage(Message msg) {

        int action = msg.what;
        switch(action) {
           case COUNT_ACTION:
               int count = msg.getData().getInt(RemoteManager.COUNT_KEY);
               Toast.makeText(context, "Remote Service answer:  value:" + count , Toast.LENGTH_LONG).show();
            break;

            case UPPERCASE_ACTION:
                String textMessage = msg.getData().getString(RemoteManager.UPPERCASE_KEY);
                Toast.makeText(context, "Remote Service answer:  value:" + textMessage , Toast.LENGTH_LONG).show();
                break;
        }
    }
}