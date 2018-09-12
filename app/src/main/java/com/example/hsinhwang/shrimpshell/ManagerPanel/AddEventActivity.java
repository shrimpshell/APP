package com.example.hsinhwang.shrimpshell.ManagerPanel;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.hsinhwang.shrimpshell.Classes.Common;
import com.example.hsinhwang.shrimpshell.Classes.CommonTask;
import com.example.hsinhwang.shrimpshell.Classes.Events;
import com.example.hsinhwang.shrimpshell.Classes.Rooms;
import com.example.hsinhwang.shrimpshell.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Type;
import java.sql.Date;

public class AddEventActivity extends AppCompatActivity {
    private final static String TAG = "AddEventActivity";
    private EditText etAddEventName, etAddEventDescription;
    private DatePicker etAddEventStartTime, etAddEventEndTime;
    private Button btnAddEvent;
    private ImageView ivEvent;
    private byte[] image;
    private static final int REQUEST_PICK_PICTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        initialization();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == RESULT_OK) {
            Uri uri = intent.getData();
            int newSize = 512;
            if (uri != null) {
                String[] columns = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(uri, columns,
                        null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    String imagePath = cursor.getString(0);
                    cursor.close();
                    Bitmap srcImage = BitmapFactory.decodeFile(imagePath);
                    Bitmap downsizedImage = Common.downSize(srcImage, newSize);
                    ivEvent.setImageBitmap(downsizedImage);
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    srcImage.compress(Bitmap.CompressFormat.JPEG, 100, out);
                    image = out.toByteArray();
                }
            }
        }
    }

    public void initialization() {
        ivEvent = findViewById(R.id.ivEvent);
        etAddEventName = findViewById(R.id.etAddEventName);
        etAddEventDescription = findViewById(R.id.etAddEventDescription);
        etAddEventStartTime = findViewById(R.id.etAddEventStartTime);
        etAddEventEndTime = findViewById(R.id.etAddEventEndTime);

        btnAddEvent = findViewById(R.id.btnAddEvent);
        btnAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertAction();
                finish();
            }
        });
        ivEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQUEST_PICK_PICTURE);
            }
        });
    }

    private void insertAction() {
        String name = etAddEventName.getText().toString().trim(),
                description = etAddEventDescription.getText().toString().trim();
        String start = etAddEventStartTime.getYear() + "-" + (etAddEventStartTime.getMonth() + 1 > 10 ? etAddEventStartTime.getMonth() + 1 : "0" + (etAddEventStartTime.getMonth() + 1)) + "-" + etAddEventStartTime.getDayOfMonth(),
                end = etAddEventEndTime.getYear() + "-" + (etAddEventEndTime.getMonth() + 1 > 10 ? etAddEventEndTime.getMonth() + 1 : "0" + (etAddEventEndTime.getMonth() + 1)) + "-" + etAddEventEndTime.getDayOfMonth();
        if (Common.networkConnected(this)) {
            String url = Common.URL + "/EventServlet";
            Events event = new Events(0, name, description, start, end);
            String imageBase64 = "";
            if (image != null) imageBase64 = Base64.encodeToString(image, Base64.DEFAULT);
            Log.e(TAG, new Gson().toJson(event));
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("action", "eventInsert");
            jsonObject.addProperty("event", new Gson().toJson(event));
            jsonObject.addProperty("imageBase64", imageBase64);
            int count = 0;
            try {
                String result = new CommonTask(url, jsonObject.toString()).execute().get();
                count = Integer.valueOf(result);
            } catch (Exception e) {
//                Log.e(TAG, e.toString());
            }
            if (count == 0) {
                Common.showToast(this, R.string.msg_InsertFail);
            } else {
                Common.showToast(this, R.string.msg_InsertSuccess);
            }
        } else {
            Common.showToast(this, R.string.msg_NoNetwork);
        }
    }

    JsonSerializer<Date> ser = new JsonSerializer<Date>() {
        @Override
        public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext
                context) {
            return src == null ? null : new JsonPrimitive(src.getTime());
        }
    };
}
