import java.sql.*;
import java.util.UUID;



public class DBManager extends BlockChain {


    public DBManager() {
        String url = "jdbc:sqlite:database.sqlite";
        String table = "CREATE TABLE IF NOT EXISTS Products (\n"
                + "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "    code TEXT,\n"
                + "    title TEXT NOT NULL,\n"
                + "    productTimestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,\n"
                + "    price REAL,\n"
                + "    description TEXT,\n"
                + "    category TEXT,\n"
                + "    previd INTEGER,\n"
                + "    hash TEXT,\n"
                + "    prevHash TEXT,\n"
                + "    blockTimestamp TEXT,\n"
                + "    nonce TEXT\n"
                + ");";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement())
        {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("Connection to the database established.");
            }
            stmt.execute(table);
            String sql = "SELECT * FROM Products WHERE id = (SELECT MAX(id) FROM Products ORDER BY id ASC)";
            ResultSet rs = stmt.executeQuery(sql);
            if (!rs.next()){   //If DB is empty
                Products product = new Products ("","initializing product", 0,"","");  //Initializing the empty DB with 1 entry of a fake product
                Block genesisBlock = new Block(product.jsonMaker(), UUID.randomUUID().toString());
                genesisBlock.mineBlock(difficulty);
                blocklist.add(genesisBlock);
                int previdinit = 0;
                String sql2 = "INSERT INTO Products (code, title, price, description, category, previd, hash, prevHash, blockTimestamp, nonce) VALUES ('" +product.code+"','" +product.title+"','" +product.price+"'," +
                        "'" +product.description+"', '" +product.category+"', '" +previdinit+"', '" +genesisBlock.hash+"', '"+genesisBlock.previousHash+"', '"+genesisBlock.getTimeStamp()+"', '"+genesisBlock.getNonce()+"') ";
                stmt.executeUpdate(sql2);
                rs.close();
            }
            else     //If DB is not empty, we just read the latest DB entry and add it in our arraylist so we can continue our work
            {
                String hash = rs.getString("hash");
                String prevHash = rs.getString("prevHash");
                long blockTimestamp = Long.decode(rs.getString("blockTimestamp"));
                int nonce = rs.getInt("nonce");
                String title = rs.getString("title");
                String code = rs.getString("code");
                float price = rs.getFloat("price");
                String description = rs.getString("description");
                String category = rs.getString("category");
                String data = new Products(code, title, price, description, category).jsonMaker();
                Block currentBlock = new Block ("",prevHash);
                currentBlock.setTimeStamp(blockTimestamp);
                currentBlock.setNonce(nonce);
                currentBlock.setData(data);
                currentBlock.hash=hash;
                blocklist.add(currentBlock);
                rs.close();
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
