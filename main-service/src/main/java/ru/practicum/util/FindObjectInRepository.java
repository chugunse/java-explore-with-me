package ru.practicum.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.category.model.Category;
import ru.practicum.category.storage.CategoryRepository;
import ru.practicum.compilation.model.Compilation;
import ru.practicum.compilation.storage.CompilationRepository;
import ru.practicum.event.model.Event;
import ru.practicum.event.storage.EventRepository;
import ru.practicum.exception.model.ResourceNotFoundException;
import ru.practicum.users.model.User;
import ru.practicum.users.storage.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindObjectInRepository {
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;

    private final CompilationRepository compilationRepository;

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("Пользователь c id = " + id + " не найден"));
    }

    public void checkUserById(Long id) {
        if (!compilationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Пользователь c id = " + id + " не найден");
        }
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("Категория c id = " + id + " не найдена"));
    }

    public Event getEventById(Long eventId) {
        return eventRepository.findById(eventId).orElseThrow(()
                -> new ResourceNotFoundException(("Event с id = " + eventId + " не найден")));
    }

    public void checkCompilation(Long compId) {
        if (!compilationRepository.existsById(compId)) {
            throw new ResourceNotFoundException("Compilation с id = " + " не найден");
        }
    }

    public Compilation getCompilationById(Long compid) {
        return compilationRepository.findById(compid).orElseThrow(()
                -> new ResourceNotFoundException("Compilation с id = " + " не найден"));
    }

    public List<Event> getAllEventByCategoryId(Long catId) {
        return eventRepository.findAllByCategoryId(catId);
    }
}
