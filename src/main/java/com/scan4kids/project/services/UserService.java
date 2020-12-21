package com.scan4kids.project.services;
import com.scan4kids.project.daos.RolesRepository;
import com.scan4kids.project.daos.UsersRepository;
import com.scan4kids.project.models.Role;
import com.scan4kids.project.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
@Service
public class UserService {
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    public void importUsersCSV(){
        List<String> lines = null;
        User importUser = null;

        try {
            lines = Files.readAllLines(Paths.get("data", "doistudentraw.csv"));
            String[] newList = null;
            for (String line : lines) {
                System.out.println("line = " + line);
                //TODO: Now that you can read a file, separate the values by commas and build a new User object with those pieces and save it into the DB.
                newList = line.split(",");
                System.out.println(newList[2]);
//                importUser = new User(newList[0].toLowerCase() + '_' + newList[1].toLowerCase(), newList[0], newList[1], passwordEncoder.encode(newList[2]), newList[6], null, newList[5], newList[4]);
                importUser = new User(newList[0].toLowerCase() + '_' + newList[1].toLowerCase(), newList[0], newList[1], passwordEncoder.encode(newList[2]), newList[6], null, newList[4], newList[3], null);

                //TODO: Consider hashing the passwords, I believe the best approach is to generate random passwords for them and let me change it in their profile page, here's a suggestion:
//                String hash = passwordEncoder.encode(newList[2]);
//                importUser.setPassword(hash);
                usersRepository.save(importUser);


            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}