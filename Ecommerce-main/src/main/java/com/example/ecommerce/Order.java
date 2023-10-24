package com.example.ecommerce;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

public class Order {

    TableView<Product> orderTable;
    public boolean placeOrder(Customer customer, Product product){
        try {
            //insert into orders(customer_id, product_id, status) values(1,1,'Ordered')
            String placeOrder = "insert into orders(customer_id, product_id, status) values(" + customer.getId() + "," + product.getId() + ",'Ordered')";
            DatabaseConnection dbConn = new DatabaseConnection();
            return dbConn.insertUpdate(placeOrder);
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public int placeOrderMultipleProducts(ObservableList<Product> productObservableList, Customer customer){
        int count = 0;
        for(Product product : productObservableList){
            if(placeOrder(customer, product))
                count++;
        }
        return count;
    }

    public boolean removeOrder(Customer customer, Product product){

        try {
            String MyOrder="Delete from orders where product_id=" + product.getId() + " and customer_id=" +customer.getId();
            System.out.println(product.getId()+ " " + customer.getId());
            DatabaseConnection dbConn = new DatabaseConnection();
            return dbConn.insertUpdate(MyOrder);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public Pane createTableFromList(ObservableList<Product> orderList){
        TableColumn id = new TableColumn("Id");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn name = new TableColumn("Product Name");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn price = new TableColumn("Product Price");
        price.setCellValueFactory(new PropertyValueFactory<>("price"));

        orderTable = new TableView<>();
        orderTable.setItems(orderList);
        orderTable.getColumns().addAll(id, name, price);

        Pane tablePane = new Pane();
        tablePane.getChildren().add(orderTable);

        return tablePane;
    }

    public Pane getOrderedProducts(Customer customer){
        String order = "select orders.oid, products.name, products.price from orders inner join products on orders.product_id = products.pid where customer_id = " + customer.getId();
        ObservableList<Product> orderList = Product.getOrderedProducts(order);

        return createTableFromList(orderList);
    }
}