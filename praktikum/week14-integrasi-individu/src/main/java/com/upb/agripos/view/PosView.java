package com.upb.agripos.view;

import com.upb.agripos.model.CartItem;
import com.upb.agripos.model.Product;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class PosView extends HBox {
    public TableView<Product> tableProd = new TableView<>();
    public TextField txtCode = new TextField(), txtName = new TextField(), txtPrice = new TextField(), txtStock = new TextField();
    public Button btnAdd = new Button("Simpan Baru"), btnUpdate = new Button("Update Data"), btnDelete = new Button("Hapus Stok"), btnClear = new Button("Reset Form");

    public TableView<CartItem> tableCart = new TableView<>();
    public TextField txtQty = new TextField("1"), txtCash = new TextField();
    public Button btnAddToCart = new Button("Add to Cart"), btnPay = new Button("BAYAR");
    public Label lblTotal = new Label("Rp 0"), lblChange = new Label("Rp 0");

    public PosView() {
        setSpacing(20); setPadding(new Insets(15));
        HBox.setHgrow(this, Priority.ALWAYS);

        // --- PANEL KIRI: PRODUK ---
        VBox left = new VBox(10); HBox.setHgrow(left, Priority.ALWAYS);
        setupProductTable();
        
        VBox formBox = new VBox(10); formBox.setPadding(new Insets(15));
        formBox.setStyle("-fx-border-color: #bdc3c7; -fx-border-radius: 5; -fx-background-color: #f9f9f9;");
        GridPane g = new GridPane(); g.setHgap(10); g.setVgap(10);
        g.add(new Label("Kode:"), 0, 0); g.add(txtCode, 1, 0);
        g.add(new Label("Nama:"), 0, 1); g.add(txtName, 1, 1);
        g.add(new Label("Harga:"), 0, 2); g.add(txtPrice, 1, 2);
        g.add(new Label("Stok:"), 0, 3); g.add(txtStock, 1, 3);
        
        btnAdd.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white;");
        btnUpdate.setStyle("-fx-background-color: #2980b9; -fx-text-fill: white;");
        btnDelete.setStyle("-fx-background-color: #c0392b; -fx-text-fill: white;");
        formBox.getChildren().addAll(new Label("MANAJEMEN DATA"), g, new HBox(10, btnAdd, btnUpdate, btnDelete, btnClear));
        left.getChildren().addAll(new Label("--- STOK PRODUK ---"), tableProd, formBox);

        // --- PANEL KANAN: KASIR ---
        VBox right = new VBox(10); right.setPrefWidth(420);
        setupCartTable();
        HBox cartControl = new HBox(10, new Label("Qty:"), txtQty, btnAddToCart);
        txtQty.setPrefWidth(60); btnAddToCart.setStyle("-fx-background-color: #f39c12; -fx-text-fill: white;");

        VBox payBox = new VBox(10); payBox.setPadding(new Insets(15));
        payBox.setStyle("-fx-background-color: #ecf0f1; -fx-border-color: #ddd;");
        GridPane pg = new GridPane(); pg.setHgap(10); pg.setVgap(10);
        pg.add(new Label("TOTAL:"), 0, 0); pg.add(lblTotal, 1, 0);
        pg.add(new Label("BAYAR:"), 0, 1); pg.add(txtCash, 1, 1);
        pg.add(new Label("KEMBALI:"), 0, 2); pg.add(lblChange, 1, 2);
        lblTotal.setStyle("-fx-font-weight: bold; -fx-text-fill: #27ae60; -fx-font-size: 18;");
        btnPay.setStyle("-fx-background-color: #1a73e8; -fx-text-fill: white; -fx-font-weight: bold;");
        btnPay.setMaxWidth(Double.MAX_VALUE);
        payBox.getChildren().addAll(pg, btnPay);

        right.getChildren().addAll(new Label("--- KERANJANG ---"), tableCart, cartControl, new Separator(), payBox);
        getChildren().addAll(left, right);
    }

    private void setupProductTable() {
        TableColumn<Product, String> c1 = new TableColumn<>("Kode"); c1.setCellValueFactory(new PropertyValueFactory<>("code"));
        TableColumn<Product, String> c2 = new TableColumn<>("Nama"); c2.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Product, Double> c3 = new TableColumn<>("Harga"); c3.setCellValueFactory(new PropertyValueFactory<>("price"));
        TableColumn<Product, Integer> c4 = new TableColumn<>("Stok"); c4.setCellValueFactory(new PropertyValueFactory<>("stock"));
        tableProd.getColumns().addAll(c1, c2, c3, c4);
        tableProd.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); // Biar Full
        VBox.setVgrow(tableProd, Priority.ALWAYS);
    }

    private void setupCartTable() {
        TableColumn<CartItem, String> c1 = new TableColumn<>("Item");
        c1.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getProduct().getName()));
        TableColumn<CartItem, Integer> c2 = new TableColumn<>("Qty"); c2.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        TableColumn<CartItem, Double> c3 = new TableColumn<>("Subtotal"); c3.setCellValueFactory(new PropertyValueFactory<>("subtotal"));
        tableCart.getColumns().addAll(c1, c2, c3);
        tableCart.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        VBox.setVgrow(tableCart, Priority.ALWAYS);
    }
}