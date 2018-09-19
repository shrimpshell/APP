package com.example.hsinhwang.shrimpshell.EmployeePanel;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hsinhwang.shrimpshell.Classes.Common;
import com.example.hsinhwang.shrimpshell.Classes.CommonTask;
import com.example.hsinhwang.shrimpshell.Classes.Employees;
import com.example.hsinhwang.shrimpshell.Classes.ImageEmployeeTask;
import com.example.hsinhwang.shrimpshell.ManagerPanel.ManagerHomeActivity;
import com.example.hsinhwang.shrimpshell.R;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Type;

public class EmployeeHomeActivity extends AppCompatActivity {
    private final static String TAG = "EmployeeHomeActivity";
    private LinearLayout employHomeBottom;
    private int idEmployee;
    private CommonTask employeeGetAllTask;
    private TextView txMyName, txMemberEmail, txPhoneNumber;
    private ImageView ivProfilePicture;
    private byte[] image;
    private Employees employee = null;
    private static final int REQUEST_TAKE_PICTURE_SMALL = 0;
    private static final int REQUEST_PICK_PICTURE = 1;
    private SharedPreferences preferences;
    private CommonTask empFindTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_home);
        initialization();
        insertDepartmentButton();
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences pref = getSharedPreferences(Common.EMPLOYEE_LOGIN, MODE_PRIVATE);
        String email = pref.getString("email", "");
        String password = pref.getString("password", "");
        idEmployee = pref.getInt("IdEmployee", 0);
        loadData();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (resultCode == RESULT_OK) {
            int newSize = 512;
            switch (requestCode) {
                case REQUEST_TAKE_PICTURE_SMALL:  //縮圖
                    Bundle bundle = intent.getExtras();
                    if (bundle != null) {
                        Bitmap picture = (Bitmap) bundle.get("data");
                        ivProfilePicture.setImageBitmap(picture);
                    }
                    break;
                case REQUEST_PICK_PICTURE:    //挑圖
                    Uri uri = intent.getData();
                    if (uri != null) {
                        String[] columns = {MediaStore.Images.Media.DATA};
                        Cursor cursor = getContentResolver().query(uri, columns,
                                null, null, null);
                        if (cursor != null && cursor.moveToFirst()) {
                            String imagePath = cursor.getString(0);
                            cursor.close();
                            Bitmap srcImage = BitmapFactory.decodeFile(imagePath);
                            Bitmap downsizedImage = Common.downSize(srcImage, newSize);
                            ivProfilePicture.setImageBitmap(downsizedImage);
                            ByteArrayOutputStream out = new ByteArrayOutputStream();
                            srcImage.compress(Bitmap.CompressFormat.JPEG, 100, out);
                            image = out.toByteArray();

                            String imageBase64 = "";
                            if (image != null) imageBase64 = Base64.encodeToString(image, Base64.DEFAULT);

                            if (Common.networkConnected(this)) {
                                String url = Common.URL + "/EmployeeServlet";
                                JsonObject jsonObject = new JsonObject();
                                jsonObject.addProperty("action", "updateImage");
                                jsonObject.addProperty("idEmployee", idEmployee);
                                jsonObject.addProperty("imageBase64", imageBase64);

                                String jsonOut = jsonObject.toString();
                                empFindTask = new CommonTask(url, jsonOut);
                                try {
                                    String result = empFindTask.execute().get();
                                    int count = Integer.valueOf(result);
                                } catch (Exception e) {
//                                    Log.e(TAG, e.toString());
                                }
                            }
                        }
                    }
                    break;
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case Common.REQ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    ivProfilePicture.setEnabled(true);
                } else {
                    ivProfilePicture.setEnabled(false);
                }
                break;
        }
    }

    private void initialization() {
        employHomeBottom = findViewById(R.id.employHomeBottom);
        employHomeBottom.setPadding(5, 5, 5,5);
        ivProfilePicture = findViewById(R.id.ivProfilePicture);
        txMyName = findViewById(R.id.txMyName);
        txMemberEmail = findViewById(R.id.txMemberEmail);
        txPhoneNumber = findViewById(R.id.txPhoneNumber);
        ivProfilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQUEST_PICK_PICTURE);
            }

        });
    }

    /**
     * 部門功能
     */
    private void loadData() {
        if (Common.networkConnected(this)) {
            String url = Common.URL + "/EmployeeServlet";
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("action", "findById");
            jsonObject.addProperty("idEmployee", idEmployee);
            String jsonOut = jsonObject.toString();
            employeeGetAllTask = new CommonTask(url, jsonOut);

            try {
                String jsonIn = employeeGetAllTask.execute().get();
                Type listType = new TypeToken<Employees>() {
                }.getType();
                employee = new Gson().fromJson(jsonIn, listType);
            } catch (Exception e) {
//                Log.e(TAG, e.toString());
            }

            if (employee == null) {
                Common.showToast(this, R.string.msg_NoEmployeesFound);
            } else {
                loadImage(idEmployee);
                txMyName.setText(employee.getName());
                txMemberEmail.setText(employee.getEmail());
                txPhoneNumber.setText(employee.getPhone());
            }
        }
    }

    private void loadImage(int id) {
        String url = Common.URL + "/EmployeeServlet";
        int imageSize = getResources().getDisplayMetrics().widthPixels / 3;
        Bitmap bitmap = null;

        try {
            bitmap = new ImageEmployeeTask(url, id, imageSize, ivProfilePicture).execute().get();
        } catch (Exception e) {
//            Log.e(TAG, e.toString());
        }
        if (bitmap != null) {
            ivProfilePicture.setImageBitmap(bitmap);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            image = out.toByteArray();
        } else {
            ivProfilePicture.setImageResource(R.drawable.employee_pic);
        }
    }

    private void insertDepartmentButton() {
        if (true) { // 黃信：如果是主管，執行這段程式碼 之後需要用switch case判斷員工編號
            // 設定按鈕尺寸/Margin
            LinearLayoutCompat.LayoutParams param = new LinearLayoutCompat.LayoutParams(240, 240);
            param.leftMargin = 20;
            // 動態生成按鈕
            Button btn = new Button(this);
            btn.setText("工作進度");
            btn.setBackgroundColor(Color.parseColor("#F7DF96"));
            btn.setLayoutParams(param);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(EmployeeHomeActivity.this, ManagerHomeActivity.class);
                    startActivity(intent);
                }
            });
            employHomeBottom.addView(btn);

            Button edit = new Button(this);
            edit.setText("編輯資料");
            edit.setBackgroundColor(Color.parseColor("#F7DF96"));
            edit.setLayoutParams(param);
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(EmployeeHomeActivity.this, EmployeeEditActivity.class);
                    Bundle bundle = new Bundle();
                    Employees emp = new Employees(employee.getId(), employee.getCode(), employee.getName(), employee.getPassword(),
                            employee.getEmail(), employee.getGender(), employee.getPhone(), employee.getAddress(), employee.getDepartmentId());
                    bundle.putSerializable("employee", emp);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
            employHomeBottom.addView(edit);

            Button logout = new Button(this);
            logout.setText("登出");
            logout.setBackgroundColor(Color.parseColor("#F7DF96"));
            logout.setLayoutParams(param);
            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreferences pref = getSharedPreferences(Common.EMPLOYEE_LOGIN, MODE_PRIVATE);
                    SharedPreferences page = getSharedPreferences(Common.PAGE, MODE_PRIVATE);
                    pref.edit().putBoolean("login", false).putString("email", "").putString("password", "").putInt("IdEmployee", 0).apply();
                    page.edit().putInt("page", 1).apply();
                    finish();
                }
            });
            employHomeBottom.addView(logout);

        }

    }

}
