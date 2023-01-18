package com.geektrust.family.service;

import com.geektrust.family.util.Constants;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileProcessor {

  /**
   * Process file.
   *
   * @param family      - service level family instance
   * @param file        - file to be processed
   * @param isInputFile - flag to check if file being processed is input or init file.
   */
  public void processInputFile(Family family, File file, boolean isInputFile) {

    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
      String line;
      while ((line = br.readLine()) != null) {
        if (isInputFile) {
          System.out.println(processInputCommand(family, line));
        } else {
          processInitCommand(family, line);
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
      System.out.println("File Not Found!! Please check the file and the location provided!");
    }

  }

  /**
   * Process a command and return the output string
   *
   * @param command - input command string to be processed
   * @return
   */
  public String processInputCommand(Family family, String command) {
    String[] commandParams = command.split(" ");
    String commandResult;
    switch (commandParams[0]) {
      case Constants.Commands.ADD_CHILD:
        commandResult = family.addChildFamily(commandParams[1], commandParams[2], commandParams[3]);
        break;

      case Constants.Commands.GET_RELATIONSHIP:
        commandResult = family.getRelationship(commandParams[1], commandParams[2]);
        break;

      default:
        commandResult = Constants.Message.INVALID_COMMAND;
        break;
    }

    return commandResult;
  }

  /**
   * Process command to initialize family tree.
   *
   * @param command
   */
  public void processInitCommand(Family family, String command) {
    String[] commandParams = command.split(" ");
    switch (commandParams[0]) {

      case Constants.Commands.ADD_FAMILY_HEAD:
        family.addFamilyHead(commandParams[1], commandParams[2]);
        break;

      case Constants.Commands.ADD_CHILD:
        family.addChildFamily(commandParams[1], commandParams[2], commandParams[3]);
        break;

      case Constants.Commands.ADD_SPOUSE:
        family.addSpouse(commandParams[1], commandParams[2], commandParams[3]);
        break;

      default:
        System.out.println("INVALID INIT COMMAND!");
        break;
    }
  }
}
