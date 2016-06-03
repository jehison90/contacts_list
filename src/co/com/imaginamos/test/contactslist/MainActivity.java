package co.com.imaginamos.test.contactslist;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.os.Build;

public class MainActivity extends ActionBarActivity {
	
	public static String UPDATE_OR_EDIT_KEY = "updateEditSelection";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);				

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			
			final ImageView addContact = (ImageView) rootView.findViewById(R.id.addContactImage);
			addContact.setOnTouchListener(new View.OnTouchListener() {
				
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					Intent intent = new Intent(getActivity(), AddContact.class);
					intent.putExtra(UPDATE_OR_EDIT_KEY, 1);
					startActivity(intent);
					return false;
				}
			});
			
			final ImageView contactList = (ImageView) rootView.findViewById(R.id.contactsListImage);
			contactList.setOnTouchListener(new View.OnTouchListener() {
				
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					Intent intent = new Intent(getActivity(), DisplayContactList.class);
					startActivity(intent);
					return false;
				}
			});
			
			return rootView;
		}
	}

}
