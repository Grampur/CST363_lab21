package lab21;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import lab21.model.Doctor;
import lab21.model.DoctorRepository;
import lab21.model.Patient;
import lab21.model.PatientRepository;
import lab21.service.SequenceService;
import lab21.view.PatientView;

/*
 * Controller class for patient interactions.
 *   register as a new patient.
 *   update patient profile.
 */
@Controller
public class ControllerPatientCreate {
	
	@Autowired
	PatientRepository patientRepository;
	
	@Autowired
	DoctorRepository doctorRepository;
	
	@Autowired
	SequenceService sequence;
	
	/*
	 * Request blank patient registration form.
	 */
	@GetMapping("/patient/new")
	public String getNewPatientForm(Model model) {
		// return blank form for new patient registration
		model.addAttribute("patient", new PatientView());
		return "patient_register";
	}
	
	/*
	 * Process new patient registration
	 */
	@PostMapping("/patient/new")
	public String createPatient(PatientView p, Model model) {
		if (doctorRepository.findByLastName(p.getPrimaryName()) != null) {
			Patient newPatient = Patient.fromView(p);
			int id = sequence.getNextSequence("PATIENT_SEQUENCE");
			newPatient.setId(id);
			p.setId(id);
			patientRepository.insert(newPatient);

			// display generated ID, message
			model.addAttribute("message", "Registration successful.");
			model.addAttribute("patient", p);
			return "patient_show";
		} else {
			model.addAttribute("message", "Doctor not found.");
			model.addAttribute("doctor", p.getPrimaryName());
			return "index";
		}
	}
	
	/*
	 * Request blank form to search for patient by and and id
	 */
	@GetMapping("/patient/edit")
	public String getSearchForm(Model model) {
		model.addAttribute("patient", new PatientView());
		return "patient_get";
	}
	
	/*
	 * Perform search for patient by patient id and name.
	 */
	@PostMapping("/patient/show")
	public String showPatient(PatientView p, Model model) {
		
		Patient searchP = patientRepository.findByIdAndLastName(p.getId(), p.getLastName());
		if (searchP != null) {
			model.addAttribute("patient", searchP);
			return "patient_show";

		} else {
			model.addAttribute("message", "Patient not found.");
			model.addAttribute("patient", p);
			return "patient_get";
		}
		
		// if found, return "patient_show", else return "patient_get"
	}
}