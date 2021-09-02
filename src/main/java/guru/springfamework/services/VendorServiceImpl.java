package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.VendorMapper;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.VendorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VendorServiceImpl implements VendorService {
    private VendorMapper vendorMapper;
    private VendorRepository vendorRepository;

    public VendorServiceImpl(VendorMapper vendorMapper, VendorRepository vendorRepository) {
        this.vendorMapper = vendorMapper;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public List<VendorDTO> getAllVendors() {
        return vendorRepository.findAll().stream().map(vendor -> vendorMapper.vendorToVendorDTO(vendor)).collect(Collectors.toList());
    }

    @Override
    public VendorDTO getVendorById(Long id) {
        Optional<Vendor> vendorOptional = vendorRepository.findById(id);
        return vendorOptional.map(vendor -> vendorMapper.vendorToVendorDTO(vendor)).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public VendorDTO createNewVendor(VendorDTO vendorDTO) {
        Vendor vendor = vendorMapper.vendorDTOToVendor(vendorDTO);

        Vendor savedVendor = vendorRepository.save(vendor);

        return vendorMapper.vendorToVendorDTO(savedVendor);
    }

    @Override
    public VendorDTO saveVendorByDTO(Long id, VendorDTO vendorDTO) {
        Vendor vendor = vendorMapper.vendorDTOToVendor(vendorDTO);
        vendor.setId(id);

        Vendor updatedVendor = vendorRepository.save(vendor);

        return vendorMapper.vendorToVendorDTO(updatedVendor);
    }

    @Override
    public VendorDTO patchVendor(Long id, VendorDTO vendorDTO) {
        Vendor vendor;
        try {
            vendor = vendorRepository.getOne(id);
        } catch (Exception e) {
            throw new ResourceNotFoundException(e);
        }

        if(vendorDTO.getName() != null) {
            vendor.setName(vendorDTO.getName());
        }

        Vendor savedVendor = vendorRepository.save(vendor);

        return vendorMapper.vendorToVendorDTO(savedVendor);
    }

    @Override
    public void deleteVendorById(Long id) {
        vendorRepository.deleteById(id);
    }
}
