package com.example.ecommerce;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

public class ProductList {

    public static TableView<Product> productTable;

    public static void removeRow() {
        Product pro = productTable.getSelectionModel().getSelectedItem();
        productTable.getItems().remove(pro);
    }

    public Pane getAllProducts(){
        ObservableList<Product> productList = Product.getAllProducts();

        return createTableFromList(productList);
    }

    public Pane getProductsByName(String search){

        String query = "select * from products where name like '%" + search + "%'";
        ObservableList<Product> productList = Product.getProducts(query);

        return createTableFromList(productList);
    }

    public Pane getorderedlist(Customer loggedInCustomer){
        TableColumn id = new TableColumn("Item");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn name = new TableColumn("Laptop");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn price = new TableColumn("Price");
        price.setCellValueFactory(new PropertyValueFactory<>("price"));

        String query = "select * from orders where cid = '"+ loggedInCustomer.getId() + "'";
        ObservableList<Product> productsList = Product.getOrders(query);
        productTable = new TableView<>();
        productTable.setItems(productsList);
        productTable.getColumns().addAll(id, name, price);

        Pane tablePane = new Pane();
        tablePane.getChildren().add(productTable);

        return tablePane;
    }

    public Pane createTableFromList(ObservableList<Product> productList){
        TableColumn id = new TableColumn("ID");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn name = new TableColumn("Product Name");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn price = new TableColumn("Product Price");
        price.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn quantity = new TableColumn("Product quantity");
        quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

//        ObservableList<Product> data = FXCollections.observableArrayList();
//      data.addAll(new Product(123, "Laptop", 2345.8),
//      new Product(1234, "Laptop", 235.5));

        productTable = new TableView<>();
        productTable.setItems(productList);
        productTable.setTranslateY(50);
        productTable.getColumns().addAll(id, name, price, quantity);

        Pane tablePane = new Pane();
        tablePane.getChildren().add(productTable);
        tablePane.setPrefSize(200, 500);

        return tablePane;
    }

    public Pane productsInCart(ObservableList<Product> productList){
        return createTableFromList(productList);
    }

    public Product getSelectedProduct(){
        //getting selected item
        return productTable.getSelectionModel().getSelectedItem();
    }
}