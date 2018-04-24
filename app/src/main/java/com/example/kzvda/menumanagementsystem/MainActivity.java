package com.example.kzvda.menumanagementsystem;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
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
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kzvda.menumanagementsystem.ViewModel.ViewModel;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.kzvda.menumanagementsystem.MESSAGE";
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle drawerToggle;
    private Fragment currentFragment;
    private NavigationView mNavigationView;
    SharedPreferences sharedPref;
    ViewModel mViewModel;

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
        mViewModel = ViewModelProviders.of(this).get(ViewModel.class);
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
                    int itemPosition = ((RestaurantListFragment) currentFragment).mRecyclerView.getChildAdapterPosition(v);
                    Intent i = new Intent(this, RestaurantMenuActivity.class);
                    i.putExtra(MainActivity.EXTRA_MESSAGE, restaurants.get(itemPosition).getId());
                    startActivity(i);
                });
                break;
            case 1:
                intent = new Intent(this, ChangePersonalInfoActivity.class);
                startActivity(intent);
                break;
            case 3:
                mViewModel.downloadRestaurants();
                break;
            case 4:
                new AlertDialog.Builder(this)
                        .setTitle(getString(R.string.are_you_sure))
                        .setMessage(getString(R.string.delete_account_comfirmation))
                        .setCancelable(true)
                        .setPositiveButton("Yes", (dialog, id) -> {
                            sharedPref.edit().clear().apply();
                            Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent1);
                        })
                        .setNegativeButton("No", null)
                        .show();
                break;
            case 5:
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
            case 6:
                intent = new Intent(this, ChangeMenuActivity.class);
                startActivity(intent);
                break;
            case 7:
                intent = new Intent(this, SendNotificationActivity.class);
                startActivity(intent);
                break;
            case 8:
                intent = new Intent(this, EditRestaurantInformationActivity.class);
                startActivity(intent);
                break;
            case 9:
                intent = new Intent(this, ManageTemplatesActivity.class);
                startActivity(intent);
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

}


