package by.uni.lab3_activityintent;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class ResultActivity extends AppCompatActivity {
	Intent intent;
	Intent back;
	private void saveItemDataToJson(ItemData newItem) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(newItem);

		try (FileWriter fileWriter = new FileWriter(new File(getFilesDir(),"dataItem.json"))) {
			fileWriter.write(json);
			Toast.makeText(this, fileWriter.toString(), Toast.LENGTH_SHORT).show();
		} catch (IOException e) {
			System.err.println("Ошибка при записи JSON данных в файл: " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		EdgeToEdge.enable(this);
		setContentView(R.layout.activity_result);
		ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
			Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
			v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
			return insets;
		});
		intent = new Intent(this, MainActivity.class);
//		intent.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
		back = new Intent(this, ThirdStepActivity.class);
		LinearLayout keyValueLayout = findViewById(R.id.keyValueLayout);
		if(getIntent().getExtras() != null) {
			intent.putExtras(getIntent().getExtras());
			back.putExtras(getIntent().getExtras());
			for (String key : intent.getExtras().keySet()) {
				String value = "";
				if (!"tags".equals(key)) {
					value = intent.getStringExtra(key);
				} else {
					ArrayList<String> tags = intent.getStringArrayListExtra(key);
					if (!tags.isEmpty()) {
						for (String tag : tags) {
							value += tag + ", ";
						}
					} else {
						value = "No tags found";
					}
				}

				TextView textView = new TextView(this);
				textView.setText(key + ": " + value);
				keyValueLayout.addView(textView);
			}
		}

		findViewById(R.id.buttonNext).setOnClickListener(v -> {
			ItemData newItem = new ItemData(intent.getStringExtra("title"), intent.getStringExtra("type"),
					intent.getStringExtra("year"), intent.getStringExtra("author"), intent.getStringExtra("pg"),
					intent.getStringExtra("description"), intent.getStringArrayListExtra("tags"));
			saveItemDataToJson(newItem);
			startActivity(intent);
			finish();
		});

		findViewById(R.id.buttonBack).setOnClickListener(v -> {
			startActivity(back);
			finish();
		});
	}
}