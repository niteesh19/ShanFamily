import com.geektrust.family.service.Family;
import com.geektrust.family.service.FileProcessor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FamilyTest {

  private static Family family;
  private static FileProcessor processor = new FileProcessor();

  @BeforeAll
  public static void setup() {
    family = new Family();
    processor = new FileProcessor();
    List.of("ADD_FAMILY_HEAD Queen_Anga Female",
            "ADD_SPOUSE Queen_Anga King_Shan Male",
            "ADD_CHILD Queen_Anga Chit Male",
            "ADD_SPOUSE Chit Amba Female",
            "ADD_CHILD Queen_Anga Ish Male",
            "ADD_CHILD Queen_Anga Vich Male",
            "ADD_SPOUSE Vich Lika Female",
            "ADD_CHILD Queen_Anga Aras Male",
            "ADD_SPOUSE Aras Chitra Female",
            "ADD_CHILD Queen_Anga Satya Female",
            "ADD_SPOUSE Satya Vyan Male",
            "ADD_CHILD Amba Dritha Female",
            "ADD_CHILD Amba Tritha Female",
            "ADD_CHILD Amba Vritha Male",
            "ADD_SPOUSE Dritha Jaya Male",
            "ADD_CHILD Dritha Yodhan Male")
        .forEach(cmd -> processor.processInitCommand(family, cmd));

    System.out.println(">> Test Init Setup Done <<");
  }

  @Test
  public void test_Siblings() {
    System.out.println(">> Test Siblings <<");
    List<String> list = Stream.of(
            "GET_RELATIONSHIP Vritha Siblings",
            "GET_RELATIONSHIP Chit Siblings",
            "GET_RELATIONSHIP Yodhan Siblings")
        .map(cmd -> processor.processInputCommand(family, cmd))
        .collect(Collectors.toList());

    assertEquals(3, list.size());
    assertEquals("Dritha Tritha", list.get(0));
    assertEquals("Ish Vich Aras Satya", list.get(1));
    assertEquals("NONE", list.get(2));
  }

  @Test
  public void test_Maternal_Uncle() {
    System.out.println(">> Test Maternal Uncle <<");
    List<String> list = Stream.of(
            "GET_RELATIONSHIP Yodhan Maternal-Uncle")
        .map(cmd -> processor.processInputCommand(family, cmd))
        .collect(Collectors.toList());

    assertEquals(1, list.size());
    assertEquals("Vritha", list.get(0));
  }

  @Test
  public void test_Paternal_Aunt() {
    System.out.println(">> Test Paternal Aunt <<");
    List<String> list = Stream.of(
            "GET_RELATIONSHIP Dritha Paternal-Aunt")
        .map(cmd -> processor.processInputCommand(family, cmd))
        .collect(Collectors.toList());

    assertEquals(1, list.size());
    assertEquals("Satya", list.get(0));
  }

}
