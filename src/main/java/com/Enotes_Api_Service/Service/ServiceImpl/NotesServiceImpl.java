package com.Enotes_Api_Service.Service.ServiceImpl;

import com.Enotes_Api_Service.Entity.Category;
import com.Enotes_Api_Service.Entity.Notes;
import com.Enotes_Api_Service.Exception.ResourceNotFoundException;
import com.Enotes_Api_Service.Repository.CategoryRepository;
import com.Enotes_Api_Service.Repository.NotesRepository;
import com.Enotes_Api_Service.Service.NotesService;
import com.Enotes_Api_Service.dto.NotesDto;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class NotesServiceImpl implements NotesService {

    @Autowired
    private NotesRepository notesRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Boolean saveNotes(NotesDto notesDto) {
        log.info("Attempting to create a new note: {}", notesDto);

        if (notesDto.getCategory() == null || notesDto.getCategory().getId() == null) {
            log.warn("Category ID is missing in request");
            throw new ResourceNotFoundException("Category ID is required.");
        }

        // Validate if category exists before proceeding
        Category category = categoryRepository.findById(notesDto.getCategory().getId())
                .orElseThrow(() -> {
                    log.warn("Category not found with ID: {}", notesDto.getCategory().getId());
                    return new ResourceNotFoundException("Category not found with ID: " + notesDto.getCategory().getId());
                });

        // Convert DTO to Entity
        Notes notes = mapper.map(notesDto, Notes.class);
        notes.setCategory(category);

        // Save the note
        notesRepository.save(notes);
        log.info("Note saved successfully with ID: {}", notes.getId());
        return true;
    }

    @Override
    public List<NotesDto> getAllNotes() {
        log.info("Fetching all notes from database");
        List<Notes> notesList = notesRepository.findAll();
        log.info("Total notes found: {}", notesList.size());
        return notesList.stream()
                .map(note -> mapper.map(note, NotesDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public NotesDto getNotesById(Integer id) {
        log.info("Fetching note by ID: {}", id);
        return notesRepository.findById(id)
                .map(note -> {
                    log.info("Note found: {}", note);
                    return mapper.map(note, NotesDto.class);
                })
                .orElseThrow(() -> {
                    log.warn("Note not found with ID: {}", id);
                    return new ResourceNotFoundException("Notes ID " + id + " not found");
                });
    }
}
