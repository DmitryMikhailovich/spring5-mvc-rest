package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.api.v1.model.CustomerDTOList;
import guru.springfamework.services.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(CustomerController.BASE_URL)
public class CustomerController {

    public static final String BASE_URL = "/api/v1/customers";


    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public CustomerDTOList getAllCustomers() {
        return new CustomerDTOList(customerService.getAllCustomers());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDTO createNewCustomer(@RequestBody CustomerDTO customerDTO) {
        return customerService.createNewCustomer(customerDTO);
    }

    @PutMapping("{id}")
    public  CustomerDTO updateCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO) {
        return customerService.saveCustomerByDTO(id, customerDTO);
    }

    @PatchMapping("{id}")
    public  CustomerDTO patchCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO) {
        return customerService.patchCustomer(id, customerDTO);
    }

    @DeleteMapping("{id}")
    public void deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomerById(id);
    }

    @GetMapping("{id}")
    public CustomerDTO getCustomerById(@PathVariable Long id) {
        return customerService.getCustomerById(id);
    }

}
