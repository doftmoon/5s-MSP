package by.uni.lab3_activityintent;

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
		if(getIntent().getExtras() != null) {
			intent.putExtras(getIntent().getExtras());
			back.putExtras(getIntent().getExtras());
			// TODO: backing tags values and buttons state
//			if (back.getStringArrayListExtra("tags") != null){
//				ArrayList<String> selectedButtonNames = new ArrayList<>();
//				for (Button selectedButton : selectedButtons) {
////					if (selectedButton.getText().toString() == back.getStringExtra())
////					selectedButtonNames.add(selectedButton.getText().toString());
//				}
//			}
		}

		findViewById(R.id.buttonNext).setOnClickListener(v -> {
			ArrayList<String> selectedButtonNames = new ArrayList<>();
			for (Button selectedButton : selectedButtons) {
				selectedButtonNames.add(selectedButton.getText().toString());
			}
			intent.putStringArrayListExtra("tags", selectedButtonNames);
			startActivity(intent);
			finish();
		});

		findViewById(R.id.buttonBack).setOnClickListener(v -> {
			startActivity(back);
			finish();
		});
	}

	public void onButtonClick(View v) {
		Button button = (Button) v;

		if (selectedButtons.contains(button)) {
			selectedButtons.remove(button);
			button.setBackgroundColor(getResources().getColor(R.color.buttonInactive)); // Возвращаем исходный цвет кнопки
		} else {
			selectedButtons.add(button);
			button.setBackgroundColor(getResources().getColor(R.color.buttonActive)); // Устанавливаем новый цвет для выбранной кнопки
		}
	}
}