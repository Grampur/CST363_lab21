package lab21;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


import lab21.model.*;
import lab21.view.*;

@Controller
public class ControllerPrescriptionFill {

	@Autowired
	PharmacyRepository pharmacyRepository;

	@Autowired
	PatientRepository patientRepository;
	
	@Autowired
	DoctorRepository doctorRepository;
	
	@Autowired
	PrescriptionRepository prescriptionRepository;

	/*
	 * Patient requests form to search for prescription.
	 */
	@GetMapping("/prescription/fill")
	public String getfillForm(Model model) {
		model.addAttribute("prescription", new PrescriptionView());
		return "prescription_fill";
	}

	@PostMapping("/prescription/fill")
	public String processFillForm(PrescriptionView p, Model model) {

		/*
		 * valid pharmacy name and address, get pharmacy id and phone
		 */
		// TODO 
		
		// TODO find the patient information 

		// TODO find the prescription 


		/*
		 * have we exceeded the number of allowed refills
		 * the first fill is not considered a refill.
		 */
		
		// TODO 
		
		/*
		 * get doctor information 
		 */
		// TODO 
		
		/*
		 * insert prescription fill record
		 */

		// TODO create a PrescriptionFill and add to prescription.fills list
		
		/*
		 * calculate cost of prescription
		 */
		// TODO 
		
		// TODO save updated prescription 

		// show the updated prescription with the most recent fill information
		model.addAttribute("message", "Prescription filled.");
		model.addAttribute("prescription", p);
		return "prescription_show";
	}

}