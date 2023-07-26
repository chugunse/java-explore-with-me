package ru.practicum.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.dto.NewCategoryDto;
import ru.practicum.category.mapper.CategoryMapper;
import ru.practicum.category.model.Category;
import ru.practicum.category.storage.CategoryRepository;
import ru.practicum.exception.model.BadRequestException;
import ru.practicum.exception.model.ConflictException;
import ru.practicum.exception.model.ResourceNotFoundException;
import ru.practicum.util.FindObjectInRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final FindObjectInRepository findObjectInRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public CategoryDto addCategory(NewCategoryDto newCategoryDto) {
        Category category = CategoryMapper.newCategoryDtoToCategory(newCategoryDto);
        return save(category);
    }

    @Override
    public void deleteCategoryById(Long catId) {
        if (!findObjectInRepository.getAllEventByCategoryId(catId).isEmpty()) {
            throw new ConflictException("Нельзя удалить категорию,тк существуют связанные с ней события");
        }
        try {
            categoryRepository.deleteById(catId);
        } catch (EmptyResultDataAccessException exception) {
            throw new ResourceNotFoundException("Категория c id = " + catId + " не найдена");
        }
    }

    @Override
    public CategoryDto updateCategory(Long catId, CategoryDto newCategoryDto) {
        Category category = CategoryMapper.categoryDtoToCategory(newCategoryDto);
        category.setId(catId);
        return save(category);
    }

    @Override
    public List<CategoryDto> getCategories(Integer from, Integer size) {
        return categoryRepository.findAll(PageRequest.of(from, size))
                .stream()
                .map(CategoryMapper::categoryToCategoryDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto getCategoryById(Long catId) {
        return CategoryMapper.categoryToCategoryDto(categoryRepository.findById(catId)
                .orElseThrow(() -> new ResourceNotFoundException("Категория c id = " + catId + " не найдена")));
    }

    private CategoryDto save(Category category) {
        try {
            return CategoryMapper.categoryToCategoryDto(categoryRepository.save(category));
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException("Имя категории должно быть уникальным, "
                    + category.getName() + " уже используется");
        } catch (Exception e) {
            throw new BadRequestException("Запрос на добавлении категории " + category.getName() + " составлен не корректно ");
        }
    }
}
