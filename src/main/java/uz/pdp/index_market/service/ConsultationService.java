package uz.pdp.index_market.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.index_market.entity.Consultation;
import uz.pdp.index_market.payload.ApiResponse;
import uz.pdp.index_market.payload.ConsultationDTO;
import uz.pdp.index_market.repository.ConsultationRepository;

import javax.management.InstanceNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConsultationService {

    private final ConsultationRepository consultationRepository;

    public ApiResponse add(ConsultationDTO dto) {
        // doim consultation oladi
        Consultation consultation = new Consultation();

        consultation.setPhone_number(dto.getPhone_number());
        consultation.setUsername(dto.getUsername());

        consultationRepository.save(consultation);

        return new ApiResponse("New Consultation is saved!",true);
    }

    public ApiResponse check(UUID id) throws InstanceNotFoundException {
        Optional<Consultation> byId = consultationRepository.findById(id);
        if (byId.isPresent()) {
            Consultation consultation = byId.get();
            consultation.setChecked(true);
            Consultation save = consultationRepository.save(consultation);

            return new ApiResponse("This consultation is checked",true,save);
        } else {
            throw new InstanceNotFoundException("Id not found");
        }
    }

    public ApiResponse all() {
        List<Consultation> all = consultationRepository.findAll();

        if (all.isEmpty()){
            return new ApiResponse("List of consultations is empty",false);
        }
        return new ApiResponse("All",true,all);
    }
}
