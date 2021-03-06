package com.project;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import com.project.boards.Board;
import com.project.boards.Board10x10;
import com.project.boards.Board12x12;
import com.project.boards.Board6x6;
import com.project.boards.Board8x8;
import com.project.boards.Board9x9;
import com.project.exceptions.MalformedFile;

public class ControllerMainWindow {
  private static Logger log = LoggerFactory.getLogger(ControllerMainWindow.class);
  private Stage mainStage;

  /**
   * Handle loading new Board from fxml file with empty sudoku fields
   * @param ev
   */
  public void handleNewBoard(ActionEvent ev) {
    log.debug("handleNewBoard, ActionEvent: {}", ev);
    Stage stage;
    Parent root;
    FXMLLoader loader;
    Board newBoard;
    Button button = (Button) ev.getSource();
    try {
      switch (button.getId()) {
        case "Board6x6": {
          log.debug("Board 6x6");
          loader = new FXMLLoader(getClass().getResource("/fxml/6x6v2.fxml"));
          newBoard = new Board6x6(new ArrayList<>());
          newBoard.initializeList(6 * 6, 0);
          root = (Parent) loader.load();
          stage = new Stage();
          stage.setTitle("00:00");
          break;
        }
        case "Board8x8": {
          log.debug("Board 8x8");
          loader = new FXMLLoader(getClass().getResource("/fxml/8x8v2.fxml"));
          newBoard = new Board8x8(new ArrayList<>());
          newBoard.initializeList(8 * 8, 0);
          root = (Parent) loader.load();
          stage = new Stage();
          stage.setTitle("00:00");
          break;
        }
        case "Board9x9": {
          log.debug("Board 9x9");
          loader = new FXMLLoader(getClass().getResource("/fxml/9x9v2.fxml"));
          newBoard = new Board9x9(new ArrayList<>());
          newBoard.initializeList(9 * 9, 0);
          root = (Parent) loader.load();
          stage = new Stage();
          stage.setTitle("00:00");
          break;
        }
        case "Board10x10": {
          log.debug("Board 10x10");
          loader = new FXMLLoader(getClass().getResource("/fxml/10x10v2.fxml"));
          newBoard = new Board10x10(new ArrayList<>());
          newBoard.initializeList(10 * 10, 0);
          root = (Parent) loader.load();
          stage = new Stage();
          stage.setTitle("00:00");
          break;
        }
        case "Board12x12": {
          log.debug("Board 12x12");
          loader = new FXMLLoader(getClass().getResource("/fxml/12x12v2.fxml"));
          newBoard = new Board12x12(new ArrayList<>());
          newBoard.initializeList(12 * 12, 0);
          root = (Parent) loader.load();
          stage = new Stage();
          stage.setTitle("00:00");
          break;
        }
        default:
          log.error("Should never happen, Invalid ID: {}", button.getId());
          throw new RuntimeException("Should never happen");
      }
      stage.setScene(new Scene(root));
      ControllerBoard cBoard = loader.getController();
      cBoard.startup(mainStage, stage, newBoard);
      mainStage.close();
      stage.setResizable(false);
      stage.show();
    } catch (Exception e) {
      log.error("Should never happen, Exception in new stage", e);
    }
  }

  /**
   * Handle loading new Board from fxml file with sudoku fields loaded from user provided file save
   * @param ev
   */
  public void handleLoadFile(ActionEvent ev) {
    FileChooser fChooser = new FileChooser();
    fChooser.setTitle("Choose sudoku board file");
    File fHandle = fChooser.showOpenDialog(mainStage);
    Board board;
    if (fHandle == null) {
      return;
    }
    try {
      SudokuState state = new SudokuState();
      state = state.loadFromFile(fHandle.getAbsolutePath());
      board = state.getCurrentBoard();
      Stage stage;
      Parent root;
      FXMLLoader loader;
      switch (board.getSize()) {
        case 6: {
          log.debug("Loading 6x6 from file");
          loader = new FXMLLoader(getClass().getResource("/fxml/6x6v2.fxml"));
          root = (Parent) loader.load();
          stage = new Stage();
          stage.setTitle("00:00");
          break;
        }
        case 8: {
          log.debug("Loading 8x8 from file");
          loader = new FXMLLoader(getClass().getResource("/fxml/8x8v2.fxml"));
          root = (Parent) loader.load();
          stage = new Stage();
          stage.setTitle("00:00");
          break;
        }
        case 9: {
          log.debug("Loading 9x9 from file");
          loader = new FXMLLoader(getClass().getResource("/fxml/9x9v2.fxml"));
          root = (Parent) loader.load();
          stage = new Stage();
          stage.setTitle("00:00");
          break;
        }
        case 10: {
          log.debug("Loading 10x10 from file");
          loader = new FXMLLoader(getClass().getResource("/fxml/10x10v2.fxml"));
          root = (Parent) loader.load();
          stage = new Stage();
          stage.setTitle("00:00");
          break;
        }
        case 12: {
          log.debug("Loading 12x12 from file");
          loader = new FXMLLoader(getClass().getResource("/fxml/12x12v2.fxml"));
          root = (Parent) loader.load();
          stage = new Stage();
          stage.setTitle("00:00");
          break;
        }
        default:
          log.error("Should never happen, Invalid size: {}", board.getSize());
          throw new RuntimeException("Should never happen");
      }
      stage.setScene(new Scene(root));
      ControllerBoard cBoard = loader.getController();
      cBoard.startup(mainStage, stage, state);
      mainStage.close();
      stage.show();
    } catch (IOException e) {
      throw new RuntimeException(e);
    } catch (MalformedFile e) {
      new Alert(Alert.AlertType.INFORMATION, "Cannot parse malformed file").show();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
      new Alert(Alert.AlertType.INFORMATION, "Class not found ???").show();
    }
  }
  
  /**
   * Setter for stage
   * @param stage
   * @author Arkadiusz
   */
  public void setStage(Stage stage) {
    mainStage = stage;
  }
}
