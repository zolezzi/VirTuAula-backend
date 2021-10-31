package ar.edu.unq.virtuaula.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;

import ar.edu.unq.virtuaula.VirtuaulaApplicationTests;
import ar.edu.unq.virtuaula.dto.AccountDTO;
import ar.edu.unq.virtuaula.exception.AccountNotFoundException;
import ar.edu.unq.virtuaula.exception.StudentAccountNotFoundException;
import ar.edu.unq.virtuaula.exception.TeacherNotFoundException;
import ar.edu.unq.virtuaula.message.ResponseMessage;
import ar.edu.unq.virtuaula.model.Account;
import ar.edu.unq.virtuaula.model.StudentAccount;
import ar.edu.unq.virtuaula.model.TeacherAccount;
import ar.edu.unq.virtuaula.model.User;
import ar.edu.unq.virtuaula.vo.AccountVO;

public class AccountServiceTest extends VirtuaulaApplicationTests {

    @Autowired
    private AccountService accountService;
    
    @Test
    public void findTeacherAccountReturnAccountWithId() throws TeacherNotFoundException {
        Account account = createOneTeacherAccount();
        Account result = (Account) accountService.findTeacherById(1l);
        assertNotNull(result);
        assertEquals(result.getId(), account.getId());
    }

    @Test
    public void whenfindTeacherAccountWithUsernameNotExistsThenThrowExpetion() {
        Exception exception = assertThrows(TeacherNotFoundException.class, () -> {
            accountService.findTeacherById(10l);
        });

        String expectedMessage = "Error not found account with id: 10";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void findAccountReturnAccountWithId() throws AccountNotFoundException {
        Account account = createOneTeacherAccount();
        Account result = (Account) accountService.findById(1l);
        assertNotNull(result);
        assertEquals(result.getId(), account.getId());
    }

    @Test
    public void whenfindAccountWithUsernameNotExistsThenThrowExpetion() {
        Exception exception = assertThrows(AccountNotFoundException.class, () -> {
            accountService.findById(10l);
        });

        String expectedMessage = "Error not found account with id: 10";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void createTeacherAccountReturnAccountWithId() throws TeacherNotFoundException {
        User user = createOneUser();
        AccountDTO account = Mockito.mock(AccountDTO.class);
        Mockito.when(account.getFirstName()).thenReturn("Charlie");
        Mockito.when(account.getLastName()).thenReturn("Zolezzi");
        Mockito.when(account.getEmail()).thenReturn("charlie@virtuaula.com");
        Mockito.when(account.getDni()).thenReturn(36001002);

        AccountVO result = accountService.createAccountTeacher(user, account);
        assertNotNull(result);
    }

    @Test
    public void findStudentAccountReturnAccountWithId() throws StudentAccountNotFoundException {
        Account account = createOneStudentAccount();
        Account result = (Account) accountService.findStudentById(1l);
        assertNotNull(result);
        assertEquals(result.getId(), account.getId());
    }

    @Test
    public void whenFindStudentAccountWithUsernameNotExistsThenThrowExpetion() {
        Exception exception = assertThrows(StudentAccountNotFoundException.class, () -> {
            accountService.findStudentById(10l);
        });

        String expectedMessage = "Error not found account with id: 10";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void getExperienceWithStudentAccountReturnExperience() throws StudentAccountNotFoundException {
        StudentAccount account = (StudentAccount) createOneStudentAccount();
        Double experience = accountService.getExperience(account.getId());
        assertNotNull(experience);
        assertEquals(experience, account.getExperience());
    }
    
    @Test
    public void testWhenLoadCSVFileWithStudentsValidThenReturnMessageSuccess() throws IOException {
    	String expected = "Uploaded the file successfully: hello.csv";
        StringBuilder csvBuilder = new StringBuilder();
        csvBuilder.append("Nombre,Apellido,DNI,Email\n");
        csvBuilder.append("Carlos,Cardozo,36000001,carlos@gmail.com");
        InputStream is = new ByteArrayInputStream(csvBuilder.toString().getBytes());
        MockMultipartFile file = new MockMultipartFile("file", "hello.csv", "text/csv", is);
        TeacherAccount account = (TeacherAccount) createOneTeacherAccount();
        ResponseMessage message = accountService.uploadFileStudents(account, file);
        assertNotNull(message);
        assertEquals(expected, message.getMessage());
    }
    
    @Test
    public void testWhenLoadCSVFileWithFormatNoValidThenReturnMessageEmpty() throws IOException {
    	String expected = "";
        StringBuilder csvBuilder = new StringBuilder();
        csvBuilder.append("Nombre,Apellido,DNI,Email\n");
        csvBuilder.append("Carlos,Cardozo,36000001,carlos@gmail.com");
        InputStream is = new ByteArrayInputStream(csvBuilder.toString().getBytes());
        MockMultipartFile file = new MockMultipartFile("file", "hello.html", "text/html", is);
        TeacherAccount account = (TeacherAccount) createOneTeacherAccount();
        ResponseMessage message = accountService.uploadFileStudents(account, file);
        assertNotNull(message);
        assertEquals(expected, message.getMessage());
    }
    
    @Test
    public void testWhenLoadCSVFileWithoutStudentsThenReturnMessageEmpty() throws IOException {
    	String expected = "I do not know loaded any lines from the file: hello.csv";
        StringBuilder csvBuilder = new StringBuilder();
        csvBuilder.append("Nombre,Apellido,DNI,Email\n");
        InputStream is = new ByteArrayInputStream(csvBuilder.toString().getBytes());
        MockMultipartFile file = new MockMultipartFile("file", "hello.csv", "text/csv", is);
        TeacherAccount account = (TeacherAccount) createOneTeacherAccount();
        ResponseMessage message = accountService.uploadFileStudents(account, file);
        assertNotNull(message);
        assertEquals(expected, message.getMessage());
    }
    
}
