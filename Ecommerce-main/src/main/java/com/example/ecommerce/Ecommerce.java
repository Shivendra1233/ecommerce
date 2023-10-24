package com.example.ecommerce;


import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Ecommerce extends Application {
    private final int width = 600, height = 600, headerLine = 50;

    Pane root;
    ProductList ProductList = new ProductList();
    Pane bodyPane;

    Order order  = new Order();

    ObservableList<Product> cartItemList = FXCollections.observableArrayList();

    Button back = new Button("Back");
    Button signUpButton = new Button("Sign Up!");;
    Button signInButton = new Button("Sign In");
    Button signOutButton = new Button("Sign Out");
    Button placeOrderButton = new Button("Place Order");
    Button buyNowButton = new Button("Buy Now");
    Button removeButton = new Button("Remove");
    Button closeButton = new Button("Exit");
    Button cartButton = new Button("Cart");
    Button getPlacedOrderButton = new Button("Your Orders");
    Label welcomeLabel = new Label("Welcome Customer");

    boolean signedUpCustomer = false;

    Customer loggedInCustomer = null;

    private void addItemsToCart(Product product){
        if(cartItemList.contains(product))
            return;
        cartItemList.add(product);
    }

    private GridPane headerBar(Button button1, Button button2, Button button3){

        TextField searchBar = new TextField();
        searchBar.setPromptText("Search Cycle");
        Button searchButton = new Button("Search");

        searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                // all products to show for now later will change
                bodyPane.getChildren().clear();
                // only searched products to show
                bodyPane.getChildren().add(ProductList.getProductsByName(searchBar.getText()));
                root.getChildren().clear();
                if (loggedInCustomer == null)
                    root.getChildren().addAll(headerBar(signInButton, signUpButton,closeButton), bodyPane, footerBar());
                else root.getChildren().addAll(headerBar(signOutButton, cartButton, getPlacedOrderButton), bodyPane, footerBar());
            }
        });

        signUpButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                bodyPane.getChildren().clear();
                bodyPane.getChildren().add(signUpPage());
            }
        });

        signInButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                bodyPane.getChildren().clear(); //signIn button will disappear
                bodyPane.getChildren().add(loginPage());
                root.getChildren().clear();
                root.getChildren().addAll(headerBar(closeButton, signUpButton, cartButton), bodyPane);
            }
        });

        signOutButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                bodyPane.getChildren().clear();
                bodyPane.getChildren().add(loginPage());
                root.getChildren().clear();
                root.getChildren().addAll(headerBar(signInButton,closeButton,placeOrderButton), bodyPane);
            }
        });

        closeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                handleCloseButtonAction();
            }
        });

        cartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                bodyPane.getChildren().clear();
                bodyPane.getChildren().add(ProductList.productsInCart(cartItemList));
            }
        });

        getPlacedOrderButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                bodyPane.getChildren().clear();
                bodyPane.getChildren().add(order.getOrderedProducts(loggedInCustomer));
            }
        });

        GridPane header = new GridPane();
        header.setHgap(10);

        header.setTranslateX(5);
        header.setTranslateY(5);
        header.add(welcomeLabel, 0, 0);
        header.add(searchBar, 1, 0);
        header.add(searchButton, 2, 0);
        header.add(button1, 3, 0);
        header.add(button2, 4, 0);
        header.add(button3, 5, 0);
//        header.add(signInButton, 3, 0);
//        header.add(signUpButton, 4, 0);

        return header;
    }

