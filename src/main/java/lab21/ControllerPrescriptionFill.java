package lab21;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


import lab21.model.*;
import lab21.model.Pharmacy.DrugCost;
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

	    Pharmacy pharmacy = pharmacyRepository.findByNameAndAddress(p.getPharmacyName(), p.getPharmacyAddress());
	    if (pharmacy == null) {
	        model.addAttribute("message", "Invalid pharmacy name or address.");
	        return "index";
	    }

	    Patient patient = patientRepository.findByLastName(p.getPatientLastName());
	    if (patient == null) {
	        model.addAttribute("message", "Invalid patient last name.");
	        return "index";
	    }

	    Prescription prescription = prescriptionRepository.findById(p.getRxid()).orElse(null);
	    if (prescription == null) {
	        model.addAttribute("message", "Invalid prescription ID.");
	        return "index";
	    }

	    if (prescription.getRefills() < 1) {
	        model.addAttribute("message", "No refills left.");
	        return "index";
	    }

	    Doctor doctor = doctorRepository.findById(prescription.getDoctor_id());
	    if (doctor == null) {
	        model.addAttribute("message", "Invalid doctor ID.");
	        return "index";
	    }

	    Prescription.FillRequest fill = new Prescription.FillRequest();
	    fill.setPharmacyID(pharmacy.getId());
	    
	    fill.setDateFilled(LocalDate.now().toString()); 

	    prescriptionRepository.save(prescription);

	    double cost = pharmacy.getDrugCosts().stream()
                .filter(drugCost -> drugCost.getDrugName().equals(prescription.getDrugName()))
                .findFirst()
                .map(DrugCost::getCost)
                .orElse(0.0) * prescription.getQuantity();

	    fill.setCost(String.valueOf(cost));

	    prescription.getFills().add(fill);
	    
	    prescription.setRefills(prescription.getRefills() - 1);

	    prescriptionRepository.save(prescription);

	    PrescriptionView prescriptionView = new PrescriptionView();
	    prescriptionView.setRxid(prescription.getRxid());
	    prescriptionView.setDrugName(prescription.getDrugName());
	    prescriptionView.setQuantity(prescription.getQuantity());
	    prescriptionView.setPatient_id(patient.getId());
	    prescriptionView.setPatientFirstName(patient.getFirstName()); 
	    prescriptionView.setPatientLastName(patient.getLastName()); 
	    prescriptionView.setDoctor_id(doctor.getId());
	    prescriptionView.setDoctorFirstName(doctor.getFirstName()); 
	    prescriptionView.setDoctorLastName(doctor.getLastName()); 
	    prescriptionView.setRefills(prescription.getRefills());
	    prescriptionView.setRefillsRemaining(prescription.getRefills());
	    prescriptionView.setPharmacyID(pharmacy.getId());
	    prescriptionView.setPharmacyName(pharmacy.getName());
	    prescriptionView.setPharmacyAddress(pharmacy.getAddress());
	    prescriptionView.setPharmacyPhone(pharmacy.getPhone());
	    prescriptionView.setDateFilled(fill.getDateFilled());
	    prescriptionView.setCost(fill.getCost());


	    model.addAttribute("message", "Prescription filled.");
	    model.addAttribute("prescription", prescriptionView);
	    return "prescription_show";
	}
	

}