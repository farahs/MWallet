package com.example.mwallet;

import android.os.Bundle;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DrawerActivity extends Activity implements OnClickListener {

	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	private ListView menuList;
	private String[] menuTitles;
	
	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	private RelativeLayout mDrawerLeftPanel;

	private TextView username;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_drawer);

		this.mTitle = this.mDrawerTitle = this.getTitle();
		this.setupView();
		this.setupEvent();
		this.setupData();
	}

	private void setupView() {
		this.mDrawerLayout = (DrawerLayout) this.findViewById(R.id.drawer_layout);
		this.mDrawerLeftPanel = (RelativeLayout) this.findViewById(R.id.left_panel);

		this.username = (TextView) this.findViewById(R.id.drawer_username);
		
		this.setupMainFragment();
		this.setupActionToggleButton();
		this.setupListPanel();
	}

	private void setupMainFragment() {
		MainActivity fragment = new MainActivity();
		FragmentManager fragmentManager = this.getFragmentManager();
		fragmentManager.beginTransaction().replace(R.id.content_frame, fragment);

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
		this.menuList.setAdapter(new ArrayAdapter<String>(this, R.layout.module_drawer_list_item, this.menuTitles));
		
	}

	private void setupEvent() {
		
		this.menuList.setOnItemClickListener(new MenuItemClickListener());
	}

	private void setupData() {

	}

	private class MenuItemClickListener implements ListView.OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,	long id) {
			// TODO Auto-generated method stub
			switch (position) {
			case 0:
				Intent payIntent = new Intent(getApplicationContext(), PaymentActivity.class);
				startActivity(payIntent);
//				Toast.makeText(this.getApplicationContext(), "Payment", Toast.LENGTH_SHORT).show();
				break;
			case 1:
				Intent topIntent = new Intent(getApplicationContext(), TopUpActivity.class);
				startActivity(topIntent);
//				 Toast.makeText(this.getApplicationContext(), "Top Up", Toast.LENGTH_SHORT).show();
				break;
			case 2:
				Intent hisIntent = new Intent(getApplicationContext(), HistoryActivity.class);
				startActivity(hisIntent);
//				 Toast.makeText(this.getApplicationContext(), "History", Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}
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
		
		return true;
	}

	public void openSetting(MenuItem item) {
		Intent i = new Intent(this.getApplicationContext(), SettingActivity.class);
		this.startActivity(i);
	}

}
