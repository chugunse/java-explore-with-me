package ru.practicum.compilation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.compilation.service.CompilationServiceAdmin;
import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.dto.NewCompilationDto;
import ru.practicum.compilation.dto.UpdateCompilationRequest;

@RestController
@RequestMapping(path = "/admin/compilations")
@Validated
@RequiredArgsConstructor
@Slf4j
public class CompilationControllerAdmin {
    private final CompilationServiceAdmin compilationServiceAdmin;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDto addCompilation(@Validated @RequestBody NewCompilationDto newCompilationDto) {
        log.info("Добавление подборки событий {}", newCompilationDto);
        return compilationServiceAdmin.addCompilation(newCompilationDto);
    }

    @DeleteMapping("/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompilation(@PathVariable Long compId) {
        log.info("Удаление подборки событий с id = {}", compId);
        compilationServiceAdmin.deleteCompilation(compId);
    }

    @PatchMapping("/{compId}")
    @ResponseStatus(HttpStatus.OK)
    public CompilationDto updateCompilation(@PathVariable Long compId,
                                            @Validated @RequestBody UpdateCompilationRequest request) {
        log.info("Обновление подборки событий с id = {}  {}", compId, request);
        return compilationServiceAdmin.updateCompilation(compId, request);
    }
}
