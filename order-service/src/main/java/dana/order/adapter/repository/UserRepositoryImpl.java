package dana.order.adapter.repository;

import dana.order.entity.User;
import dana.order.usecase.port.DatabaseMapper;
import dana.order.usecase.port.DatabaseRepository;
import dana.order.usecase.port.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    DatabaseRepository databaseRepository;

    public Boolean doesUserExist(String idUser){
        if (databaseRepository.getUserExistById(idUser) < 1){
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    public Boolean doesPhoneNumberCorrect(String idUser, String phoneNumber){
        User user = databaseRepository.getUserById(idUser);
        String newPhone = "+62"+phoneNumber.substring(1);
        if (!user.getPhoneNumber().equals(newPhone)){
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    public User getUserById(String idUser){
        return databaseRepository.getUserById(idUser);
    }

    public String getUniqueKey(String key){
        return databaseRepository.getUniqueKey(key);
    }

    public void addUniqueKey(String key, String response){
        databaseRepository.addUniqueKey(key, response);
    }

}
