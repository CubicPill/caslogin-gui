package main;

import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

public class Exec {
    public static Image logo = Toolkit.getDefaultToolkit().getImage("logo.png");
    public static Image icon = Toolkit.getDefaultToolkit().getImage("icon.png");
    public static List<Image> icons = new ArrayList<Image>();

    public static void main(String args[]) { // entry point
        icons.add(logo);
        icons.add(icon);
        new MainView();
    }

}