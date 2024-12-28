package by.uni.lab4_activityintentfs;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class DetailItemActivity extends AppCompatActivity {
    Intent intent;
    Intent back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail_item);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        intent = new Intent(this, MainActivity.class);
        back = new Intent(this, ThirdStepActivity.class);

        LinearLayout detailLayout = findViewById(R.id.detailLayout);

        if (getIntent().getExtras() != null) {
            intent.putExtras(getIntent().getExtras());
            back.putExtras(getIntent().getExtras());

            for (String key : intent.getExtras().keySet()) {
                String value = "";
                if (!"tags".equals(key)) {
                    value = intent.getStringExtra(key);
                } else {
                    ArrayList<String> tags = intent.getStringArrayListExtra(key);
                    if (tags != null && !tags.isEmpty()) {
                        value = String.join(", ", tags);
                    } else {
                        value = "No tags found";
                    }
                }

                TextView textView = new TextView(this);
                textView.setText(key + ": " + value);
                detailLayout.addView(textView);
            }
        }
    }
}