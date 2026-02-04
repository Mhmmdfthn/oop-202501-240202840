package com.upb.agripos.view;

import com.upb.agripos.AppJavaFX;
import com.upb.agripos.model.User;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class LoginView extends Application {

    private TextField txtUsername;
    private PasswordField txtPassword;
    private Label lblError;
    private Stage stage;

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        
        // 1. Root Background (Gradient Hijau Pertanian)
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #2E7D32, #81C784);");

        // 2. Login Card (Kotak Putih di tengah)
        VBox loginCard = new VBox(15);
        loginCard.setMaxWidth(350);
        loginCard.setMaxHeight(450);
        loginCard.setPadding(new Insets(30));
        loginCard.setAlignment(Pos.CENTER);
        loginCard.setStyle("-fx-background-color: white; -fx-background-radius: 15;");
        
        // Efek Bayangan pada Card
        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.rgb(0, 0, 0, 0.3));
        shadow.setRadius(20);
        loginCard.setEffect(shadow);

        // 3. Komponen UI
        // Judul
        Label lblTitle = new Label("AGRI-POS");
        lblTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 28));
        lblTitle.setTextFill(Color.web("#1B5E20"));
        
        Label lblSubtitle = new Label("Sistem Kasir Pertanian");
        lblSubtitle.setFont(Font.font("Segoe UI", 14));
        lblSubtitle.setTextFill(Color.GRAY);

        // Input Fields
        txtUsername = new TextField();
        txtUsername.setPromptText("Username");
        txtUsername.setStyle("-fx-pref-height: 40; -fx-background-radius: 5; -fx-border-color: #ddd; -fx-border-radius: 5;");
        
        txtPassword = new PasswordField();
        txtPassword.setPromptText("Password");
        txtPassword.setStyle("-fx-pref-height: 40; -fx-background-radius: 5; -fx-border-color: #ddd; -fx-border-radius: 5;");

        // Error Label
        lblError = new Label();
        lblError.setTextFill(Color.RED);
        lblError.setVisible(false);

        // Tombol Login
        Button btnLogin = new Button("MASUK / LOGIN");
        btnLogin.setStyle("-fx-background-color: #2E7D32; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14; -fx-background-radius: 5; -fx-cursor: hand;");
        btnLogin.setPrefWidth(Double.MAX_VALUE);
        btnLogin.setPrefHeight(40);
        
        // Action Button
        btnLogin.setOnAction(e -> handleLogin());

        // 4. KOTAK INFORMASI AKUN (DEMO CREDENTIALS)
        VBox infoBox = new VBox(5);
        infoBox.setStyle("-fx-background-color: #f5f5f5; -fx-padding: 10; -fx-background-radius: 5; -fx-border-color: #eee; -fx-border-radius: 5;");
        infoBox.setAlignment(Pos.CENTER_LEFT);
        
        Label lblInfoTitle = new Label("Info Akun Demo :");
        lblInfoTitle.setFont(Font.font("Arial", FontWeight.BOLD, 11));
        
        Label lblAdmin = new Label("• Admin: admin / admin123");
        lblAdmin.setFont(Font.font("Monospaced", 11));
        
        Label lblKasir = new Label("• Kasir: kasir1 / kasir123");
        lblKasir.setFont(Font.font("Monospaced", 11));
        
        infoBox.getChildren().addAll(lblInfoTitle, lblAdmin, lblKasir);
        
        // Spacer agar info box agak ke bawah
        Region spacer = new Region();
        spacer.setPrefHeight(10);

        // Masukkan semua ke Card
        loginCard.getChildren().addAll(
            lblTitle, lblSubtitle, 
            new Region() {{ setPrefHeight(10); }}, // Spacer kecil
            txtUsername, txtPassword, 
            lblError, 
            btnLogin,
            spacer,
            infoBox
        );

        root.getChildren().add(loginCard);

        Scene scene = new Scene(root, 600, 500);
        stage.setTitle("Agri-POS - Login");
        stage.setScene(scene);
        stage.show();
    }

    private void handleLogin() {
        try {
            String user = txtUsername.getText();
            String pass = txtPassword.getText();

            // Panggil Controller
            User loggedInUser = AppJavaFX.getAuthController().login(user, pass);

            // Jika sukses, tutup login, buka PosView
            new PosView().start(new Stage());
            stage.close();

        } catch (Exception e) {
            lblError.setText("Login Gagal: " + e.getMessage());
            lblError.setVisible(true);
            
            // Efek getar sederhana jika salah (opsional)
            // shakeNode(txtUsername); 
        }
    }
}