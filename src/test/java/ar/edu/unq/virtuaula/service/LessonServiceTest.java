package ar.edu.unq.virtuaula.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.unq.virtuaula.VirtuaulaApplicationTests;
import ar.edu.unq.virtuaula.dto.LessonDTO;
import ar.edu.unq.virtuaula.model.Classroom;
import ar.edu.unq.virtuaula.model.Lesson;
import ar.edu.unq.virtuaula.model.Task;
import ar.edu.unq.virtuaula.util.MapperUtil;
import ar.edu.unq.virtuaula.vo.LessonVO;
import ar.edu.unq.virtuaula.vo.TaskVO;
import static java.util.stream.Collectors.toList;

public class LessonServiceTest extends VirtuaulaApplicationTests {

    @Autowired
    private LessonService guestLessonService;
    
    @Autowired
    private MapperUtil mapperUtil;

    @Test
    public void getAllByClassroomWithExistingClassroomReturnLesson() {
        int expected = 1;
        Classroom classroom = createOneClassroom();
        List<LessonDTO> result = guestLessonService.getAllByClassroom(classroom);
        assertEquals(expected, result.size());
    }

    @Test
    public void getAllByClassroomWithExistingClassroomReturnLessonWithId() {
        Classroom classroom = createOneClassroom();
        List<LessonDTO> result = guestLessonService.getAllByClassroom(classroom);
        assertNotNull(result.get(0).getId());
        assertEquals(classroom.getLessons().get(0).getId(), result.get(0).getId());
    }

    @Test
    public void getAllByClassroomWithExistingClassroomReturnLessonWithName() {
        Classroom classroom = createOneClassroom();
        List<LessonDTO> result = guestLessonService.getAllByClassroom(classroom);
        assertEquals(classroom.getLessons().get(0).getName(), result.get(0).getName());
    }

    @Test
    public void findByIdWithLessonReturnLesson() {
        Classroom classroom = createOneClassroom();
        Lesson lessonReturn = guestLessonService.findById(classroom.getLessons().get(0).getId());
        assertEquals(classroom.getLessons().get(0).getId(), lessonReturn.getId());
        assertEquals(classroom.getLessons().get(0).getName(), lessonReturn.getName());
    }

    @Test
    public void completeTaskWithTaskProgressComplete() {
        int expected = 100;
        Classroom classroom = createOneClassroom();
        List<TaskVO> tasks = createTaskVO(classroom.getLessons().get(0).getTasks()); 
        LessonVO lessonVo = guestLessonService.completeTasks(classroom, classroom.getLessons().get(0), tasks);
        assertEquals(expected, lessonVo.getProgress());
    }

	private List<TaskVO> createTaskVO(List<Task> tasks) {
		return tasks.stream().map(task -> { 
			TaskVO taskVO = mapperUtil.getMapper().map(task, TaskVO.class);
			return taskVO;
		}).collect(toList());
	}

}