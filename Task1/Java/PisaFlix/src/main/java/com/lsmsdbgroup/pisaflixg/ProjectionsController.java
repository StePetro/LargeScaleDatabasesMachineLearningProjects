package com.lsmsdbgroup.pisaflixg;

import com.lsmsdbgroup.pisaflix.Entities.*;
import com.lsmsdbgroup.pisaflix.pisaflixservices.PisaFlixServices;
import com.lsmsdbgroup.pisaflix.pisaflixservices.UserPrivileges;
import com.lsmsdbgroup.pisaflix.pisaflixservices.exceptions.InvalidPrivilegeLevelException;
import com.lsmsdbgroup.pisaflix.pisaflixservices.exceptions.UserNotLoggedException;
import java.net.URL;
import java.time.*;
import java.util.*;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class ProjectionsController implements Initializable {

    @FXML 
    private TableView<ObservableList> projectionTable;
    
    @FXML 
    private TableColumn<ObservableList, String> dataCol;
    
    @FXML 
    private TableColumn<ObservableList, String> roomCol;
    
    @FXML 
    private TableColumn<ObservableList, String> cinemaCol;
    
    @FXML 
    private TableColumn<ObservableList, String> filmCol;
    
    @FXML
    private ComboBox cinemaCombo;
    
    @FXML
    private ComboBox filmCombo;
    
    @FXML
    private DatePicker datePicker;
    
    @FXML
    private Button addProjectionButton;
    
    @FXML
    private Button removeProjectionButton;
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	
        try {
            PisaFlixServices.UserManager.checkUserPrivilegesForOperation(UserPrivileges.MODERATOR);
        } catch (UserNotLoggedException | InvalidPrivilegeLevelException ex) {
            addProjectionButton.setVisible(false);
            addProjectionButton.setManaged(false);
            removeProjectionButton.setVisible(false);
            removeProjectionButton.setManaged(false);
        }
        
        Set<Film> filmSet = PisaFlixServices.FilmManager.getAll();
        Set<Cinema> cinemaSet = PisaFlixServices.CinemaManager.getAll();
        ObservableList observableFilmSet = FXCollections.observableArrayList(filmSet);
        ObservableList observableCinemaSet = FXCollections.observableArrayList(cinemaSet);
        

        filmCombo.getItems().setAll(observableFilmSet);
        filmCombo.getItems().add("All");
        
        cinemaCombo.getItems().setAll(observableCinemaSet);
        cinemaCombo.getItems().add("All");

    }
    
    @FXML
    private void clickAddProjectionButton(){
        App.setMainPane("AddProjection");
    }
    
    @FXML
    private void clickRemoveProjectionButton(){
        if (projectionTable.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Deleting Projection");
            alert.setHeaderText("warning");
            alert.setContentText("You must select a projection");
            
            alert.showAndWait();
            
            return;
        }
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Deleting Projection");
        alert.setHeaderText("You're deleting the projection");
        alert.setContentText("Are you sure do you want continue?");
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() != ButtonType.OK){
            return;
        }
        
        Projection projection = (Projection) projectionTable.getSelectionModel().getSelectedItem();
        PisaFlixServices.ProjectionManager.removeProjection(projection.getIdProjection());
        showSearch();
    }
    
    @FXML
    public void showSearch(){
        dataCol.setCellValueFactory(new PropertyValueFactory<>("DateTime"));
        roomCol.setCellValueFactory(new PropertyValueFactory<>("Room"));
        cinemaCol.setCellValueFactory(new PropertyValueFactory<>("Cinema"));
        filmCol.setCellValueFactory(new PropertyValueFactory<>("Film"));
        
        int cinemaId;
        int filmId;

        if(cinemaCombo.getValue() == null || "All".equals(cinemaCombo.getValue().toString())){
            cinemaId = -1;
        }else{
            Cinema cinema = (Cinema) cinemaCombo.getValue();
            cinemaId = cinema.getIdCinema();
        }
        
        if(filmCombo.getValue() == null || "All".equals(filmCombo.getValue().toString())){
            filmId = -1;
        }else{
            Film film = (Film) filmCombo.getValue();
            filmId = film.getIdFilm();
        }
        
        projectionTable.getItems().setAll(getItemsToAdd(cinemaId, filmId));
    }
    
    private ObservableList getItemsToAdd(int cinemaId, int filmId){

        LocalDate localDate = datePicker.getValue();
        String dateStr;
        
        if(localDate != null) {
            dateStr = localDate.toString();
        } else
            dateStr = "all";
        
        Set<Projection> projectionSet = PisaFlixServices.ProjectionManager.queryProjections(cinemaId, filmId, dateStr, -1);
        ObservableList observableProjectionSet = FXCollections.observableArrayList(projectionSet);
        
        if(projectionSet == null)
            return null;
        
        return observableProjectionSet;
    }
}
