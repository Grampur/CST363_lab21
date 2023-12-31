package lab21.view;

import lab21.model.Patient;

/*
 * This class is used to transfer data to/from patient templates.
 */
public class PatientView {
	
	private int id;  // unique id generated by database.
	private String lastName;
	private String firstName;
	private String birthdate;  // yyyy-mm-dd
	private String ssn;
	private String street;
	private String city;
	private String state;
	private String zipcode;
	private String primaryName; 
	
	public static PatientView fromDB(Patient pd) {
		PatientView pv = new PatientView();
		pv.setBirthdate(pd.getBirthdate());
		pv.setCity(pd.getCity());
		pv.setFirstName(pd.getFirstName());
		pv.setId(pd.getId());
		pv.setLastName(pd.getLastName());
		pv.setPrimaryName(pd.getPrimaryName());
		pv.setSsn(pd.getSsn());
		pv.setState(pd.getState());
		pv.setStreet(pd.getStreet());
		pv.setZipcode(pd.getZipcode());
		return pv;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}
	public String getSsn() {
		return ssn;
	}
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getPrimaryName() {
		return primaryName;
	}
	public void setPrimaryName(String primaryName) {
		this.primaryName = primaryName;
	}
	
	@Override
	public String toString() {
		return "Patient [id=" + id + ", lastName=" + lastName + ", firstName=" + firstName + ", birthdate=" + birthdate
				+ ", ssn=" + ssn + ", street=" + street + ", city=" + city + ", state=" + state + ", zipcode=" + zipcode
				+ ", primaryName=" + primaryName + "]";
	}
}
