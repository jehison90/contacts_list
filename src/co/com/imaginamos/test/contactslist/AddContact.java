package co.com.imaginamos.test.contactslist;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import co.com.imaginamos.test.contactslist.persistence.ContactsDTO;
import co.com.imaginamos.test.contactslist.persistence.ContactsDatasource;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.os.Build;
import android.provider.MediaStore;

public class AddContact extends ActionBarActivity {

	int updateOrEdit;
	ContactsDTO contactToEdit;
	byte imageInByte[];
	
	public static int REQUEST_CODE_CAMERA = 0;
	public static int REQUEST_CODE_GALLERY = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_contact);
		
		Intent intent = getIntent();
		updateOrEdit = intent.getIntExtra(MainActivity.UPDATE_OR_EDIT_KEY, 2);
		
		if(updateOrEdit == 2){
			Bundle mBundle = intent.getExtras();
			contactToEdit = (ContactsDTO)mBundle.getSerializable(DisplayContactList.CONTACT_INTENT_KEY);
		}

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_contact, menu);
		return true;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == REQUEST_CODE_CAMERA){
			if(resultCode == RESULT_OK || resultCode == RESULT_FIRST_USER){
				Bundle extras = data.getExtras();

				   if (extras != null) {
				    Bitmap image = (Bitmap) extras.get("data");
				    // convert bitmap to byte
				    ByteArrayOutputStream stream = new ByteArrayOutputStream();
				    image.compress(Bitmap.CompressFormat.PNG, 100, stream);
				    imageInByte = stream.toByteArray();			    			    
			}

		}
	} else if (requestCode == REQUEST_CODE_GALLERY){
		if(resultCode == RESULT_OK || resultCode == RESULT_FIRST_USER){
//			Bundle extras2 = data.getExtras();
				   Uri selectedImageUri = data.getData();
				   Bitmap image;
				   image = null;
				try {
					image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    // convert bitmap to byte
			    ByteArrayOutputStream stream = new ByteArrayOutputStream();
			    if (image != null){
			    	image.compress(Bitmap.CompressFormat.PNG, 100, stream);
				    imageInByte = stream.toByteArray();			    		
			    }
			    	    
		}
	}
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
	
	public boolean validateEmail(String email){
         String regExpn =
             "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                 +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                   +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                   +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                   +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                   +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

     CharSequence inputStr = email;

     Pattern pattern = Pattern.compile(regExpn,Pattern.CASE_INSENSITIVE);
     Matcher matcher = pattern.matcher(inputStr);

     if(matcher.matches()){
    	 return true; 
     }else
        return false;
    }
	
	public boolean validateMandatoryField(EditText et){
		if(et.getText().toString().equals("") || et.getText().toString().isEmpty()){
			return false;
		} else{
			return true;
		}
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		
		String messageOfValidation;
		
		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			final View rootView = inflater.inflate(R.layout.fragment_add_contact,
					container, false);
			
			final EditText etName = (EditText)rootView.findViewById(R.id.etName);
			final EditText etPhone = (EditText)rootView.findViewById(R.id.etPhone);
			final EditText etEmail = (EditText)rootView.findViewById(R.id.etEmail);
			
			final LinearLayout lyDelete = (LinearLayout)rootView.findViewById(R.id.lyDelete);
			
			final Button deleteButton = (Button)rootView.findViewById(R.id.deleteButton);
			
			Button b = (Button) rootView.findViewById(R.id.addButton);
			if(((AddContact)getActivity()).updateOrEdit == 1){
				b.setText("AGREGAR");
				lyDelete.setVisibility(View.GONE);
				b.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						
						if(validateFields(rootView)){
							ContactsDTO contact = new ContactsDTO();
							contact.setName(etName.getText().toString());
							contact.setPhone(etPhone.getText().toString());
							contact.setEmail(etEmail.getText().toString());
							contact.setImage(((AddContact)getActivity()).imageInByte);
							
							ContactsDatasource datasource = new ContactsDatasource(getActivity());
							datasource.open();
							datasource.createContact(contact);
							datasource.close();
							
							getActivity().finish();
						} else {
							Toast.makeText(getActivity(), messageOfValidation, Toast.LENGTH_SHORT).show();
						}
						// TODO Auto-generated method stub
						
					}
				});
			} else{
				
				final ContactsDTO contact = ((AddContact) getActivity()).contactToEdit;
				
				etName.setText(contact.getName());
				etPhone.setText(contact.getPhone());
				etEmail.setText(contact.getEmail());
				
				lyDelete.setVisibility(View.VISIBLE);
				
				b.setText("ACTUALIZAR");
				b.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View arg0) {												
						
						if(validateFields(rootView)){							
							contact.setName(((EditText)(rootView.findViewById(R.id.etName))).getText().toString());
							contact.setPhone(((EditText)(rootView.findViewById(R.id.etPhone))).getText().toString());
							contact.setEmail(((EditText)(rootView.findViewById(R.id.etEmail))).getText().toString());
							
							ContactsDatasource datasource = new ContactsDatasource(getActivity());
							datasource.open();
							datasource.updateContact(contact);
							datasource.close();
							
							Intent intent = new Intent(getActivity(), DisplayContactList.class);
							startActivity(intent);
							
							getActivity().finish();
						} else{
							Toast.makeText(getActivity(), messageOfValidation, Toast.LENGTH_SHORT).show();
						}
						// TODO Auto-generated method stub
						
					}
				});
				
				deleteButton.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						ContactsDatasource datasource = new ContactsDatasource(getActivity());
						datasource.open();
						datasource.deleteContact(contact.getId());
						datasource.close();
						
						Intent intent = new Intent(getActivity(), DisplayContactList.class);
						startActivity(intent);
						
						getActivity().finish();
					}
				});
				
			}

			ImageView imCamera = (ImageView) rootView.findViewById(R.id.imCamera);
			imCamera.setOnTouchListener(new View.OnTouchListener() {
				
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					getActivity().startActivityForResult(takePicture, AddContact.REQUEST_CODE_CAMERA);
					return false;
				}
			});
			
			ImageView imGallery = (ImageView) rootView.findViewById(R.id.imGallery);
			imGallery.setOnTouchListener(new View.OnTouchListener() {
				
				@Override
				public boolean onTouch(View v, MotionEvent event) {				
					
					Intent selectImageFromGallery = new Intent(Intent.ACTION_PICK,
					           android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
					getActivity().startActivityForResult(selectImageFromGallery , AddContact.REQUEST_CODE_GALLERY);
					return false;
				}
			});
			
			return rootView;
		}
		
		public boolean validateFields(View rootView){
			String name = ((EditText)(rootView.findViewById(R.id.etName))).getText().toString();
			String phone = ((EditText)(rootView.findViewById(R.id.etPhone))).getText().toString();
			String email = ((EditText)(rootView.findViewById(R.id.etEmail))).getText().toString();
			
			if(name.equals("") || name.isEmpty()){
				messageOfValidation = "El campo nombre es obligatorio";
				return false;
			} else if(phone.equals("") || phone.isEmpty()){
				messageOfValidation = "El campo teléfono es obligatorio";
				return false;
			} else if(email.equals("") || email.isEmpty()){
				messageOfValidation = "El campo email es obligatorio";
				return false;
			} else {
			    if(!((AddContact)getActivity()).validateEmail(email)){
			    	messageOfValidation = "Por favor ingrese un email válido";
			    }
			}
			
			return true;
			
		}
		
	}

}
