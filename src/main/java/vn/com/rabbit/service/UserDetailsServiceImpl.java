package vn.com.rabbit.service;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vn.com.rabbit.entity.Account;
import vn.com.rabbit.repository.AccountRepository;
import vn.com.rabbit.repository.AccountRoleRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {


    private final AccountRepository accountRepository;

    private final AccountRoleRepository accountRoleRepository;
    public UserDetailsServiceImpl(AccountRepository acc, AccountRoleRepository accountRoleRepository)
    {
        this.accountRepository = acc;
        this.accountRoleRepository = accountRoleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account user = accountRepository.findByName("login", username);
        System.out.println("UserInfo= " + user);

        if (user == null) {
            throw new UsernameNotFoundException("User " + username + " was not found in the database");
        }

        // [USER,ADMIN,..]
        List<String> roles= accountRoleRepository.findAllJoinTableByID(user.getUuid(), "login", String.class);

        List<GrantedAuthority> grantList= new ArrayList<GrantedAuthority>();
        if(roles!= null)  {
            for(String role: roles)  {
                // ROLE_USER, ROLE_ADMIN,..
                GrantedAuthority authority = new SimpleGrantedAuthority(role);
                grantList.add(authority);
            }
        }
        UserDetails userDetails = new User(user.getLogin(), //
                user.getPassword(),grantList);

        return userDetails;
    }

}