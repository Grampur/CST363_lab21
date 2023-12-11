package lab21.model;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface PharmacyRepository extends MongoRepository<Pharmacy, Integer> {
	
	Pharmacy findByNameAndAddress(String name, String address);

}
