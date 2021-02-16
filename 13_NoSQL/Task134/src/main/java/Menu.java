import service.DbService;

import java.util.Scanner;

public class Menu {
    public static void main(String[] args) {
        menu();
    }

    private static void menu() {
        DbService dbService = new DbService();
        System.out.println("Введите команду\n" +
                "Пример:\n" +
                "ДОБАВИТЬ_МАГАЗИН Девяточка - добавляет магазин с указанным именем\n" +
                "ДОБАВИТЬ_ТОВАР Вафли 54 - добавляет товар с именем и ценой\n" +
                "ВЫСТАВИТЬ_ТОВАР Вафли Девяточка - выставляет товар в указанном магазине\n" +
                "СТАТИСТИКА_ТОВАРОВ");
        Scanner scanner = new Scanner(System.in);
        String command = scanner.nextLine();
        boolean flag = true;
        while (flag) {
            if (command.contains("ДОБАВИТЬ_МАГАЗИН")) {
                dbService.addShop(command);
            }
            if (command.contains("ДОБАВИТЬ_ТОВАР")) {
                dbService.addProduct(command);
            }
            if (command.contains("ВЫСТАВИТЬ_ТОВАР")) {
                dbService.exposeProduct(command);
            }
            if (command.contains("СТАТИСТИКА_ТОВАРОВ")) {
                dbService.printStat();
            }
            if (command.equals("q")) {
                flag = false;
            } else {
                command = scanner.nextLine();
            }
        }
    }
}
