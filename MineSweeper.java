import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class MineSweeper {
    private int row;
    private int column;
    private int numberOfMines=0;
    private String[][] answer;       //mayınların yerini gösterecek dizi
    private String[][] playArray = new String[row][column];    // kullanıcının oyunu oynayacağı dizi

    public MineSweeper(int row, int column) {
        this.row = row;
        this.column = column;
        numberOfMines = (row * column) / 4;           //mayın sayısı
        answer= new String[this.row][this.column];
        playArray=new String[this.row][this.column];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                answer[i][j]="-";
                playArray[i][j]="-";
            }
        }
    }
    //mayınların 2 boyutlu array'e eklendiği metod
    public void minePosition(){
        Random random= new Random();
        int added=0;
        while(added!=numberOfMines){   //mayınların eklendiği kısım
            int rowRandom, columnRandom ;
            rowRandom= random.nextInt(this.row);
            columnRandom=random.nextInt(this.column);
            if(!answer[rowRandom][columnRandom].equals("*")){
                answer[rowRandom][columnRandom] = "*";
                added++;
            }
        }
        printArray(answer);  // mayınları ekranda gösterelim, test amaçlı
    }

    //oyunun oynandığı metod
    public void play() {
        minePosition();
        System.out.println("Mayın Tarlası Oyununa Hoşgeldiniz ! ");
        System.out.println("Oyunu oynamak için satır ve sütun numaralarını " +
                "oyun istediğinde girmeniz gerekmektedir.");
        while (true) {
            System.out.println("----------------------------------");
            Scanner scanner = new Scanner(System.in);
            printArray(this.playArray);
            try {
                System.out.print("Satır giriniz : ");
                int inRow = scanner.nextInt();
                System.out.print("Sütun giriniz : ");
                int inCol = scanner.nextInt();
                // Burada mayınların olduğu array'e bakıyorum, ekrana farklı bir array basıyorum,
                // mayınların olduğu arrayde'de açılan kutucukları (daha doğrusu satır sütunları)
                // array'e işliyorum.
                if (answer[inRow][inCol].equals("-")) {            //mayına basmadıysan
                    playArray[inRow][inCol] = String.valueOf(getTouchedMines(answer, inRow, inCol));
                    answer[inRow][inCol] = playArray[inRow][inCol];
                    //değerler girildi her seferinde oyun kazanıldı mı diye kontrol ediyoruz
                    if (isWin()) {
                        System.out.println("Tebrikler, Oyunu Kazandınız!! \n Mayın Haritası :");
                        printArray(answer);
                        break;
                    }
                } else if (answer[inRow][inCol].equals("*")) {       //mayına bastıysan
                    System.out.println("Oyun bitti kaybettiniz..");
                    break;
                } else { //zaten girdigi yeri tekrar girmis oluyor
                    System.out.println("Bu pozisyona giriş yaptınız, lütfen baska konumu deneyin.");
                }
            } catch (ArrayIndexOutOfBoundsException ex) {  //olası dizin değerini aşma hatası için
                System.out.println("Dizinin sınırlarından yüksek değerler girmeyiniz.");
            } catch (InputMismatchException ex) {          //kullanıcı yanlış veri girmesi hatası için
                System.out.println("Lütfen harf veya sembol girmeyiniz.");
            }
        }
    }
    //mayın tarlasını yani array'i ekrana yazdırma metodu
    public void printArray(String[][] arr) {
        for (String[] r : arr) {
            for (String s : r) {
                System.out.print(s + " ");
            }
            System.out.println();
        }
    }
    //seçilen konumun sağında,solunda,altında,üstünde vs... olan mayınların sayısını veren metod
    public int getTouchedMines(String[][] arr,int row,int column) {
        int mineNumbers = 0, i = row - 1, j = column - 1, k = j;
        if (i < 0) i++;
        if (j < 0) {
            j++;
            k++;
        }
        while (true) {
            try {
                for (; i <= row + 1 && i < this.row; i++) {
                    for (; j <= column + 1 && j < this.column; j++) {
                        if (arr[i][j].equals("*")) {
                            mineNumbers++;
                        }
                    }
                    j = k; // yapmamın sebebi j değişkeni for döngüsünün sonunda ilk değere dönmüyor,
                    // bu yüzden döngü doğru biçimde çalışmıyordu.
                }
                break;
            } catch (ArrayIndexOutOfBoundsException e) {

            }
        }
        return mineNumbers;
    }

    //çok amatör mü oldu bilmiyorum ama answer array'ine bakarım, "-" içermiyorsa kazanmış olurum :p
    public boolean isWin(){
        for (int i = 0; i < answer.length; i++) {
            for (int j = 0; j <answer[i].length ; j++) {
                if(answer[i][j].equals("-")){
                    return false;
                }
            }
        }
        return true;
    }
}
