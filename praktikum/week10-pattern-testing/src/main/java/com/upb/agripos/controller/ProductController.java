package main.java.com.upb.agripos.controller;

import main.java.com.upb.agripos.model.Product;
import main.java.com.upb.agripos.view.ConsoleView;

public class ProductController {
    private final Product model;
    private final ConsoleView view;

    // Constructor Injection
    public ProductController(Product model, ConsoleView view) {
        this.model = model;
        this.view = view;
    }

    public void updateView() {
        view.displayProductDetails(model.getCode(), model.getName());
    }

    public void showCustomMessage(String msg) {
        view.showMessage(msg);
    }
}