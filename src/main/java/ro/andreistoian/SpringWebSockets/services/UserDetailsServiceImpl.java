package ro.andreistoian.SpringWebSockets.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ro.andreistoian.SpringWebSockets.dao.UserDao;
import ro.andreistoian.SpringWebSockets.models.Role;
import ro.andreistoian.SpringWebSockets.models.User;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserDao repo;

    @Autowired
    PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        Optional<User> optionalUser = repo.findByUserName(s);

        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            Set<Role> roleSet =user.getRoles();
            String[] roleArr = new String[roleSet.size()];
            roleSet.stream().map(elem -> elem.getRoleName()).collect(Collectors.toSet()).toArray(roleArr);


            return org.springframework.security.core.userdetails.User.builder().
                    username(user.getUserName()).roles(roleArr).password(user.getPassword()).build();
        }
        else throw new UsernameNotFoundException("username not found");
    }
}
