package org.example.backend.Service;


import lombok.AllArgsConstructor;
import org.example.backend.Domains.Users;
import org.example.backend.Repo.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UsersService {

    @Autowired
    private final UsersRepo usersRepo;

    public Long create(Users user){

//        Need to make sure that username is not taken
        if (usersRepo.existsByUsername(user.getUsername()))
            throw new RuntimeException("Username already exists");

//        Wanting to set the users token number so we have an easier thing to move around.
//        Don't want to do something with the password or username since those are sensative

        user.setToken(setToken());
//        Saves there info into the db
        usersRepo.save(user);

//        Brings back the token so it instantly signs them in. If everything is smooth
        return user.getToken();
    }


    public void delete(Long token){

//  New object for the user to eventually find it
        Users user = usersRepo.findByToken(token);
        usersRepo.delete(user);
    }

    public long login(Users user) {
        Users login = usersRepo.findByUsernameAndPassword(user.getUsername(), user.getPassword()).orElse(null);


        if (login == null){
            throw new RuntimeException("Invalid username or password");
        }

        return login.getToken();
    }




    private long setToken() {
        long token;
        do {
            token = (long) Math.ceil(Math.random() * 10000000);
        } while (usersRepo.existsByToken(token)); // retry if it already exists
        return token;
    }




}
