package com.example.mwallet;

import java.util.Arrays;
import java.util.List;

import com.example.mwallet.adapter.DrawerAdapter;
import com.example.pengguna.PenggunaController;

import android.os.Bundle;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class DrawerActivity extends FragmentActivity implements OnClickListener {

	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	private ListView menuList;
	private String[] menuTitles;
	
	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	private RelativeLayout mDrawerLeftPanel;
	
	private PenggunaController penggunaController;
	
	private LinearLayout logoutButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_drawer);

		this.mTitle = this.mDrawerTitle = this.getTitle();
		this.penggunaController = new PenggunaController();
		this.setupView();
		this.setupEvent();
		this.setupData();
	}

	private void setupView() {
		this.mDrawerLayout = (DrawerLayout) this.findViewById(R.id.drawer_layout);
		this.mDrawerLeftPanel = (RelativeLayout) this.findViewById(R.id.left_panel);
		this.logoutButton = (LinearLayout) this.findViewById(R.id.logoutButton);
		
		logoutButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				logoutUser();
			}
		});
		
		this.setupMainFragment();
		this.setupActionToggleButton();
		this.setupListPanel();
	}

	private void setupMainFragment() {
		
		MainFragment fragment = new MainFragment();
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

	}

	private void setupActionToggleButton() {
		this.getActionBar().setDisplayHomeAsUpEnabled(true);
		this.getActionBar().setHomeButtonEnabled(true);

		this.mDrawerToggle = new ActionBarDrawerToggle(this, /* host Activity */
		this.mDrawerLayout, /* DrawerLayout object */
		R.drawable.ic_drawer, /* nav drawer image to replace 'Up' caret */
		R.string.drawer_open, /* "open drawer" description for accessibility */
		R.string.drawer_close /* "close drawer" description for accessibility */
		) {

			@Override
			public void onDrawerClosed(View view) {
				DrawerActivity.this.getActionBar().setTitle(
						DrawerActivity.this.mTitle);
				DrawerActivity.this.invalidateOptionsMenu(); // creates call
																// to
				// onPrepareOptionsMenu()
			}

			@Override
			public void onDrawerOpened(View drawerView) {
				DrawerActivity.this.getActionBar().setTitle(
						DrawerActivity.this.mDrawerTitle);
				DrawerActivity.this.invalidateOptionsMenu(); // creates call
																// to
				// onPrepareOptionsMenu()
			}
		};
		this.mDrawerLayout.setDrawerListener(this.mDrawerToggle);
	}

	private void setupListPanel() {
		
		this.menuTitles = this.getResources().getStringArray(R.array.main_menus_array);
		this.menuList = (ListView) this.findViewById(R.id.menuList);
		this.mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
		List<String> listMenu = Arrays.asList(menuTitles);

		this.menuList.setAdapter(new DrawerAdapter(getApplicationContext(),listMenu));
		
	}

	private void setupEvent() {
		
		this.menuList.setOnItemClickListener(new MenuItemClickListener());
	}

	private void setupData() {

	}

	private class MenuItemClickListener implements ListView.OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,	long id) {
			
			DrawerActivity.this.menuSelectItem(position);
			
		}
		
	}
	
	private void menuSelectItem(int position) {
		
		this.mDrawerLayout.closeDrawer(this.mDrawerLeftPanel);
		Fragment fragment;
		FragmentManager fragmentManager;
		
		switch (position) {
		case 0:
			fragment = new MainFragment();
			fragmentManager = this.getSupportFragmentManager();
			fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
			break;
		case 1:
			fragment = new PaymentFragment();
			fragmentManager = this.getSupportFragmentManager();
			fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
			break;
		case 2:
			fragment = new TopUpFragment();
			fragmentManager = this.getSupportFragmentManager();
			fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
			break;
		case 3:
			fragment = new HistoryFragment();
			fragmentManager = this.getSupportFragmentManager();
			fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
			break;
		default:
			break;
		}

		
		
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu)
	{
		boolean drawerOpen = this.mDrawerLayout.isDrawerOpen(this.mDrawerLeftPanel);
		// menu.findItem(R.id.action_openSettingDrawer).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// The action bar home/up action should open or close the drawer.
		// ActionBarDrawerToggle will take care of this.
		if (this.mDrawerToggle.onOptionsItemSelected(item))
		{

			return true;
		}
		// Handle action buttons
		switch (item.getItemId())
		{
			case android.R.id.home:
				return true;

			default:
				return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState)
	{
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		this.mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig)
	{
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		this.mDrawerToggle.onConfigurationChanged(newConfig);
	}
	
	@Override
	public void onClick(View v) {
		
	}
		
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
	
		menu.findItem(R.id.action_settings).setOnMenuItemClickListener(
				new OnMenuItemClickListener() {

					@Override
					public boolean onMenuItemClick(MenuItem item) {
						Intent intent = new Intent(DrawerActivity.this,
								SettingActivity.class);
						startActivity(intent);
						return true;
					}
				});

		return super.onCreateOptionsMenu(menu);
		
	}
	
	public void logoutUser(){
		penggunaController.logoutUser(getApplicationContext());
		Toast.makeText(this, "Logout successful", Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(this, LoginActivity.class);
		startActivity(intent);
		finish();
	}
}
