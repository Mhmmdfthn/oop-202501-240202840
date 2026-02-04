package com.upb.agripos;

import com.upb.agripos.controller.AuthController;
import com.upb.agripos.controller.PosController;
import com.upb.agripos.controller.ReportController;
import com.upb.agripos.dao.JdbcProductDAO;
import com.upb.agripos.dao.JdbcTransactionDAO;
import com.upb.agripos.dao.JdbcUserDAO;
import com.upb.agripos.dao.ProductDAO;
import com.upb.agripos.dao.TransactionDAO;
import com.upb.agripos.dao.UserDAO;
import com.upb.agripos.service.AuthService;
import com.upb.agripos.service.CartService;
import com.upb.agripos.service.ProductService;
import com.upb.agripos.service.ReportService;
import com.upb.agripos.service.TransactionService;
import com.upb.agripos.view.LoginView;

import javafx.application.Application;
import javafx.stage.Stage;

public class AppJavaFX extends Application {
    private static PosController posController;
    private static AuthController authController;
    private static ReportController reportController;

    @Override
    public void start(Stage stage) {
        try {
            // 1. DAO
            ProductDAO pDao = new JdbcProductDAO();
            UserDAO uDao = new JdbcUserDAO();
            TransactionDAO tDao = new JdbcTransactionDAO();

            // 2. Service
            AuthService aService = new AuthService(uDao);
            ProductService pService = new ProductService(pDao);
            CartService cService = new CartService();
            TransactionService tService = new TransactionService(tDao, pService);
            ReportService rService = new ReportService(tDao);

            // 3. Controller
            authController = new AuthController(aService);
            reportController = new ReportController(rService);
            posController = new PosController(pService, cService, tService, aService, rService);

            // 4. Start
            new LoginView().start(stage);

        } catch (Exception e) { e.printStackTrace(); }
    }

    public static PosController getPosController() { return posController; }
    public static AuthController getAuthController() { return authController; }
    public static ReportController getReportController() { return reportController; }

    public static void main(String[] args) { launch(args); }
}