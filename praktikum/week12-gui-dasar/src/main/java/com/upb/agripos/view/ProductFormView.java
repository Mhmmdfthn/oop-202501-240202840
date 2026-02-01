package com.upb.agripos.view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class ProductFormView extends VBox {
    public TextField txtCode = new TextField(), txtName = new TextField(), 
                     txtPrice = new TextField(), txtStock = new TextField();
    public Button btnAdd = new Button("Tambah Produk");
    public ListView<String> listView = new ListView<>();

    public ProductFormView() {
        setSpacing(10);
        setPadding(new Insets(15));

        GridPane grid = new GridPane();
        grid.setHgap(10); grid.setVgap(10);
        grid.add(new Label("Kode:"), 0, 0); grid.add(txtCode, 1, 0);
        grid.add(new Label("Nama:"), 0, 1); grid.add(txtName, 1, 1);
        grid.add(new Label("Harga:"), 0, 2); grid.add(txtPrice, 1, 2);
        grid.add(new Label("Stok:"), 0, 3); grid.add(txtStock, 1, 3);
        grid.add(btnAdd, 1, 4);

        getChildren().addAll(new Label("--- INPUT PRODUK ---"), grid, 
                           new Label("--- DAFTAR PRODUK ---"), listView);
    }
}