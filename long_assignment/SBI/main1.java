//import org.graalvm.compiler.loop.LoopsData;
import java.util.*;
import java.io.*;
class account{
    int accountNumber;
    int balance;
   // public account
    public account(int accountNumber,int balance){
        this.accountNumber=accountNumber;
        this.balance=balance;
    }
    public int debit(int debitAmount){
        if(this.balance>debitAmount){
            this.balance-=debitAmount;
            return 1;
        }
        else
            return 0;
    }
    public void credit(int creditAmount){
        this.balance+=creditAmount;
        return;
    }
}
class transaction{
    int debitAccount;
    int creditAccount;
    int amount;
    int done;
    public transaction(int debitAccount,int creditAccount,int amount){
        this.creditAccount=creditAccount;
        this.debitAccount=debitAccount;
        this.amount=amount;
        this.done=0;
    }
}
class Looper implements Runnable {

    private int start;
    private int end;
    private account[] accountBunch;
    private transaction[] transactionsBunch;
    public Looper(int start, int end,transaction[] transactionBunch,account[] accountBunch){
        this.start = start;
        this.end = end;
        this.transactionsBunch=transactionBunch;
        this.accountBunch=accountBunch;
    }

    @Override
    public void run (){
        try{
        for(int i=start; i<end; i++){
           if(i%1000000==0)
             System.out.println("thread id : " + Thread.currentThread().getId() + "; index : " + i) ;
            //int amount=transactionsBunch[i].amount;
           //for(int j=-100000000;j<1000000000;j+=0.01){
           //    j+=0.05;
           //}
            accountBunch[transactionsBunch[i].debitAccount].debit(transactionsBunch[i].amount);
           // Thread.sleep(30);
            accountBunch[transactionsBunch[i].creditAccount].credit(transactionsBunch[i].amount);
           // Thread.sleep(30);
            //int creditAccount=transactionsBunch[i].creditAccount;

            

        }}
        catch(Exception e){
            System.out.println("lol");
        }
    }
}
class main1{
    private static ArrayList<Thread> arrThreads = new ArrayList<Thread>();
    private static void this_func(account[] accountBunch,transaction[] transactionBunch) throws InterruptedException{

        
        Thread looper1 = new Thread(new Looper(0,10000000,transactionBunch,accountBunch));
        // Thread looper2 = new Thread (new Looper(2500001,5000000,transactionBunch,accountBunch));
       
        // Thread looper3 = new Thread (new Looper(5000001,7500000,transactionBunch,accountBunch));
        // Thread looper4 = new Thread (new Looper(7500001,10000000,transactionBunch,accountBunch));
        looper1.start();
        arrThreads.add(looper1);
        //Thread.sleep(100);

        
        // looper2.start();
        // arrThreads.add(looper2);
        // looper3.start();
        // arrThreads.add(looper3);
        // looper4.start();
        // arrThreads.add(looper3);
        //return;
        for (int i = 0; i < arrThreads.size(); i++) 
        {
            arrThreads.get(i).join(); 
        }
    }
    public static void main(String[] args) throws InterruptedException{
        account[] accountBunch=new account[10000];
        System.out.println(accountBunch.length);
        for(int i=0;i<accountBunch.length;i++){
            accountBunch[i]=new account(i,10000);
        }
        transaction[] transactionBunch= new transaction[10000000];
        for(int i=0;i<transactionBunch.length;i++){
            transactionBunch[i]=new transaction(i%10000, (i+1)%10000, 1);
        }

        //Thread looper3 = new Thread (new Looper(500001,7500000,transactionBunch,accountBunch));
        //Thread looper4 = new Thread (new Looper(7500001,10000000,transactionBunch,accountBunch));
        
        final long startTime = System.currentTimeMillis();
        //looper3.start();
        //looper4.start();
        this_func(accountBunch,transactionBunch);

       final long endTime = System.currentTimeMillis();

       // System.out.println("HEEELLLLLOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOo");
       System.out.println("Total execution time: " + (endTime - startTime));

    }
}