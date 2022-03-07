package template.try_demo;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The class provides methods to close the window through clicking on a button
 */
public class ConfirmOrderMsgController {
    @FXML
    private Button quitBtn;
    @FXML
    protected void onHelloButtonClick() throws IOException {
        // get a handle to the stage
        Stage stage = (Stage) quitBtn.getScene().getWindow();
        // do what you have to do
        stage.close();

    }

}
