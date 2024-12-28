package by.uni.lab4_activityintentfs;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ResultActivity extends AppCompatActivity {
	Intent intent;
	Intent back;

	private void saveItemDataToJson(ItemData newItem) {
		Gson gson = new Gson();
		String fileName = "event_data.json";
		List<ItemData> itemList = new ArrayList<>();

		try (FileInputStream fis = openFileInput(fileName)) {
			InputStreamReader isr = new InputStreamReader(fis);
			Type eventListType = new TypeToken<ArrayList<ItemData>>() {}.getType();
			itemList = gson.fromJson(isr, eventListType);
			if (itemList == null) {
				itemList = new ArrayList<>();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		itemList.add(newItem);

		String json = gson.toJson(itemList);

		try (FileOutputStream fos = openFileOutput(fileName, MODE_PRIVATE)) {
			fos.write(json.getBytes());
			Toast.makeText(this, "Event saved successfully", Toast.LENGTH_LONG).show();
		} catch (Exception e) {
			Toast.makeText(this, "Error saving event data", Toast.LENGTH_SHORT).show();
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