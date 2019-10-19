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

    public ArrayList<String> ls(String path){
        File file = new File(Dd+path);
        File filesout = new File(path);
        String [] arr  = file.list();
        String [] arr2 = filesout.list();
        ArrayList<String> lst = new ArrayList<>();

        if (file.exists()){
            if (arr.length<=0){
                //System.out.println("Directory is empty");
            }
            else {
                Collections.addAll(lst, arr);
                return lst;
            }
        }
        else if (filesout.exists()){

            if (arr.length<=0){
                //System.out.println("Directory is empty");
            }
            else {
                return lst;
            }
        }
        else {
            obj.printError("Path is not available");
            return lst;
        }
        return lst;
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
                "more [path] : display content of this path\n" +
                "cd [path] : change Default directory to this path\n" +
                "rm [filepath] : remove file content of this path\n" +
                "cp [source_path] [destination_path] : copy source to destination\n" +
                "mv [source_path] [destination_path] : move source to destination\n" +
                "cat [OPTION]... [FILE]... : Show contains of this files\n" +
                "cat [FILE_path]... > [destination_File] : copy all contains of files into destination path which created if not exist \n" +
                "cat [FILE_path]... >> [destination_File] : Will append the contents of one file to the end of another file");
    }

    public void setDd (String newDd){
        Dd = newDd;
    }

    public void rm(String sourcePath) {
        File f= new File (sourcePath);
        File f1= new File (Dd+sourcePath);
        if (f1.exists()) {
            if(f1.delete())
                System.out.println("deleted");
            else
                System.out.println("Not deleted");
        }
        else if (f.exists()) {
            if(f.delete())
                System.out.println("deleted");
            else
                System.out.println("Not deleted");
        }

    }

    public Boolean cp(String sourcePath, String destinationPath ) throws IOException{

        String a="";
        File f =new File(sourcePath);
        File f1= new File (Dd+sourcePath);
        Boolean help = true;
        if (f1.exists()) {
            a=Dd+sourcePath;
        }
        else if (f.exists()) {
            a=sourcePath;
        }
        else {
            System.out.println("can't find source to copy");
            return false;
        }

        FileReader myReader= new FileReader(a);
        File file1= new File(destinationPath);
        File file2= new File(Dd+destinationPath);
        String b = "";
        if (file2.exists()) {
            b=Dd+destinationPath;
        }
        else if (file1.exists()) {
            b=destinationPath;
        }
        else {
            try{
                if(file2.createNewFile()) {
                    b=Dd+destinationPath;
                    help = false;
                }
            }catch (IOException e){}

            if(help){
                try{
                    if(file1.createNewFile()) {
                        b=destinationPath;
                    }
                }catch (IOException e ){
                    System.out.println("Error");
                    return false;
                }
            }
        }

        FileWriter myWriter= new FileWriter(b);

        Scanner read= new Scanner(myReader);
        String s;
        while (read.hasNextLine()) {
            s = read.nextLine();
            myWriter.write(s+"\n");
        }

        myReader.close();
        myWriter.close();
        read.close();
        return true;

    }

    public void mv(String sourcePath, String destinationPath )throws IOException{
        if(cp( sourcePath,  destinationPath ))
            rm(sourcePath);
    }

    public ArrayList<String> cat(ArrayList<String> a) throws FileNotFoundException {
        ArrayList<String> c=new ArrayList<String>();
        String b;
        for (String i:a) {
            File f =new File(i);
            File f2=new File(Dd+i);
            String m;
            if (f2.exists()) {
                m=Dd+i;
            }
            else if (f.exists()) {
                m=i;
            }
            else {
                System.out.println("File "+i+" Not existed");
                continue;
            }
            FileReader f1= new FileReader (m);
            Scanner s= new Scanner (f1);
            while (s.hasNextLine()) {
                b=s.nextLine();
                c.add(b);
            }
            s.close();
        }
        return c;
    }

    public void R1Command(String destinationPath, ArrayList<String> content) throws IOException {

        Boolean help = true ;
        File file1= new File(destinationPath);
        File file2= new File(Dd+destinationPath);
        String b = "";
        if (file2.exists()) {
            b=Dd+destinationPath;
        }
        else if (file1.exists()) {
            b=destinationPath;
        }
        else {
            try{
                if(file2.createNewFile()) {
                    b=Dd+destinationPath;
                    help = false;
                }
            }catch (IOException e){}

            if(help){
                try{
                    if(file1.createNewFile()) {
                        b=destinationPath;
                    }
                }catch (IOException e ){
                    System.out.println("Error");
                    return ;
                }
            }
        }
        FileWriter file = new FileWriter(b);
        for (String i : content) {
            file.write(i + "\n");
        }
        file.close();
    }

    public void R2Command(String path, ArrayList<String> content) throws IOException{
        File f= new File (path);
        File f1= new File (Dd+path);
        String a;
        if (f1.exists()) {
            a=Dd+path;
        }
        else if (f.exists()) {
            a=path;
        }
        else {
            System.out.println("can't find source to copy");
            return;
        }
        BufferedWriter file = new BufferedWriter(new FileWriter(a, true));
        for (String i: content) {
            file.write(i+"\n");
        }
        file.close();
    }
}
