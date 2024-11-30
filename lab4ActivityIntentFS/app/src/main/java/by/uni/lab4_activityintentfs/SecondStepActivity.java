package by.uni.lab4_activityintentfs;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SecondStepActivity extends AppCompatActivity {
	Intent intent;
	Intent back;
	EditText author;
	EditText pg;
	EditText description;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		EdgeToEdge.enable(this);
		setContentView(R.layout.activity_second_step);
		ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
			Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
			v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
			return insets;
		});
		author = findViewById(R.id.author);
		pg = findViewById(R.id.pg);
		description = findViewById(R.id.description);
		intent = new Intent(this, ThirdStepActivity.class);
		back = new Intent(this, FirstStepActivity.class);
		if(getIntent().getExtras() != null){
			intent.putExtras(getIntent().getExtras());
			back.putExtras(getIntent().getExtras());
			author.setText(intent.getStringExtra("author"));
			pg.setText(intent.getStringExtra("pg"));
			description.setText(intent.getStringExtra("description"));
		}

		findViewById(R.id.buttonNext).setOnClickListener(v -> {
			intent.putExtra("author", author.getText().toString());
			intent.putExtra("pg", pg.getText().toString());
			intent.putExtra("description", description.getText().toString());
			startActivity(intent);
			finish();
		});

		findViewById(R.id.buttonBack).setOnClickListener(v -> {
			startActivity(back);
			finish();
		});
	}
}