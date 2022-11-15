package no.it1901.groups2022.gr2227.timemaster.persistence;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class FileHandlerTest {

  FileHandler fileHandler;
  ObjectMapper objectMapper;
  ArrayList<String> testList;
  String testName1;
  String testName2;
  File file;

  @BeforeEach
  public void setup() {
    fileHandler = new FileHandler("testSaveFile.json");
    objectMapper = new ObjectMapper();
    file = new File(Paths.get(System.getProperty("user.dir"), "../timeMasterSaveFiles").toString(), "testSaveFile.json");
    testName1 = "Test1";
    testName2 = "Test2";
    testList = new ArrayList<>();
    testList.add(testName1);
    testList.add(testName2);
  }

  @AfterEach
  public void cleanUp() {
    file.delete();
  }

  @Test
  public void testWrite() {
    assertDoesNotThrow(() -> fileHandler.write(this.objectMapper.writeValueAsString(testList)));
  }

  @Test
  public void testReadFile() {
    assertDoesNotThrow(() -> {
      file.delete();
      assertNull(fileHandler.readFile());
      file.createNewFile();
    });
    assertDoesNotThrow(() -> fileHandler.write(this.objectMapper.writeValueAsString(testList)));
    assertDoesNotThrow(() -> fileHandler.readFile());
    JsonNode node = fileHandler.readFile();
    assertDoesNotThrow(() -> this.objectMapper.readValue(node.asText(), new TypeReference<ArrayList<String>>() {} ));
    
    assertDoesNotThrow(() -> {
      final ArrayList<String> list = this.objectMapper.readValue(node.asText(), new TypeReference<ArrayList<String>>() {} );
      assertTrue(list.size() == 2);
      assertTrue(list.get(0).equals(testName1));
      assertTrue(list.get(1).equals(testName2));
    });
  }

  @Test
  public void testParseString() {
    assertNull(fileHandler.parseString(null));
    assertDoesNotThrow(() -> {
    assertEquals(
      this.objectMapper.readTree(this.objectMapper.writeValueAsString(testList)), 
      fileHandler.parseString(this.objectMapper.writeValueAsString(testList)));
    });
  }
  
}
