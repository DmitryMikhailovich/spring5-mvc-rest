package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.VendorMapper;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.bootstrap.Bootstrap;
import guru.springfamework.domain.Vendor;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(SpringRunner.class)
@DataJpaTest
public class VendorServiceImplIT {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    VendorRepository vendorRepository;

    VendorService vendorService;

    @Before
    public void setUp() throws Exception {
        Bootstrap bootstrap = new Bootstrap(categoryRepository, customerRepository, vendorRepository);
        bootstrap.run();

        vendorService = new VendorServiceImpl(VendorMapper.INSTANCE, vendorRepository);
    }

    @Test
    public void patchVendorUpdateName() throws Exception {
        Vendor vendor = vendorRepository.findAll().get(0);
        String newName = vendor.getName() + "_new";

        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(newName);

        VendorDTO patchedVendorDTO = vendorService.patchVendor(vendor.getId(), vendorDTO);

        assertEquals(newName, patchedVendorDTO.getName());
    }

    @Test
    public void deleteVendor() throws Exception {
        Vendor vendor = vendorRepository.findAll().get(0);

        vendorService.deleteVendorById(vendor.getId());

        Optional<Vendor> deletedVendorOptional = vendorRepository.findById(vendor.getId());

        assertFalse(deletedVendorOptional.isPresent());
    }
}
