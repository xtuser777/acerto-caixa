package br.unoeste.fipp.testecaixa.view;

import br.unoeste.fipp.testecaixa.control.AcertoCaixaControl;
import br.unoeste.fipp.testecaixa.model.Caixa;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class AcertoCaixa extends Application
{    
    private Caixa caixa;
    
    public AcertoCaixa(Caixa caixa)
    {
        this.caixa = caixa;
    }
    
    @Override
    public void start(Stage primaryStage)
    {
        Label lbData = new Label("Data: ");
        lbData.setFont(Font.font("System", 11));
        lbData.setLayoutX(10);
        lbData.setLayoutY(12);
        
        TextField txData = new TextField(LocalDate.now().toString());
        txData.setLayoutX(60);
        txData.setLayoutY(8);
        txData.setDisable(true);
        
        Label lbTipo = new Label("Tipo: ");
        lbTipo.setFont(Font.font("System", 11));
        lbTipo.setLayoutX(10);
        lbTipo.setLayoutY(50);
        
        RadioButton rbDec = new RadioButton("Decrementar");
        rbDec.setFont(Font.font("System", 11));
        rbDec.setLayoutX(60);
        rbDec.setLayoutY(50);
        
        RadioButton rbInc = new RadioButton("Incrementar");
        rbInc.setFont(Font.font("System", 11));
        rbInc.setLayoutX(165);
        rbInc.setLayoutY(50);
        
        ToggleGroup rg = new ToggleGroup();
        rbDec.setToggleGroup(rg);
        rbInc.setToggleGroup(rg);
        rbDec.setSelected(true);
        
        Label lbValor = new Label("Valor R$:");
        lbValor.setFont(Font.font("System", 11));
        lbValor.setLayoutX(10);
        lbValor.setLayoutY(92);
        
        TextField txValor = new TextField();
        txValor.setLayoutX(60);
        txValor.setLayoutY(88);
        
        Label lbMotivo = new Label("Motivo: ");
        lbMotivo.setFont(Font.font("System", 11));
        lbMotivo.setLayoutX(10);
        lbMotivo.setLayoutY(132);
        
        TextField txMotivo = new TextField();
        txMotivo.setLayoutX(60);
        txMotivo.setLayoutY(128);
        txMotivo.setPrefWidth(230);
        
        Button btConfirmar = new Button("CONFIRMAR");
        btConfirmar.setLayoutX(10);
        btConfirmar.setLayoutY(180);
        btConfirmar.setPrefWidth(280);
        btConfirmar.setOnAction((ActionEvent event) -> { confirmar(rbDec, txValor, txMotivo, primaryStage); });
        
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
    
    private void confirmar(RadioButton rbDec, TextField txValor, TextField txMotivo, Stage primaryStage)
    {
            int tipo = rbDec.isSelected() ? 1 : 2;
            String svalor = txValor.getText();
            String motivo = txMotivo.getText();
            
            double valor = Double.parseDouble(svalor);
            
            String mensagem = new AcertoCaixaControl().confirmar(tipo, valor, motivo, this.caixa.getId());
            
            if (mensagem.length() > 0)
            {
                new Alert(Alert.AlertType.ERROR, mensagem, ButtonType.CLOSE).show();
            }
            else
            {
                rbDec.setSelected(true);
                txValor.setText("");
                txMotivo.setText("");
                
                new Inicio().start(primaryStage);
            }
    }
}
