package com.upb.agripos.view;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.upb.agripos.AppJavaFX;
import com.upb.agripos.controller.PosController;
import com.upb.agripos.controller.ReportController;
import com.upb.agripos.model.CartItem;
import com.upb.agripos.model.CashPayment;
import com.upb.agripos.model.EWalletPayment;
import com.upb.agripos.model.PaymentMethod;
import com.upb.agripos.model.Product;
import com.upb.agripos.model.Transaction;
import com.upb.agripos.model.User;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class PosView extends Application {

    private final PosController posController = AppJavaFX.getPosController();
    private final ReportController reportController = AppJavaFX.getReportController();
    
    // --- UI GLOBAL COMPONENTS ---
    private ObservableList<Product> productData = FXCollections.observableArrayList();

    private TableView<Product> tableProduct = new TableView<>();      // Tabel Kasir
    private TableView<CartItem> tableCart = new TableView<>();        // Tabel Keranjang
    private TableView<Product> tableAdminProduct = new TableView<>(); // Tabel Admin
    
    private Label lblTotalCart = new Label("Rp 0");
    
    // Komponen Pembayaran
    private ComboBox<String> cmbPaymentMethod;
    private TextField txtCashAmount;
    private TextField txtEwalletID;
    private VBox paymentInputContainer;
    
    // Komponen Admin Form
    private TextField txtCodeAdmin, txtNameAdmin, txtCategoryAdmin, txtPriceAdmin, txtStockAdmin;
    private Product selectedProduct; 
    
    private Stage mainStage;

    @Override
    public void start(Stage stage) {
        this.mainStage = stage;
        BorderPane root = new BorderPane();

        // Header
        root.setTop(createHeader());

        // Content (Cek Role)
        if (posController.isAdmin()) {
            root.setCenter(createAdminLayout());
        } else {
            root.setCenter(createCashierLayout());
        }

        Scene scene = new Scene(root, 1150, 700);
        stage.setTitle("Agri-POS System - " + (posController.getCurrentUser() != null ? posController.getCurrentUser().getRole() : "Guest"));
        stage.setScene(scene);
        stage.show();
        
        loadProducts();
    }

    private HBox createHeader() {
        HBox header = new HBox();
        header.setPadding(new Insets(15));
        header.setStyle("-fx-background-color: #2E7D32;"); 
        header.setAlignment(Pos.CENTER_LEFT);

        Label lblTitle = new Label("Agri-POS");
        lblTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        lblTitle.setStyle("-fx-text-fill: white;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        User user = posController.getCurrentUser();
        Label lblUser = new Label("User: " + (user != null ? user.getName() : "Guest"));
        lblUser.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
        
        Button btnLogout = new Button("Logout");
        btnLogout.setStyle("-fx-background-color: #c62828; -fx-text-fill: white; -fx-cursor: hand;");
        btnLogout.setOnAction(e -> handleLogout());

        header.getChildren().addAll(lblTitle, spacer, lblUser, new Label("   "), btnLogout);
        return header;
    }

    private void handleLogout() {
        mainStage.close();
        try { new LoginView().start(new Stage()); } catch (Exception e) { e.printStackTrace(); }
    }

    // ==========================================
    // LAYOUT KASIR
    // ==========================================
    private SplitPane createCashierLayout() {
        // --- KIRI: DAFTAR PRODUK & PENCARIAN ---
        VBox leftSide = new VBox(10);
        leftSide.setPadding(new Insets(10));
        leftSide.getChildren().add(new Label("DAFTAR PRODUK"));
        
        FilteredList<Product> filteredData = new FilteredList<>(productData, p -> true);
        
        HBox searchBox = new HBox(10);
        searchBox.setAlignment(Pos.CENTER_LEFT);
        TextField txtSearch = new TextField();
        txtSearch.setPromptText("Cari Kode / Nama Produk...");
        txtSearch.setPrefWidth(250);
        
        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(product -> {
                if (newValue == null || newValue.isEmpty()) return true;
                String lowerVal = newValue.toLowerCase();
                return product.getName().toLowerCase().contains(lowerVal) || 
                       product.getCode().toLowerCase().contains(lowerVal);
            });
        });
        
        Button btnReset = new Button("Reset");
        btnReset.setOnAction(e -> txtSearch.clear());
        searchBox.getChildren().addAll(new Label("Cari:"), txtSearch, btnReset);

        setupProductTable(tableProduct);
        
        SortedList<Product> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableProduct.comparatorProperty());
        tableProduct.setItems(sortedData);
        
        VBox.setVgrow(tableProduct, Priority.ALWAYS);
        
        HBox actionBox = new HBox(10);
        actionBox.setAlignment(Pos.CENTER_LEFT);
        TextField txtQty = new TextField("1");
        txtQty.setPrefWidth(60);
        Button btnAdd = new Button("Tambah ke Keranjang (+)");
        btnAdd.setStyle("-fx-background-color: #2E7D32; -fx-text-fill: white; -fx-cursor: hand;");
        
        btnAdd.setOnAction(e -> {
            Product p = tableProduct.getSelectionModel().getSelectedItem();
            if (p != null) {
                try {
                    int qty = Integer.parseInt(txtQty.getText());
                    posController.addToCart(p, qty);
                    refreshCartTable();
                } catch (Exception ex) { showAlert("Error", ex.getMessage()); }
            } else {
                showAlert("Info", "Pilih produk dulu!");
            }
        });
        
        actionBox.getChildren().addAll(new Label("Qty:"), txtQty, btnAdd);
        leftSide.getChildren().addAll(searchBox, tableProduct, actionBox);

        // --- KANAN: KERANJANG & PEMBAYARAN ---
        VBox rightSide = new VBox(10);
        rightSide.setPadding(new Insets(15));
        rightSide.setStyle("-fx-background-color: #f4f4f4; -fx-border-color: #ddd; -fx-border-width: 0 0 0 1;");
        
        Label lblTitleCart = new Label("KERANJANG BELANJA");
        lblTitleCart.setFont(Font.font("System", FontWeight.BOLD, 14));
        
        setupCartTable();
        tableCart.setPrefHeight(250);
        
        HBox cartActions = new HBox(10);
        Button btnRemove = new Button("Hapus Item");
        btnRemove.setOnAction(e -> {
            CartItem item = tableCart.getSelectionModel().getSelectedItem();
            if(item != null) { posController.removeFromCart(item.getProduct().getCode()); refreshCartTable(); }
        });
        Button btnClear = new Button("Kosongkan Cart");
        btnClear.setStyle("-fx-text-fill: red;");
        btnClear.setOnAction(e -> { posController.clearCart(); refreshCartTable(); });
        cartActions.getChildren().addAll(btnRemove, btnClear);
        
        // AREA PEMBAYARAN
        VBox paymentBox = new VBox(10);
        paymentBox.setPadding(new Insets(15));
        paymentBox.setStyle("-fx-background-color: white; -fx-background-radius: 5; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 1);");
        
        Label lblPayTitle = new Label("METODE PEMBAYARAN");
        lblPayTitle.setFont(Font.font("System", FontWeight.BOLD, 12));
        
        cmbPaymentMethod = new ComboBox<>();
        cmbPaymentMethod.getItems().addAll("Tunai (Cash)", "E-Wallet (QRIS/Transfer)");
        cmbPaymentMethod.setValue("Tunai (Cash)");
        cmbPaymentMethod.setMaxWidth(Double.MAX_VALUE);
        
        paymentInputContainer = new VBox(5);
        txtCashAmount = new TextField(); 
        txtCashAmount.setPromptText("Nominal Uang (Rp)");
        txtEwalletID = new TextField(); 
        txtEwalletID.setPromptText("Nomor HP / ID Wallet");
        
        cmbPaymentMethod.setOnAction(e -> updatePaymentInputVisibility());
        updatePaymentInputVisibility(); 

        HBox totalBox = new HBox(10);
        totalBox.setAlignment(Pos.CENTER_RIGHT);
        Label lblTotalTitle = new Label("TOTAL TAGIHAN:");
        lblTotalCart.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        lblTotalCart.setStyle("-fx-text-fill: #2E7D32;");
        totalBox.getChildren().addAll(lblTotalTitle, lblTotalCart);
        
        Button btnCheckout = new Button("PROSES PEMBAYARAN");
        btnCheckout.setMaxWidth(Double.MAX_VALUE);
        btnCheckout.setPrefHeight(45);
        btnCheckout.setStyle("-fx-background-color: #1565C0; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px; -fx-cursor: hand;");
        btnCheckout.setOnAction(e -> handleCheckoutIntegrated());

        paymentBox.getChildren().addAll(lblPayTitle, cmbPaymentMethod, paymentInputContainer, new Separator(), totalBox, btnCheckout);
        
        rightSide.getChildren().addAll(lblTitleCart, tableCart, cartActions, new Region(){{setPrefHeight(10);}}, paymentBox);
        VBox.setVgrow(rightSide, Priority.ALWAYS);

        SplitPane split = new SplitPane();
        split.getItems().addAll(leftSide, rightSide);
        split.setDividerPositions(0.60); 
        return split;
    }

    private void updatePaymentInputVisibility() {
        paymentInputContainer.getChildren().clear();
        String selected = cmbPaymentMethod.getValue();
        if (selected != null && selected.contains("Tunai")) {
            paymentInputContainer.getChildren().addAll(new Label("Nominal Uang:"), txtCashAmount);
        } else {
            paymentInputContainer.getChildren().addAll(new Label("ID Pelanggan / No. HP:"), txtEwalletID);
        }
    }

    private void handleCheckoutIntegrated() {
        if(posController.getCartItems().isEmpty()) {
            showAlert("Warning", "Keranjang kosong! Masukkan produk dulu.");
            return;
        }
        try {
            PaymentMethod method;
            String selected = cmbPaymentMethod.getValue();
            BigDecimal totalTagihan = posController.getCartTotal();

            if (selected.contains("Tunai")) {
                String cashStr = txtCashAmount.getText().trim();
                if(cashStr.isEmpty()) throw new Exception("Masukkan nominal uang!");
                BigDecimal uangBayar = new BigDecimal(cashStr);
                
                if(uangBayar.compareTo(totalTagihan) < 0) throw new Exception("Uang Kurang!");
                method = new CashPayment(uangBayar);
            } else {
                String walletID = txtEwalletID.getText().trim();
                if(walletID.isEmpty()) throw new Exception("Masukkan Nomor HP/ID Wallet!");
                method = new EWalletPayment("QRIS/Wallet", walletID);
            }

            Transaction t = posController.checkout(method);
            showReceiptDialog(t);
            
            refreshCartTable();
            txtCashAmount.clear();
            txtEwalletID.clear();
            loadProducts(); 

        } catch (NumberFormatException e) {
            showAlert("Input Error", "Nominal harus berupa angka valid!");
        } catch (Exception ex) {
            showAlert("Gagal", ex.getMessage());
        }
    }

    // ==========================================
    // LAYOUT ADMIN
    // ==========================================
    private TabPane createAdminLayout() {
        TabPane tabs = new TabPane();
        Tab tabProduk = new Tab("Manajemen Produk", createAdminProductView());
        tabProduk.setClosable(false);
        Tab tabLaporan = new Tab("Laporan Penjualan", createAdminReportView());
        tabLaporan.setClosable(false);
        tabs.getTabs().addAll(tabProduk, tabLaporan);
        return tabs;
    }

    private VBox createAdminProductView() {
        VBox layout = new VBox(10); layout.setPadding(new Insets(10));
        
        GridPane form = new GridPane(); form.setHgap(10); form.setVgap(10);
        form.setStyle("-fx-background-color: #f9f9f9; -fx-padding: 10; -fx-background-radius: 5;");
        
        txtCodeAdmin = new TextField(); txtCodeAdmin.setPromptText("Kode");
        txtNameAdmin = new TextField(); txtNameAdmin.setPromptText("Nama");
        txtPriceAdmin = new TextField(); txtPriceAdmin.setPromptText("Harga");
        txtStockAdmin = new TextField(); txtStockAdmin.setPromptText("Stok");
        
        // --- [PERBAIKAN] INISIALISASI TXT KATEGORI ---
        txtCategoryAdmin = new TextField(); txtCategoryAdmin.setPromptText("Kategori");
        // ---------------------------------------------
        
        form.addRow(0, new Label("Kode:"), txtCodeAdmin, new Label("Nama:"), txtNameAdmin);
        form.addRow(1, new Label("Harga:"), txtPriceAdmin, new Label("Stok:"), txtStockAdmin);
        
        // --- [PERBAIKAN] MENAMBAHKAN KE LAYOUT GRID ---
        form.addRow(2, new Label("Kategori:"), txtCategoryAdmin);
        // ----------------------------------------------

        HBox buttons = new HBox(10);
        Button btnSave = new Button("Simpan Baru");
        Button btnUpdate = new Button("Update");
        Button btnDelete = new Button("Hapus");
        Button btnClear = new Button("Clear Form");
        
        btnSave.setOnAction(e -> handleAdminAdd());
        btnUpdate.setOnAction(e -> handleAdminUpdate());
        btnDelete.setOnAction(e -> handleAdminDelete());
        btnClear.setOnAction(e -> clearAdminForm());
        
        buttons.getChildren().addAll(btnSave, btnUpdate, btnDelete, btnClear);
        form.add(buttons, 0, 3, 4, 1);

        setupProductTable(tableAdminProduct);
        tableAdminProduct.setItems(productData); 
        
        tableAdminProduct.setOnMouseClicked(e -> {
            Product p = tableAdminProduct.getSelectionModel().getSelectedItem();
            if(p!=null) {
                selectedProduct = p;
                txtCodeAdmin.setText(p.getCode());
                txtCodeAdmin.setDisable(true);
                txtNameAdmin.setText(p.getName());
                
                // --- [PERBAIKAN] MENGISI FIELD KATEGORI SAAT KLIK ---
                txtCategoryAdmin.setText(p.getCategory());
                // ----------------------------------------------------
                
                txtPriceAdmin.setText(p.getPrice().toString());
                txtStockAdmin.setText(String.valueOf(p.getStock()));
            }
        });
        
        layout.getChildren().addAll(new Label("Form Produk"), form, new Separator(), tableAdminProduct);
        VBox.setVgrow(tableAdminProduct, Priority.ALWAYS);
        return layout;
    }
    
    private void handleAdminAdd() {
        try {
            posController.saveProduct(
                txtCodeAdmin.getText(), txtNameAdmin.getText(), 
                txtCategoryAdmin.getText().isEmpty() ? "Umum" : txtCategoryAdmin.getText(), 
                new BigDecimal(txtPriceAdmin.getText()), Integer.parseInt(txtStockAdmin.getText()), false
            );
            loadProducts(); clearAdminForm(); showAlert("Sukses","Tersimpan");
        } catch(Exception ex){ showAlert("Err", ex.getMessage());}
    }

    private void handleAdminUpdate() {
        if(selectedProduct == null) return;
        try {
            posController.saveProduct(
                txtCodeAdmin.getText(), txtNameAdmin.getText(), txtCategoryAdmin.getText(), 
                new BigDecimal(txtPriceAdmin.getText()), Integer.parseInt(txtStockAdmin.getText()), true
            );
            loadProducts(); clearAdminForm(); showAlert("Sukses","Diupdate");
        } catch(Exception ex){ showAlert("Err", ex.getMessage());}
    }

    private void handleAdminDelete() {
        if(selectedProduct == null) return;
        try {
            posController.deleteProduct(selectedProduct.getCode());
            loadProducts(); clearAdminForm(); showAlert("Sukses","Dihapus");
        } catch(Exception ex){ showAlert("Err", ex.getMessage());}
    }

    private void clearAdminForm() {
        txtCodeAdmin.clear(); txtCodeAdmin.setDisable(false);
        txtNameAdmin.clear(); 
        
        // --- [PERBAIKAN] CLEAR FIELD KATEGORI ---
        if(txtCategoryAdmin != null) txtCategoryAdmin.clear();
        // ----------------------------------------
        
        txtPriceAdmin.clear(); txtStockAdmin.clear();
        selectedProduct = null;
    }
    
    private VBox createAdminReportView() {
        VBox layout = new VBox(10); layout.setPadding(new Insets(10));
        DatePicker dp = new DatePicker(LocalDate.now());
        Button btnL = new Button("Load Laporan");
        TextArea ta = new TextArea(); ta.setEditable(false); ta.setFont(Font.font("Monospaced", 12));
        
        btnL.setOnAction(e -> {
            try {
                var list = reportController.getDailySalesReport(dp.getValue());
                BigDecimal rev = reportController.calculateRevenue(list);
                StringBuilder sb = new StringBuilder("LAPORAN TGL: " + dp.getValue() + "\n--------------------------------\n");
                for(Transaction t : list) sb.append(String.format("%s | Rp %,.0f | %s\n", t.getId(), t.getTotalAmount(), t.getStatus()));
                sb.append("--------------------------------\nTOTAL OMSET: Rp ").append(String.format("%,.0f", rev));
                ta.setText(sb.toString());
            } catch(Exception ex) { showAlert("Err", ex.getMessage()); }
        });
        
        layout.getChildren().addAll(new Label("Laporan Harian"), new HBox(10, dp, btnL), ta);
        VBox.setVgrow(ta, Priority.ALWAYS);
        return layout;
    }

    // ==========================================
    // HELPERS
    // ==========================================
    
    private void loadProducts() {
        try {
            productData.setAll(posController.getAllProducts());
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error Load Data", e.getMessage());
        }
    }

    private void setupProductTable(TableView<Product> table) {
        table.getColumns().clear();
        TableColumn<Product, String> c1 = new TableColumn<>("Kode"); c1.setCellValueFactory(new PropertyValueFactory<>("code"));
        TableColumn<Product, String> c2 = new TableColumn<>("Nama"); c2.setCellValueFactory(new PropertyValueFactory<>("name")); c2.setPrefWidth(150);
        
        // --- [PERBAIKAN] MENAMBAHKAN KOLOM KATEGORI DI TABEL ---
        TableColumn<Product, String> cCat = new TableColumn<>("Kategori"); cCat.setCellValueFactory(new PropertyValueFactory<>("category"));
        // -------------------------------------------------------
        
        TableColumn<Product, BigDecimal> c3 = new TableColumn<>("Harga"); c3.setCellValueFactory(new PropertyValueFactory<>("price"));
        TableColumn<Product, Integer> c4 = new TableColumn<>("Stok"); c4.setCellValueFactory(new PropertyValueFactory<>("stock"));
        table.getColumns().addAll(c1,c2,cCat,c3,c4);
    }
    
    private void setupCartTable() {
        tableCart.getColumns().clear();
        TableColumn<CartItem, String> c1 = new TableColumn<>("Item"); 
        c1.setCellValueFactory(d->new javafx.beans.property.SimpleStringProperty(d.getValue().getProduct().getName()));
        TableColumn<CartItem, Integer> c2 = new TableColumn<>("Qty"); 
        c2.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        TableColumn<CartItem, BigDecimal> c3 = new TableColumn<>("Subtotal"); 
        c3.setCellValueFactory(new PropertyValueFactory<>("subtotal"));
        tableCart.getColumns().addAll(c1,c2,c3);
        tableCart.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    private void refreshCartTable() {
        tableCart.setItems(FXCollections.observableArrayList(posController.getCartItems()));
        lblTotalCart.setText("Rp " + String.format("%,.0f", posController.getCartTotal()));
    }

    private void showReceiptDialog(Transaction t) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Struk Belanja");
        alert.setHeaderText("Transaksi Berhasil!");
        TextArea area = new TextArea(posController.generateReceipt(t).formatReceipt());
        area.setEditable(false); area.setFont(Font.font("Monospaced", 12));
        alert.getDialogPane().setContent(area);
        alert.showAndWait();
    }

    private void showAlert(String title, String msg) {
        new Alert(Alert.AlertType.INFORMATION, msg).show();
    }
}