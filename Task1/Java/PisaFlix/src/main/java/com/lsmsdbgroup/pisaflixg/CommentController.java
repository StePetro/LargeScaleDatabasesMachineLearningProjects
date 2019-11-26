package com.lsmsdbgroup.pisaflixg;

import com.lsmsdbgroup.pisaflix.Entities.Cinema;
import com.lsmsdbgroup.pisaflix.Entities.Comment;
import com.lsmsdbgroup.pisaflix.Entities.Film;
import com.lsmsdbgroup.pisaflix.Entities.User;
import com.lsmsdbgroup.pisaflix.pisaflixservices.PisaFlixServices;
import com.lsmsdbgroup.pisaflix.pisaflixservices.UserPrivileges;
import com.lsmsdbgroup.pisaflix.pisaflixservices.exceptions.InvalidPrivilegeLevelException;
import com.lsmsdbgroup.pisaflix.pisaflixservices.exceptions.UserNotLoggedException;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Region;

public class CommentController implements Initializable {

    /*
        0 = Film
        1 = Cinema
     */
    private final int type;

    private final StringProperty usernameProperty = new SimpleStringProperty();
    private final StringProperty timestampProperty = new SimpleStringProperty();
    private final StringProperty commentProperty = new SimpleStringProperty();

    private Comment comment;

    public CommentController(String username, String timestamp, String commment, int type) {
        usernameProperty.set(username);

        String[] timestampSplit = timestamp.split(":");
        String timestampStr = timestampSplit[0] + ":" + timestampSplit[1];
        timestampProperty.set(timestampStr);
        commentProperty.set(commment);

        this.type = type;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    @FXML
    private VBox commentVbox;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label timestampLabel;

    @FXML
    private Label commentLabel;

    @FXML
    private ContextMenu commentMenu;

    @FXML
    private MenuItem updateMenuItem;

    @FXML
    private MenuItem deleteMenuItem;

    @FXML
    private TextArea commentTextArea;

    @FXML
    private HBox buttonHbox;

    @FXML
    private Button cancelButton;

    @FXML
    private Button updateButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        usernameLabel.setText(usernameProperty.get());
        timestampLabel.setText(timestampProperty.get());
        commentLabel.setText(commentProperty.get());

        commentLabel.setMinHeight(Region.USE_PREF_SIZE);

        commentTextArea.setVisible(false);
        commentTextArea.setManaged(false);
        buttonHbox.setVisible(false);
        buttonHbox.setManaged(false); 
    }
    
    private boolean canMenuBeVisible(){
        try{
            User user = PisaFlixServices.authenticationService.getLoggedUser();
            if(user != null){
                if(Objects.equals(comment.getUser().getIdUser(), user.getIdUser())) {
                    return true;
                } 
                PisaFlixServices.userService.checkUserPrivilegesForOperation(UserPrivileges.SOCIAL_MODERATOR, "Delete/Update other user comment");
                return true;
            }
        } catch(Exception ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }

    private void switchState(boolean state) {
        commentTextArea.setVisible(state);
        commentTextArea.setManaged(state);

        buttonHbox.setVisible(state);
        buttonHbox.setManaged(state);

        commentLabel.setVisible(!state);
        commentLabel.setManaged(!state);
    }

    private void refreshComment() {
        comment = PisaFlixServices.commentService.getById(comment.getIdComment());
    }


    @FXML
    private void confirmComment() {
        if (!App.printConfirmationDialog("Updating a comment", "You are updating a comment", "Are you sure to continue")) {
            return;
        }

        comment.setText(commentTextArea.getText());
   
        try {
            if(comment.getUser().equals(PisaFlixServices.authenticationService.getLoggedUser())){
               PisaFlixServices.commentService.update(comment); 
            }else{
                App.printErrorDialog("Updating comment", "There was an error while updating comment", "Only the user who's written the commet can update it.");
            }
        } catch (InvalidPrivilegeLevelException | UserNotLoggedException ex) {
            App.printErrorDialog("Updating comment", "There was an error while updating comment", ex.getMessage());
        }

        switchState(false);

        refreshComment();

        commentLabel.setText(comment.getText());
    }

    @FXML
    private void cancelComment() {
        switchState(false);
    }

    @FXML
    private void updateComment() {
        switchState(true);

        commentTextArea.setText(commentLabel.getText());
    }

    @FXML
    private void deleteComment() {
        if (!App.printConfirmationDialog("Deleting a comment", "You are deleting a comment", "Are you sure to continue")) {
            return;
        }

        try {
            PisaFlixServices.commentService.delete(comment);
        } catch (InvalidPrivilegeLevelException | UserNotLoggedException ex) {
            App.printErrorDialog("Deleting comment", "There was an error while deleting comment", ex.getMessage());
        }
        

        if (type == 0) {
            Film film = comment.getFilmSet().iterator().next();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("FilmDetailPage.fxml"));
            AnchorPane anchorPane = null;
            try {
                anchorPane = loader.load();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
            FilmDetailPageController filmDetailPageController = loader.getController();
            filmDetailPageController.setFilm(film);

            App.setMainPane(anchorPane);

            filmDetailPageController.refreshFilm();
            filmDetailPageController.refreshComment();
        } else {
            Cinema cinema = comment.getCinemaSet().iterator().next();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("CinemaDetailPage.fxml"));
            AnchorPane anchorPane = null;
            try {
                anchorPane = loader.load();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }

            CinemaDetailPageController cinemaDetailPageController = loader.getController();
            cinemaDetailPageController.setCinema(cinema);

            App.setMainPane(anchorPane);

            cinemaDetailPageController.refreshCinema();
            cinemaDetailPageController.refreshComment();
        }
    }
}
