package com.example.kzvda.menumanagementsystem;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.kzvda.menumanagementsystem.MESSAGE";
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private ActionBarDrawerToggle drawerToggle;
    private RecycleListFragment currentFragment;
    private User user;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);


        // Find our drawer view
        navigationView = findViewById(R.id.nav_view);
        // Setup drawer view
        setupDrawerContent(navigationView);

        // Find our drawer view
        mDrawer = findViewById(R.id.drawer_layout);
        drawerToggle = setupDrawerToggle();

        // Tie DrawerLayout events to the ActionBarToggle
        mDrawer.addDrawerListener(drawerToggle);
        selectDrawerItem(navigationView.getMenu().getItem(0));

        try {
            user = new User((HashMap) getIntent().getExtras().get(EXTRA_MESSAGE));
        } catch (NullPointerException e) {
            user = null;
//            user = Data.getAdmin();
        }
        updateView();

    }

    private void updateView() {
        boolean[][] DRAWER_MENU = {{true, false, false, true}, {true, true, false, true}, {true, false, true, true}, {true, false, false, false}};
        Menu menu = navigationView.getMenu();
        LinearLayout nav_header = (LinearLayout) navigationView.getHeaderView(0);
        int userType = 3;
        if (user == null) {
            ((ImageView) nav_header.getChildAt(0)).setImageDrawable(roundDrawable(getDrawable(R.drawable.ic_launcher_foreground)));
            ((TextView) nav_header.getChildAt(1)).setText(R.string.login);
            nav_header.getChildAt(1).setEnabled(true);

        } else {
            userType = user.getType();
            ((ImageView) nav_header.getChildAt(0)).setImageDrawable(roundDrawable(getDrawable(R.drawable.ic_launcher_foreground)));
            ((TextView) nav_header.getChildAt(1)).setText(user.getUsername());
        }
        for (int i = 0; i < 4; i++) {
            menu.getItem(i).setVisible(DRAWER_MENU[userType][i]);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return drawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        currentFragment = null;
        Class fragmentClass;
        Bundle bundle = new Bundle();
        switch (menuItem.getItemId()) {
            case R.id.nav_restaurants:
                fragmentClass = RecycleListFragment.class;
                break;
            case R.id.nav_settings:
                fragmentClass = SettingsListFragment.class;
                if (user != null) {
                    bundle.putInt("userType", user.getType());
                }
                break;
            default:
                fragmentClass = RecycleListFragment.class;
        }
        try {
            currentFragment = (RecycleListFragment) fragmentClass.newInstance();
            currentFragment.setArguments(bundle);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, currentFragment).commit();

        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        // NOTE: Make sure you pass in a valid toolbar reference.  ActionBarDrawToggle() does not require it
        // and will not render the hamburger icon without it.
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open, R.string.drawer_close);
    }

    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.username:
                intent = new Intent(this, RegistrationActivity.class);
                startActivity(intent);
                break;
            case R.id.restaurant_list_item:
                int itemPosition = currentFragment.mRecyclerView.indexOfChild(v);
                intent = new Intent(this, RestaurantMenuActivity.class);
                intent.putExtra(MainActivity.EXTRA_MESSAGE, itemPosition);
                startActivity(intent);
                break;
            case 1:
                intent = new Intent(this, ChangePersonalInfo.class);
                startActivity(intent);
                break;
            case 4:
                new AlertDialog.Builder(this)
                        .setTitle(getString(R.string.are_you_sure))
                        .setMessage(getString(R.string.delete_account_comfirmation))
                        .setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
                break;
            case 5:
                new AlertDialog.Builder(this)
                        .setTitle(getString(R.string.are_you_sure))
                        .setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
                break;

        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    // `onPostCreate` called when activity start-up is complete after `onStart()`
    // NOTE 1: Make sure to override the method with only a single `Bundle` argument
    // Note 2: Make sure you implement the correct `onPostCreate(Bundle savedInstanceState)` method.
    // There are 2 signatures and only `onPostCreate(Bundle state)` shows the hamburger icon.
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }

    public Drawable roundDrawable(Drawable dr) {
        RoundedBitmapDrawable drawable =
                RoundedBitmapDrawableFactory.create(getResources(), drawableToBitmap(dr));
        drawable.setCircular(true);
        return drawable;
    }

    private static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

}


