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
        return mMenuDao.getRestaurantMenu(id);
    }

    public LiveData<List<RestaurantEntity>> getRestaurantsList() {
        return mRestaurantDao.getAll();
    }

    public LiveData<List<MenuEntity>> getTemplates(int id) {
        return mMenuDao.getRestaurantTemplates(id);
    }

    public LiveData<RestaurantEntity> getRestaurant(int id) {
        return mRestaurantDao.get(id);
    }

    public void insertToMenu(MenuEntity dish) {
        new insertToMenuAsyncTask(mMenuDao).execute(dish);
    }

    private static class insertToMenuAsyncTask extends AsyncTask<MenuEntity, Void, Void> {

        private MenuDao mAsyncTaskDao;

        insertToMenuAsyncTask(MenuDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final MenuEntity... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    public void updateRestaurantList(List<RestaurantEntity> restList) {
        new updateRestListAsyncTask(mRestaurantDao).execute(restList);
    }

    private static class updateRestListAsyncTask extends AsyncTask<List<RestaurantEntity>, Void, Void> {

        private RestaurantDao mAsyncTaskDao;

        updateRestListAsyncTask(RestaurantDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final List<RestaurantEntity>... params) {
            mAsyncTaskDao.deleteAll();
            mAsyncTaskDao.insertRests(params[0]);
            return null;
        }
    }

}