package web.superspring.service;

import web.superspring.model.User;
import java.util.List;
import java.util.Optional;

public interface UserService{
    void add(User user);
    List<User> listUsers();
    Optional<User> show(int id);
    void update(User updatedUser);
    void delete(int id);
    User findUsersByEmail(String username);
}
