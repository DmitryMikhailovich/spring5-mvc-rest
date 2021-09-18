package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.services.VendorService;
import guru.springfamework.services.ResourceNotFoundException;
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

public class VendorControllerTest {

    private static final Long ID = 1L;
    private static final String NAME = "Acme";

    @Mock
    VendorService vendorService;

    @InjectMocks
    VendorController vendorController;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(vendorController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler()).build();
    }

    @Test
    public void getAllVendors() throws Exception {
        VendorDTO vendor1 = new VendorDTO();
        VendorDTO vendor2 = new VendorDTO();

        List<VendorDTO> vendorDTOS = Arrays.asList(vendor1, vendor2);

        when(vendorService.getAllVendors()).thenReturn(vendorDTOS);

        mockMvc.perform(get(VendorController.BASE_URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vendors", hasSize(2)));
    }

    @Test
    public void getVendorById() throws Exception {

        VendorDTO vendor = new VendorDTO();
        vendor.setId(ID);
        vendor.setName(NAME);
        vendor.setVendorUrl("url");

        when(vendorService.getVendorById(anyLong())).thenReturn(vendor);

        mockMvc.perform(get(VendorController.BASE_URL + "/" + ID.intValue())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(ID.intValue())))
                .andExpect(jsonPath("$.name", is(NAME)))
                .andExpect(jsonPath("$.vendor_url", is("url")));
    }

    @Test
    public void getVendorByIdNotFound() throws Exception {

        when(vendorService.getVendorById(anyLong())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(VendorController.BASE_URL + "/" + ID.intValue())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void createNewVendor() throws Exception {

        VendorDTO vendor = new VendorDTO();
        vendor.setName(NAME);

        VendorDTO newVendor = new VendorDTO();
        newVendor.setId(ID);
        newVendor.setVendorUrl("url");

        when(vendorService.createNewVendor(any())).thenReturn(newVendor);

        mockMvc.perform(post(VendorController.BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(asJsonString(vendor)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(ID.intValue())))
                .andExpect(jsonPath("$.vendor_url", is("url")));
    }

    @Test
    public void updateVendor() throws Exception {

        VendorDTO vendor = new VendorDTO();
        vendor.setName(NAME);

        VendorDTO newVendor = new VendorDTO();
        newVendor.setId(ID);
        newVendor.setVendorUrl("url");

        when(vendorService.saveVendorByDTO(anyLong(), any())).thenReturn(newVendor);

        mockMvc.perform(put(VendorController.BASE_URL + "/" + ID.intValue())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(vendor)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(ID.intValue())))
                .andExpect(jsonPath("$.vendor_url", is("url")));
    }

    @Test
    public void patchVendor() throws Exception {

        VendorDTO vendor = new VendorDTO();
        vendor.setName(NAME);

        VendorDTO newVendor = new VendorDTO();
        newVendor.setId(ID);
        newVendor.setName(NAME);
        newVendor.setVendorUrl("url");

        when(vendorService.patchVendor(anyLong(), any())).thenReturn(newVendor);

        mockMvc.perform(patch(VendorController.BASE_URL + "/" + ID.intValue())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(vendor)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(ID.intValue())))
                .andExpect(jsonPath("$.name", is(NAME)))
                .andExpect(jsonPath("$.vendor_url", is("url")));
    }

    @Test
    public void deleteVendor() throws Exception {
        mockMvc.perform(delete(VendorController.BASE_URL + "/" + ID.intValue())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}