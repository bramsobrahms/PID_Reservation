package be.icc.Pid_Reservations_2024.Repositories;

import be.icc.Pid_Reservations_2024.Models.Localities;
import org.springframework.data.repository.CrudRepository;

public interface LocalityRepository extends CrudRepository<Localities, Long> {

    Localities finByPostalCode(String postalCode);

    Localities findbyLocality(String locality);

}
