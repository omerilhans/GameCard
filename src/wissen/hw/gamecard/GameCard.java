package wissen.hw.gamecard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class GameCard {

    static Scanner oku = new Scanner(System.in);

    static List<String> mapList = new ArrayList<String>();
    static String[] updateMap = {"?", "?", "?", "?", "?", "?", "?", "?",
        "?", "?", "?", "?", "?", "?", "?", "?"};
    static String[] cardList = {"A", "B", "C", "Q", "W", "E", "G", "H"};
    static int first, second, cntSucces = 0, cntFail = 0;
    static long start = 0, finish = 0;
    static String yeniden = "";
    static boolean replay = false;

    static void createMap() {
        /**
         * Alttaki For döngüsü açılırsa, Oyunun başında tüm kartlar açık gösterilir.
         * Bu, oyunun doğru çalışıp çalışmadığını kontrol edilmek için kullanılabilir.
         */
//        for (int i = 0; i < 2; i++) 
//            for (int j = 0; j < 8; j++) 
//                mapList.add(cardList[j]);
        
        Collections.shuffle(mapList);
    }

    static void printMap() {
        for (int i = 0; i < 16; i++) {
            System.out.print(updateMap[i] + " ");
            if (i == 3 || i == 7 || i == 11) {
                System.out.println();
            }
        }
        System.out.println("\n--------");
    }

    static void makeYourChoice() {
        System.out.println("Make Your Choice: ");
        int cnt = 0;
        while (true) {
            int koord = oku.nextInt();
            if (koord < 0 || koord > 17) {
                System.err.println("Lütfen 0-16 arası, yeniden girin.");
            } else if (cnt == 0) {
                cnt++;
                first = koord - 1;
            } else {
                second = koord - 1;
                break;
            }
        }
    }

    static boolean choiceControl() {
        if (mapList.get(first).equals(mapList.get(second))) {
            return true;
        }
        return false;
    }

    static void updateMap() {
        updateMap[first] = mapList.get(first);
        updateMap[second] = mapList.get(second);
        printMap();
        System.out.println("\n");
        if (choiceControl()) {
            cntSucces++;
            updateMap[first] = " ";
            updateMap[second] = " ";
            printMap();
            System.out.println("Bildiniz\n");
        } else {
            cntFail++;
            if (!updateMap[first].equals(" ")) {
                updateMap[first] = "?";
            }
            if (!updateMap[second].equals(" ")) {
                updateMap[second] = "?";
            }
            printMap();
            System.out.println("Seçilen değerler eşit değil. Yeniden deneyin.\n");
        }
    }

    static boolean isFinish() {
        int count = 0;
        for (int i = 0; i < updateMap.length; i++) {
            if (updateMap[i].equals(" ")) {
                count++;
            }
        }
        if (count == updateMap.length) {
            return true;
        }
        return false;
    }

    static void reset() {
        for (int i = 0; i < 18; i++) {
            updateMap[i] = "?";
        }
        first = 0;
        second = 0;
        start = 0;
        finish = 0;
        cntFail = 0;
        cntSucces = 0;
        mapList.clear();
    }

    public static void main(String[] args) {
        boolean play = true;

        while (play) {
            System.out.print("--Oyun Başlıyor--\n");
            start = System.currentTimeMillis();
            play = false;
            createMap();
            for (int i = 0; i < mapList.size(); i++) {
                System.out.print(mapList.get(i) + " ");
                if (i == 3 || i == 7 || i == 11) {
                    System.out.println();
                }
            }
            System.out.println("\n");
            printMap();
            System.out.println("\n");
            while (true) {
                makeYourChoice();
                updateMap();
                if (isFinish()) {
                    finish = System.currentTimeMillis();
                    System.out.println("Oyun Bitti");
                    System.out.println(cntSucces + " adet DOGRU hamle yaptınız");
                    System.out.println(cntFail + " adet YANLIŞ hamle yaptınız");
                    System.out.println((finish - start) / 1000 + " saniye oynadınız...\n");
                    System.out.println("Yeniden Oynamak İster Misiniz? (E/H) ");
                    yeniden = oku.next();
                    if (yeniden.toLowerCase().equals("e")) {
                        System.out.print("Yeniden ");
                        play = true;
                        break;
                    } else if (yeniden.toLowerCase().equals("h")) {
                        play = false;
                        break;
                    }
                }
            }
        }

        System.out.println("\n\t\tPeki İyi Günler...");
    }

}
