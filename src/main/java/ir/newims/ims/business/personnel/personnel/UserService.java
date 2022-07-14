package ir.newims.ims.business.personnel.personnel;

import ir.newims.ims.models.personnel.personnel.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    User getUserByUsername(String username);

    User getCurrentUser();
}
