package com.metastudios.mserp.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.metastudios.mserp.dto.EmployeeDTO;
import com.metastudios.mserp.dto.EmployeeRequestDTO;
import com.metastudios.mserp.entity.Employee;
import com.metastudios.mserp.entity.User;
import com.metastudios.mserp.exception.ResourceNotFoundException;
import com.metastudios.mserp.repository.EmployeeRepository;
import com.metastudios.mserp.repository.UserRepository;

@Service
@Transactional
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;

    public EmployeeService(EmployeeRepository employeeRepository, UserRepository userRepository) {
        this.employeeRepository = employeeRepository;
        this.userRepository = userRepository;
    }

    public List<EmployeeDTO> getAllEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public EmployeeDTO getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
        return convertToDTO(employee);
    }

    public EmployeeDTO createEmployee(EmployeeRequestDTO request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + request.getUserId()));

        Employee employee = Employee.builder()
                .user(user)
                .employeeCode(request.getEmployeeCode())
                .department(request.getDepartment())
                .designation(request.getDesignation())
                .joiningDate(request.getJoiningDate())
                .salary(request.getSalary())
                .phoneNumber(request.getPhoneNumber())
                .build();

        Employee savedEmployee = employeeRepository.save(employee);
        return convertToDTO(savedEmployee);
    }

    public void deleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
        employeeRepository.delete(employee);
    }

    private EmployeeDTO convertToDTO(Employee employee) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(employee.getId());
        dto.setEmployeeCode(employee.getEmployeeCode());
        dto.setDepartment(employee.getDepartment());
        dto.setDesignation(employee.getDesignation());
        dto.setJoiningDate(employee.getJoiningDate());
        dto.setSalary(employee.getSalary());
        dto.setPhoneNumber(employee.getPhoneNumber());
        dto.setUserId(employee.getUser().getId());
        dto.setUserName(employee.getUser().getName());
        dto.setUserEmail(employee.getUser().getEmail());
        return dto;
    }
}
