package com.dailycodework.dream_shops.service.user;

import com.dailycodework.dream_shops.dto.UserDto;
import com.dailycodework.dream_shops.exceptions.AlreadyExistsException;
import com.dailycodework.dream_shops.exceptions.ResourceNotFoundException;
import com.dailycodework.dream_shops.model.Users;
import com.dailycodework.dream_shops.repository.UsersRepository;
import com.dailycodework.dream_shops.request.user.CreateUserRequest;
import com.dailycodework.dream_shops.request.user.UserUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UsersRepository usersRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Users getUserById(Long userId) {
        return usersRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User Not Found!"));
    }

    @Override
    public Users createUser(CreateUserRequest request) {
        return Optional
                .of(request)
                .filter(user -> !usersRepository.existsByEmail(request.getEmail()))
                .map(req -> {
                    Users user = new Users();
                    user.setEmail(request.getEmail());
                    user.setPassword(passwordEncoder.encode(request.getPassword()));
                    user.setFirstName(request.getFirstName());
                    user.setLastName(request.getLastName());
                    user.setRoles(request.getRoles());
                    return usersRepository.save(user);
                }).orElseThrow(() -> new AlreadyExistsException("Oops! " + request.getEmail() + "Already Exists!"));
    }

    @Override
    public Users updateUser(UserUpdateRequest request, Long userId) {
        return usersRepository.findById(userId).map(existingUser -> {
            existingUser.setFirstName(request.getFirstName());
            existingUser.setLastName(request.getLastName());
            return usersRepository.save(existingUser);
        }).orElseThrow(() -> new ResourceNotFoundException("User Not Found!"));
    }

    @Override
    public void deleteUser(Long userId) {
        usersRepository.findById(userId).ifPresentOrElse(usersRepository::delete, () -> {
            throw new ResourceNotFoundException("User Not Found!");
        });
    }

    @Override
    public UserDto convertUserToDto(Users user) {
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public Users getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return usersRepository.findUsersByEmail(email);
    }
}
