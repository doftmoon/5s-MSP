package by.uni.lab4_activityintentfs;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {
	Intent intent;
	Intent back;

	private void saveItemDataToJson(ItemData newItem) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(newItem);

		try (FileWriter fileWriter = new FileWriter(new File(getFilesDir(), "dataItem.json"), true)) {
			fileWriter.write(json + "\n");
			Toast.makeText(this, "Item saved successfully", Toast.LENGTH_SHORT).show();
		} catch (IOException e) {
			System.err.println("Error saving JSON data to file: " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);

		intent = new Intent(this, MainActivity.class);
		back = new Intent(this, ThirdStepActivity.class);

		LinearLayout keyValueLayout = findViewById(R.id.keyValueLayout);

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
				keyValueLayout.addView(textView);
			}
		}

		findViewById(R.id.buttonNext).setOnClickListener(v -> {
			ItemData newItem = new ItemData(
					intent.getStringExtra("title"),
					intent.getStringExtra("type"),
					intent.getStringExtra("year"),
					intent.getStringExtra("author"),
					intent.getStringExtra("pg"),
					intent.getStringExtra("description"),
					intent.getStringArrayListExtra("tags")
			);

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