package lab21;

import java.time.LocalDate;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import lab21.model.Doctor;
import lab21.model.DoctorRepository;
import lab21.model.Drug;
import lab21.model.DrugRepository;
import lab21.model.Patient;
import lab21.model.PatientRepository;
import lab21.model.Prescription;
import lab21.model.PrescriptionRepository;
import lab21.service.SequenceService;
import lab21.view.PrescriptionView;

@Controller
public class ControllerPrescriptionCreate {

	@Autowired
	DoctorRepository doctorRepository;

	@Autowired
	PatientRepository patientRepository;

	@Autowired
	DrugRepository drugRepository;
	
	@Autowired
	PrescriptionRepository prescriptionRepository;
	
	@Autowired
	SequenceService sequence;
	/*
	 * Doctor requests blank form for new prescription.
	 */
	@GetMapping("/prescription/new")
	public String getPrescriptionForm(Model model) {
		model.addAttribute("prescription", new PrescriptionView());
		return "prescription_create";
	}

	@PostMapping("/prescription")
	public String createPrescription(PrescriptionView p, Model model) {

	    // Validate doctor name and id
	    Doctor doctor = doctorRepository.findByIdAndFirstNameAndLastName(p.getDoctor_id(), p.getDoctorFirstName(), p.getDoctorLastName());
	    if (doctor == null) {
	        model.addAttribute("message", "Invalid doctor name or ID.");
	        return "index";
	    }

	    // Validate patient name and id
	    Patient patient = patientRepository.findByIdAndFirstNameAndLastName(p.getPatient_id(), p.getPatientFirstName(), p.getPatientLastName());
	    if (patient == null) {
	        model.addAttribute("message", "Invalid patient name or ID.");
	        return "index";
	    }

	    // Validate drug name
	    Drug drug = drugRepository.findByName(p.getDrugName());
	    if (drug == null) {
	        model.addAttribute("message", "Invalid drug name.");
	        return "index";
	    }
	    
	    

	    // Insert prescription
	    Prescription prescription = new Prescription();
	    prescription.setRxid(sequence.getNextSequence("RXID_SEQUENCE"));
	    prescription.setDrugName(p.getDrugName());
	    prescription.setQuantity(p.getQuantity());
	    prescription.setPatient_id(p.getPatient_id());
	    prescription.setDoctor_id(p.getDoctor_id());
	    prescription.setDateCreated(LocalDate.now().toString());
	    prescription.setRefills(p.getRefills());
	    
	    prescriptionRepository.save(prescription);

	    // Create a new PrescriptionView object to send back to the view
	    PrescriptionView prescriptionView = new PrescriptionView();
	    prescriptionView.setRxid(prescription.getRxid());
	    prescriptionView.setDrugName(prescription.getDrugName());
	    prescriptionView.setQuantity(prescription.getQuantity());
	    prescriptionView.setPatient_id(prescription.getPatient_id());
	    prescriptionView.setDoctor_id(prescription.getDoctor_id());
	    prescriptionView.setDateCreated(prescription.getDateCreated());
	    prescriptionView.setRefills(prescription.getRefills());

	    // Assuming you have setters for doctor and patient names in PrescriptionView
	    prescriptionView.setDoctorFirstName(doctor.getFirstName());
	    prescriptionView.setDoctorLastName(doctor.getLastName());
	    prescriptionView.setPatientFirstName(patient.getFirstName());
	    prescriptionView.setPatientLastName(patient.getLastName());

	    model.addAttribute("message", "Prescription created.");
	    model.addAttribute("prescription", prescriptionView); // Pass the PrescriptionView object
	    return "prescription_show";
	}

}