package com.example.kzvda.menumanagementsystem.db;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.kzvda.menumanagementsystem.db.Entity.MenuEntity;
import com.example.kzvda.menumanagementsystem.db.Entity.RestaurantEntity;
import com.example.kzvda.menumanagementsystem.db.dao.MenuDao;
import com.example.kzvda.menumanagementsystem.db.dao.RestaurantDao;

import java.util.List;

public class Repository {

    private MenuDao mMenuDao;
    private RestaurantDao mRestaurantDao;

    public Repository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mMenuDao = db.menuDao();
        mRestaurantDao = db.restaurantDao();
    }

    public LiveData<List<MenuEntity>> getRestaurantMenu(int id) {
        return mMenuDao.findByRestaurant(id);
    }

    public LiveData<List<RestaurantEntity>> getRestaurantsList() {
        return mRestaurantDao.getAll();
    }

    public void insert (MenuEntity word) {
        new insertAsyncTask(mMenuDao).execute(word);
    }

    private static class insertAsyncTask extends AsyncTask<MenuEntity, Void, Void> {

        private MenuDao mAsyncTaskDao;

        insertAsyncTask(MenuDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final MenuEntity... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}