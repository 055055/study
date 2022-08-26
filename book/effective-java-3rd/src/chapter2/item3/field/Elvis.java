package chapter2.item3.field;

public class Elvis {
    public static final Elvis INSTATNCE = new Elvis();

    private Elvis() {

    }

    public void leaveTheBuilding() {
        System.out.println("leaveTheBuilding");
    }

    public static void main(String[] args) {
        Elvis instatnce = Elvis.INSTATNCE;
        instatnce.leaveTheBuilding();
    }
}
