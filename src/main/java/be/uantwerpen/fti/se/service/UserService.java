package be.uantwerpen.fti.se.service;

import be.uantwerpen.fti.se.model.User;
import be.uantwerpen.fti.se.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Edwin on 6/10/2015.
 */
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Iterable<User> findAll() {
        return this.userRepository.findAll();
    }

    public void add(final User user) {
        this.userRepository.save(user);
    }

    public void delete(Long id) {
        this.userRepository.delete(id);
    }


    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    public void saveSomeAttributes(User user) {
        User tempUser = user.getId()==null?null:findOne(user.getId());
        if (tempUser != null){
            tempUser.setRoles(user.getRoles());
            tempUser.setUserName(user.getUserName());
            userRepository.save(tempUser);
        }
        else{
            userRepository.save(user);
        }
    }

    private User findOne(Long id) {
        return userRepository.findOne(id);
    }
}
