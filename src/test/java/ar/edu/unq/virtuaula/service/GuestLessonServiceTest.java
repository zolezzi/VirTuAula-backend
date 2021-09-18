package ar.edu.unq.virtuaula.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.unq.virtuaula.VirtuaulaApplicationTests;
import ar.edu.unq.virtuaula.dto.LessonDTO;
import ar.edu.unq.virtuaula.dto.TaskDTO;
import ar.edu.unq.virtuaula.model.Classroom;
import ar.edu.unq.virtuaula.model.Lesson;
import ar.edu.unq.virtuaula.model.Task;
import ar.edu.unq.virtuaula.vo.LessonVO;

public class GuestLessonServiceTest extends VirtuaulaApplicationTests {
    
    @Autowired
    private GuestLessonService guestLessonService;
    
    @Test
    public void getAllByClassroomWithExistingClassroomReturnLesson() {
        int expected = 1;
        Classroom classroom = createClassroomWithName("Matematicas");
        createLessonWithName("Ecuaciones", classroom);
        
        List<LessonDTO> result = guestLessonService.getAllByClassroom(classroom);
        
        assertEquals(expected, result.size());
    }
    
    @Test
    public void getAllByClassroomWithExistingClassroomReturnLessonWithId() {
        Classroom classroom = createClassroomWithName("Matematicas");
        createLessonWithName("Ecuaciones", classroom);
        
        List<LessonDTO> result = guestLessonService.getAllByClassroom(classroom);
        
        assertNotNull(result.get(0).getId());
    }
    
    @Test
    public void getAllByClassroomWithExistingClassroomReturnLessonWithName() {
        String name = "Ecuaciones";
        Classroom classroom = createClassroomWithName("Matematicas");
        createLessonWithName(name, classroom);
        
        List<LessonDTO> result = guestLessonService.getAllByClassroom(classroom);
        
        assertEquals(name, result.get(0).getName());
    }
    
    @Test
    public void getAllByClassroomWithoutLessonsReturnEmptyList() {
        int expected = 0;
        Classroom classroom = createClassroomWithName("Matematicas");
        List<LessonDTO> result = guestLessonService.getAllByClassroom(classroom);
        
        assertEquals(expected, result.size());
    }
    
    @Test
    public void findByIdWithLessonReturnLesson() {
    	String name = "Ecuaciones";
        Classroom classroom = createClassroomWithName("Matematicas");
        Lesson lesson = createLessonWithName(name, classroom);
        Lesson lessonReturn = guestLessonService.findById(lesson.getId());
        assertEquals(lesson.getId(), lessonReturn.getId());
        assertEquals(name, lessonReturn.getName());
    }
    
    @Test
    public void getAllTaskByLessonWithoutLessonsReturnEmptyList() {
    	int expected = 0;
    	String name = "Ecuaciones";
        Classroom classroom = createClassroomWithName("Matematicas");
        Lesson lesson = createLessonWithName(name, classroom);
        List<TaskDTO> result = guestLessonService.getAllTaskByLesson(lesson);
        assertEquals(expected, result.size());
    }
    
    //@Test
    public void completeTask() {
    	String name = "Ecuaciones";
    	String statement = "Cuanto es 1 + 1";
        Classroom classroom = createClassroomWithName("Matematicas");
        createLessonWithName(name, classroom);
        Lesson lesson = classroom.getLessons().get(0);
        Task task = createTask(classroom, lesson, statement);
        LessonVO result = guestLessonService.completeTask(classroom, lesson, task);
    }
}
