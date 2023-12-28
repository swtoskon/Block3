import com.google.gson.GsonBuilder;



public class Products {
    public String code;
    public String title;
    public float price;
    public String description;
    public String category;

    public Products(String code, String title, float price, String description,String category)
    {
        this.code = code;
        this.title = title;
        this.price = price;
        this.description = description;
        this.category = category;
    }

    public String jsonMaker()
    {
        String json = new GsonBuilder().setPrettyPrinting().create().toJson(this);
        return json;
    }

}
