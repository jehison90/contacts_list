package co.com.imaginamos.test.contactslist.adapter;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import co.com.imaginamos.test.contactslist.R;
import co.com.imaginamos.test.contactslist.persistence.ContactsDTO;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ContactsListAdapter extends BaseAdapter{
	
	Context context;
	int layoutId;
	List<ContactsDTO> contactsList = null;
	
	public ContactsListAdapter(Context context, int layoutId, List<ContactsDTO> existingContacts){
		this.context = context;
		this.layoutId = layoutId;
		this.contactsList = existingContacts;
	}
	
	static class ContactHolder{
		ImageView imContactIcon;
		TextView contactName;
		TextView contactPhone;
	}

	@Override
	public int getCount() {
		return contactsList.size();
	}

	@Override
	public Object getItem(int position) {		
		return contactsList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View item = convertView;
//		ContactHolder contactHolder = null;
		
		if(item == null){
			LayoutInflater inflater = ((Activity)context).getLayoutInflater();
			item = inflater.inflate(layoutId, null);
			
		}
		
		ContactsDTO contact = contactsList.get(position);
		
		((TextView) item.findViewById(R.id.contactName)).setText(contact.getName());
		((TextView) item.findViewById(R.id.contactPhone)).setText(contact.getPhone());
					
		ByteArrayInputStream imageStream = new ByteArrayInputStream(contact.getImage());
        Bitmap image = BitmapFactory.decodeStream(imageStream);
        
        if(image != null){
        	((ImageView) item.findViewById(R.id.contactIcon)).setImageBitmap(image);
        }                
        
        if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
        	((ImageView) item.findViewById(R.id.contactIcon)).setVisibility(View.VISIBLE);
            //Do some stuff
        } else {
        	((ImageView) item.findViewById(R.id.contactIcon)).setVisibility(View.GONE);
        }
		
		return item;
	}

}
