package by.uni.lab4_activityintentfs;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
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
		loadItemDataFromJson();

		ArrayAdapter<ItemData> adapter = new ArrayAdapter<>(this,
				android.R.layout.simple_list_item_1, itemDataList);
		listViewItems.setAdapter(adapter);

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
		Gson gson = new Gson();

		try (FileInputStream fis = new FileInputStream(new File(getFilesDir(), "dataItem.json"))) {
			InputStreamReader isr = new InputStreamReader(fis);
			Type eventListType = new TypeToken<ArrayList<ItemData>>() {}.getType();
			itemDataList = gson.fromJson(isr, eventListType);

			if (itemDataList == null) {
				itemDataList = new ArrayList<>();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}