package chapter2.item3.factory;

public class Elvis {
    private static final Elvis INSTATNCE = new Elvis();
    private Elvis(){

    }

    public static Elvis getInstance(){
        return INSTATNCE;
    }

    public void leaveTheBuilding() {
        System.out.println("leaveTheBuilding");
    }

    public static void main(String[] args) {
        Elvis instance = Elvis.getInstance();
        instance.leaveTheBuilding();
    }
}
