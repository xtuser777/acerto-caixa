package br.unoeste.fipp.testecaixa;

import br.unoeste.fipp.testecaixa.view.AcertoCaixa;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application
{    
    @Override
    public void start(Stage primaryStage) 
    {
        new AcertoCaixa().start(primaryStage);
    }
    
    public static void main(String[] args)
    {
        launch(args);
    }
}
