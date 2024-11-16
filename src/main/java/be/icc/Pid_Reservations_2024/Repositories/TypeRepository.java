package be.icc.Pid_Reservations_2024.Repositories;

import be.icc.Pid_Reservations_2024.Models.Types;
import org.springframework.data.repository.CrudRepository;

public interface TypeRepository extends CrudRepository<Types, Long> {

    Types findById(long id);

}
