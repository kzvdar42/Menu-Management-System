package com.example.kzvda.menumanagementsystem.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kzvda.menumanagementsystem.R;
import com.example.kzvda.menumanagementsystem.db.Entity.RestaurantEntity;
import com.example.kzvda.menumanagementsystem.fragment.AdministratorPageFragment;
import com.example.kzvda.menumanagementsystem.fragment.ManageFragment;
import com.example.kzvda.menumanagementsystem.fragment.RestaurantListFragment;
import com.example.kzvda.menumanagementsystem.fragment.SettingsListFragment;
import com.example.kzvda.menumanagementsystem.viewModel.MainViewModel;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.kzvda.menumanagementsystem.MESSAGE";
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle drawerToggle;
    private Fragment currentFragment;
    private NavigationView mNavigationView;
    SharedPreferences sharedPref;
    MainViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionbar = getSupportActionBar();
        Objects.requireNonNull(actionbar).setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        // Find our nav view
        mNavigationView = findViewById(R.id.nav_view);
        // Setup drawer view
        setupDrawerContent(mNavigationView);
        // Find our drawer view
        mDrawer = findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open, R.string.drawer_close);
        mDrawer.addDrawerListener(drawerToggle);
        if (savedInstanceState == null) {
            selectDrawerItem(mNavigationView.getMenu().getItem(0));
        }
        sharedPref = this.getSharedPreferences("user", Context.MODE_PRIVATE);
        updateView();
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mViewModel.downloadRestaurants();

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
        switch (menuItem.getItemId()) {
            case R.id.nav_restaurants:
                fragmentClass = RestaurantListFragment.class;
                break;
            case R.id.nav_manage:
                fragmentClass = ManageFragment.class;
                break;
            case R.id.nav_administrator_page:
                fragmentClass = AdministratorPageFragment.class;
                break;
            case R.id.nav_settings:
                fragmentClass = SettingsListFragment.class;
                break;
            default:
                fragmentClass = RestaurantListFragment.class;
        }
        try {
            currentFragment = (Fragment) fragmentClass.newInstance();
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

    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.username:
                intent = new Intent(this, RegistrationActivity.class);
                startActivity(intent);
                break;
            case R.id.restaurant_list_item:
                mViewModel.getRestaurantsList().observe(this, restaurants -> {
                    try {
                        int itemPosition = ((RestaurantListFragment) currentFragment).mRecyclerView.getChildAdapterPosition(v);
                        RestaurantEntity restaurant = restaurants.get(itemPosition);
                        Intent i = new Intent(this, RestaurantMenuActivity.class);
                        i.putExtra(MainActivity.EXTRA_MESSAGE, restaurant.getId());
                        i.putExtra("restaurantName", restaurant.getName());
                        startActivity(i);
                    } catch (Exception e) {
                    }
                });
                break;
            case R.id.settings_change_personal_info:
                intent = new Intent(this, ChangePersonalInfoActivity.class);
                startActivity(intent);
                break;
            case R.id.settings_support:
                intent = new Intent(this, SupportActivity.class);
                startActivity(intent);
                break;
            case R.id.settings_delete_account:
                new AlertDialog.Builder(this)
                        .setTitle(getString(R.string.are_you_sure))
                        .setMessage(getString(R.string.delete_account_comfirmation))
                        .setCancelable(true)
                        .setPositiveButton("Yes", (dialog, id) -> {
                            mViewModel.deleteAccount(sharedPref.getString("username", ""));
                            sharedPref.edit().clear().apply();
                            Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent1);
                        })
                        .setNegativeButton("No", null)
                        .show();
                break;
            case R.id.settings_logout:
                new AlertDialog.Builder(this)
                        .setTitle(getString(R.string.are_you_sure))
                        .setCancelable(true)
                        .setPositiveButton("Yes", (dialog, id) -> {
                            sharedPref.edit().clear().apply();
                            Intent intent12 = new Intent(getApplicationContext(), MainActivity.class);
                            intent12.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent12);
                        })
                        .setNegativeButton("No", null)
                        .show();
                break;
            case R.id.change_menu:
                intent = new Intent(this, ChangeMenuActivity.class);
                startActivity(intent);
                break;
            case R.id.send_notification:
                intent = new Intent(this, SendNotificationActivity.class);
                startActivity(intent);
                break;
            case R.id.edit_restaurant_info:
                intent = new Intent(this, EditRestaurantInformationActivity.class);
                startActivity(intent);
                break;
            case R.id.manage_templates:
                intent = new Intent(this, ManageTemplatesActivity.class);
                startActivity(intent);
                break;
            case R.id.manage_users:
                intent = new Intent(this, ManageUsersActivity.class);
                startActivity(intent);
                break;
            case R.id.manage_restaurants:
                break;
        }
    }

    private void updateView() {
        boolean[][] DRAWER_MENU = {{true, false, false, true}, {true, true, false, true}, {true, false, true, true}, {true, false, false, false}};
        Menu menu = mNavigationView.getMenu();
        LinearLayout nav_header = (LinearLayout) mNavigationView.getHeaderView(0);
        int usertype = sharedPref.getInt("usertype", 3);
        String text = sharedPref.getString("username", getString(R.string.register_login));
        ((TextView) nav_header.getChildAt(0)).setText(text);
        nav_header.getChildAt(0).setEnabled(usertype == 3);
        for (int i = 0; i < 4; i++) {
            menu.getItem(i).setVisible(DRAWER_MENU[usertype][i]);
        }
        menu.getItem(1).setEnabled(sharedPref.getBoolean("verified", false));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return drawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
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


    @Override
    protected void onPostResume() {
        super.onPostResume();
        mViewModel.downloadRestaurants();
    }
}


