package Chapter_11.Synch;

/* Использование ключевого слова synchronized
*  для управления доступом */

class SumArray {
    private int sum;

    synchronized int sumArray(int nums[]) { //Метод sumArray() синхронизирован
    sum = 0; //обнуление суммы

        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            System.out.println("Текущее значение суммы для " +
                    Thread.currentThread().getName() + " будет " + sum);
            try {
                Thread.sleep(10); // разрешение переключения между задачами
            } catch (InterruptedException exc) {
                System.out.println("Поток прерван.");
            }
        }
        return sum;
    }
}

class MyThread6 implements Runnable {
    Thread thread;
    static SumArray sa = new SumArray();    //используется СТАТИЧЕСКИЙ объект sa типа SumArray для получения
                                            //суммы элементов целочисленного массива. А поскольку он СТАТИЧЕСКИЙ,
                                            //то ВСЕ(!) экземпляры класса MyThread6 используют ОДНУ его копию!
    private int a[];
    private int answer;

    MyThread6(String name, int nums[]) {
        thread = new Thread(this, name);
         a = nums;
    }

    //Создание и запуск потока с помощью фабричного метода
    public static MyThread6 createAndStart(String name, int nums[]) {
        MyThread6 mth = new MyThread6(name, nums);

        mth.thread.start(); //запуск потока
        return mth;
    }

    //Точка входа для потока
    public void  run() {
        //int sum;

        System.out.println(thread.getName() + " - запуск.");

        answer = sa.sumArray(a);
        System.out.println("Сумма для " + thread.getName() + " будет " + answer);
        System.out.println(thread.getName() + " - завершение.");
    }
}



public class Sync {
    public static void main(String[] args) {

        int a[] = { 1, 2, 3, 4, 5, 6 };

        MyThread6 mth1 = MyThread6.createAndStart("Порождённый поток #1", a);
        MyThread6 mth2 = MyThread6.createAndStart("Порождённый поток #2", a);

        try {
            mth1.thread.join();
            mth2.thread.join();
        } catch (InterruptedException exc) {
            System.out.println("Прекращение выполнения основного потока.");
        }
    }
}
