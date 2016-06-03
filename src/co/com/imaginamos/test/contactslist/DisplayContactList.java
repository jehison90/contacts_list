package co.com.imaginamos.test.contactslist;

import java.util.List;

import co.com.imaginamos.test.contactslist.adapter.ContactsListAdapter;
import co.com.imaginamos.test.contactslist.persistence.ContactsDTO;
import co.com.imaginamos.test.contactslist.persistence.ContactsDatasource;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.os.Build;

public class DisplayContactList extends ActionBarActivity {

	private ContactsDatasource datasource;
	public static String CONTACT_INTENT_KEY = "contact";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_contact_list);
		
		try{
			datasource = new ContactsDatasource(this);
			datasource.open();
			
			final List<ContactsDTO> existingContacts = datasource.getAllContacts();
			ContactsListAdapter adapter = new ContactsListAdapter(this, R.layout.contact_list_item, existingContacts);
			
			ListView lvContacts = (ListView) findViewById(R.id.lvContacts);
			
			lvContacts.setAdapter(adapter);		
			
			lvContacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int position, long id) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(DisplayContactList.this, AddContact.class);
					intent.putExtra(MainActivity.UPDATE_OR_EDIT_KEY, 2);
					intent.putExtra(CONTACT_INTENT_KEY, existingContacts.get(position));
					startActivity(intent);
				}
				
			});

			datasource.close();
		}catch (Exception e){
			Log.e("error en db", e.getMessage());
		}
//				if (savedInstanceState == null) {
//			getSupportFragmentManager().beginTransaction()
//					.add(R.id.container, new PlaceholderFragment()).commit();
//		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_contact_list, menu);
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
			View rootView = inflater.inflate(
					R.layout.fragment_display_contact_list, container, false);
			return rootView;
		}
	}

}
