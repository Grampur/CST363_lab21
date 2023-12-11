package lab21;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import lab21.model.DoctorRepository;
import lab21.model.Patient;
import lab21.model.PatientRepository;
import lab21.view.PatientView;
/*
 * Controller class for patient interactions.
 *   register as a new patient.
 *   update patient profile.
 */
@Controller
public class ControllerPatientUpdate {
	
	@Autowired
	PatientRepository patientRepository;
	
	@Autowired
	DoctorRepository doctorRepository;

	/*
	 *  Display patient profile given patient id.
	 */
	@SuppressWarnings("unused")
	@GetMapping("/patient/edit/{id}")
	public String getUpdateForm(@PathVariable int id, Model model) {
		Patient pd = null;
		
		// TODO search for patient by id
		//  if not found, return to home page using return "index"; 
		//  else create PatientView and add to model.
		return "patient_edit";		 		
}
	
	
	/*
	 * Process changes to patient profile.
	 *  Primary doctor, street, city, state, zip can be changed
	 *  ssn, patient id, name, birthdate, ssn are read only in template.
	 */
	@PostMapping("/patient/edit")
	public String updatePatient(PatientView p, Model model) {

		// validate doctor last name 
		
		// TODO 
		
		// TODO find the patient entity and save back 
		
		//  create PatientView and add to model.
		
		return "patient_show";
	}
}
