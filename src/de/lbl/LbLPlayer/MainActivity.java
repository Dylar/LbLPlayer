package de.lbl.LbLPlayer;



import android.app.*;
import android.content.res.*;
import android.graphics.drawable.*;
import android.os.*;
import android.support.v4.app.*;
import android.support.v4.widget.*;
import android.util.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import android.widget.SeekBar.*;
import de.lbl.LbLPlayer.*;
import de.lbl.LbLPlayer.gui.*;
import de.lbl.LbLPlayer.model.*;
import de.lbl.LbLPlayer.system.*;
import java.util.*;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.View.OnClickListener;

public class MainActivity extends Activity
{
//	final String PLAY_RANDOM = "Play Random";
//	final String PLAY_STRAIGHT = "Play straight";
//	final String PLAY_SELECTED = "Play just selected";
//	final String PLAY_ALL = "Play all";

	public SystemController sc;

	String[] menutitles;
	TypedArray menuIcons;

	// nav drawer title
	private CharSequence mDrawerTitle;
	private CharSequence mTitle;

	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	private List<DrawerRowInfo> rowItems;
	private DrawerMenuAdapter adapter;
	private FragmentManager fragmentManager;
	
	private SeekbarHandler sbh;
	private MusicButtonHandler mbh;

	public void startSeekbar(int p0, int dur)
	{
		sbh.startSeekbar(p0, dur);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		sc = SystemController.GetInstance();
		sc.startSystem(this);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		
		initDrawerMenu();
		sbh = new SeekbarHandler(this);
		mbh = new MusicButtonHandler(this);
		
		initDisplay();
		
	}
	
	private void initDisplay(){
		fragmentManager = getFragmentManager();
		
//		FragmentTransaction transaction = fragmentManager.beginTransaction();
//		//transaction.setCustomAnimations(R.animator.enter, R.animator.exit, R.animator.popenter, R.animator.popexit);
//		Fragment frag = new Songs_Fragment();
//		//frag.se
//		transaction.add(R.id.main_container, frag);
//		//transaction.addToBackStack(null);
//		transaction.commit();
		updateDisplay(0);
	}

	private void changeFragment(Fragment frag)
	{
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		transaction.setCustomAnimations(R.animator.enter, R.animator.exit, R.animator.popenter, R.animator.popexit);

		transaction.replace(R.id.main_container, frag);
		transaction.addToBackStack(null);
		transaction.commit();
	}

	private void initDrawerMenu()
	{
		mTitle = mDrawerTitle = getTitle();

		getActionBar().setIcon(
			new ColorDrawable(getResources().getColor(android.R.color.transparent)));   

		menutitles = getResources().getStringArray(R.array.drawerRowTitles);
		menuIcons = getResources().obtainTypedArray(R.array.drawerRowIcons);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
		mDrawerList = (ListView) findViewById(R.id.slider_list);

		rowItems = new ArrayList<DrawerRowInfo>();

		for (int i = 0; i < menutitles.length; i++)
		{
			DrawerRowInfo items = new DrawerRowInfo(menutitles[i], menuIcons.getResourceId(
														i, -1));
			rowItems.add(items);
		}

		menuIcons.recycle();

		adapter = new DrawerMenuAdapter(getApplicationContext(), rowItems);

		mDrawerList.setAdapter(adapter);
		mDrawerList.setOnItemClickListener(new SlideitemListener());

		// enabling action bar app icon and behaving it as toggle button
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
												  R.drawable.ic_launcher , R.string.app_name, R.string.app_name) {
			public void onDrawerClosed(View view)
			{
				getActionBar().setTitle(mTitle);
				// calling onPrepareOptionsMenu() to show action bar icons
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView)
			{
				getActionBar().setTitle(mDrawerTitle);
				// calling onPrepareOptionsMenu() to hide action bar icons
				invalidateOptionsMenu();
			}
		};

		mDrawerLayout.setDrawerListener(mDrawerToggle);

//		if (savedInstanceState == null) {
//			// on first time display view for first nav item
//			updateDisplay(0);
//		}

	}

	private void updateDisplay(int position)
	{
		Fragment fragment = null;
		switch (position)
		{
			case 0:
				fragment = new Songs_Fragment();
				
				showToast("pos 0");
				break;
			case 1:
				showToast("pos 1");
				//adapt = playlistAdapt;
				fragment = new PlaylistFragment();
				break;
			case 2:
				showToast("pos 2");
				
				fragment = new FolderFragment();
				break;
			default:
				break;
		}
		mDrawerLayout.closeDrawer(mDrawerList);
		if (fragment != null)
		{
			changeFragment(fragment);
             // update selected item and title, then close the drawer
            setTitle(menutitles[position]);
			mDrawerLayout.closeDrawer(mDrawerList);
		}
		else
		{
		 //error in creating fragment
		Log.e("MainActivity", "Error in creating fragment");
		}

	}

	@Override
	public void setTitle(CharSequence title)
	{
		mTitle = title;
        getActionBar().setTitle(mTitle);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// toggle nav drawer on selecting action bar app icon/title
		if (mDrawerToggle.onOptionsItemSelected(item))
		{
			return true;
		}
		// Handle action bar actions click
		switch (item.getItemId())
		{
				//case R.id.action_settings:
				//return true;
			default :
				return super.onOptionsItemSelected(item);
		}
	}

	/***
	 * Called when invalidateOptionsMenu() is triggered
	 */
    @Override
	public boolean onPrepareOptionsMenu(Menu menu)
	{
		// if nav drawer is opened, hide the action items
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		menu.findItem(R.id.menu).setVisible(true);
		invalidateOptionsMenu();
		return super.onPrepareOptionsMenu(menu);
	}

	
	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

    @Override
	protected void onPostCreate(Bundle savedInstanceState)
	{
		super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig)
	{
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggles
		mDrawerToggle.onConfigurationChanged(newConfig);
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item)
	{
		switch (item.getItemId())
		{
			case R.id.close:
				sc.stopSystem();
				break;
			case R.id.menu:
				
				break;
				//onDestroy();
		}
		return super.onMenuItemSelected(featureId, item);
	}

	private void showToast(String s)
	{
		Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
	}

	public void refreshList()
	{
		Fragment frag = fragmentManager.findFragmentById(R.id.main_container);
		if (frag instanceof Songs_Fragment)
			((Songs_Fragment)frag).refresh();
		mbh.setPlayButtonImage();
	}

	@Override
	public void onDestroy()
	{
		super.onDestroy();
		SystemAction sa = sc.getNewAction(SystemController.ON_DESTROY);
		sc.tryAction(sa);
	}


	
	private class SlideitemListener implements ListView.OnItemClickListener
	{
		@Override
		public void onItemClick(AdapterView parent, View view, int position, long id)
		{
			updateDisplay(position);
		}
	}

	@Override
	public void onBackPressed()
	{
		if(fragmentManager.getBackStackEntryCount() < 3){
			fragmentManager.popBackStack();
			super.onBackPressed();
		}
		else
			fragmentManager.popBackStack();
	}

	
}
