package uz.pdp.index_market.service;

import lombok.RequiredArgsConstructor;
import org.apache.catalina.authenticator.SavedRequest;
import org.springframework.stereotype.Service;
import uz.pdp.index_market.entity.Category;
import uz.pdp.index_market.payload.ApiResponse;
import uz.pdp.index_market.payload.CategoryDTO;
import uz.pdp.index_market.repository.CategoryRepository;

import java.net.CacheRequest;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public ApiResponse all() {
        String message = "";
        List<Category> all = categoryRepository.findByActiveIsTrue();
        if (all.size()==0) {
            message = "There is no categories!";
        } else {
            message = "All categories";
        }
        return new ApiResponse(message, true, all);
    }

    public ApiResponse getOne(UUID id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isPresent()) {
            Category category = optionalCategory.get();
            return new ApiResponse("Category found", true, category);
        } else {
            return new ApiResponse("Category not found", false);
        }
    }

    public ApiResponse add(CategoryDTO dto) {
        Category category = new Category();
        category.setName_ru(dto.getName_ru());
        category.setName_uz(dto.getName_uz());
        Category save = categoryRepository.save(category);

        return new ApiResponse("Category added successfully",true,save);
    }

    public ApiResponse delete(UUID id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isPresent()) {
            Category category = optionalCategory.get();
            category.setActive(false);
            Category save = categoryRepository.save(category);
            return new ApiResponse("Category deleted", true, save);
        } else {
            return new ApiResponse("Category not found", false);
        }
    }

    public ApiResponse edit(CategoryDTO dto, UUID id) {
        Optional<Category> byId = categoryRepository.findById(id);

        if (byId.isPresent()) {
            Category category = byId.get();

            category.setName_ru(dto.getName_ru());
            category.setName_uz(dto.getName_uz());

            Category save = categoryRepository.save(category);

            return new ApiResponse("Category saved",true,save);
        }
        return new ApiResponse("Something went wrong",false);
    }
}
