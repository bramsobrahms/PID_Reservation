package be.icc.Pid_Reservations_2024.Services;

import be.icc.Pid_Reservations_2024.Models.Localities;
import be.icc.Pid_Reservations_2024.Repositories.LocalityRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class LocalitiesService {

    @Autowired
    private LocalityRepository localityRepository;

    public List<Localities> getAll() {
        java.util.List<Localities> localities = new ArrayList<>();

        localityRepository.findAll().forEach(localities::add);

        return localities;
    }

    public Localities getById(long id) {
        return localityRepository.findById(id).orElse(null);
    }

    public void addLocality(Localities locality) {
        localityRepository.save(locality);
    }

    public void updateLocality(long id, Localities locality) {
        localityRepository.save(locality);
    }

    public void deleteLocality(long id) {
        localityRepository.deleteById(id);
    }

}
