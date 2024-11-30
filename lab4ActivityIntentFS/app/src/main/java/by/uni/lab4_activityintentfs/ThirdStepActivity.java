package by.uni.lab4_activityintentfs;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class ThirdStepActivity extends AppCompatActivity {
	Intent intent;
	Intent back;
	private ArrayList<Button> selectedButtons = new ArrayList<>();
	ArrayList<String> selectedButtonNames = new ArrayList<>();
	ArrayList<Button> allButtons = new ArrayList<>();

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
		intent = new Intent(this, ResultActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
		back = new Intent(this, SecondStepActivity.class);
		allButtons.add(findViewById(R.id.button));
		allButtons.add(findViewById(R.id.button2));
		allButtons.add(findViewById(R.id.button3));
		allButtons.add(findViewById(R.id.button4));
		Toast.makeText(this, selectedButtonNames.toString(), Toast.LENGTH_SHORT).show();
		if(getIntent().getExtras() != null) {
			intent.putExtras(getIntent().getExtras());
			back.putExtras(getIntent().getExtras());
			//restore buttons
			if (back.hasExtra("tags")) {
				selectedButtonNames = back.getStringArrayListExtra("tags");
				returnButtonState();
			}
		}

		findViewById(R.id.buttonNext).setOnClickListener(v -> {
			intent.putStringArrayListExtra("tags", selectedButtonNames);
			startActivity(intent);
			finish();
		});

		findViewById(R.id.buttonBack).setOnClickListener(v -> {
			startActivity(back);
			finish();
		});
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putStringArrayList("selectedButtonTags", new ArrayList<>(selectedButtonNames));
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		selectedButtonNames = savedInstanceState.getStringArrayList("selectedButtonTags");
		if (selectedButtonNames != null) {
			returnButtonState();
			selectedButtons.clear();
			for (Button button : allButtons) {
				if (selectedButtonNames.contains(button.getText().toString())) {
					selectedButtons.add(button);
				}
			}
		}
	}

	public void onButtonClick(View v) {
		Button button = (Button) v;

		if (selectedButtons.contains(button)) {
			selectedButtons.remove(button);
			selectedButtonNames.remove(button.getText().toString());
			button.setBackgroundColor(getResources().getColor(R.color.buttonInactive));
		} else {
			selectedButtons.add(button);
			selectedButtonNames.add(button.getText().toString());
			button.setBackgroundColor(getResources().getColor(R.color.buttonActive));
		}
	}

	public void returnButtonState() {
		if (selectedButtonNames != null) {
			for (String backItem : selectedButtonNames) {
				for (Button button : allButtons) {
					if (button.getText().toString().contains(backItem) && !selectedButtons.contains(button)) {
						selectedButtons.add(button);
						button.setBackgroundColor(getResources().getColor(R.color.buttonActive));
					}
				}
			}
		}
	}
}