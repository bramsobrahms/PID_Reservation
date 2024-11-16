package be.icc.Pid_Reservations_2024.Services;

import be.icc.Pid_Reservations_2024.Models.Artists;
import be.icc.Pid_Reservations_2024.Repositories.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class ArtistService {

    @Autowired
    private ArtistRepository artistRepository;

    public List<Artists> getAllArtists() {
        List<Artists> artists = new ArrayList<>();
        artistRepository.findAll().forEach(artists::add);
        return artists;
    }

}
