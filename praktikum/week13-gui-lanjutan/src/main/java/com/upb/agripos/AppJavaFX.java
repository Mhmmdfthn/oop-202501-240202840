package com.upb.agripos;

import com.upb.agripos.controller.ProductController;
import com.upb.agripos.dao.ProductDAO;
import com.upb.agripos.model.Product;
import com.upb.agripos.service.ProductService;
import com.upb.agripos.view.ProductTableView;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class AppJavaFX extends Application {
    private ProductController controller;
    private ProductTableView view;

    @Override
    public void init() {
        controller = new ProductController(new ProductService(new ProductDAO()));
    }

    @Override
    public void start(Stage stage) {
        view = new ProductTableView();

        // Lambda: Handle Tambah
        view.btnAdd.setOnAction(e -> {
            try {
                controller.processAdd(view.txtCode.getText(), view.txtName.getText(), 
                                     view.txtPrice.getText(), view.txtStock.getText());
                refreshTable(); clearForm();
            } catch (Exception ex) { showMsg("Error", ex.getMessage(), Alert.AlertType.ERROR); }
        });

        // Lambda: Handle Hapus
        view.btnDelete.setOnAction(e -> {
            Product selected = view.table.getSelectionModel().getSelectedItem();
            if (selected != null) {
                try {
                    controller.processDelete(selected.getCode());
                    refreshTable();
                } catch (Exception ex) { showMsg("Error", ex.getMessage(), Alert.AlertType.ERROR); }
            } else { showMsg("Peringatan", "Pilih data di tabel dulu!", Alert.AlertType.WARNING); }
        });

        stage.setScene(new Scene(view, 550, 650));
        stage.setTitle("Agri-POS Week 13 - Muhammad Nuur Fathan");
        refreshTable(); stage.show();
    }

    private void refreshTable() {
        try { view.table.getItems().setAll(controller.fetchAll()); } catch (Exception e) { e.printStackTrace(); }
    }

    private void clearForm() { view.txtCode.clear(); view.txtName.clear(); view.txtPrice.clear(); view.txtStock.clear(); }

    private void showMsg(String t, String c, Alert.AlertType type) {
        Alert a = new Alert(type); a.setTitle(t); a.setContentText(c); a.show();
    }

    public static void main(String[] args) { launch(args); }
}