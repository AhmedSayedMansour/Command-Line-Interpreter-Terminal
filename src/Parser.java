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
        else{
            System.out.print(input + ": command not found\n");
            emptyattr();
            return true;
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
            for (String i:argsarr) {
                args.add(i);
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
}