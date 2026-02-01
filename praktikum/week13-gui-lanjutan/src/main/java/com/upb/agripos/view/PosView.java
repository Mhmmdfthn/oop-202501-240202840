package com.upb.agripos.view;

import com.upb.agripos.model.Product;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class PosView extends VBox {
    public TextField txtCode = new TextField(), txtName = new TextField(), 
                     txtPrice = new TextField(), txtStock = new TextField();
    public Button btnAdd = new Button("Tambah Produk"), btnDelete = new Button("Hapus Terpilih");
    public TableView<Product> table = new TableView<>();

    public PosView() {
        setSpacing(10); setPadding(new Insets(15));

        // Konfigurasi Kolom Tabel
        TableColumn<Product, String> colCode = new TableColumn<>("Kode");
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        TableColumn<Product, String> colName = new TableColumn<>("Nama");
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Product, Double> colPrice = new TableColumn<>("Harga");
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        TableColumn<Product, Integer> colStock = new TableColumn<>("Stok");
        colStock.setCellValueFactory(new PropertyValueFactory<>("stock"));

        table.getColumns().addAll(colCode, colName, colPrice, colStock);

        GridPane grid = new GridPane();
        grid.setHgap(10); grid.setVgap(10);
        grid.add(new Label("Kode:"), 0, 0); grid.add(txtCode, 1, 0);
        grid.add(new Label("Nama:"), 0, 1); grid.add(txtName, 1, 1);
        grid.add(new Label("Harga:"), 0, 2); grid.add(txtPrice, 1, 2);
        grid.add(new Label("Stok:"), 0, 3); grid.add(txtStock, 1, 3);

        HBox actions = new HBox(10, btnAdd, btnDelete);
        btnDelete.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");
        btnAdd.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white;");

        getChildren().addAll(new Label("--- FORM INPUT ---"), grid, actions, 
                           new Label("--- DATA PRODUK ---"), table);
    }
}