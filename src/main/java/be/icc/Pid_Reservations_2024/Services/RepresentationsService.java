package be.icc.Pid_Reservations_2024.Services;

import be.icc.Pid_Reservations_2024.Models.Representation;
import be.icc.Pid_Reservations_2024.Repositories.RepresentationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RepresentationsService {

    @Autowired
    RepresentationRepository representationRepository;

    public Representation getRepresentation(long id){
        return representationRepository.findById(id).orElse(null);
    }

    public List<Representation> getAllRepresentations(){
        return representationRepository.findAll();
    }

}
