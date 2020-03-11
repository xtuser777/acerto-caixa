package br.unoeste.fipp.testecaixa.view;

import java.time.LocalDate;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AcertoCaixa extends Application
{    
    @Override
    public void start(Stage primaryStage)
    {
        Label lbData = new Label("Data: ");
        lbData.setLayoutX(10);
        lbData.setLayoutY(10);
        
        TextField txData = new TextField(LocalDate.now().toString());
        txData.setLayoutX(60);
        txData.setLayoutY(8);
        txData.setDisable(true);
        
        Label lbTipo = new Label("Tipo: ");
        lbTipo.setLayoutX(10);
        lbTipo.setLayoutY(50);
        
        RadioButton rbDec = new RadioButton("Decrementar");
        rbDec.setLayoutX(60);
        rbDec.setLayoutY(50);
        
        RadioButton rbInc = new RadioButton("Incrementar");
        rbInc.setLayoutX(165);
        rbInc.setLayoutY(50);
        
        ToggleGroup rg = new ToggleGroup();
        rbDec.setToggleGroup(rg);
        rbInc.setToggleGroup(rg);
        rbDec.setSelected(true);
        
        Label lbValor = new Label("Valor R$:");
        lbValor.setLayoutX(10);
        lbValor.setLayoutY(90);
        
        TextField txValor = new TextField();
        txValor.setLayoutX(60);
        txValor.setLayoutY(88);
        
        Label lbMotivo = new Label("Motivo: ");
        lbMotivo.setLayoutX(10);
        lbMotivo.setLayoutY(130);
        
        TextField txMotivo = new TextField();
        txMotivo.setLayoutX(60);
        txMotivo.setLayoutY(128);
        txMotivo.setPrefWidth(230);
        
        Button btConfirmar = new Button("CONFIRMAR");
        btConfirmar.setLayoutX(10);
        btConfirmar.setLayoutY(180);
        btConfirmar.setPrefWidth(280);
        btConfirmar.setOnAction((ActionEvent event) -> {
            System.out.println("Hello World!");
        });
        
        AnchorPane root = new AnchorPane();
        root.getChildren().add(lbData);
        root.getChildren().add(txData);
        root.getChildren().add(lbTipo);
        root.getChildren().add(rbDec);
        root.getChildren().add(rbInc);
        root.getChildren().add(lbValor);
        root.getChildren().add(txValor);
        root.getChildren().add(lbMotivo);
        root.getChildren().add(txMotivo);
        root.getChildren().add(btConfirmar);
        
        Scene scene = new Scene(root, 300, 220);
        
        primaryStage.setTitle("Acerto de Caixa");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
}
