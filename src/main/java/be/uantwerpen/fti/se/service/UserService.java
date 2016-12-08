package be.uantwerpen.fti.se.service;

import be.uantwerpen.fti.se.model.TestPlan;
import be.uantwerpen.fti.se.model.User;
import be.uantwerpen.fti.se.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

    public List<User> findAllAdmins() {

        List<User> userList = new ArrayList<User>();
        for (User  user : userRepository.findAll()) {
            if(user.isAdmin())
                userList.add(user);
        }
        return userList;
    }

    public void add(final User user) {
        this.userRepository.save(user);
    }

    public void delete(Long id) {
        if(findOne(id).isAdmin()) {
            int admins = findAllAdmins().size();
            if (((List<?>) findAllAdmins()).size() > 1) {
                this.userRepository.delete(id);
            }
        }
        else{
            this.userRepository.delete(id);
        }
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
