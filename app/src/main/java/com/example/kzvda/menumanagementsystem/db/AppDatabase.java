package com.example.kzvda.menumanagementsystem.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.example.kzvda.menumanagementsystem.R;
import com.example.kzvda.menumanagementsystem.db.Entity.MenuEntity;
import com.example.kzvda.menumanagementsystem.db.Entity.RestaurantEntity;
import com.example.kzvda.menumanagementsystem.db.dao.MenuDao;
import com.example.kzvda.menumanagementsystem.db.dao.RestaurantDao;


@Database(entities = {MenuEntity.class, RestaurantEntity.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract MenuDao menuDao();

    public abstract RestaurantDao restaurantDao();

    private static AppDatabase INSTANCE;


    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "app_database")
//                            .addCallback(sRoomDatabaseCallback)
                            .build();

                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback() {

                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                    super.onOpen(db);
                    new PopulateDB(INSTANCE).execute();
                }
            };

    static class PopulateDB extends AsyncTask<Void, Void, Void> {
        private static final String DESCRIPTION = "Lorem ipsum dolor sit amet," +
                "consectetur adipiscing elit, sed do" +
                "eiusmod tempor incididunt ut" +
                "labore et dolore magna aliqua.";


        private final MenuDao mMenuDao;

        private final RestaurantDao mRestaurantDao;

        private PopulateDB(AppDatabase db) {
            mMenuDao = db.menuDao();
            mRestaurantDao = db.restaurantDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mMenuDao.deleteAll();
            mRestaurantDao.deleteAll();

            mRestaurantDao.insert(new RestaurantEntity(7,"Cacio e Vino", "Italian Restaurant",
                    DESCRIPTION, "+78432453020", "geo:0,0?q=Cacio e Vino, Innopolis, Russia",
                    "https://t.me/CacioeVinoInno", R.drawable.cacio_e_vino));

            mRestaurantDao.insert(new RestaurantEntity(8,"OMC", "Canteen in University builing",
                    DESCRIPTION, "42", "geo:0,0?q=ОМС, Innopolis, Russia",
                    "https://telegram.me/joinchat/Av77aUAFkVZKStnjfWD7NA", R.drawable.omc));

            mRestaurantDao.insert(new RestaurantEntity(9,"Wrap & Go", "Ne Shaurmichnaya",
                    DESCRIPTION, "42", "geo:0,0?q=Wrap and Go, Innopolis, Russia",
                    "https://telegram.me/joinchat/DA8vV0CGBulCOEpoyMK_vA", R.drawable.wrap_and_go));
            mRestaurantDao.insert(new RestaurantEntity("Cacio e Vino2", "Italian Restaurant",
                    DESCRIPTION, "+78432453020", "geo:0,0?q=Cacio e Vino, Innopolis, Russia",
                    "https://t.me/CacioeVinoInno", R.drawable.ic_launcher_foreground));

            mRestaurantDao.insert(new RestaurantEntity("OMC2", "Canteen in University builing",
                    DESCRIPTION, "42", "geo:0,0?q=ОМС, Innopolis, Russia",
                    "https://telegram.me/joinchat/Av77aUAFkVZKStnjfWD7NA", R.drawable.ic_launcher_foreground));

            mRestaurantDao.insert(new RestaurantEntity("Wrap & Go2", "Ne Shaurmichnaya",
                    DESCRIPTION, "42", "geo:0,0?q=Wrap and Go, Innopolis, Russia",
                    "https://telegram.me/joinchat/DA8vV0CGBulCOEpoyMK_vA", R.drawable.ic_launcher_foreground));


            mMenuDao.insert(new MenuEntity(7, "Pizza Diavola", DESCRIPTION, R.drawable.food_example, 300,true));
            mMenuDao.insert(new MenuEntity(7, "Pizza Margherita", DESCRIPTION, R.drawable.food_example, 300,true));
            mMenuDao.insert(new MenuEntity(7, "Noodles soup", DESCRIPTION, R.drawable.food_example, 300,true));
            mMenuDao.insert(new MenuEntity(7, "Pizza Diavola", DESCRIPTION, R.drawable.food_example, 300,true));
            mMenuDao.insert(new MenuEntity(7, "Pizza Margherita", DESCRIPTION, R.drawable.food_example, 300,true));
            mMenuDao.insert(new MenuEntity(7, "Noodles soup", DESCRIPTION, R.drawable.food_example, 300,true));
            mMenuDao.insert(new MenuEntity(7, "Pizza Diavola", DESCRIPTION, R.drawable.food_example, 300,true));
            mMenuDao.insert(new MenuEntity(7, "Pizza Margherita", DESCRIPTION, R.drawable.food_example, 300,true));
            mMenuDao.insert(new MenuEntity(7, "Noodles soup", DESCRIPTION, R.drawable.food_example, 300,true));
            mMenuDao.insert(new MenuEntity(8, "Borsch", DESCRIPTION, R.drawable.food_example, 300,true));
            mMenuDao.insert(new MenuEntity(8, "Salad", DESCRIPTION, R.drawable.food_example, 300,true));
            mMenuDao.insert(new MenuEntity(8, "Noodles soup", DESCRIPTION, R.drawable.food_example, 300,true));
            mMenuDao.insert(new MenuEntity(9, "Wrap", DESCRIPTION, R.drawable.food_example, 300,true));
            mMenuDao.insert(new MenuEntity(9, "Another Wrap", DESCRIPTION, R.drawable.food_example, 300,true));
            mMenuDao.insert(new MenuEntity(9, "S", DESCRIPTION, R.drawable.food_example, 300,true));

            mMenuDao.insert(new MenuEntity(7, "Pizza Diavola", DESCRIPTION, R.drawable.ic_launcher_background, 300, false));
            mMenuDao.insert(new MenuEntity(7, "Pizza Margherita", DESCRIPTION, R.drawable.ic_launcher_background, 300, false));
            mMenuDao.insert(new MenuEntity(7, "Noodles soup", DESCRIPTION, R.drawable.ic_launcher_background, 300, false));
            mMenuDao.insert(new MenuEntity(7, "Pizza Diavola", DESCRIPTION, R.drawable.ic_launcher_background, 300, false));
            mMenuDao.insert(new MenuEntity(7, "Pizza Margherita", DESCRIPTION, R.drawable.ic_launcher_background, 300, false));
            mMenuDao.insert(new MenuEntity(7, "Noodles soup", DESCRIPTION, R.drawable.ic_launcher_background, 300, false));
            mMenuDao.insert(new MenuEntity(8, "Borsch", DESCRIPTION, R.drawable.ic_launcher_background, 300, false));
            mMenuDao.insert(new MenuEntity(8, "Salad", DESCRIPTION, R.drawable.ic_launcher_background, 300, false));
            mMenuDao.insert(new MenuEntity(8, "Noodles soup", DESCRIPTION, R.drawable.ic_launcher_background, 300, false));
            mMenuDao.insert(new MenuEntity(9, "Wrap", DESCRIPTION, R.drawable.ic_launcher_background, 300, false));
            mMenuDao.insert(new MenuEntity(9, "Another Wrap", DESCRIPTION, R.drawable.ic_launcher_background, 300, false));
            mMenuDao.insert(new MenuEntity(9, "S", DESCRIPTION, R.drawable.ic_launcher_background, 300, false));
            return null;
        }
    }

}