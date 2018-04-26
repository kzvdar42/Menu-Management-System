package com.example.kzvda.menumanagementsystem.viewModel;

import android.app.Application;
import android.widget.Toast;

import com.example.kzvda.menumanagementsystem.R;
import com.example.kzvda.menumanagementsystem.serverApi.Interfaces.ServerApi;
import com.example.kzvda.menumanagementsystem.serverApi.Models.SimpleResponceModel;
import com.example.kzvda.menumanagementsystem.serverApi.RequestBodies.SetImageToDishRequest;
import com.example.kzvda.menumanagementsystem.serverApi.RetrofitInstance;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditRestaurantInfoViewModel extends RestaurantInfoViewModel {
    private ServerApi serverApi;

    public EditRestaurantInfoViewModel(Application application) {
        super(application);
        serverApi = RetrofitInstance.getApi();
    }

    public Call<SimpleResponceModel> postImage(MultipartBody.Part body) {
        return serverApi.postImage(body);
    }

    public void setImageToRest(int dishId, String imageSrc) {
        serverApi.setImageToDish(new SetImageToDishRequest("set_rest_photo", dishId, imageSrc)).enqueue(new Callback<SimpleResponceModel>() {
            @Override
            public void onResponse(Call<SimpleResponceModel> call, Response<SimpleResponceModel> response) {

            }

            @Override
            public void onFailure(Call<SimpleResponceModel> call, Throwable t) {
                Toast.makeText(getApplication().getBaseContext(), getApplication().getString(R.string.something_is_wrong) + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
