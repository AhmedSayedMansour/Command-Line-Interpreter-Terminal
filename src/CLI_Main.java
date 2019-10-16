import java.io.IOException;
import java.util.Scanner;

public class CLI_Main {
    public static void main (String[] args) throws Exception {

        Parser CLI = new Parser();
        Terminal term = new Terminal();
        Scanner In = new Scanner(System.in);
        Color Col = new Color();

        boolean flag = true;

        while (flag){

            System.out.print(Col.print$("$ "));
            String input = In.nextLine();
            flag = CLI.parse(input);

            if (!flag)  continue;                       //check if the command is right
            if ( CLI.getCmd().matches("exit") )     break;

            if ( CLI.getCmd().matches("date") && CLI.getArguments().size() == 0 ){
                term.date();
                CLI.emptyattr();
            }
            else if( CLI.getCmd().matches("help") && CLI.getArguments().size() == 0 ){
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
                CLI.emptyattr();
            }
            else if(  ( CLI.getCmd().matches("exit")&& CLI.getArguments().size()==0  )  || ( (CLI.getCmd().matches("exit")) && (CLI.getArguments().size()==1) && (CLI.getArguments().get(0).matches("--help")) ) ){
                if( CLI.getCmd().matches("exit") && CLI.getArguments().size()==0 ){
                    CLI.emptyattr();
                }
                else{
                    System.out.print("exit: exit [n]\n" +
                            "    Exit the shell.\n" +
                            "\n" +
                            "    Exits the shell with a status of N.  If N is omitted, the exit status\n" +
                            "    is that of the last command executed.\n" +
                            "\n");
                    CLI.emptyattr();
                }
            }
            else if( CLI.getCmd().matches("clear") && CLI.getArguments().size()==0 ){
                term.clear();
                CLI.emptyattr();
            }
            else if(CLI.getCmd().matches("mkdir")){
                int begin = 0;
                Boolean v = CLI.getArguments().contains("-v");
                if( v )  begin++  ;
                if(CLI.getArguments().contains("-p")){
                    begin++ ;
                    String[] lastargsarr;
                    lastargsarr = ( CLI.getArguments().get(CLI.getArguments().size()-1) ).split("/|\\\\");
                    String first = lastargsarr[0];
                    for(int i=0 ; i< lastargsarr.length ; ++i){
                        term.mkdir(first,v);
                        if ( i<lastargsarr.length-1 ) first += "/" + lastargsarr[i+1];
                    }
                }
                else{
                    for(int i=begin ; i< CLI.getArguments().size() ; ++i){
                        term.mkdir(CLI.getArguments().get(i),v);
                    }
                }
                CLI.emptyattr();
            }
            else if( CLI.getCmd().matches("pwd") && CLI.getArguments().size()==0 ){
                term.pwd();
                CLI.emptyattr();
            }
            else if( CLI.getCmd().matches("rmdir") && CLI.getArguments().size()==1  ){
                term.rmdir(CLI.getArguments().get(0) );
                CLI.emptyattr();
            }
            else if( CLI.getCmd().matches("ls") && CLI.getArguments().size()==0  ){
                term.ls();
                CLI.emptyattr();
            }
            else if( CLI.getCmd().matches("more") && CLI.getArguments().size()==1  ){
                term.more( CLI.getArguments().get(0) );
                CLI.emptyattr();
            }
            else if( CLI.getCmd().matches("cd") && CLI.getArguments().size()==1  ){
                term.setDd( term.cd(CLI.getArguments().get(0)) );
                CLI.emptyattr();
            }
        }
    }
}
