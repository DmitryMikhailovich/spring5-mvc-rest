package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.controllers.v1.CustomerController;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class CustomerServiceTest {

    private static final Long ID = 1L;
    private static final String FIRST_NAME = "John";
    private static final String LAST_NAME = "Doe";

    @Mock
    CustomerRepository customerRepository;

    CustomerService customerService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        customerService = new CustomerServiceImpl(CustomerMapper.INSTANCE, customerRepository);
    }

    @Test
    public void getAllCustomers() {
        Customer customer1 = new Customer();
        customer1.setId(1L);
        Customer customer2 = new Customer();
        customer2.setId(2L);
        List<Customer> customers = Arrays.asList(customer1, customer2);

        when(customerRepository.findAll()).thenReturn(customers);

        List<CustomerDTO> customerDTOS = customerService.getAllCustomers();

        assertEquals(2, customerDTOS.size());
    }

    @Test
    public void getCustomerById() {
        Customer customer = new Customer();
        customer.setId(ID);
        customer.setFirstName(FIRST_NAME);
        customer.setLastName(LAST_NAME);

        when(customerRepository.findById(anyLong())).thenReturn(java.util.Optional.of(customer));

        CustomerDTO customerDTO = customerService.getCustomerById(ID);

        assertNotNull(customerDTO);
        assertEquals(ID, customerDTO.getId());
        assertEquals(FIRST_NAME, customerDTO.getFirstName());
        assertEquals(LAST_NAME, customerDTO.getLastName());
    }

    @Test
    public void createNewCustomer() {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName(FIRST_NAME);
        customerDTO.setLastName(LAST_NAME);

        Customer savedCustomer = new Customer();
        savedCustomer.setId(ID);

        when(customerRepository.save(any())).thenReturn(savedCustomer);

        CustomerDTO newCustomerDTO = customerService.createNewCustomer(customerDTO);

        assertNotNull(newCustomerDTO);
        assertNotEquals(customerDTO, newCustomerDTO);
        assertEquals(CustomerController.BASE_URL + "/" + ID.intValue(), newCustomerDTO.getCustomerUrl());
    }

    @Test
    public void saveCustomerByDTO() {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName(FIRST_NAME);
        customerDTO.setLastName(LAST_NAME);

        Customer updatedCustomer = new Customer();
        updatedCustomer.setId(ID);

        when(customerRepository.save(any())).thenReturn(updatedCustomer);

        CustomerDTO savedCustomerDTO = customerService.createNewCustomer(customerDTO);

        assertNotNull(savedCustomerDTO);
        assertNotEquals(customerDTO, savedCustomerDTO);
        assertEquals(CustomerController.BASE_URL + "/" + ID.intValue(), savedCustomerDTO.getCustomerUrl());
    }

    @Test
    public void patchCustomer() {
        Customer customer = new Customer();
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName(FIRST_NAME);
        customerDTO.setLastName(null);

        Customer updatedCustomer = new Customer();
        updatedCustomer.setId(ID);
        updatedCustomer.setFirstName(FIRST_NAME);

        when(customerRepository.getOne(anyLong())).thenReturn(customer);
        when(customerRepository.save(any())).thenReturn(updatedCustomer);

        CustomerDTO patchedCustomerDTO = customerService.patchCustomer(ID, customerDTO);

        assertNotNull(patchedCustomerDTO);
        assertNotEquals(customerDTO, patchedCustomerDTO);
        assertEquals(FIRST_NAME, patchedCustomerDTO.getFirstName());
        assertEquals(CustomerController.BASE_URL + "/" + ID.intValue(), patchedCustomerDTO.getCustomerUrl());
    }
}