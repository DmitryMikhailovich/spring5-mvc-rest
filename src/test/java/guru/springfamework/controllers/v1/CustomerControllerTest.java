package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.services.CustomerService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static guru.springfamework.controllers.v1.AbstractRestControllerTest.asJsonString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CustomerControllerTest {

    private static final Long ID = 1L;
    private static final String FIRST_NAME = "Johny";
    private static final String LAST_NAME = "Bravo";
    @Mock
    CustomerService customerService;

    @InjectMocks
    CustomerController customerController;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    public void getAllCustomers() throws Exception {
        CustomerDTO customer1 = new CustomerDTO();
        CustomerDTO customer2 = new CustomerDTO();

        List<CustomerDTO> customerDTOS = Arrays.asList(customer1, customer2);

        when(customerService.getAllCustomers()).thenReturn(customerDTOS);

        mockMvc.perform(get("/api/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers", hasSize(2)));
    }

    @Test
    public void getCustomerById() throws Exception {

        CustomerDTO customer = new CustomerDTO();
        customer.setId(ID);
        customer.setFirstName(FIRST_NAME);
        customer.setLastName(LAST_NAME);
        customer.setCustomerUrl("url");

        when(customerService.getCustomerById(anyLong())).thenReturn(customer);

        mockMvc.perform(get("/api/v1/customers/" + ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(ID.intValue())))
                .andExpect(jsonPath("$.firstname", is(FIRST_NAME)))
                .andExpect(jsonPath("$.lastname", is(LAST_NAME)))
                .andExpect(jsonPath("$.customer_url", is("url")));
    }

    @Test
    public void createNewCustomer() throws Exception {

        CustomerDTO customer = new CustomerDTO();
        customer.setFirstName(FIRST_NAME);
        customer.setLastName(LAST_NAME);

        CustomerDTO newCustomer = new CustomerDTO();
        newCustomer.setId(ID);
        newCustomer.setCustomerUrl("url");

        when(customerService.createNewCustomer(any())).thenReturn(newCustomer);

        mockMvc.perform(post("/api/v1/customers/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(customer)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(ID.intValue())))
                .andExpect(jsonPath("$.customer_url", is("url")));
    }

    @Test
    public void updateCustomer() throws Exception {

        CustomerDTO customer = new CustomerDTO();
        customer.setFirstName(FIRST_NAME);
        customer.setLastName(LAST_NAME);

        CustomerDTO newCustomer = new CustomerDTO();
        newCustomer.setId(ID);
        newCustomer.setCustomerUrl("url");

        when(customerService.saveCustomerByDTO(anyLong(), any())).thenReturn(newCustomer);

        mockMvc.perform(put("/api/v1/customers/" + ID.intValue())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(ID.intValue())))
                .andExpect(jsonPath("$.customer_url", is("url")));
    }

    @Test
    public void patchCustomer() throws Exception {

        CustomerDTO customer = new CustomerDTO();
        customer.setFirstName(FIRST_NAME);
        customer.setLastName(LAST_NAME);

        CustomerDTO newCustomer = new CustomerDTO();
        newCustomer.setId(ID);
        newCustomer.setFirstName(FIRST_NAME);
        newCustomer.setCustomerUrl("url");

        when(customerService.patchCustomer(anyLong(), any())).thenReturn(newCustomer);

        mockMvc.perform(patch("/api/v1/customers/" + ID.intValue())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(ID.intValue())))
                .andExpect(jsonPath("$.firstname", is(FIRST_NAME)))
                .andExpect(jsonPath("$.customer_url", is("url")));
    }

    @Test
    public void deleteCustomer() throws Exception {
        mockMvc.perform(delete("/api/v1/customers/" + ID.intValue())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}