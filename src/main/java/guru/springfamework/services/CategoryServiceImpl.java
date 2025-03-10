package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.CategoryMapper;
import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryMapper categoryMapper;
    private CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryMapper categoryMapper, CategoryRepository categoryRepository) {
        this.categoryMapper = categoryMapper;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll()
                .stream().map(category -> categoryMapper.categoryToCategoryDTO(category)).collect(Collectors.toList());
    }

    @Override
    public CategoryDTO getCategoryByName(String name) {
        try {
            return categoryMapper.categoryToCategoryDTO(categoryRepository.findByName(name));
        } catch (Exception e) {
            throw new ResourceNotFoundException(e);
        }
    }
}
