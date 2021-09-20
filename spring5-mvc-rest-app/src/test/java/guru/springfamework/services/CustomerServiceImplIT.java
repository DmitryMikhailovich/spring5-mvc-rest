package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.bootstrap.Bootstrap;
import guru.springfamework.domain.Customer;
import guru.springfamework.model.CustomerDTO;
import guru.springfamework.repositories.CategoryRepository;
import guru.springfamework.repositories.CustomerRepository;
import guru.springfamework.repositories.VendorRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomerServiceImplIT {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    VendorRepository vendorRepository;

    CustomerService customerService;

    @Before
    public void setUp() throws Exception {
        Bootstrap bootstrap = new Bootstrap(categoryRepository, customerRepository, vendorRepository);
        bootstrap.run();

        customerService = new CustomerServiceImpl(CustomerMapper.INSTANCE, customerRepository);
    }

    @Test
    public void patchCustomerUpdateFirstName() throws Exception {
        Customer customer = customerRepository.findAll().get(0);
        String newFirstName = customer.getFirstName() + "_new";

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName(newFirstName);

        CustomerDTO patchedCustomerDTO = customerService.patchCustomer(customer.getId(), customerDTO);

        assertEquals(newFirstName, patchedCustomerDTO.getFirstName());
        assertEquals(customer.getLastName(), patchedCustomerDTO.getLastName());
    }

    @Test
    public void patchCustomerUpdateLastName() throws Exception {
        Customer customer = customerRepository.findAll().get(0);
        String newLastName = customer.getLastName() + "_new";

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setLastName(newLastName);

        CustomerDTO patchedCustomerDTO = customerService.patchCustomer(customer.getId(), customerDTO);

        assertEquals(customer.getFirstName(), patchedCustomerDTO.getFirstName());
        assertEquals(newLastName, patchedCustomerDTO.getLastName());
    }

    @Test
    public void deleteCustomer() throws Exception {
        Customer customer = customerRepository.findAll().get(0);

        customerService.deleteCustomerById(customer.getId());

        Optional<Customer> deletedCustomerOptional = customerRepository.findById(customer.getId());

        assertFalse(deletedCustomerOptional.isPresent());
    }
}
