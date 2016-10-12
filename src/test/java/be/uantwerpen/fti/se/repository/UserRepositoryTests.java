package be.uantwerpen.fti.se.repository;

import be.uantwerpen.fti.se.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created by Edwin on 22/10/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UserRepositoryTests {
    @Autowired
    UserRepository userRepository;
    @Test
    public void testSaveProduct(){
        //setup product
        User user = new User();
        user.setUserName("TestUserName");

        //save product, verify has ID value after save
        assertNull(user.getId()); //null before save
        userRepository.save(user);
        assertNotNull(user.getId()); //not null after save

        //fetch from DB
        User fetchedUser = userRepository.findOne(user.getId());

        //should not be null
        assertNotNull(fetchedUser);

        //should equal
        assertEquals(user.getId(), fetchedUser.getId());
        assertEquals(user.getUserName(), fetchedUser.getUserName());

        //update description and save
        fetchedUser.setUserName("NewTestUserName");
        userRepository.save(fetchedUser);

        //get from DB, should be updated
        User fetchedUpdatedUser = userRepository.findOne(fetchedUser.getId());
        assertEquals(fetchedUser.getUserName(), fetchedUpdatedUser.getUserName());

        //verify count of products in DB
        long userCount = userRepository.count();
        assertEquals(userCount, 3);

        //get all products, list should only have one more then initial value
        Iterable<User> users = userRepository.findAll();

        int count = 0;

        for(User p : users){
            count++;
        }

        assertEquals(count, 3);// we starten reeds met 2 gebruikers in de database
    }
}