//    private GridPane headerBarAfterLogin(){
//
//        //bodyPane = new Pane();
//        searchBar = new TextField();
//        searchBar.setPromptText("Search Cycle");
//        Button searchButton = new Button("Search");
//        Button cartButton = new Button("Cart");
//        signOutButton = new Button("Sign Out");
//
//        Button ordersButton = new Button("Orders");
//
//        ordersButton.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent actionEvent) {
//                bodyPane.getChildren().clear();
//                bodyPane.getChildren().add(order.getOrders(searchBar.getText()));
//            }
//        });
//
//        searchButton.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent actionEvent) {
//                bodyPane.getChildren().clear();
//                bodyPane.getChildren().add(ProductList.getAllProducts(searchBar.getText()));
//            }
//        });
//
//        signOutButton.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent actionEvent) {
////                bodyPane.getChildren().clear();
////                bodyPane.getChildren().add(loginPage());
//
//            }
//        });
//
//        cartButton.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent actionEvent) {
//                bodyPane.getChildren().clear();
//                bodyPane.getChildren().add(ProductList.productsInCart(cartItemList));
//            }
//        });
//
//        GridPane newHeader = new GridPane();
//        newHeader.setHgap(10);
//
//        newHeader.setTranslateX(5);
//        newHeader.setTranslateY(5);
//        newHeader.add(welcomeLabel, 0, 0);
//        newHeader.add(searchBar, 1, 0);
//        newHeader.add(searchButton, 2, 0);
//        newHeader.add(signOutButton, 3, 0);
//        newHeader.add(cartButton, 4, 0);
//        newHeader.add(ordersButton, 5, 0);
//
//        return newHeader;
//    }

    private GridPane signUpPage(){
        Label userLabel1 = new Label("Name");
        userLabel1.setPrefWidth(500);
        Label userLabel2 = new Label("Email");
        Label userLabel3 = new Label("Phone Number");
        Label passLabel = new Label("Password");
        Label userLabel4 = new Label("Address");

        TextField userName = new TextField();
        userName.setPromptText("Enter Your Name");

        TextField userEmail = new TextField();
        userEmail.setPromptText("Enter Your Email");

        TextField mobileNumber = new TextField();
        mobileNumber.setPromptText("Enter Your Phone Number");

        PasswordField password = new PasswordField();
        password.setPromptText("Enter Your Password");

        TextField userAddress = new TextField();
        userAddress.setPromptText("Enter Your Address");

        Button signUpButton2 = new Button("Sign Up");
        Label messageLabel = new Label("Message");

        signUpButton2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String user = userName.getText();
                String email = userEmail.getText();
                String mobile = mobileNumber.getText();
                String pass = password.getText();
                String address = userAddress.getText();

                signedUpCustomer = Signup.customerSignUp(user, email, mobile, pass, address);
                if(signedUpCustomer != false){
                    messageLabel.setText("Registration Successful!!");
                    welcomeLabel.setText("Welcome " + user);
                    bodyPane.getChildren().clear();
                    bodyPane.getChildren().add(ProductList.getAllProducts());
                    root.getChildren().clear();
                    root.getChildren().addAll(headerBar(signInButton, cartButton, closeButton), welcomeLabel, bodyPane, footerBar());

                } else{
                    messageLabel.setText("Please enter all the fields!!");
                }
            }
        });

//        FileInputStream imageStream = null;
//        try {
//            imageStream = new FileInputStream("/Users/ankush/Desktop/new cycle 1.jpeg");
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//        Image image = new Image(imageStream, 475, 50, false, false);


        GridPane signUpPane = new GridPane();
        signUpPane.setTranslateX(50);
        signUpPane.setTranslateY(50);
        signUpPane.setVgap(10);
        signUpPane.setHgap(10);
//        signUpPane.add(new ImageView(image), 0, 0);
        signUpPane.add(userLabel1, 0, 1);
        signUpPane.add(userName, 0, 2);
        signUpPane.add(userLabel2, 0, 3);
        signUpPane.add(userEmail, 0, 4);
        signUpPane.add(userLabel3, 0, 5);
        signUpPane.add(mobileNumber, 0, 6);
        signUpPane.add(passLabel, 0, 7);
        signUpPane.add(password, 0, 8);
        signUpPane.add(userLabel4, 0, 9);
        signUpPane.add(userAddress, 0, 10);
        signUpPane.add(signUpButton2, 0, 11);
        signUpPane.add(messageLabel, 0,12);
        //signUpPane.setPrefSize(500, 350);
        //signUpPane.setStyle("-fx-background-image: url('newCycle.jpeg');");

        return signUpPane;
    }

    private GridPane loginPage(){

        //bodyPane = new Pane();
//        FileInputStream imageStream = null;
//        try {
//            imageStream = new FileInputStream("/Users/ankush/Desktop/new cycle 1.jpeg");
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//        Image image = new Image(imageStream, 500, 350, false, false);

        Label userLabel = new Label("User Name");
        Label passLabel = new Label("Password");
        TextField userName = new TextField();
        //userName.setText("ankushnitjsr@gmail.com");
        userName.setPrefWidth(300);
        userName.setPromptText("Enter Your email");
        PasswordField password = new PasswordField();
        //password.setText("Ankush@123");
        password.setPromptText("Enter Your Password");
        Button loginButton = new Button("Login");
        Label messageLabel = new Label("Login - Not done!!");

        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String user = userName.getText();
                String pass = password.getText();
                loggedInCustomer = Login.customerLogin(user, pass);
                if(loggedInCustomer != null){
                    messageLabel.setText("Login Successful!!");
                    welcomeLabel.setText("Welcome back " + loggedInCustomer.getName());

                    bodyPane.getChildren().clear();
                    bodyPane.getChildren().add(ProductList.getAllProducts());

                    root.getChildren().clear();
                    root.getChildren().addAll(headerBar(signOutButton, cartButton, getPlacedOrderButton), bodyPane, footerBar());
                } else{
                    messageLabel.setText("Login Failed!!");
                }
            }
        });

        GridPane loginPane = new GridPane();
        //loginPane.setTranslateY(250);
        loginPane.setTranslateX(35);
        loginPane.setVgap(2); //vertical gap in pixels
        loginPane.setHgap(0); //horizontal gap in pixels
