package be.icc.Pid_Reservations_2024.Services;

import be.icc.Pid_Reservations_2024.Models.Representation;
import be.icc.Pid_Reservations_2024.Repositories.RepresentationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RepresentationsService {

    @Autowired
    RepresentationsRepository representationsRepository;

    public Representation getRepresentation(long id){
        return representationsRepository.findById(id).orElse(null);
    }

}
