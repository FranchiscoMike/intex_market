package uz.pdp.index_market.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.index_market.entity.Attachment;
import uz.pdp.index_market.entity.Category;
import uz.pdp.index_market.entity.Product;
import uz.pdp.index_market.payload.ApiResponse;
import uz.pdp.index_market.payload.ProductDTO;
import uz.pdp.index_market.repository.AttachmentRepository;
import uz.pdp.index_market.repository.CategoryRepository;
import uz.pdp.index_market.repository.ProductRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final AttachmentRepository attachmentRepository;
    private final CategoryRepository categoryRepository;


    public ApiResponse all() {
        List<Product> all = productRepository.findAll();
        if (all.isEmpty()) {
            return new ApiResponse("List is empty!",false);
        } else {
            return new ApiResponse("All products",true,all);
        }
    }

    public ApiResponse one(UUID id) {
        Optional<Product> byId = productRepository.findById(id);
        return byId.map(product -> new ApiResponse("Found", true, product)).orElseGet(() -> new ApiResponse("There is no product with this id", false));
    }


    public ApiResponse add(ProductDTO dto) {

         // hammasi avvaldan aniq keladi deb hisoblanib code yozilmoqda

        Optional<Attachment> optionalAttachment = attachmentRepository.findById(dto.getAttachment_id());
        if (optionalAttachment.isPresent()) {
            Attachment attachment = optionalAttachment.get();

            Optional<Category> optionalCategory = categoryRepository.findById(dto.getCategory_id());
            if (optionalCategory.isPresent()) {
                Category category = optionalCategory.get();

                // hamma narsa bor endi save qilinadi set qilip :

                Product product = new Product();

                product.setAttachment(attachment);
                product.setCategory(category);
                product.setDepth(dto.getDepth());
                product.setComplecationList_ru(dto.getComplecationList_ru());
                product.setComplecationList_uz(dto.getComplecationList_uz());

                if (dto.getReal_price() <= dto.getDiscount_price() ){
                    return new ApiResponse("You are cheating me please enter discount price less than real price",false);
                }

                product.setDiscount_price(dto.getDiscount_price());
                product.setReal_price(dto.getReal_price());
                product.setSize(dto.getSize());
                product.setRu_frame(dto.getRu_frame());
                product.setUz_frame(dto.getUz_frame());

                Product save = productRepository.save(product);

                return new ApiResponse("New product saved successfully!",true,save);
            }
        }
        return new ApiResponse("Something went wrong",false);
    }

    public ApiResponse edit(UUID id, ProductDTO dto) {
        Optional<Product> byId = productRepository.findById(id);
        if (byId.isPresent()) {
            Product product = byId.get();

            Optional<Attachment> optionalAttachment = attachmentRepository.findById(dto.getAttachment_id());
            if (optionalAttachment.isPresent()) {
                Attachment attachment = optionalAttachment.get();

                Optional<Category> optionalCategory = categoryRepository.findById(dto.getCategory_id());
                if (optionalCategory.isPresent()) {
                    Category category = optionalCategory.get();

                    product.setAttachment(attachment);
                    product.setCategory(category);
                    product.setDepth(dto.getDepth());
                    product.setComplecationList_ru(dto.getComplecationList_ru());
                    product.setComplecationList_uz(dto.getComplecationList_uz());

                    if (dto.getReal_price() <= dto.getDiscount_price() ){
                        return new ApiResponse("You are cheating me please enter discount price less than real price",false);
                    }

                    product.setDiscount_price(dto.getDiscount_price());
                    product.setReal_price(dto.getReal_price());
                    product.setSize(dto.getSize());
                    product.setRu_frame(dto.getRu_frame());
                    product.setUz_frame(dto.getUz_frame());

                    Product save = productRepository.save(product);

                    return new ApiResponse("Product saved successfully",true,save);
                }
            }
        }
        return new ApiResponse("Something went wrong!",false);
    }

    public ApiResponse delete(UUID id) {
        Optional<Product> byId = productRepository.findById(id);
        if (byId.isPresent()) {
            productRepository.delete(byId.get());
            return new ApiResponse("Product is deleted",true);
        } else {
            return new ApiResponse("Product is not found",false);
        }
    }
}
