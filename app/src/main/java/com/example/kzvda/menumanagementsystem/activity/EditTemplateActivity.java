package com.example.kzvda.menumanagementsystem.activity;

import android.Manifest;
import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.kzvda.menumanagementsystem.R;
import com.example.kzvda.menumanagementsystem.serverApi.Constants;
import com.example.kzvda.menumanagementsystem.serverApi.Models.SimpleResponceModel;
import com.example.kzvda.menumanagementsystem.viewModel.EditTemplateViewModel;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditTemplateActivity extends AppCompatActivity {

    private EditText nameField;
    private EditText descriptionField;
    private CheckBox addToMenuBox;
    private EditText priceField;
    private ImageView dishPhoto;
    private EditTemplateViewModel mViewModel;
    private int dishId;
    private int restaurantId;
    private boolean isInMenu;
    private String newImage = null;

    private static final String TAG = EditTemplateActivity.class.getSimpleName();
    private static final int REQUEST_GALLERY_CODE = 200;
    private static final int READ_REQUEST_CODE = 300;
    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_template);

        Intent intent = getIntent();
        restaurantId = intent.getIntExtra("restId", -1);
        dishId = intent.getIntExtra("id", -1);
        String name = intent.getStringExtra("name");
        String description = intent.getStringExtra("description");
        int price = intent.getIntExtra("price", -1);
        isInMenu = intent.getBooleanExtra("isInMenu", false);
        String photoSrc = intent.getStringExtra("photoSrc");

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.edit_template);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(v -> finish());
        ActionBar actionbar = getSupportActionBar();
        Objects.requireNonNull(actionbar).setDisplayHomeAsUpEnabled(true);

        nameField = findViewById(R.id.setName);
        descriptionField = findViewById(R.id.setDescription);
        addToMenuBox = findViewById(R.id.add_to_templates);
        priceField = findViewById(R.id.setPrice);
        dishPhoto = findViewById(R.id.dishImage);

        nameField.setText(name);
        descriptionField.setText(description);
        priceField.setText(String.valueOf(price));
        addToMenuBox.setChecked(isInMenu);
        Picasso.with(this).load(Constants.BASE_URL + photoSrc).fit().into(dishPhoto);
        mViewModel = ViewModelProviders.of(this).get(EditTemplateViewModel.class);
    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.updateButton:
                String name = nameField.getText().toString();
                String description = descriptionField.getText().toString();
                int price = Integer.valueOf(priceField.getText().toString());
                mViewModel.editTemplate(restaurantId, dishId, name, description, price, isInMenu).enqueue(new Callback<SimpleResponceModel>() {
                    @Override
                    public void onResponse(Call<SimpleResponceModel> call, Response<SimpleResponceModel> response) {
                        if (response.body().getResult().equals("OK")) {
                            if (newImage != null) {
                                mViewModel.setImageToDish(dishId, newImage);
                            }
                            finish();
                        } else {
                            Toast.makeText(getApplication().getBaseContext(), "NO: " + response.body().getResult() + " " + response.body().getInfo(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<SimpleResponceModel> call, Throwable t) {
                        Toast.makeText(getApplication().getBaseContext(), getString(R.string.something_is_wrong) + t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
                break;
            case R.id.deleteButton:
                new AlertDialog.Builder(this)
                        .setTitle(getString(R.string.are_you_sure))
                        .setCancelable(true)
                        .setPositiveButton("Yes", (dialog, id) -> {
                            mViewModel.deleteTemplate(dishId);
                            finish();
                        })
                        .setNegativeButton("No", null)
                        .show();
                break;
            case R.id.dishImage:
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, REQUEST_GALLERY_CODE);
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, EditTemplateActivity.this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_GALLERY_CODE && resultCode == Activity.RESULT_OK) {
            uri = data.getData();
            if (EasyPermissions.hasPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                String filePath = getRealPathFromURIPath(uri, EditTemplateActivity.this);
                File file = new File(filePath);
                Log.d(TAG, "Filename " + file.getName());
                //RequestBody mFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file);
                MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("photo", file.getName(), mFile);
                RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());

                Call<SimpleResponceModel> fileUpload = mViewModel.postImage(fileToUpload);
                fileUpload.enqueue(new Callback<SimpleResponceModel>() {
                    @Override
                    public void onResponse(Call<SimpleResponceModel> call, Response<SimpleResponceModel> response) {
                        newImage = response.body().getFilename();
                        Picasso.with(getApplicationContext()).load(Constants.BASE_URL + newImage).fit().into(dishPhoto);
                    }

                    @Override
                    public void onFailure(Call<SimpleResponceModel> call, Throwable t) {

                    }
                });
            } else {
                EasyPermissions.requestPermissions(this, getString(R.string.read_file), READ_REQUEST_CODE, Manifest.permission.READ_EXTERNAL_STORAGE);
            }
        }
    }

    private String getRealPathFromURIPath(Uri contentURI, Activity activity) {
        Cursor cursor = activity.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }
}
