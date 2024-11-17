package by.uni.lab2_activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
	EditText usedFuelI;
	EditText distanceI;
	EditText fuelCostI;
	EditText avgUsedO;
	EditText cost1kmO;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		EdgeToEdge.enable(this);
		setContentView(R.layout.activity_main);
		int currentOrientation = getResources().getConfiguration().orientation;
		if (currentOrientation == 1) {
			ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
				Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
				v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
				return insets;
			});
		} else if (currentOrientation == 2) {
			ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainLand), (v, insets) -> {
				Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
				v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
				return insets;
			});
		}
		usedFuelI = findViewById(R.id.usedFuelI);
		distanceI = findViewById(R.id.distanceI);
		fuelCostI = findViewById(R.id.fuelCostI);
		avgUsedO = findViewById(R.id.avgUsedO);
		cost1kmO = findViewById(R.id.cost1kmO);

		findViewById(R.id.buttonSubmit).setOnClickListener((View v) -> {
			String usedFuelText = usedFuelI.getText().toString();
			String distanceText = distanceI.getText().toString();
			String fuelCostText = fuelCostI.getText().toString();

			if (usedFuelText.isEmpty() || distanceText.isEmpty() || fuelCostText.isEmpty()) {
				return;
			}
			int usedFuelV = Integer.parseInt(usedFuelText);
			int distanceV = Integer.parseInt(distanceText);
			int fuelCostV = Integer.parseInt(fuelCostText);

			avgUsedO.setText(String.valueOf((float)usedFuelV / ((float)distanceV / 100)));
			if (distanceV != 0) {
				cost1kmO.setText(String.valueOf(usedFuelV * (double)fuelCostV / distanceV));
			}
		});
	}
}