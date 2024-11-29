package by.uni.lab3_activityintent;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
	private void saveItemDataToJson(ItemData newItem) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(newItem);

		try (FileWriter fileWriter = new FileWriter("item_data.json")) {
			fileWriter.write(json);
			Toast.makeText(this, fileWriter.toString(), Toast.LENGTH_SHORT).show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

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
		Intent intent = new Intent(this, FirstStepActivity.class);

		findViewById(R.id.buttonStart).setOnClickListener(v -> {
			startActivity(intent);
			finish();
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		Toast.makeText(this, ""+resultCode, Toast.LENGTH_SHORT).show();
		if (requestCode == 1){
			if (resultCode == RESULT_OK){
				ItemData newItem = new ItemData(data.getStringExtra("title"), data.getStringExtra("type"),
						data.getStringExtra("year"), data.getStringExtra("author"), data.getStringExtra("pg"),
						data.getStringExtra("description"), data.getStringArrayListExtra("tags"));
				saveItemDataToJson(newItem);
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}