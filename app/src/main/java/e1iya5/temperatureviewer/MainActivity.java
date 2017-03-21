package e1iya5.temperatureviewer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.loopj.android.http.*;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button refreshButton = (Button) findViewById(R.id.refreshButton);
        final TextView tVvalue = (TextView) findViewById(R.id.tVvalue);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AsyncHttpClient client = new AsyncHttpClient();
                client.get("http://sayoservices.net/temperature.php", new AsyncHttpResponseHandler() {
                    @Override
                    public void onStart() {
                        tVvalue.setText("[...]");
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        String response = new String(responseBody);
                        Log.w("response", response);
                        double value = 0;
                        try {
                            JSONObject reader = new JSONObject(response);
                            value = reader.getDouble("value");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        tVvalue.setText(value+"°C");
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        tVvalue.setText("Fehler");
                    }
                });
            }
        });
    }
}
