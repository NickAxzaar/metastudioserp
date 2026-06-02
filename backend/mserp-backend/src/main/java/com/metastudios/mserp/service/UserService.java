package com.metastudios.mserp.service;

import com.metastudios.mserp.dto.UserDTO;
import com.metastudios.mserp.dto.UserRequestDTO;
import com.metastudios.mserp.entity.Role;
import com.metastudios.mserp.entity.User;
import com.metastudios.mserp.exception.ResourceNotFoundException;
import com.metastudios.mserp.repository.RoleRepository;
import com.metastudios.mserp.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id: " + id));

        return convertToDTO(user);
    }

    @Transactional
    public UserDTO createUser(UserRequestDTO request) {

        Role role = roleRepository.findByName(request.getRoleName())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Role not found: " + request.getRoleName()));

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())
                .isActive(true)
                .role(role)
                .build();

        return convertToDTO(userRepository.save(user));
    }

    @Transactional
    public void deleteUser(Long id) {

        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }

        userRepository.deleteById(id);
    }

    private UserDTO convertToDTO(User user) {

        UserDTO dto = new UserDTO();

        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setIsActive(user.getIsActive());

        UserDTO.RoleDTO roleDTO = new UserDTO.RoleDTO();
        roleDTO.setName(user.getRole().getName());

        dto.setRole(roleDTO);

        return dto;
    }
}