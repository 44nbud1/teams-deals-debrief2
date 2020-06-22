package dana.order.usecase.port;

import dana.order.entity.User;

public interface UserRepository {
    Boolean doesUserExist(String idUser);
    Boolean doesPhoneNumberCorrect(String idUser, String phoneNumber);
    User getUserById(String idUser);
}
