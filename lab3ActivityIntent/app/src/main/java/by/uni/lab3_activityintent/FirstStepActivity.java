package by.uni.lab3_activityintent;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

public class FirstStepActivity extends AppCompatActivity {
	Intent intent;
	Intent back;
	EditText title;
	Spinner spinnerType;
	EditText year;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		EdgeToEdge.enable(this);
		setContentView(R.layout.activity_first_step);
		ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
			Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
			v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
			return insets;
		});
		title = findViewById(R.id.title);
		spinnerType = findViewById(R.id.spinnerType);
		year = findViewById(R.id.year);
		year.setFilters(new InputFilter[] {
				  new InputFilter.LengthFilter(4),
				  new InputFilter() {
					  @Override
					  public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
						  String newVal = dest.toString().substring(0, dstart) + source.subSequence(start, end) + dest.toString().substring(dend);
						  if (newVal.isEmpty()){
							   return "";
						  }

						  try {
							  int input = Integer.parseInt(newVal);

							  if (input >= 0 && input <= Calendar.getInstance().get(Calendar.YEAR)) {
								  return null;
							  }
						  } catch (NumberFormatException e) {
							  return "";
						  }

						  return "";
					  }
				  }
		});
		intent = new Intent(this, SecondStepActivity.class);
		back = new Intent(this, MainActivity.class);
		if(getIntent().getExtras() != null){
			intent.putExtras(getIntent().getExtras());
			title.setText(intent.getStringExtra("title"));
			returnSpinnerValue();
			year.setText(intent.getStringExtra("year"));
		}

		findViewById(R.id.buttonNext).setOnClickListener(v -> {
			intent.putExtra("title", title.getText().toString());
			intent.putExtra("type", spinnerType.getSelectedItem().toString());
			intent.putExtra("year", year.getText().toString());
			startActivity(intent);
			finish();
		});

		findViewById(R.id.buttonBack).setOnClickListener(v -> {
			startActivity(back);
			finish();
		});
	}

	private void returnSpinnerValue() {
		ArrayAdapter adapter = (ArrayAdapter) spinnerType.getAdapter();
		int pos = -1;
		for (int i = 0; i < adapter.getCount(); i++) {
			if (adapter.getItem(i).equals(intent.getStringExtra("type"))){
				pos = i;
				break;
			}
		}
		if (pos != -1) {
			spinnerType.setSelection(pos);
		}
	}
}