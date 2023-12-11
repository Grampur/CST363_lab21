package lab21;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import lab21.model.Doctor;
import lab21.model.DoctorRepository;
import lab21.service.SequenceService;

/*
 * Controller class for doctor registration and profile update.
 * This class is complete and provided as an example.
 */
@Controller
public class ControllerDoctor {

	@Autowired
	DoctorRepository doctorRepository;
	
	@Autowired
	SequenceService sequence;
	/*
	 * Request for new doctor registration form.
	 */
	@GetMapping("/doctor/register")
	public String getNewDoctorForm(Model model) {
		// return blank form for new patient registration
		model.addAttribute("doctor", new Doctor());
		return "doctor_register";
	}

	/*
	 * Process doctor registration.
	 */
	@PostMapping("/doctor/register")
	public String createDoctor(Doctor doctor, Model model) {
		// get the next unique id for doctor.
		int id = sequence.getNextSequence("DOCTOR_SEQUENCE");
		doctor.setId(id);
		doctorRepository.insert(doctor);
		// display message and patient information
		model.addAttribute("message", "Registration successful.");
		model.addAttribute("doctor", doctor);
		return "doctor_show";

	}

	/*
	 * Request blank form for doctor search.
	 */
	@GetMapping("/doctor/get")
	public String getSearchForm(Model model) {
		// return bank form to enter doctor id and name
		model.addAttribute("doctor", new Doctor());
		return "doctor_get";
	}

	/*
	 * Search for doctor by id and name.
	 */
	@PostMapping("/doctor/get")
	public String showDoctor(Doctor doctor, Model model) {

		Doctor d = doctorRepository.findByIdAndLastName(doctor.getId(), doctor.getLastName());
		if (d != null) {
			model.addAttribute("doctor", d);
			return "doctor_show";

		} else {
			model.addAttribute("message", "Doctor not found.");
			model.addAttribute("doctor", doctor);
			return "doctor_get";
		}
	}

	/*
	 * search for doctor by id.
	 */
	@GetMapping("/doctor/edit/{id}")
	public String getUpdateForm(@PathVariable int id, Model model) {

		Doctor d = doctorRepository.findById(id);
		if (d != null) {
			model.addAttribute("doctor", d);
			return "doctor_edit";
		} else {
			model.addAttribute("message", "Doctor not found.");
			return "doctor_get";
		}
	}

	/*
	 * process profile update for doctor. 
	 * Only specialty or year of practice fields are allowed to be changed.
	 */
	@PostMapping("/doctor/edit")
	public String updateDoctor(Doctor doctor, Model model) {
		Doctor d = doctorRepository.save(doctor);
		model.addAttribute("message", "Update successful");
		model.addAttribute("doctor", d);
		return "doctor_show";
	}
	
}
