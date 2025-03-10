package guru.springfamework.bootstrap;

import guru.springfamework.domain.Category;
import guru.springfamework.domain.Customer;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.CategoryRepository;
import guru.springfamework.repositories.CustomerRepository;
import guru.springfamework.repositories.VendorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {

    private CategoryRepository categoryRepository;

    private CustomerRepository customerRepository;

    private VendorRepository vendorRepository;

    public Bootstrap(CategoryRepository categoryRepository, CustomerRepository customerRepository, VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Category fruits = new Category();
        fruits.setName("Fruits");

        Category dried = new Category();
        dried.setName("Dried");

        Category fresh = new Category();
        fresh.setName("Fresh");

        Category exotic = new Category();
        exotic.setName("Exotic");

        Category nuts = new Category();
        nuts.setName("Nuts");

        categoryRepository.save(fruits);
        categoryRepository.save(dried);
        categoryRepository.save(fresh);
        categoryRepository.save(exotic);
        categoryRepository.save(nuts);

        System.out.println("Categories Data Loaded: " + categoryRepository.count());

        Customer john = new Customer();
        john.setFirstName("John");
        john.setLastName("Doe");

        Customer jane = new Customer();
        jane.setFirstName("Jane");
        jane.setLastName("Doe");

        customerRepository.save(john);
        customerRepository.save(jane);

        System.out.println("Customers Data Loaded: " + customerRepository.count());

        Vendor amazon = new Vendor();
        amazon.setName("Amazon");

        Vendor magnit = new Vendor();
        magnit.setName("Magnit");

        vendorRepository.save(amazon);
        vendorRepository.save(magnit);

        System.out.println("Vendors Data Loaded: " + vendorRepository.count());
    }
}
