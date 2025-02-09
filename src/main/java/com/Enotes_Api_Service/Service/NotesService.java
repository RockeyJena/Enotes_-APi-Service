package com.Enotes_Api_Service.Service;

import com.Enotes_Api_Service.dto.NotesDto;

import java.util.List;

public interface NotesService {
    public Boolean saveNotes(NotesDto notesDto);
    public List<NotesDto> getAllNotes();

    NotesDto getNotesById(Integer id);

}
