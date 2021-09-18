package ar.edu.unq.virtuaula.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import ar.edu.unq.virtuaula.dto.ClassroomDTO;
import ar.edu.unq.virtuaula.model.Classroom;
import ar.edu.unq.virtuaula.repository.GuestClassromRepository;
import ar.edu.unq.virtuaula.util.MapperUtil;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class GuestClassroomService {

    private final GuestClassromRepository guestClassromRepository;
    private final MapperUtil mapperUtil;

    public List<ClassroomDTO> getAll() {
        List<Classroom> classrooms = guestClassromRepository.findAll();
        return Arrays.asList(mapperUtil.getMapper().map(classrooms, ClassroomDTO[].class));
    }

    public Classroom findById(Long id) {
        return guestClassromRepository.findById(id).get();
    }
}
