import java.awt.SystemTray;
import java.util.*;
import java.io.*;
import java.nio.file.*;
import java.nio.charset.*;
// import java.io.File;
// import java.io.FileNotFoundException;
// import java.io.PrintWriter;
// import java.io.BufferedReader;
// import java.io.FileNotFoundException;
// import java.io.FileReader;
// import java.io.IOException;
//import java.util.Scanner;
class product{
    //String category;
    String subcategory;
    String productName;
    int price;
    int units;
    public product(String subcategory,String productName,int price,int units){
        //this.category=category;
        this.subcategory=subcategory;
        this.productName=productName;
        this.price=price;
        this.units=units;
    }
    public void product_modify(int price,int units){
        this.price=price;
        this.units=units;
    }
    public void product_modify_price(int price){
        this.price=price;
    }
    public void product_modify_units(int units){
        this.units=units;
    }
}
class database{
    //product[] prod_collection=new product[25];
    //List<product> product_list; //=new ArrayList<product>();
    Map<String, List<product>> databaseMap;// = new HashMap<>();


    public database(){
        databaseMap = new HashMap<String, List<product>>();;
//product_list=new ArrayList<product>();
    }
    public void insert(product productToBeAdded){
        String subcategory=productToBeAdded.subcategory;
        if(databaseMap.containsKey(subcategory)){
            List<product> productListInThatCategory=databaseMap.get(subcategory);
            productListInThatCategory.add(productToBeAdded);
            
        }
        else{
            List<product> productList =new ArrayList<product>();
            productList.add(productToBeAdded);
            databaseMap.put(subcategory, productList);
            //System.out.println("Done!");
            //intScanner.close();
        }

    }
    public void insert(String subcategory,String productName){
        if(databaseMap.containsKey(subcategory)){
            List<product> productListInThatCategory=databaseMap.get(subcategory);
            int flag=0;
            for (int i = 0; i < productListInThatCategory.size(); i++) {
                if(productName.equals((productListInThatCategory.get(i)).productName)){
                    System.out.println("Error: The product is already present! You may have wanted to use modify functionality.");
                    flag=1;
                    break;
                }

            }
            if(flag!=1){
                System.out.println("Seems like it is a new product! Please enter the price and units to enter the product into database");
                Scanner intScanner=new Scanner(System.in);
                int price=intScanner.nextInt();
                int units=intScanner.nextInt();
                product productToBeAdded=new product(subcategory, productName, price, units);
                productListInThatCategory.add(productToBeAdded);
                databaseMap.put(subcategory,productListInThatCategory);
                System.out.println("Done!");
                //intScanner.close();

            }
            
        }
        else{
            List<product> productList =new ArrayList<product>();
            System.out.println("Seems like it is a new product and a subcategory! Please enter the price and units to enter the product into database");
            Scanner intScanner=new Scanner(System.in);
            int price=intScanner.nextInt();
            int units=intScanner.nextInt();
            product productToBeAdded=new product(subcategory, productName, price, units);
            productList.add(productToBeAdded);
            databaseMap.put(subcategory, productList);
            System.out.println("Done!");
            //intScanner.close();
        }
    }
    public void delete(String subcategory){
        //int inputLength=subcategory.length();
        int greaterThanCount=0;
        for(int i=0;i<subcategory.length();i++){
            if(subcategory.charAt(i)=='>'){
                greaterThanCount=greaterThanCount+1;

            }
        }


        //it is a product case
        if(greaterThanCount==2){
            String[] parts = subcategory.split(">");
            String category = parts[0]; //category
            subcategory = parts[1]; //subcategory
            String productName=parts[2];
            subcategory=category+">"+subcategory;

            if(databaseMap.containsKey(subcategory)){
                List<product> productListInThatCategory=databaseMap.get(subcategory);
                int flag=0;
                for (int i = 0; i < productListInThatCategory.size(); i++) {
                    if(productName.equals((productListInThatCategory.get(i)).productName)){
                        productListInThatCategory.remove(i);
                        System.out.println("The product has been removed!");
                        flag=1;
                        break;
                    }
                databaseMap.put(subcategory,productListInThatCategory);    
    
                }
                if(flag!=1){
                    System.out.println("Invalid path");
    
                }
                
            }
            else{
                System.out.println("Invalid path!");
            }



        }
        // when it is a subcategory
        else if(greaterThanCount==1){


            if (databaseMap.containsKey(subcategory)){
                databaseMap.remove(subcategory);
                System.out.println("The product has been removed!");
            }
            else{
                System.out.println("Invalid path!");
            }

        }
        else{
            System.out.println("Invalid path!");
        }
    }
    public product search(String productName){
        Iterator<Map.Entry<String, List<product>>> it= databaseMap.entrySet().iterator();
        //int flag=0;
        while (it.hasNext()) {
            Map.Entry<String, List<product>> pair = (Map.Entry<String, List<product>>)it.next();
            List<product> productList =pair.getValue();
            for (int i = 0; i < productList.size(); i++) {
                if(productName.equals((productList.get(i)).productName)){
                    //System.out.println("The path to the product is:"+productList.get(i).subcategory+">"+productList.get(i).productName);
                    
                    return productList.get(i);

                }
            //System.out.println(pair.getKey() + " = " + pair.getValue());
            it.remove(); // avoids a ConcurrentModificationException
            }
        }
        System.out.println("Product not found, returning a false reference!");
        return new product("false","false",-1,-1);




    }
    public void modify(String productName){
        product productToBeModified=search(productName);
        if((productToBeModified.productName).equals("false")){
            System.out.println("The product ain't there in the database!");
        }
        else{
            System.out.println("Enter the new price and units, enter '-1' if you don't want the attribute to be changed");
            Scanner intScanner=new Scanner(System.in);
            int price1=intScanner.nextInt();
            int units1=intScanner.nextInt();
            
            if(price1!=-1){
                productToBeModified.product_modify_price(price1);
            }
            if(units1!=-1){
                productToBeModified.product_modify_units(units1);
            }
            //intScanner.close();
        }
    }
    public int sale(product productToBeBought,int quantityDemanded,int remainingFundsWithCustomer){
        int unitsAvailable=productToBeBought.units;
        if(unitsAvailable<quantityDemanded){
            System.out.println("The number of units demanded aren't available, concerned product is: "+ productToBeBought.productName);
            return remainingFundsWithCustomer;
        }
        int priceToBePaid=quantityDemanded*productToBeBought.price;
        if(priceToBePaid>remainingFundsWithCustomer){
            System.out.println("Funds aren't sufficient for this transaction!,concerned product is: "+ productToBeBought.productName + "\nYou need to pay" +priceToBePaid);
            return remainingFundsWithCustomer;
        }
        productToBeBought.units-=quantityDemanded;
        System.out.println("Transaction successful of product: "+productToBeBought.productName);
        return remainingFundsWithCustomer-priceToBePaid;

    }

}
class Pair<L,R> {
    private L l;
    private R r;
    public Pair(L l, R r){
        this.l = l;
        this.r = r;
    }
    public L getL(){ return l; }
    public R getR(){ return r; }
    public void setL(L l){ this.l = l; }
    public void setR(R r){ this.r = r; }
}
class customer{
    List<Pair<product,Integer>> cart; //=new ArrayList<product>();
    String customerID;
    int remainingFunds;
    public customer(String customerID,int funds){
        cart=new ArrayList<Pair<product,Integer>>();
        this.customerID=customerID;
        this.remainingFunds=funds;

    }
    public void chekoutCart(database myDatabase){
        for (int i = 0; i < cart.size(); i++) {
            this.remainingFunds=myDatabase.sale(cart.get(i).getL(),cart.get(i).getR(),this.remainingFunds);
        }
    }
    public void addFunds(int fundsToBeAdded){
        this.remainingFunds+=fundsToBeAdded;
        System.out.println("Success, funds added!");
    }
    public void addToCart(product productToBeAdded,int quantityDemanded){
        Pair<product,Integer> pair= new Pair<product,Integer>(productToBeAdded,quantityDemanded);
        cart.add(pair);
        System.out.println("Success, product added to your cart!");
    }
}
class main1{
    public static void main(String[] args) throws IOException{
        database myDatabase=new database();
        BufferedReader bro = null;
        String strLine = "";
        try {
            bro = new BufferedReader( new FileReader("database.csv"));
            while( (strLine = bro.readLine()) != null){
                String[] databaseData=strLine.split(",");
                product productToBeAddedFromFile=new product(databaseData[0],databaseData[1],Integer.parseInt(databaseData[2]),Integer.parseInt(databaseData[3]));
                myDatabase.insert(productToBeAddedFromFile);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Seems like no database file already exists, will create a new database!");
        } catch (IOException e) {
            System.err.println("Unable to read database file!");
        }
        customer myCustomer=new customer("1", 0);
        while(true){
            System.out.println("Enter 1 to use as admin, enter 0 to use as customer, any other integer to quit");
            Scanner intScanner=new Scanner(System.in);
            int whatTheUserWants=intScanner.nextInt();
            //administrator case
            if(whatTheUserWants==1){
                System.out.println("Enter:\n 1: to insert product/category \n 2: delete product/category \n 3: Search for a product \n 4: Modify a product \n 5: Exit as administrator");
                int userChoice=intScanner.nextInt();
                if(userChoice==1){
                    System.out.println("Enter the category");
                    BufferedReader brObject = new BufferedReader(new InputStreamReader(System.in));  
                    String category= brObject.readLine();
                    //String category=intScanner.nextLine();
                    System.out.println("Enter the subcategory");
                    String subcategory=brObject.readLine();
                    System.out.println("Enter the product name");
                    String productName=brObject.readLine();
                    myDatabase.insert(category+">"+subcategory, productName);
                }
                else if(userChoice==2){
                    System.out.println("Enter the category");
                    BufferedReader brObject = new BufferedReader(new InputStreamReader(System.in));  
                    String category= brObject.readLine();
                    //String category=intScanner.nextLine();
                    System.out.println("Enter the subcategory");
                    String subcategory=brObject.readLine();
                    System.out.println("Enter the product name, enter nothing if you want to delete the whole subcategory");
                    String productName=brObject.readLine();
                    myDatabase.delete(category+">"+subcategory+">"+productName);
                }
                else if(userChoice==3){
                    BufferedReader brObject = new BufferedReader(new InputStreamReader(System.in));  
                    System.out.println("Enter the product name you wish to search for");
                    String productName=brObject.readLine();
                    product returnedProduct=myDatabase.search(productName);
                    if(!(returnedProduct.productName.equals("false"))){
                        System.out.println("The path to the product is:\n"+returnedProduct.subcategory+">"+returnedProduct.productName);
                        System.out.println("The number of units available are:"+returnedProduct.units);
                        System.out.println("The price of the product is:"+returnedProduct.price);
                        
                    }
                }
                else if(userChoice==4){
                    BufferedReader brObject = new BufferedReader(new InputStreamReader(System.in));  
                    System.out.println("Enter the product name you wish to modify");
                    String productName=brObject.readLine();
                    myDatabase.modify(productName);
                }
                else if(userChoice==5){
                    continue;

                }
                
            }
            //customer case
            else if(whatTheUserWants==0){
                System.out.println("Enter:\n 1: to add funds \n 2: add product to cart \n 3: checkout your cart\n 4: Exit as customer \n ");
                int userChoice=intScanner.nextInt();
                if(userChoice==1){
                    System.out.println("Enter the amount of funds you wish to add");
                    int fundsToBeAdded=intScanner.nextInt();
                    myCustomer.addFunds(fundsToBeAdded);
                }
                else if(userChoice==2){
                    System.out.println("Enter the product's name");
                    BufferedReader brObject = new BufferedReader(new InputStreamReader(System.in));  
                    String productName= brObject.readLine();
                    product productToBeAdded=myDatabase.search(productName);
                    if(!(productToBeAdded.productName.equals("false"))){
                        //System.out.println("The path to the product is:\n"+returnedProduct.subcategory+">"+returnedProduct.productName);
                        System.out.println("The number of units available are:"+productToBeAdded.units);
                        System.out.println("The price of the product is:"+productToBeAdded.price);
                        System.out.println("Enter the number of units you would like to purchase");
                        int quantityDemanded=intScanner.nextInt();
                        myCustomer.addToCart(productToBeAdded,quantityDemanded);
                        
                    }
                }
                else if(userChoice==3){
                    myCustomer.chekoutCart(myDatabase);
                }
                else if(userChoice==4){
                    continue;
                }



            }
            else{
                System.out.println("Seems like you're not interested, exiting...");
                try {
                    File file = new File("database.csv"); 
                    file.delete();
                    final Path path = Paths.get("database.csv");
                   // for(int i=0;i<myDatabase.length();)
                   Iterator<Map.Entry<String, List<product>>> it= myDatabase.databaseMap.entrySet().iterator();
                   //int flag=0;
                    while (it.hasNext()) {
                        Map.Entry<String, List<product>> pair = (Map.Entry<String, List<product>>)it.next();
                        List<product> productList =pair.getValue();
                        for (int i = 0; i < productList.size(); i++) {
                            product productToBeWritten=productList.get(i);
                            String toWriteToDatabaseFile=productToBeWritten.subcategory+","+productToBeWritten.productName+","+productToBeWritten.price+","+productToBeWritten.units;
                            Files.write(path, Arrays.asList(toWriteToDatabaseFile), StandardCharsets.UTF_8,
                        Files.exists(path) ? StandardOpenOption.APPEND : StandardOpenOption.CREATE);
                       //System.out.println(pair.getKey() + " = " + pair.getValue());
                        it.remove(); // avoids a ConcurrentModificationException
                        }
                    }
                    
                } catch (final IOException ioe) {
                    // Add your own exception handling...
                }
                //intScanner.close();
                return;
            }
        }

    }
}