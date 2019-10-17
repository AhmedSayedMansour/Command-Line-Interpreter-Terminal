import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.*;
import java.util.*;


public class Terminal{

    private String Dd = System.getProperty("user.home")+"\\";    //default directory (current)
    Color obj = new Color();

    public void mkdir (String path , Boolean v){

        File file = new File(path);
        File file2 = new File(Dd+path);

        if (file2.exists()){ if(v)  System.out.println(path + obj.printWarning(" already exists"));

        } else if (file2.mkdir()){ if(v)    System.out.println(path + obj.print$(" Created Successfully"));

        } else if(file.exists()){ if(v)    System.out.println(path + obj.printWarning(" is already exists"));

        }else if(file.mkdir()){ if(v)    System.out.println(path + obj.print$( " Created Successfully"));

        }
        else{ System.out.println( obj.printError("Error path may not be right")); }
    }

    public void pwd(){
        System.out.println(obj.printWarning(Dd));
    }

    public void rmdir(String path ) {

        File file = new File(path);
        File fileSameDir = new File(Dd+path);
        String [] f1 = file.list();
        String [] f2 = fileSameDir.list();

        if (fileSameDir.exists() ){
            if (f2.length<=0){ fileSameDir.delete();
                System.out.println(path + obj.print$(" Deleted Successfully"));
            }else{
                System.out.println(obj.printError("Directory is not empty"));
            }
        } else if (file.exists() && f1.length <=0){
            if (f1.length<=0){ file.delete();
                System.out.println(path + obj.print$(" Deleted Successfully"));
            }else{
                System.out.println(obj.printError("Directory is not empty"));
            }
        }else{System.out.println(obj.printError("Directory does not exist use ( mkdir "+ path + " ) to create"));

        }
    }

    public void ls(){
        File file = new File(Dd);
        String [] arr  = file.list();

        if (file.exists()){
            if (arr.length<=0){
                System.out.println("Directory is empty");
            }
            else {
                System.out.println(obj.printWarning("Content of the directory: "));

                for (int i = 0; i < arr.length; i++) {

                    System.out.println(arr[i]);
                }
            }
        }
        else {
            obj.printError("Path is not available");
        }
    }

    public void more(String path) throws Exception  {

        File file = new File(path);
        File fileSameDir = new File(Dd+path);
        Scanner enter = new Scanner(System.in);

        if (fileSameDir.exists() ){

            try{Scanner sc = new Scanner(fileSameDir);

                while (sc.hasNextLine()){
                    System.out.print(sc.nextLine());
                    String x = enter.nextLine();
                    if (x=="")continue;}
            }
            catch (Exception e){
                System.out.println("This is not a file");
            }
        } else if (file.exists()){

            try{Scanner sc = new Scanner(file);

                while (sc.hasNextLine()){
                    System.out.print(sc.nextLine());
                    String x = enter.nextLine();
                    if (x=="")continue;}
            }
            catch (Exception e){
                System.out.println("This is not a file");
            }

        }else {
            System.out.println(obj.printError("File not found"));
        }
    }

    public String cd (String path){
        //checks whether the directory available or not then return it

        File file = new File(path);

        File fileSameDir = new File(Dd+path);

        if (path.equals( "...")){

            int counter = 0;
            int index = Dd.length();
            boolean found = false;

            for (int i = 0; i < Dd.length(); i++) {

                if ((Dd.charAt(i)=='\\' || Dd.charAt(i) == '/')){
                    found = true;
                }

                else if (found){
                    index = i-1;
                    found = false;
                }

            }
            System.out.println("Directory changed to "+obj.printWarning(Dd.substring(0,index+1)));
            return Dd.substring(0,index+1);
        }

        if (path.toLowerCase().equals("desktop")){

            System.out.println("Directory changed to " + obj.printWarning("Desktop"));

            return (System.getProperty("user.home") + "\\Desktop"+"\\");
        }

        if (fileSameDir.exists()){

            System.out.println("Directory changed to " + obj.printWarning(path));

            return (Dd+path+'\\');

        } else if (file.exists()){

            System.out.println("Directory changed to " + obj.printWarning(path));

            return path+"\\";

        }else if (path.equals("~")){

            System.out.println("Directory changed to " + obj.printWarning(path));
            Dd = System.getProperty("user.home")+'\\';
            return System.getProperty("user.home")+'\\';
        }
        else{
            System.out.println(obj.printError("Path may not be available use -mkdir- command to create directory"));
            return Dd;
        }

    }

    public void date() {
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedDate = myDateObj.format(myFormatObj);
        System.out.println(formattedDate);
    }

    public void clear(){
        for ( int i =0 ; i < 10 ; ++i) {
            System.out.println("\n");
        }
    }

    public void help(){
        System.out.println("args : List all command arguments\n" +
                "date : Current date/time\n" +
                "exit : Stop all\n" +
                "mkdir [path] : Make new folder\n" +
                "mkdir [-v] [path] : Show status of this new created folder\n" +
                "mkdir [-v] [-p] [path/newfile/...] : show status of interanl created folder\n" +
                "clear : clears the console\n" +
                "pwd : print current directory\n" +
                "rmdir [path] :remove this path\n" +
                "ls : list all files in current directory\n" +
                "more [path] : display content of this path\n"+
                "cd [path] : change Default directory to this path");
    }

    public void setDd (String newDd){
        Dd = newDd;
    }

    public void rm(String sourcePath) {
        File f= new File (sourcePath);
        if(f.delete())
            System.out.println("deleted");
        else
            System.out.println("Not deleted");
    }
}