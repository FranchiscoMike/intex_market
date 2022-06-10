package uz.pdp.index_market.data_loader;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import uz.pdp.index_market.entity.UserRole;
import uz.pdp.index_market.repository.UserRoleRepository;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddl;

    private final UserRoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        System.out.println(ddl);
        if (ddl.equals("create")){

            UserRole role = new UserRole();
            role.setName("user");
            roleRepository.save(role);
        }
    }
}
