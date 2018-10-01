package ritik.e8085;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.vending.billing.IInAppBillingService;

import org.json.JSONException;
import org.json.JSONObject;

public class About extends AppCompatActivity {
    public static final int REQUEST_CODE = 77;
    TextView suggestion, developedby;
    private Button donate;
    IInAppBillingService mService;
    ServiceConnection mServiceConn = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
        }

        @Override
        public void onServiceConnected(ComponentName name,
                                       IBinder service) {
            mService = IInAppBillingService.Stub.asInterface(service);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        getSupportActionBar().hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        suggestion = findViewById(R.id.suggestions);
        suggestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                emailIntent.setType("text/plain");

                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"leotiklabs@gmail.com"});
                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Regarding 8085 Emulator App (Leotik Labs)");
                emailIntent.setType("message/rfc822");

                try {
                    startActivity(Intent.createChooser(emailIntent,
                            "Send using..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getApplicationContext(),
                            "No email clients installed.",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });
        donate = findViewById(R.id.donate_btn);
        donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Bundle buyIntentBundle = mService.getBuyIntent(3, getPackageName(), "donate_1", "inapp", "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAw35VvBnRZc7bzdG4VpWR39Q9EeV7nawuqwG2a4UnZupGkWTwDevsXM+t7Bv9MJXdwSCFpM/K/I74K73pRajnAszG2wF404KnGhqmUmHJ6lzx95K0V7zUbKY1S69+Fl/FloNXywBdA96OI8u9Z3bSTu0AujwlrWbBoxT0MjY9ApEjiYImHM2lUXDCM1ld5iOHv8Y9JI+H8jcSFiCE1xHkf/amddkF1pSMggM/ozbfRQJi3Ft5a7EQ1OJZC5eBtw3fq07YH5lOMdrwg5MpD1/nAuyohz0M2OnuUUhPUGrI4sJU2vI/6Clt7Fcb4AgVZvPXjG3xRG+DyKB+9q9OUjDRdQIDAQAB");
                    PendingIntent pendingIntent = buyIntentBundle.getParcelable("BUY_INTENT");
                    startIntentSenderForResult(pendingIntent.getIntentSender(),
                            REQUEST_CODE, new Intent(), Integer.valueOf(0), Integer.valueOf(0),
                            Integer.valueOf(0));

                } catch (Exception e) {


                }
            }
        });
        Intent serviceIntent =
                new Intent("com.android.vending.billing.InAppBillingService.BIND");
        serviceIntent.setPackage("com.android.vending");
        bindService(serviceIntent, mServiceConn, Context.BIND_AUTO_CREATE);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            int responseCode = data.getIntExtra("RESPONSE_CODE", 0);
            String purchaseData = data.getStringExtra("INAPP_PURCHASE_DATA");
            String dataSignature = data.getStringExtra("INAPP_DATA_SIGNATURE");

            if (resultCode == RESULT_OK) {
                try {
                    JSONObject jo = new JSONObject(purchaseData);
                    String sku = jo.getString("productId");
                    Toast.makeText(About.this, "Thank You !!", Toast.LENGTH_LONG);
                    donate.setEnabled(false);

                } catch (JSONException e) {
                    Toast.makeText(About.this, "Payment Failed, Please try again later or write us an email", Toast.LENGTH_LONG);
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mService != null) {
            unbindService(mServiceConn);
        }
    }


}
