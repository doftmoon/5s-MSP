package by.uni.lab4_activityintentfs;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
	ArrayAdapter<ItemData> adapter;
	ListView listViewItems;
	List<ItemData> itemDataList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		EdgeToEdge.enable(this);
		setContentView(R.layout.activity_main);
		ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
			Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
			v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
			return insets;
		});
		listViewItems = findViewById(R.id.listViewItems);
		itemDataList = new ArrayList<>();

		adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, itemDataList);
		listViewItems.setAdapter(adapter);
		loadItemDataFromJson();

		listViewItems.setOnItemClickListener((parent, view, position, id) -> {
			ItemData selectedItem = itemDataList.get(position);
			Intent detailIntent = new Intent(MainActivity.this, DetailItemActivity.class);
			detailIntent.putExtra("title", selectedItem.getTitle());
			startActivity(detailIntent);
		});
		Intent intent = new Intent(this, FirstStepActivity.class);

		findViewById(R.id.buttonStart).setOnClickListener(v -> {
			startActivity(intent);
			finish();
		});
	}

	private void loadItemDataFromJson() {
		File file = new File(getFilesDir(), "dataItem.json");
		if (file.length() == 0) {
			Toast.makeText(this, "No data found in the Json file", Toast.LENGTH_SHORT).show();
			return;
		}
		try (BufferedReader reader = new BufferedReader(new FileReader(new File(getFilesDir(), "dataItem.json")))) {
			Gson gson = new GsonBuilder().create();
			String line;
			while ((line = reader.readLine()) != null) {
				if(!line.trim().isEmpty()) {
					ItemData item = gson.fromJson(line, ItemData.class);
					itemDataList.add(item);
				}
			}
		} catch (IOException e) {
			Toast.makeText(this, "Error loading item data: " +e.getMessage(), Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		} catch (JsonSyntaxException e) {
			Toast.makeText(this, "Error parsing Json data: " +e.getMessage(), Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
	}
}