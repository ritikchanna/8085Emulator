package ritik.e8085;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class About extends AppCompatActivity {
    TextView suggestion, developedby;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        getSupportActionBar().hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        suggestion = (TextView) findViewById(R.id.suggestions);
        suggestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                emailIntent.setType("text/plain");

                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"chnritik@gmail.com"});
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
        developedby = (TextView) findViewById(R.id.developer);
        developedby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (developedby.getText().equals("Ritik Channa"))
                    developedby.setText("Leotik Labs");
                else
                    developedby.setText("Ritik Channa");
            }
        });
    }


}
