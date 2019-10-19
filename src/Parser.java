import java.io.IOException;
import java.lang.instrument.ClassDefinition;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

// Interface for parser
public class Parser {

    ArrayList<String> args = new ArrayList<String>() ;
    String cmd;

    public boolean parse(String input) {

        input = input.trim().replaceAll(" +", " ");  //remove duplicated spaces
        full(input);

        if ( cmd.matches("date") && args.size() == 0 ){
            return true;
        }
        else if( cmd.matches("help") && args.size() == 0 ){
            return true;
        }
        else if( ( cmd.matches("exit")&& args.size()==0  )  || ( (cmd.matches("exit")) && (args.size()==1) && (args.get(0).matches("--help")) ) ){
            return true;
        }
        else if( cmd.matches("clear") && args.size()==0 ){
            return true;
        }
        else if(cmd.matches("mkdir")){
            return true;
        }
        else if( cmd.matches("pwd") && args.size()==0 ){
            return true;
        }
        else if( cmd.matches("rmdir") && args.size()==1  ){
            return true;
        }
        else if( cmd.matches("ls") && args.size()==0  ){
            return true;
        }
        else if( cmd.matches("more") && args.size()==1  ){
            return true;
        }
        else if( cmd.matches("cd") && args.size()==1  ){
            return true;
        }
        else if( cmd.matches("rm") && args.size()==1  ){
            return true;
        }
        else if( cmd.matches("cp") && args.size()==2  ){
            return true;
        }
        else if( cmd.matches("mv") && args.size()==2  ){
            return true;
        }
        else if( cmd.matches("cat") && args.size()>0  ){
            return true;
        }
        else{
            System.out.print(input + ": command not found\n");
            emptyattr();
            return false;
        }
    }

    public void full (String input){            //put command in cmd and arguments in args
        String[] argsarr;
        if( !input.contains(" ") ){
            cmd = input;
        }
        else{
            cmd=input.substring(0,input.indexOf(" "));
            input = input.substring(input.indexOf(" ")+1,input.length());
            argsarr = input.split(" ");
            String a= "";
            for (int i=0 ; i < argsarr.length ; ++i) {
                if ( argsarr[i].charAt(0) == '"'){
                    a = argsarr[i].substring(1,argsarr[i].length()) + " ";
                    i++;
                    while ( !( argsarr[i].charAt(argsarr[i].length()-1)=='"' ) ){
                        a += argsarr[i] + " ";
                        i++;
                    }
                    a += argsarr[i].substring(0,argsarr[i].length()-1);
                    args.add(a);
                }
                else{
                    args.add(argsarr[i]);
                }
            }
        }
    }

    public void emptyattr(){                //to empty cmd and args array after each command finished
        cmd = "";
        args.clear();
    }

    public String getCmd(){
        return cmd;
    }

    public ArrayList<String> getArguments(){
        return args;
    }

    public void setCmd (String C){
        cmd = C;
    }

    public void setArgs(String[] arr,Integer f,Integer l){
        args.clear();
        for(int i = l ; i < l-f ; ++i){
            args.add(arr[i]);
        }
    }
}