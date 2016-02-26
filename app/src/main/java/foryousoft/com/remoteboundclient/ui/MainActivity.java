package foryousoft.com.remoteboundclient.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import foryousoft.com.remoteboundclient.R;
import foryousoft.com.remoteboundclient.model.RemoteManager;

public class MainActivity extends AppCompatActivity {

    public static final int COUNT_ACTION = 100;
    public static final int UPPERCASE_ACTION = 101;

    private String TAG = getClass().getName();
    private Button countButton;
    private Button uppercaseButton;
    private EditText inputEditText;
    private RemoteManager remoteManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        remoteManager = RemoteManager.build(this);

        inputEditText = (EditText) findViewById(R.id.inputText);
        countButton = (Button) findViewById(R.id.countButton);
        uppercaseButton = (Button) findViewById(R.id.uppercaseButton);

        countButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = getInputText();
                remoteManager.sendMessage(message, COUNT_ACTION);
            }
        });
        uppercaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = getInputText();
                remoteManager.sendMessage(message, UPPERCASE_ACTION);
            }
        });
    }

    @Override
    protected void onStop() {

        super.onStop();
        remoteManager.unbind();
    }

    @Override
    protected void onStart() {

        super.onStart();
        remoteManager.bindToService();
    }

    public String getInputText() {
        return inputEditText.getText().toString();
    }
}
