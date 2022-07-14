package ir.newims.ims.business.personnel.personnel;

import ir.newims.ims.ResponseConstant;
import ir.newims.ims.ResponseConstantMessage;
import ir.newims.ims.business.personnel.footwork.FootWorkCheck;
import ir.newims.ims.exception.BusinessException;
import ir.newims.ims.filter.services.UserDetailsImpl;
import ir.newims.ims.models.personnel.personnel.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserOperational implements UserService {

    @Autowired
    FootWorkCheck footWorkCheck;

    @Autowired
    UserRepo userRepo;

    @Override
    public User getUserByUsername(String username){
        return userRepo.getByUsername(username).orElseThrow(() -> {
            throw new BusinessException(ResponseConstant.USERNAME_NOT_EXIST, ResponseConstantMessage.USERNAME_NOT_EXIST);
        });
    }

    @Override
    public User getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return getUserByUsername(userDetails.getUsername());
    }
}
