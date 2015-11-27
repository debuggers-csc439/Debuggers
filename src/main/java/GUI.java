import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.LinkedHashMap;
import java.util.HashSet;

public class GUI extends Application {
    
    TextField SearchTextField; 
    LinkedHashMap hashMap = Setup.getInstance();
    TextArea resultText;


    @Override
    public void start(Stage primaryStage) {
        //Objects
        SearchTextField = new TextField();

        Label labelExpl = new Label("Input Search word: ");
        labelExpl.setTextFill(Color.web("#0076a3"));

        resultText = new TextArea();
        resultText.setText("Search Results: \n");
        resultText.setWrapText(true);
        
        Button btn = new Button("Search");
        btn.setPrefWidth(170);
        
        Button btnCrawler = new Button("Crawler");
        btnCrawler.setPrefWidth(170);

        // TODO: Turn these things into lambdas
        //Add handle to btn (Search:)
        btn.setOnAction(new EventHandler<ActionEvent>() {
            // @Override
            public void handle(ActionEvent event) {
                String userInput = SearchTextField.getText(); //Collect input from textField
                
                if (userInput.length() != 0) {  // textField does not handle (userInput != null)
                    long start = System.currentTimeMillis(); // Search time count start
                    
                    HashSet<String> results = Searcher.search(userInput, hashMap);
                    
                    long end = System.currentTimeMillis(); // Search time count end
                    
                    long time = ((end - start));  // Search time total ms
                    
                    if (results == null) {
                        resultText.setText("The search did not find any results for " +userInput);
                    } else {
                        resultText.setText("Search Results for " +userInput  +": \n"); //Resets the textArea for new results to be shown
                        
                        int count = 0;
                        
                        for(String result: results) { // for-each loop through the result and append
                            resultText.appendText(result +"\n");
                            count++;
                        }
                        resultText.appendText(count +" results in " +time +" milisecond(s).");
                    }
                } else {
                    resultText.setText("Please enter a search query");
                }
            }
        });
        
        //GridPane (grid - top)
        GridPane pane = new GridPane();
        pane.setAlignment(Pos.BOTTOM_LEFT);
        pane.setHgap(5);
        pane.setVgap(5);
        pane.setPadding(new Insets(5, 10, 10, 45));
        pane.setBorder(Border.EMPTY);
        
        //Add all elements to pane (into grid)
        //GridLayout(int rows, int columns, int horizontalGap, int verticalGap)
        pane.add(labelExpl,0,0,2,1);
        pane.add(SearchTextField,0,1,2,2);
    
        //GridPane (grid - center)
        GridPane paneCenter = new GridPane();
        pane.setAlignment(Pos.BOTTOM_LEFT);
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setPadding(new Insets(5, 10, 10, 45));
        pane.setBorder(Border.EMPTY);
    
        //Add all elements to pane (into grid)
        //GridLayout(int rows, int columns, int horizontalGap, int verticalGap)  
        pane.add(resultText,0,3,4,4);
        
        //HBox
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(5, 10, 10, 45));
        hbox.setSpacing(10);
        hbox.getChildren().addAll(btn, btnCrawler);
    
        //BorderPane (border)
        BorderPane border = new BorderPane();
        border.setBottom(hbox); //add hbox to border from method
        border.setTop(pane); //add grid to border from method
        border.setCenter(paneCenter);
   
        //Scene: (contains border)
        Scene scene = new Scene(border, 600, 300);
   
        //Stage:
        primaryStage.setTitle("JavaSearchEngine");
        primaryStage.setScene(scene); // Show Scene
        primaryStage.show();
    } 
    
    public static void main (String[] args) throws IOException {
        LinkedHashMap hashMap = Setup.initialise(args[0]);
        
        while(hashMap != null){
            launch(args);
        }
    }
}


    