package by.uni.lab5_navmenu;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddEventActivity extends AppCompatActivity {
    EditText titleI, dateI, timeI, descriptionI;
    LinearLayout imagesContainer;
    List<String> photoPaths = new ArrayList<>();
    private static final int PICK_PHOTO_REQUEST = 1;
    String selectedDate, selectedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_event);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        titleI = findViewById(R.id.editTextTitle);
        dateI = findViewById(R.id.editTextDate);
        timeI = findViewById(R.id.editTextTime);
        descriptionI = findViewById(R.id.editTextDescription);
        imagesContainer = findViewById(R.id.imageLayout);

        // Date selection
        dateI.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    AddEventActivity.this,
                    (view, year1, month1, dayOfMonth) -> {
                        selectedDate = dayOfMonth + "/" + (month1 + 1) + "/" + year1;
                        dateI.setText(selectedDate);
                    },
                    year, month, day);
            datePickerDialog.show();
        });

        // Time selection
        timeI.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(
                    AddEventActivity.this,
                    (view, hourOfDay, minute1) -> {
                        selectedTime = String.format("%02d:%02d", hourOfDay, minute1);
                        timeI.setText(selectedTime);
                    },
                    hour, minute, true);
            timePickerDialog.show();
        });

        // Photo selection
        findViewById(R.id.buttonImage).setOnClickListener(v -> selectPhoto());

        findViewById(R.id.buttonAdd).setOnClickListener(v -> {
            String title = titleI.getText().toString();
            String description = descriptionI.getText().toString();

            if (title.isEmpty() || description.isEmpty() || selectedDate == null || selectedTime == null) {
                Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Create new Event object
            Event newEvent = new Event(title, description, selectedDate, selectedTime, photoPaths);

            // Save event to JSON
            saveEventToJsonFile(newEvent);

            // Return to main screen
            setResult(RESULT_OK);
            finish();
        });
    }

    private void selectPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_PHOTO_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_PHOTO_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            if (selectedImageUri != null) {
                // Add the URI to your list and update the UI
                photoPaths.add(selectedImageUri.toString());
                updatePhotoList();
            }
        }
    }

    private void updatePhotoList() {
        imagesContainer.removeAllViews(); // Clear current views
        for (String path : photoPaths) {
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            imageView.setImageURI(Uri.parse(path));
            imageView.setAdjustViewBounds(true);
            imageView.setMaxWidth(200);
            imageView.setMaxHeight(200);

            imagesContainer.addView(imageView);
        }
    }

    private void saveEventToJsonFile(Event event) {
        Gson gson = new Gson();
        String fileName = "event_data.json";
        List<Event> eventList = new ArrayList<>();

        try (FileInputStream fis = openFileInput(fileName)) {
            InputStreamReader isr = new InputStreamReader(fis);
            Type eventListType = new TypeToken<ArrayList<Event>>() {}.getType();
            eventList = gson.fromJson(isr, eventListType);
            if (eventList == null) {
                eventList = new ArrayList<>();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        eventList.add(event);

        String json = gson.toJson(eventList);

        try (FileOutputStream fos = openFileOutput(fileName, MODE_PRIVATE)) {
            fos.write(json.getBytes());
            Toast.makeText(this, "Event saved successfully", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(this, "Error saving event data", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}