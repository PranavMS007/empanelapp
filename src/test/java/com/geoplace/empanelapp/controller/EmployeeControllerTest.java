package com.geoplace.empanelapp.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.geoplace.empanelapp.dto.EmployeeDTO;
import com.geoplace.empanelapp.exception.ResourceNotFoundException;
import com.geoplace.empanelapp.service.EmployeeService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {EmployeeController.class})
@ExtendWith(SpringExtension.class)
class EmployeeControllerTest {
    @Autowired
    private EmployeeController employeeController;

    @MockBean
    private EmployeeService employeeService;

    @Test
    void testCreateEmployee() throws Exception {
        List<String> skills = new ArrayList<>();
        skills.add("c#");
        skills.add("Java");
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setDepartmentId(1L);
        employeeDTO.setId(1L);
        employeeDTO.setName("Joe");
        employeeDTO.setSkills(skills);
        when(employeeService.createEmployee(Mockito.<EmployeeDTO>any())).thenReturn(employeeDTO);

        EmployeeDTO employeeDTO2 = new EmployeeDTO();
        employeeDTO2.setDepartmentId(1L);
        employeeDTO2.setId(1L);
        employeeDTO2.setName("Joe");
        employeeDTO2.setSkills(skills);
        String content = (new ObjectMapper()).writeValueAsString(employeeDTO2);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/employees/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(employeeController)
                .build()
                .perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(
                        MockMvcResultMatchers.content().string("{\"id\":1,\"name\":\"Joe\",\"skills\":[\"c#\",\"Java\"],\"departmentId\":1}"));
    }

    @Test
    void testCreateEmployeeNotFound() throws Exception {
        List<String> skills = new ArrayList<>();
        skills.add("c#");
        skills.add("Java");
        when(employeeService.createEmployee(Mockito.<EmployeeDTO>any()))
                .thenThrow(new ResourceNotFoundException("An error occurred"));

        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setDepartmentId(1L);
        employeeDTO.setId(1L);
        employeeDTO.setName("Joe");
        employeeDTO.setSkills(skills);
        String content = (new ObjectMapper()).writeValueAsString(employeeDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/employees/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(employeeController)
                .build()
                .perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testGetAllEmployees() throws Exception {

        when(employeeService.getAllEmployees()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/employees");

        // Assert
        MockMvcBuilders.standaloneSetup(employeeController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testGetAllEmployeesNotFound() throws Exception {
        when(employeeService.getAllEmployees()).thenThrow(new ResourceNotFoundException("An error occurred"));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/employees");

        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(employeeController)
                .build()
                .perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testGetEmployeeById() throws Exception {
        List<String> skills = new ArrayList<>();
        skills.add("c#");
        skills.add("Java");

        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setDepartmentId(1L);
        employeeDTO.setId(1L);
        employeeDTO.setName("Joe");
        employeeDTO.setSkills(skills);
        Optional<EmployeeDTO> ofResult = Optional.of(employeeDTO);
        when(employeeService.getEmployeeById(Mockito.<Long>any())).thenReturn(ofResult);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/employees/{id}", 1L);

        // Assert
        MockMvcBuilders.standaloneSetup(employeeController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(
                        MockMvcResultMatchers.content().string("{\"id\":1,\"name\":\"Joe\",\"skills\":[\"c#\",\"Java\"],\"departmentId\":1}"));
    }


    @Test
    void testGetEmployeeByIdNotFound() throws Exception {
        Optional<EmployeeDTO> emptyResult = Optional.empty();
        when(employeeService.getEmployeeById(Mockito.<Long>any())).thenReturn(emptyResult);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/employees/{id}", 1L);

        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(employeeController)
                .build()
                .perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testGetEmployeeByIdNotFoundCase() throws Exception {
        when(employeeService.getEmployeeById(Mockito.<Long>any()))
                .thenThrow(new ResourceNotFoundException("An error occurred"));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/employees/{id}", 1L);

        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(employeeController)
                .build()
                .perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testGetEmployeesByDepartment() throws Exception {
        when(employeeService.getEmployeesByDepartment(Mockito.<Long>any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/employees/department/{departmentId}", 1L);

        // Assert
        MockMvcBuilders.standaloneSetup(employeeController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testGetEmployeesByDepartmentNotFound() throws Exception {
        when(employeeService.getEmployeesByDepartment(Mockito.<Long>any()))
                .thenThrow(new ResourceNotFoundException("An error occurred"));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/employees/department/{departmentId}", 1L);

        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(employeeController)
                .build()
                .perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testUpdateEmployee() throws Exception {
        List<String> skills = new ArrayList<>();
        skills.add("c#");
        skills.add("Java");

        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setDepartmentId(1L);
        employeeDTO.setId(1L);
        employeeDTO.setName("Joe");
        employeeDTO.setSkills(skills);
        when(employeeService.updateEmployee(Mockito.<Long>any(), Mockito.<EmployeeDTO>any())).thenReturn(employeeDTO);

        EmployeeDTO employeeDTO2 = new EmployeeDTO();
        employeeDTO2.setDepartmentId(1L);
        employeeDTO2.setId(1L);
        employeeDTO2.setName("Joe");
        employeeDTO2.setSkills(skills);
        String content = (new ObjectMapper()).writeValueAsString(employeeDTO2);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/employees/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Assert
        MockMvcBuilders.standaloneSetup(employeeController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(
                        MockMvcResultMatchers.content().string("{\"id\":1,\"name\":\"Joe\",\"skills\":[\"c#\",\"Java\"],\"departmentId\":1}"));
    }

    @Test
    void testUpdateEmployeeNotFound() throws Exception {
        List<String> skills = new ArrayList<>();
        skills.add("c#");
        skills.add("Java");

        when(employeeService.updateEmployee(Mockito.<Long>any(), Mockito.<EmployeeDTO>any()))
                .thenThrow(new ResourceNotFoundException("An error occurred"));

        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setDepartmentId(1L);
        employeeDTO.setId(1L);
        employeeDTO.setName("Joe");
        employeeDTO.setSkills(skills);
        String content = (new ObjectMapper()).writeValueAsString(employeeDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/employees/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(employeeController)
                .build()
                .perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testDeleteEmployee() throws Exception {
        doNothing().when(employeeService).deleteEmployee(Mockito.<Long>any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/employees/{id}", 1L);

        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(employeeController)
                .build()
                .perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void testDeleteEmployeeNotFound() throws Exception {
        doThrow(new ResourceNotFoundException("An error occurred")).when(employeeService)
                .deleteEmployee(Mockito.<Long>any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/employees/{id}", 1L);

        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(employeeController)
                .build()
                .perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testDeleteEmployeeError() throws Exception {
        doNothing().when(employeeService).deleteEmployee(Mockito.<Long>any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/employees/{id}", 1L);
        requestBuilder.contentType("https://example.org/example");

        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(employeeController)
                .build()
                .perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