//        loginPane.add(new ImageView(image), 0, 1);
        loginPane.add(userLabel, 0, 10);
        loginPane.add(userName, 0, 11);
        loginPane.add(passLabel, 0, 16);
        loginPane.add(password, 0, 17);
        loginPane.add(loginButton, 0, 21);
        loginPane.add(messageLabel, 0,22);
        //loginPane.setStyle("-fx-background-color: brown;");

        return loginPane;
    }

    private void showDialogue(String message){
        Dialog<String> dialog = new Dialog<String>();
        //Setting the title
        dialog.setTitle("Order Status");
        ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        //Setting the content of the dialog
        dialog.setContentText(message);
        //Adding buttons to the dialog pane
        dialog.getDialogPane().getButtonTypes().add(type);


        dialog.showAndWait();

    }

    private GridPane footerBar(){
        buyNowButton = new Button("Buy Now");
        Button addToCartButton = new Button("Add to Cart");
        placeOrderButton = new Button("Place Order");

        buyNowButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Product product = ProductList.getSelectedProduct();
                boolean orderStatus = false;
                if(product != null && loggedInCustomer != null){
                    orderStatus = order.placeOrder(loggedInCustomer, product);
                    order.getOrderedProducts(loggedInCustomer);
                }
                if(orderStatus == true){
                    showDialogue("Order Successful");
                } else if(orderStatus = false){
                    showDialogue("Order can't be placed, please Sign in!!");
                }
            }
        });

        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                buyNowButton.setVisible(true);
                bodyPane.getChildren().clear();
                bodyPane.getChildren().addAll(ProductList.getAllProducts());
                root.getChildren().clear();
                root.getChildren().addAll(headerBar(signOutButton, cartButton, placeOrderButton), bodyPane, footerBar());
            }
        });

        removeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    Product product = ProductList.getSelectedProduct();
                    boolean orderStatus = false;
                    orderStatus = order.removeOrder(loggedInCustomer, product);
                    ProductList.removeRow();
                    if (orderStatus) {
                        showDialogue("Order Deleted!!!!");
                    }
                    else {
                        showDialogue("No product in cart to remove!!");
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        addToCartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Product product = ProductList.getSelectedProduct();
                addItemsToCart(product);
                System.out.println("Products in cart" + cartItemList.stream().count());
            }
        });

        placeOrderButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                int orderCount = 0;
                if(!cartItemList.isEmpty() && loggedInCustomer != null){
                    orderCount = order.placeOrderMultipleProducts(cartItemList, loggedInCustomer);
                }
                if(orderCount > 0){
                    //
                    showDialogue("Order for " + orderCount + " products placed Successfully");

                } else{
                    showDialogue("Please add products to cart");
                }
            }
        });

        GridPane footer = new GridPane();
        footer.setHgap(10);
        footer.setTranslateY(headerLine+height);
        footer.add(buyNowButton, 0, 0);
        footer.add(addToCartButton, 1, 0);
        footer.add(placeOrderButton, 2, 0);
        footer.add(removeButton, 3, 0);

        return footer;
    }

    private Pane createContent(){
        root = new Pane();
        root.setPrefSize(width, height + 2 * headerLine);

        bodyPane = new Pane();
        bodyPane.setPrefSize(width, height);
        bodyPane.setTranslateX(10);
        bodyPane.setTranslateY(headerLine);

        bodyPane.getChildren().add(loginPage());

        root.getChildren().addAll(headerBar(signInButton, signUpButton, closeButton),bodyPane);
        root.setStyle("-fx-background-color: white;");
        return root;
    }
    @Override
    public void start(Stage stage) throws IOException {
        //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(createContent());
        stage.setTitle("Ecommerce");
        stage.setScene(scene);
        stage.setResizable(false);

        stage.show();
    }

    @FXML
    public void handleCloseButtonAction() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
    public static void main(String[] args) {
        launch();
    }
}