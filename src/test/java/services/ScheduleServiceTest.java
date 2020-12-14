package services;

import domain.Group;
import domain.Lesson;
import domain.User;
import domain.UserType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import storage.LessonRepository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;

public class ScheduleServiceTest {

    private ScheduleService service;
    private LessonRepository lessonRepository;
    private User teacher;

    @Before
    public void setUp() throws Exception {
        lessonRepository = mock(LessonRepository.class);
        service = new ScheduleService(lessonRepository);
        teacher = new User("demon", "1234", "Alex", UserType.TEACHER, 0);
    }

    @Test
    public void getTeacherSchedule() {
        List<Lesson> result = List.of(new Lesson(0, LocalDateTime.now(), "Good lesson", "English", "do nothing", 0, teacher.getLogin(), new HashMap<>(Map.of("max", true))));
        when(lessonRepository.findLessonsByTeacherIdFromInterval(any(), any(), any())).thenReturn(result);
        List<Lesson> teacherSchedule = service.getTeacherSchedule(teacher.getLogin());
        Assert.assertEquals(result, teacherSchedule);
    }

    @Test
    public void getGroupSchedule() {
        Group group = new Group(1, "IP-94", "max", List.of("Vania", "Oleg", "Vladimir"));
        List<Lesson> result = List.of(new Lesson(0, LocalDateTime.now(), "Good lesson", "English", "do nothing", 0, teacher.getLogin(), new HashMap<>(Map.of("max", true))));
        when(lessonRepository.findLessonsByGroupIdFromInterval(any(), any(), any())).thenReturn(result);
        List<Lesson> groupSchedule = service.getGroupSchedule(group.getId().toString());
        Assert.assertEquals(result, groupSchedule);
    }
}
