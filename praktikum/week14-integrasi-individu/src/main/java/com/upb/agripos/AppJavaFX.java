package com.upb.agripos;

import com.upb.agripos.dao.JdbcProductDAO;
import com.upb.agripos.model.Product;
import com.upb.agripos.service.CartService;
import com.upb.agripos.view.PosView;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class AppJavaFX extends Application {
    private JdbcProductDAO dao = new JdbcProductDAO();
    private CartService cartService = new CartService();

    @Override
    public void start(Stage stage) {
        System.out.println("Hello World, I am Muhammad Nuur Fathan - 240202840"); //

        PosView view = new PosView();

        // 1. Logika "Diferensiasi": Klik Tabel -> Mode Update
        view.tableProd.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> {
            if (newV != null) {
                view.txtCode.setText(newV.getCode()); view.txtCode.setEditable(false); // Code tak boleh diedit
                view.txtName.setText(newV.getName()); view.txtPrice.setText(String.valueOf(newV.getPrice()));
                view.txtStock.setText(String.valueOf(newV.getStock()));
                view.btnAdd.setDisable(true); view.btnUpdate.setDisable(false);
            }
        });

        // 2. Handler: Tambah Produk Baru
        view.btnAdd.setOnAction(e -> {
            try {
                dao.insert(new Product(view.txtCode.getText(), view.txtName.getText(), Double.parseDouble(view.txtPrice.getText()), Integer.parseInt(view.txtStock.getText())));
                refreshProdTable(view); clearForm(view);
            } catch (Exception ex) { showMsg("Error DB", ex.getMessage()); }
        });

        // 3. Handler: Update Produk
        view.btnUpdate.setOnAction(e -> {
            try {
                dao.update(new Product(view.txtCode.getText(), view.txtName.getText(), Double.parseDouble(view.txtPrice.getText()), Integer.parseInt(view.txtStock.getText())));
                refreshProdTable(view); clearForm(view);
            } catch (Exception ex) { showMsg("Error Update", ex.getMessage()); }
        });

        // 4. Handler: Reset Form (Mode Tambah Baru)
        view.btnClear.setOnAction(e -> clearForm(view));

        // 5. Handler: Keranjang & Kembalian
        view.btnAddToCart.setOnAction(e -> {
            Product sel = view.tableProd.getSelectionModel().getSelectedItem();
            try {
                if (sel != null) {
                    cartService.addToCart(sel, Integer.parseInt(view.txtQty.getText()));
                    view.tableCart.getItems().setAll(cartService.getCartItems());
                    view.lblTotal.setText("Rp " + cartService.getTotal());
                }
            } catch (Exception ex) { showMsg("Peringatan", ex.getMessage()); }
        });

        view.txtCash.textProperty().addListener((obs, ov, nv) -> {
            try {
                double change = Double.parseDouble(nv) - cartService.getTotal();
                view.lblChange.setText("Rp " + (change < 0 ? 0 : change));
            } catch (Exception ex) { view.lblChange.setText("Rp 0"); }
        });

        stage.setScene(new Scene(view, 1200, 750));
        stage.setTitle("Agri-POS Integrated - Muhammad Nuur Fathan");
        refreshProdTable(view); view.btnUpdate.setDisable(true);
        stage.show();
    }

    private void refreshProdTable(PosView v) { try { v.tableProd.getItems().setAll(dao.findAll()); } catch (Exception e) {} }
    private void clearForm(PosView v) { v.txtCode.clear(); v.txtCode.setEditable(true); v.txtName.clear(); v.txtPrice.clear(); v.txtStock.clear(); v.btnAdd.setDisable(false); v.btnUpdate.setDisable(true); v.tableProd.getSelectionModel().clearSelection(); }
    private void showMsg(String t, String c) { new Alert(Alert.AlertType.WARNING, c).show(); }

    public static void main(String[] args) { launch(args); }
}