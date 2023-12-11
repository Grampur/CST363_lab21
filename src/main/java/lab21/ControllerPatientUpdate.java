package lab21;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import lab21.model.Doctor;
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
		Patient patient = patientRepository.findById(id).get();
		
		if (patient != null) {
			new PatientView();
			model.addAttribute("patient", PatientView.fromDB(patient));
			return "patient_edit";
		} else {
			model.addAttribute("message", "Patient not found.");
			model.addAttribute("id", id);
			return "index";
		}
		
		// TODO search for patient by id
		//  if not found, return to home page using return "index"; 
		//  else create PatientView and add to model.	 		
}
	
	
	/*
	 * Process changes to patient profile.
	 *  Primary doctor, street, city, state, zip can be changed
	 *  ssn, patient id, name, birthdate, ssn are read only in template.
	 */
	@PostMapping("/patient/edit")
	public String updatePatient(PatientView p, Model model) {

		// validate doctor last name 
		Doctor doctor = doctorRepository.findByLastName(p.getPrimaryName());
		if (doctor != null) {
			Patient updatedPatient = Patient.fromView(p);
			updatedPatient.setPrimaryName(p.getPrimaryName());
			updatedPatient.setStreet(p.getStreet());
			updatedPatient.setCity(p.getCity());
			updatedPatient.setState(p.getState());
			updatedPatient.setZipcode(p.getZipcode());
			
			patientRepository.save(updatedPatient);
			
			model.addAttribute("message", "Update successful");
			model.addAttribute("patient", p);
			return "patient_show";
		} else {
			model.addAttribute("message", "Doctor not found.");
			model.addAttribute("id", p.getPrimaryName());
			return "index";
		}
		
		// TODO find the patient entity and save back 
		
		//  create PatientView and add to model.
	}
}