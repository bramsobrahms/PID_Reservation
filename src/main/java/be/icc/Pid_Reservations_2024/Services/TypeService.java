package be.icc.Pid_Reservations_2024.Services;

import be.icc.Pid_Reservations_2024.Models.Types;
import be.icc.Pid_Reservations_2024.Repositories.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TypeService {

    @Autowired
    private TypeRepository typeRepository;

    public List<Types> getAllTypes() {
        List<Types> types = new ArrayList<>();
        typeRepository.findAll().forEach(types::add);
        return types;
    }

    public Types getType(long id){
        return typeRepository.findById(id);
    }

}
