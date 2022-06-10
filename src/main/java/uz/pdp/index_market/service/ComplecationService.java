package uz.pdp.index_market.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.index_market.entity.Complecation;
import uz.pdp.index_market.payload.ApiResponse;
import uz.pdp.index_market.payload.ComplecationDTO;
import uz.pdp.index_market.repository.ComplecationRepository;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class ComplecationService {

    private final ComplecationRepository complecationRepository;

    public ApiResponse all() {
        String message = "";
        List<Complecation> all = complecationRepository.findByActiveIsTrue();
        if (all.isEmpty()) {
            message = "There is no categories!";
        } else {
            message = "All categories";
        }
        return new ApiResponse(message, true, all);
    }

    public ApiResponse getOne(Integer id) {
        Optional<Complecation> optionalComplecation = complecationRepository.findById(id);
        if (optionalComplecation.isPresent()) {
            Complecation complecation = optionalComplecation.get();
            return new ApiResponse("Complecation found", true, complecation);
        } else {
            return new ApiResponse("Complecation not found", false);
        }
    }

    public ApiResponse add(@Valid ComplecationDTO dto) {
        Complecation complecation = new Complecation();
        complecation.setRu_name(dto.getRu_name());
        complecation.setUz_name(dto.getUz_name());
        Complecation save = complecationRepository.save(complecation);

        return new ApiResponse("Complecation added successfully",true,save);
    }

    public ApiResponse delete(Integer id) {
        Optional<Complecation> optionalComplecation = complecationRepository.findById(id);
        if (optionalComplecation.isPresent()) {
            Complecation complecation = optionalComplecation.get();
            complecation.setActive(false);
            Complecation save = complecationRepository.save(complecation);
            return new ApiResponse("Complecation deleted", true, save);
        } else {
            return new ApiResponse("Complecation not found", false);
        }
    }

    public ApiResponse edit(@Valid ComplecationDTO dto, Integer id) {
        Optional<Complecation> byId = complecationRepository.findById(id);

        if (byId.isPresent()) {
            Complecation complecation = byId.get();

            complecation.setRu_name(dto.getRu_name());
            complecation.setUz_name(dto.getUz_name());

            Complecation save = complecationRepository.save(complecation);

            return new ApiResponse("Complecation saved",true,save);
        }
        return new ApiResponse("Something went wrong",false);
    }
}
