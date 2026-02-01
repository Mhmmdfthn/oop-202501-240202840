package com.upb.agripos;

import com.upb.agripos.controller.ProductController;
import com.upb.agripos.dao.ProductDAO;
import com.upb.agripos.service.ProductService;
import com.upb.agripos.view.ProductFormView;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class AppJavaFX extends Application {
    private ProductController controller;
    private ProductFormView view;

    @Override
    public void init() {
        // Inisialisasi dependensi sesuai struktur
        ProductDAO dao = new ProductDAO();
        ProductService service = new ProductService(dao);
        controller = new ProductController(service);
    }

    @Override
    public void start(Stage stage) {
        view = new ProductFormView();

        // Event Handling
        view.btnAdd.setOnAction(e -> {
            try {
                controller.processAdd(
                    view.txtCode.getText(), view.txtName.getText(), 
                    view.txtPrice.getText(), view.txtStock.getText()
                );
                refreshUI();
                clearForm();
            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, "Gagal Simpan: " + ex.getMessage()).show();
            }
        });

        stage.setScene(new Scene(view, 450, 600));
        stage.setTitle("Agri-POS Week 12 - " + "Muhammad Nuur Fathan");
        refreshUI();
        stage.show();
    }

    private void refreshUI() {
        try {
            view.listView.getItems().clear();
            controller.fetchAll().forEach(p -> 
                view.listView.getItems().add(p.getCode() + " | " + p.getName() + " | Rp" + p.getPrice())
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearForm() {
        view.txtCode.clear(); view.txtName.clear(); view.txtPrice.clear(); view.txtStock.clear();
    }

    public static void main(String[] args) { launch(args); }
}