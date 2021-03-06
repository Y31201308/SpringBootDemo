package com.nokia.example.concurrent.volatileIssue;

/**
 * @author by YingLong on 2020/9/18
 */
public class NoVisibility {


    private static boolean ready;
    private static int number;

    private static class ReaderThread extends Thread {
        @Override
        public void run() {
            while (!ready) {
                Thread.yield();
            }
            System.out.println("number: " + number);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new ReaderThread().start();
        ready = true;
        Thread.sleep(1000);
        number = 42;
    }

}
