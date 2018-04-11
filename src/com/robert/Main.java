//package com.robert;
//import com.robert.dao.JDBCDriver;
//
//import java.util.Scanner;
//
//public class Main {
//    public static void main(String[] args) {
//        JDBCDriver driver = new JDBCDriver();
//        int suma = 0;
//        String temp = "";
//        while (true){
//            Scanner scanner=new Scanner(System.in);
//            //TODO regex do sprawdzenia poprawnej formy kodu kreskowego
//            temp=scanner.nextLine();
//            if (temp.equals("exit")) break;
//            if (temp.isEmpty()) System.out.println("Invaild barcode");
//            else
//            {
//                System.out.println(driver.select("SELECT ProductName, UnitPrice FROM Products WHERE Barcode="+temp.toString()));
//                System.out.println("TOTAL: $"+driver.TOTAL);
//            }
//        }
//
//        System.out.println("Podsumowanie: "+driver.ORDER);
//        System.out.println("TOTAL: $"+driver.TOTAL);
//    }
//}
