import anna.klueva.Dog;
import anna.klueva.DogController;
import anna.klueva.dao.DogDAO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.OK;

public class DogControllerTest {

    @Test(groups = "unitTest")
    public void testGetDogByIdMethod() throws Exception {
        Dog expectedDog = Dog.builder().name("Test dog").build();

        DogDAO mockRepository = mock(DogDAO.class);
        when(mockRepository.findById(1)).thenReturn(Optional.of(expectedDog));

        DogController controller = new DogController(mockRepository);
        assertEquals(new ResponseEntity<>(expectedDog, OK), controller.getDogById(1));
    }

    @Test(groups = "unitTest")
    public void testGetAllDogsMethod() throws Exception {
        Dog firstDog = Dog.builder()
                .name("First dog")
                .dateOfBirth(new Date())
                .build();
        Dog secondDog = Dog.builder()
                .name("Second dog")
                .dateOfBirth(new Date())
                .build();
        Iterable<Dog> expectedList = Arrays.asList(firstDog, secondDog);

        DogDAO mockRepository = mock(DogDAO.class);
        when(mockRepository.findAll()).thenReturn(expectedList);

        DogController controller = new DogController(mockRepository);
        assertEquals(expectedList, controller.getAllDogs());
    }

    @Test(groups = "unitTest")
    public void testDeleteDogMethod() throws Exception {
        DogDAO mockRepository = mock(DogDAO.class);
        doNothing().when(mockRepository).deleteById(1);
        doReturn(true).when(mockRepository).existsById(1);

        DogController controller = new DogController(mockRepository);
        assertEquals(new ResponseEntity(HttpStatus.OK), controller.deleteDogById(1));
    }

    @Test(groups = "unitTest")
    public void testUpdateDogMethod() throws Exception {
        Dog expectedDog = new Dog();

        DogDAO mockRepository = mock(DogDAO.class);
        doReturn(true).when(mockRepository).existsById(1);
        when(mockRepository.findById(1)).thenReturn(Optional.of(expectedDog));
        when(mockRepository.save(expectedDog)).thenReturn(expectedDog);

        DogController controller = new DogController(mockRepository);
        assertEquals(new ResponseEntity<>(new Dog(), OK), controller.updateDogById(1, expectedDog));
    }

    @Test(groups = "unitTest")
    public void testCreateDogMethod() throws Exception {
        Dog dogToBeAdded = Dog.builder()
                .name("First dog")
                .dateOfBirth(new Date())
                .build();

        DogDAO mockRepository = mock(DogDAO.class);
        when(mockRepository.save(dogToBeAdded)).thenReturn(dogToBeAdded);

        DogController controller = new DogController(mockRepository);
        assertEquals(dogToBeAdded, controller.createNewDog(dogToBeAdded));
    }
}
