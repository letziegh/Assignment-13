package com.coderscampus.assignment13.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.coderscampus.assignment13.domain.Address;
import com.coderscampus.assignment13.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coderscampus.assignment13.domain.Account;
import com.coderscampus.assignment13.domain.User;
import com.coderscampus.assignment13.repository.AccountRepository;
import com.coderscampus.assignment13.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private AccountRepository accountRepo;

    @Autowired
    private AddressRepository addressRepo;

    public List<User> findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    public List<User> findByNameAndUsername(String name, String username) {
        return userRepo.findByNameAndUsername(name, username);
    }

    public List<User> findByCreatedDateBetween(LocalDate date1, LocalDate date2) {
        return userRepo.findByCreatedDateBetween(date1, date2);
    }

    public User findExactlyOneUserByUsername(String username) {
        List<User> users = userRepo.findExactlyOneUserByUsername(username);
        if (users.size() > 0)
            return users.get(0);
        else
            return new User();
    }

    public Set<User> findAll() {
        return userRepo.findAllUsersWithAccountsAndAddresses();
    }

    public User findById(Long userId) {
        Optional<User> userOpt = userRepo.findById(userId);
        return userOpt.orElse(new User());
    }

    public User saveUser(User user) {
        if (user.getUserId() == null) {
            Account checking = new Account();
            checking.setAccountName("Checking Account");
            checking.getUsers().add(user);
            Account savings = new Account();
            savings.setAccountName("Savings Account");
            savings.getUsers().add(user);

            user.getAccounts().add(checking);
            user.getAccounts().add(savings);
            accountRepo.save(checking);
            accountRepo.save(savings);
            user = userRepo.save(user);

        } else {

            if (user.getAddress() != null) {
                Address address = user.getAddress();
                address.setUser(user); // Set the reference back to the user
                addressRepo.save(address); // Save or update the address
            }
        }

        return userRepo.save(user); // Save or update the user
    }


    @Transactional
    public User updateUser(User user) {
        User existingUser = userRepo.findById(user.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        existingUser.setUsername(user.getUsername());
        // Only update the password if it's not null or empty
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            existingUser.setPassword(user.getPassword());
        }
        existingUser.setName(user.getName());

        Address address = existingUser.getAddress();
        if (address == null) {

            address = new Address();
            address.setUser(existingUser);
        }


        address.setAddressLine1(user.getAddress().getAddressLine1());
        address.setAddressLine2(user.getAddress().getAddressLine2());
        address.setCity(user.getAddress().getCity());
        address.setRegion(user.getAddress().getRegion());
        address.setCountry(user.getAddress().getCountry());
        address.setZipCode(user.getAddress().getZipCode());

        addressRepo.save(address);

        existingUser.setAddress(address);  // Link the updated address to the user

        return userRepo.save(existingUser);
    }


    public Address findAddressByUserId(Long userId) {
        Optional<User> userOpt = userRepo.findById(userId);
        if (userOpt.isPresent()) {
            return userOpt.get().getAddress();
        } else {
            return null;
        }
    }

    public void delete(Long userId) {
        userRepo.deleteById(userId);
    }
}

