package by.uni.lab3_activityintent;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ThirdStepActivity extends AppCompatActivity {
	Intent intent;
	Intent back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		EdgeToEdge.enable(this);
		setContentView(R.layout.activity_third_step);
		ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
			Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
			v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
			return insets;
		});
		intent = new Intent(this, FirstStepActivity.class);
		back = new Intent(this, SecondStepActivity.class);
		if(getIntent().getExtras() != null){
			intent.putExtras(getIntent().getExtras());
			back.putExtras(getIntent().getExtras());
		}

		findViewById(R.id.buttonNext).setOnClickListener(v -> {
			startActivity(intent);
			finish();
		});

		findViewById(R.id.buttonBack).setOnClickListener(v -> {
			startActivity(back);
			finish();
		});
	}
}