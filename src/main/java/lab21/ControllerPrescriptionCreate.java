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
	    Doctor doctor = doctorRepository.findByIdAndFirstNameAndLastName(p.getDoctor_id(),p.getDoctorFirstName(), p.getDoctorLastName());
	    if (doctor == null) {
	        model.addAttribute("message", "Invalid doctor name or ID.");
	        return "prescription_create";
	    }

	    // Validate patient name and id
	    Patient patient = patientRepository.findByIdAndFirstNameAndLastName(p.getPatient_id(), p.getPatientFirstName(), p.getPatientLastName());
	    if (patient == null) {
	        model.addAttribute("message", "Invalid patient name or ID.");
	        return "prescription_create";
	    }

	    // Validate drug name
	    Drug drug = drugRepository.findByName(p.getDrugName());
	    if (drug == null) {
	        model.addAttribute("message", "Invalid drug name.");
	        return "prescription_create";
	    }

	    // Insert prescription
	    Prescription prescription = new Prescription();
	    prescription.setRxid(sequence.getNextSequence("RXID_SEQUENCE"));
	    prescription.setDrugName(drug.getName());
	    prescription.setQuantity(p.getQuantity());
	    prescription.setPatient_id(patient.getId());
	    prescription.setDoctor_id(doctor.getId());
	    prescription.setDateCreated(LocalDate.now().toString());
	    prescription.setRefills(p.getRefills());
	    
	    prescriptionRepository.save(prescription);

	    model.addAttribute("message", "Prescription created.");
	    model.addAttribute("prescription", prescription);
	    return "prescription_show";
	}

}