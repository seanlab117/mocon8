package com.example.samplemenu;

import java.util.ArrayList;
import java.util.List;

public class MockupDA {

    List<Drink> drinks;

    public MockupDA(){
        drinks = new ArrayList<>();
        drinks.add(new Drink("Scenario 0 BLE terminal class access", 12, "scenario1"));
        drinks.add(new Drink("Scenario 1 BLE Terminal class access ", 12, "scenario1"));
        drinks.add(new Drink("Scenario 2 Wifi Scan class access  ", 12, "scenario1"));
        drinks.add(new Drink("Scenario 3 WiFi Info ", 4, "scenario2"));
        drinks.add(new Drink("Scenario 4 wifi scan activity app access", 5, "scenario2"));
        drinks.add(new Drink("Scenario 5 ESP Connection Test", 6, "scenario2"));
        drinks.add(new Drink("Scenario 6 Scenario-RGB App test", 7, "scenario3"));
        drinks.add(new Drink("Scenario 7 Scenario ", 8, "scenario3"));
        drinks.add(new Drink("Scenario 8 ", 9, "scenario3"));
        drinks.add(new Drink("Scenario 9 RGB TEST", 10, "scenario3"));
//        drinks.add(new Drink("Mango Juice", 2.50, "fruit juice"));
//        drinks.add(new Drink("Milk", 1.00, "dairy drink"));
//        drinks.add(new Drink("Yogurt Drink (Ayran)", 2.00, "dairy drink"));
//        drinks.add(new Drink("Lassi", 2.50, "dairy drink"));
//        drinks.add(new Drink("Coconut Water", 3.00, "cold drink"));
//        drinks.add(new Drink("Mint Lemonade", 2.75, "mocktail"));
//        drinks.add(new Drink("Virgin Mojito", 3.00, "mocktail"));
//        drinks.add(new Drink("Pineapple Juice", 2.50, "fruit juice"));
//        drinks.add(new Drink("Banana Milkshake", 3.00, "dairy drink"));
//        drinks.add(new Drink("Energy Drink (Red Bull)", 2.75, "energy drink"));
//        drinks.add(new Drink("Ginger Lemonade", 2.50, "mo
    }

    public List<Drink> getAllDrinks(){
        return drinks;
    }

    public List<String> getDrinkTypes(){
        List<String> types = new ArrayList<>();
        types.add("All scenario");
        for (Drink drink : drinks) {
            if (!types.contains(drink.getType())){
                types.add(drink.getType());
            }
        }
        return  types;
    }

    public List<Drink> getDrinkByType(String type){
        List<Drink> drinkList = new ArrayList<>();
        for (Drink drink : drinks) {
            if (drink.getType().equals(type)) {
                drinkList.add(drink);
            }
        }
        return drinkList;
    }

}
