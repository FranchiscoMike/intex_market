package uz.pdp.index_market.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import uz.pdp.index_market.entity.UserRole;
import uz.pdp.index_market.payload.ApiResponse;
import uz.pdp.index_market.repository.UserRoleRepository;

import javax.management.InstanceNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserRoleService {
    private final UserRoleRepository roleRepository;

    public ApiResponse all() {
        List<UserRole> roleList = roleRepository.findByActiveIsTrue();

        if (roleList.isEmpty()) {
            return new ApiResponse("There is no roles", false);
        }
        return new ApiResponse("All list of roles", true, roleList);
    }

    public ApiResponse add(String name) {
        Optional<UserRole> optional = roleRepository.findByName(name);

        if (optional.isPresent()) {
            return new ApiResponse("This role already exists",false);
        }
        UserRole role = new UserRole();
        role.setName(name);
        UserRole save = roleRepository.save(role);
        return new ApiResponse("New role saved successfully!",true,save);
    }

    public ApiResponse one(UUID id) {
        Optional<UserRole> byId = roleRepository.findById(id);
        return byId.map(userRole -> new ApiResponse("Found", true, userRole)).orElseGet(() -> new ApiResponse("Role not found", false));
    }

    @SneakyThrows
    public ApiResponse delete(UUID id) {
        Optional<UserRole> byId = roleRepository.findById(id);

        if (byId.isPresent()) {
            UserRole role = byId.get();
            role.setActive(false);
            roleRepository.save(role);
            return new ApiResponse("Role deleted successfully!",true);
        } else {
            throw new InstanceNotFoundException("This id not found!");
        }
    }

    public ApiResponse edit(UUID id, String new_name) {  // name va id bo'yicha search qilish kerak edi
        Optional<UserRole> byName = roleRepository.findByName(new_name);
        if (byName.isEmpty() ) {
            // yani bunaqa name li role bo'lmasa
            Optional<UserRole> byId = roleRepository.findById(id);
            if (byId.isPresent()) {
                UserRole role = byId.get();
                role.setName(new_name);
                UserRole save = roleRepository.save(role);
                return new ApiResponse("Role is edited",true,save);

            }
        } else {
            // bunda shunaqa nameli role bo'ladi endi active is checked
            UserRole role = byName.get();

            if (role.isActive()){
                return new ApiResponse("Role is already exists",false);
            }else {
                roleRepository.delete(role);
                // avtive false bo'lgani o'chadi va berilgan role active bo'ladi
                Optional<UserRole> byId = roleRepository.findById(id);
                if (byId.isPresent()) {
                    UserRole role1 = byId.get();
                    role1.setName(new_name);
                    UserRole saved = roleRepository.save(role1);
                    return new ApiResponse("Role is edited",true,saved);
                }
            }
        }
        return new ApiResponse("Something went wrong!",false);
    }
}
