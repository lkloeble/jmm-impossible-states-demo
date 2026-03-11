public class ImpossibleStateDemo {  
  
    /**  
     * Shared variables with NO synchronization.     * This is the whole point of the demo.     */    private static int x = 0;  
    private static int y = 0;  
    private static int r1 = 0;  
    private static int r2 = 0;  
  
    public static void main(String[] args) throws InterruptedException {  
        long iterations = 0;  
        long startedAt = System.currentTimeMillis();  
  
        while (true) {  
            iterations++;  
  
            x = 0;  
            y = 0;  
            r1 = 0;  
            r2 = 0;  
  
            Thread t1 = new Thread(() -> {  
                x = 1;  
                r1 = y;  
            });  
  
            Thread t2 = new Thread(() -> {  
                y = 1;  
                r2 = x;  
            });  
  
            t1.start();  
            t2.start();  
  
            t1.join();  
            t2.join();  
  
            if (r1 == 0 && r2 == 0) {  
                long durationMs = System.currentTimeMillis() - startedAt;  
  
                System.out.println("Observed the \"impossible\" state:");  
                System.out.println("r1 = " + r1 + ", r2 = " + r2);  
                System.out.println("First observed after " + iterations + " iterations");  
                System.out.println("Elapsed time: " + durationMs + " ms");  
  
                break;  
            }  
  
            if (iterations % 1_000_000 == 0) {  
                System.out.println("Still running... iterations = " + iterations);  
            }  
        }  
    }  
}
