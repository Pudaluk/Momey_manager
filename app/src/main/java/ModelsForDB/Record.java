package ModelsForDB;

/**
 * Created by Lukas on 02.12.2017.
 */

public class Record {

    int id;
    String type;
    String create_at;
    int amount;
    int actual_balance;

    public Record(){
    }

    public Record(String type, String create_at, int amount, int actual_balance){
        this.type = type;
        this.create_at = create_at;
        this.amount = amount;
        this.actual_balance = actual_balance;
    }

    public Record(int id, String type, String create_at, int amount, int actual_balance){
        this.id = id;
        this.type = type;
        this.create_at = create_at;
        this.amount = amount;
        this.actual_balance = actual_balance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreate_at() {
        return create_at;
    }

    public void setCreate_at(String create_at) {
        this.create_at = create_at;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getActual_balance() {
        return actual_balance;
    }

    public void setActual_balance(int actual_balance) {
        this.actual_balance = actual_balance;
    }
}
