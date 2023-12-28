import java.util.Scanner;
import java.lang.System;
import java.sql.*;
import java.lang.String;



public class Main {

    public static int menuchoice;
    public static String code;
    public static String title;
    public static float price;
    public static String description;
    public static String category;
    public static int previd;

    public static void main(String args[])
    {
        DBManager dbmanage = new DBManager();
        menu();
        while(true) {                                        //Menu functions
            if (menuchoice == 1) {                           //View all products
                String url = "jdbc:sqlite:database.sqlite";
                try (Connection conn = DriverManager.getConnection(url);
                     Statement stmt = conn.createStatement()){
                    if (conn != null) {
                        DatabaseMetaData meta = conn.getMetaData();
                    }
                    String sql = "SELECT DISTINCT title FROM Products";
                    ResultSet rs = stmt.executeQuery(sql);
                    while (rs.next())
                    {
                        if (!rs.getString(1).equals("initializing product")) {
                            System.out.println(rs.getString(1));
                        }
                    }
                }
                catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
                System.out.println("\nGive any input to continue: ");
                Scanner cont = new Scanner(System.in);
                String temp = cont.nextLine();
                menu();
            } else if (menuchoice == 2) {                                     //Add 1 product
                System.out.println("Enter the code of the product: ");
                Scanner input = new Scanner(System.in);
                code = input.nextLine();

                System.out.println("Enter the name of the product: ");
                Scanner input2 = new Scanner(System.in);
                title = input2.nextLine();

                System.out.println("Enter the price of the product: ");
                Scanner input3 = new Scanner(System.in);
                price = input3.nextFloat();

                System.out.println("Enter the description of the product: ");
                Scanner input4 = new Scanner(System.in);
                description = input4.nextLine();

                System.out.println("Enter the category of the product: ");
                Scanner input5 = new Scanner(System.in);
                category = input5.nextLine();

                Products product = new Products(code,title,price,description,category);
                BlockChain.addblock(product.jsonMaker());
                String url = "jdbc:sqlite:database.sqlite";
                try (Connection conn = DriverManager.getConnection(url);
                     Statement stmt = conn.createStatement()) {
                    if (conn != null) {
                        DatabaseMetaData meta = conn.getMetaData();
                    }

                    String sql2 = "SELECT MAX(id) FROM Products WHERE title = '"+product.title+"' ORDER BY id ASC";
                    ResultSet rs = stmt.executeQuery(sql2);
                    if (rs.next()) {
                        previd = rs.getInt(1);
                        String sql3 = "INSERT INTO Products (code, title, price, description, category, previd, hash, prevHash, blockTimestamp, nonce) VALUES ('" +product.code+"','" +product.title+"','" +product.price+"'," +
                                "'" +product.description+"', '" +product.category+"', '" +previd+"', '" +BlockChain.blocklist.get(BlockChain.blocklist.size()-1).hash+"'," +
                                " '"+BlockChain.blocklist.get(BlockChain.blocklist.size()-1).previousHash+"', " +
                                "'"+BlockChain.blocklist.get(BlockChain.blocklist.size()-1).getTimeStamp()+"', " +
                                "'"+BlockChain.blocklist.get(BlockChain.blocklist.size()-1).getNonce()+"') ";
                        stmt.executeUpdate(sql3);
                        rs.close();
                    }
                    else {
                        String sql = "INSERT INTO Products (code, title, price, description, category, hash, prevHash, blockTimestamp, nonce) VALUES ('" + product.code + "','" + product.title + "','" + product.price + "'," +
                                "'" + product.description + "', '" + product.category + "', '" + BlockChain.blocklist.get(BlockChain.blocklist.size() - 1).hash + "'," +
                                " '" + BlockChain.blocklist.get(BlockChain.blocklist.size() - 1).previousHash + "', " +
                                "'" + BlockChain.blocklist.get(BlockChain.blocklist.size() - 1).getTimeStamp() + "', " +
                                "'" + BlockChain.blocklist.get(BlockChain.blocklist.size() - 1).getNonce() + "') ";

                        stmt.executeUpdate(sql);
                        rs.close();
                    }
                }
                catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
                System.out.println("\nGive any input to continue: ");
                Scanner cont = new Scanner(System.in);
                String temp = cont.nextLine();
                menu();
            } else if (menuchoice == 3) {         // Add multiple products
                System.out.println("Enter the number of products you wish to add: ");
                Scanner input6 = new Scanner(System.in);
                int number = input6.nextInt();
                for (int i=0;i<number;i++)
                {
                    System.out.println("Enter the code of the product: ");
                    Scanner input = new Scanner(System.in);
                    code = input.nextLine();

                    System.out.println("Enter the name of the product: ");
                    Scanner input2 = new Scanner(System.in);
                    title = input2.nextLine();

                    System.out.println("Enter the price of the product: ");
                    Scanner input3 = new Scanner(System.in);
                    price = input3.nextFloat();

                    System.out.println("Enter the description of the product: ");
                    Scanner input4 = new Scanner(System.in);
                    description = input4.nextLine();

                    System.out.println("Enter the category of the product: ");
                    Scanner input5 = new Scanner(System.in);
                    category = input5.nextLine();

                    Products product = new Products(code,title,price,description,category);
                    BlockChain.addblock(product.jsonMaker());
                    String url = "jdbc:sqlite:database.sqlite";
                    try (Connection conn = DriverManager.getConnection(url);
                         Statement stmt = conn.createStatement()) {
                        if (conn != null) {
                            DatabaseMetaData meta = conn.getMetaData();
                        }

                        String sql2 = "SELECT MAX(id) FROM Products WHERE title = '"+product.title+"' ORDER BY id ASC";
                        ResultSet rs = stmt.executeQuery(sql2);
                        if (rs.next()) {
                            previd = rs.getInt(1);
                            String sql3 = "INSERT INTO Products (code, title, price, description, category, previd, hash, prevHash, blockTimestamp, nonce) VALUES ('" +product.code+"','" +product.title+"','" +product.price+"'," +
                                    "'" +product.description+"', '" +product.category+"', '" +previd+"', '" +BlockChain.blocklist.get(BlockChain.blocklist.size()-1).hash+"'," +
                                    " '"+BlockChain.blocklist.get(BlockChain.blocklist.size()-1).previousHash+"', " +
                                    "'"+BlockChain.blocklist.get(BlockChain.blocklist.size()-1).getTimeStamp()+"', " +
                                    "'"+BlockChain.blocklist.get(BlockChain.blocklist.size()-1).getNonce()+"') ";
                            stmt.executeUpdate(sql3);
                            rs.close();
                        }
                        else {
                            String sql = "INSERT INTO Products (code, title, price, description, category, hash, prevHash, blockTimestamp, nonce) VALUES ('" + product.code + "','" + product.title + "','" + product.price + "'," +
                                    "'" + product.description + "', '" + product.category + "', '" + BlockChain.blocklist.get(BlockChain.blocklist.size() - 1).hash + "'," +
                                    " '" + BlockChain.blocklist.get(BlockChain.blocklist.size() - 1).previousHash + "', " +
                                    "'" + BlockChain.blocklist.get(BlockChain.blocklist.size() - 1).getTimeStamp() + "', " +
                                    "'" + BlockChain.blocklist.get(BlockChain.blocklist.size() - 1).getNonce() + "') ";

                            stmt.executeUpdate(sql);
                            rs.close();
                        }
                    }
                    catch (SQLException e) {
                        System.out.println(e.getMessage());
                    }
                }
                System.out.println("\nGive any input to continue: ");
                Scanner cont = new Scanner(System.in);
                String temp = cont.nextLine();
                menu();
            } else if (menuchoice == 4) {                               //Search products
                String url = "jdbc:sqlite:database.sqlite";
                try (Connection conn = DriverManager.getConnection(url);
                     Statement stmt = conn.createStatement()){
                    if (conn != null) {
                        DatabaseMetaData meta = conn.getMetaData();
                    }
                    System.out.println("\nSearch by:");
                    System.out.println("1 - Product name");
                    System.out.println("2 - Product code");
                    System.out.println("3 - Product price");
                    System.out.println("4 - Product category");
                    System.out.println("\nEnter your option: ");
                    Scanner input = new Scanner(System.in);
                    int menuinp = input.nextInt();
                    if (menuinp==1)                        //Searching via name
                    {
                        System.out.println("Give product's name: ");
                        Scanner reader = new Scanner(System.in);
                        String str = reader.nextLine();
                        String sql = "SELECT id, code, title, productTimestamp, price, description, category FROM Products WHERE title = '"+str+"' ORDER BY id ASC";
                        ResultSet rs = stmt.executeQuery(sql);
                        ResultSetMetaData rsmd = rs.getMetaData();
                        int columnsNumber = rsmd.getColumnCount();
                        int counter = 0;
                        while (rs.next()) {
                                for (int i = 1; i <= columnsNumber; i++) {
                                    if (i > 1) System.out.print(",  ");
                                    String columnValue = rs.getString(i);
                                    System.out.print(columnValue + " " + rsmd.getColumnName(i));
                                }
                                System.out.println("");
                                ++counter;
                            }
                        if (counter==0)
                        {
                            System.out.println("\nThere are no entries matching your search.");
                        }
                        else
                        {
                            System.out.println("\n"+counter+" entries matching your search were found and are presented above in ascending adding order");
                        }
                    }
                    else if(menuinp==2)     //Searching via product code
                    {
                        System.out.println("Give product's code: ");
                        Scanner reader = new Scanner(System.in);
                        String str = reader.nextLine();
                        String sql = "SELECT id, code, title, productTimestamp, price, description, category FROM Products WHERE code = '"+str+"' ORDER BY id ASC";
                        ResultSet rs = stmt.executeQuery(sql);
                        ResultSetMetaData rsmd = rs.getMetaData();
                        int columnsNumber = rsmd.getColumnCount();
                        int counter = 0;
                        while (rs.next()) {
                            for (int i = 1; i <= columnsNumber; i++) {
                                if (i > 1) System.out.print(",  ");
                                String columnValue = rs.getString(i);
                                System.out.print(columnValue + " " + rsmd.getColumnName(i));
                            }
                            System.out.println("");
                            ++counter;
                        }
                        if (counter==0)
                        {
                            System.out.println("\nThere are no entries matching your search.");
                        }
                        else
                        {
                            System.out.println("\n"+counter+" entries matching your search were found and are presented above in ascending adding order");
                        }
                    }
                    else if(menuinp==3)           //Searching via product price
                    {
                        System.out.println("Give product's price: ");
                        Scanner reader = new Scanner(System.in);
                        Float prc = reader.nextFloat();
                        String sql = "SELECT id, code, title, productTimestamp, price, description, category FROM Products WHERE price = '"+prc+"' ORDER BY id ASC";
                        ResultSet rs = stmt.executeQuery(sql);
                        ResultSetMetaData rsmd = rs.getMetaData();
                        int columnsNumber = rsmd.getColumnCount();
                        int counter = 0;
                        while (rs.next()) {
                            for (int i = 1; i <= columnsNumber; i++) {
                                if (i > 1) System.out.print(",  ");
                                String columnValue = rs.getString(i);
                                System.out.print(columnValue + " " + rsmd.getColumnName(i));
                            }
                            System.out.println("");
                            ++counter;
                        }
                        if (counter==0)
                        {
                            System.out.println("\nThere are no entries matching your search.");
                        }
                        else
                        {
                            System.out.println("\n"+counter+" entries matching your search were found and are presented above in ascending adding order");
                        }
                    }
                    else if (menuinp==4)        //Searching via product category
                    {
                        System.out.println("Give product's category: ");
                        Scanner reader = new Scanner(System.in);
                        String str = reader.nextLine();
                        String sql = "SELECT id, code, title, productTimestamp, price, description, category FROM Products WHERE category = '"+str+"' ORDER BY id ASC";
                        ResultSet rs = stmt.executeQuery(sql);
                        ResultSetMetaData rsmd = rs.getMetaData();
                        int columnsNumber = rsmd.getColumnCount();
                        int counter = 0;
                        while (rs.next()) {
                            for (int i = 1; i <= columnsNumber; i++) {
                                if (i > 1) System.out.print(",  ");
                                String columnValue = rs.getString(i);
                                System.out.print(columnValue + " " + rsmd.getColumnName(i));
                            }
                            System.out.println("");
                            ++counter;
                        }
                        if (counter==0)
                        {
                            System.out.println("\nThere are no entries matching your search.");
                        }
                        else
                        {
                            System.out.println("\n"+counter+" entries matching your search were found and are presented above in ascending adding order");
                        }
                    }
                }
                catch (SQLException e) {
                    System.out.println(e.getMessage());
                }

                System.out.println("\nGive any input to continue: ");
                Scanner cont = new Scanner(System.in);
                String temp = cont.nextLine();
                menu();
            } else if (menuchoice == 5) {            //View product statistics
                System.out.println("\nEnter the name of the product: ");
                Scanner input = new Scanner(System.in);
                String str = input.nextLine();
                String url = "jdbc:sqlite:database.sqlite";
                try (Connection conn = DriverManager.getConnection(url);
                     Statement stmt = conn.createStatement()){
                    if (conn != null) {
                        DatabaseMetaData meta = conn.getMetaData();
                    }
                    String sql = "SELECT price, productTimestamp FROM Products WHERE title = '"+str+"'";
                    ResultSet rs = stmt.executeQuery(sql);
                    ResultSetMetaData rsmd = rs.getMetaData();
                    int columnsNumber = rsmd.getColumnCount();
                    int counter=0;
                    while (rs.next()) {
                        for (int i = 1; i <= columnsNumber; i++) {
                            if (i > 1) System.out.print(",  ");
                            String columnValue = rs.getString(i);
                            System.out.print(columnValue);
                        }
                        counter++;
                        System.out.println("");
                    }
                    if (counter==0){System.out.println("\nNo such product name found.");}
                }
                catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
                System.out.println("\nGive any input to continue: ");
                Scanner cont = new Scanner(System.in);
                String temp = cont.nextLine();
                menu();
            }
            else if (menuchoice == 6) {
                System.exit(0);
            }
        }
    }
    public static void menu() {

        System.out.println("\nFunction Menu:");
        System.out.println("-------------------------");
        System.out.println("1 - View all products");
        System.out.println("2 - Add 1 product");
        System.out.println("3 - Add multiple products");
        System.out.println("4 - Search products");
        System.out.println("5 - View product statistics");
        System.out.println("6 - Exit");
        System.out.println("\nEnter your desired option: ");
        Scanner input = new Scanner(System.in);
        menuchoice = input.nextInt();
    }

    }

