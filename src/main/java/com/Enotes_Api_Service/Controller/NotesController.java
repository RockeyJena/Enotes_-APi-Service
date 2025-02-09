package com.Enotes_Api_Service.Controller;

import com.Enotes_Api_Service.Service.NotesService;
import com.Enotes_Api_Service.dto.NotesDto;
import com.Enotes_Api_Service.Handler.GenericResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("api/v1/notes")
public class NotesController {

    @Autowired
    private NotesService notesService;

    /**
     * Create a new note
     */
    @PostMapping("/create")
    public ResponseEntity<GenericResponse> createNotes(@RequestBody NotesDto notesDto) {
        log.info("Received request to create note: {}", notesDto);
        Boolean result = notesService.saveNotes(notesDto);
        log.info("Successfully created note: {}", notesDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(GenericResponse.success(HttpStatus.CREATED, "Notes created successfully", notesDto));
    }

    /**
     * Get all notes
     */
    @GetMapping("/")
    public ResponseEntity<GenericResponse> getAllNotes() {
        log.info("Received request to fetch all notes");
        List<NotesDto> notesList = notesService.getAllNotes();
        log.info("Successfully fetched {} notes", notesList.size());
        return ResponseEntity.ok(GenericResponse.success(HttpStatus.OK, "Notes fetched successfully", notesList));
    }

    /**
     * Get a note by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<GenericResponse> getNotesById(@PathVariable("id") Integer id) {
        log.info("Received request to fetch note by ID: {}", id);
        NotesDto notesDto = notesService.getNotesById(id);
        log.info("Successfully fetched note details: {}", notesDto);
        return ResponseEntity.ok(GenericResponse.success(HttpStatus.OK, "Notes details fetched successfully", notesDto));
    }
}
