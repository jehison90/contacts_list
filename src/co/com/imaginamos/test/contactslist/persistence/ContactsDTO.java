package co.com.imaginamos.test.contactslist.persistence;

import java.io.Serializable;

public class ContactsDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int id;
	public String name;
	public String phone;
	public String email;
	public byte[] image;
	
	public ContactsDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ContactsDTO(int id, String name, String phone, String email) {
		super();
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.email = email;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}		

}
