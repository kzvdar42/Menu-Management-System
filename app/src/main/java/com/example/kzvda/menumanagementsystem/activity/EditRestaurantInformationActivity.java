package com.example.kzvda.menumanagementsystem.activity;

import android.Manifest;
import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.kzvda.menumanagementsystem.R;
import com.example.kzvda.menumanagementsystem.db.Entity.RestaurantEntity;
import com.example.kzvda.menumanagementsystem.serverApi.Constants;
import com.example.kzvda.menumanagementsystem.serverApi.Models.SimpleResponceModel;
import com.example.kzvda.menumanagementsystem.viewModel.EditRestaurantInfoViewModel;
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

public class EditRestaurantInformationActivity extends AppCompatActivity {

    RestaurantEntity restaurant;
    int restaurantId;
    EditRestaurantInfoViewModel mViewModel;
    EditText restaurantName;
    EditText subtitle;
    EditText description;
    EditText location;
    ImageView restaurantImage;
    private String newImage = null;

    private static final String TAG = EditRestaurantInformationActivity.class.getSimpleName();
    private static final int REQUEST_GALLERY_CODE = 200;
    private static final int READ_REQUEST_CODE = 300;
    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_restaurant_information);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.edit_restaurant_info);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(v -> finish());
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        restaurantName = findViewById(R.id.restaurant_name);
        subtitle = findViewById(R.id.subtitle);
        description = findViewById(R.id.description);
        location = findViewById(R.id.location);
        restaurantImage = findViewById(R.id.restaurantImage);

        SharedPreferences sharedPref = this.getSharedPreferences("user", Context.MODE_PRIVATE);
        restaurantId = sharedPref.getInt("restaurantId", 0);
        mViewModel = ViewModelProviders.of(this).get(EditRestaurantInfoViewModel.class);
        mViewModel.getRestaurant(restaurantId).observe(this, restaurant -> {
            this.restaurant = restaurant;
            restaurantName.setText(restaurant.getName());
            subtitle.setText(restaurant.getSubname());
            description.setText(restaurant.getDescription());
            Picasso.with(this).load(Constants.BASE_URL + restaurant.getPhotoSrc()).fit().into(restaurantImage);
            location.setText(restaurant.getLocation());
        });

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.restaurantImage:
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, REQUEST_GALLERY_CODE);
                break;
            case R.id.button:
                mViewModel.setImageToRest(restaurantId, newImage);
                finish();
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, EditRestaurantInformationActivity.this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_GALLERY_CODE && resultCode == Activity.RESULT_OK) {
            uri = data.getData();
            if (EasyPermissions.hasPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                String filePath = getRealPathFromURIPath(uri, EditRestaurantInformationActivity.this);
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
                        Picasso.with(getApplicationContext()).load(Constants.BASE_URL + newImage).fit().into(restaurantImage);
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