import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.regex.Pattern;

public class CLI_Main {
    public static void main (String[] args) throws Exception {

        Parser CLI = new Parser();
        Terminal term = new Terminal();
        Scanner In = new Scanner(System.in);
        Color Col = new Color();

        boolean flag = false;
        Integer pipe = 0;
        String input = "";

        while (!flag){

            System.out.print(Col.print$("$ "));
            input = In.nextLine();
            String[] pipes = input.split( Pattern.quote(" | ") );
            pipe = pipes.length;

            for(int i = 0 ; i < pipe ; ++i ){
                if (!CLI.parse(pipes[i]))  continue;                       //check if the command is right
                flag = CLI.getCmd().matches("exit") && CLI.getArguments().size()==0 ;
                cases(CLI,term);
            }
        }
    }


    public static void cases( Parser CLI ,Terminal term) throws Exception {
        Color Col = new Color();

        if ( CLI.getCmd().matches("date") && CLI.getArguments().size() == 0 ){
            term.date();
        }
        else if( CLI.getCmd().matches("help") && CLI.getArguments().size() == 0 ){
            term.help();
        }
        else if(  ( CLI.getCmd().matches("exit")&& CLI.getArguments().size()==0  )  || ( (CLI.getCmd().matches("exit")) && (CLI.getArguments().size()==1) && (CLI.getArguments().get(0).matches("--help")) ) ){
            if( ( (CLI.getCmd().matches("exit")) && (CLI.getArguments().size()==1) && (CLI.getArguments().get(0).matches("--help")) )){
                System.out.print("exit: exit [n]\n" +
                        "    Exit the shell.\n" +
                        "\n" +
                        "    Exits the shell with a status of N.  If N is omitted, the exit status\n" +
                        "    is that of the last command executed.\n" +
                        "\n");
            }
        }
        else if( CLI.getCmd().matches("clear") && CLI.getArguments().size()==0 ){
            term.clear();
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
        }
        else if( CLI.getCmd().matches("pwd") && CLI.getArguments().size()==0 ){
            term.pwd();
        }
        else if( CLI.getCmd().matches("rmdir") && CLI.getArguments().size()==1  ){
            term.rmdir(CLI.getArguments().get(0) );
        }
        else if( CLI.getCmd().matches("ls")  ){
            ArrayList<String> Arr = new ArrayList<String>();
            if ( CLI.getArguments().contains(">") ){
                String path = CLI.getArguments().get(CLI.getArguments().size()-1);
                CLI.getArguments().remove(CLI.getArguments().size()-1);
                CLI.getArguments().remove(CLI.getArguments().size()-1);
                if(CLI.getArguments().size() == 0)  {
                    String [] r = term.ls(term.getDd());
                    Collections.addAll(Arr, r);
                    term.R1Command( path, Arr );
                }
                else {
                    String [] r = term.ls(CLI.getArguments().get(0));
                    Collections.addAll(Arr, r);
                    term.R1Command(path, Arr );
                }
            }
            else if (CLI.getArguments().contains(">>") ){
                String path = CLI.getArguments().get(CLI.getArguments().size()-1);
                CLI.getArguments().remove(CLI.getArguments().size()-1);
                CLI.getArguments().remove(CLI.getArguments().size()-1);
                if(CLI.getArguments().size() == 0)  {
                    String [] r = term.ls(term.getDd());
                    Collections.addAll(Arr, r);
                    term.R2Command( path, Arr );
                }
                else {
                    String [] r = term.ls(CLI.getArguments().get(0));
                    Collections.addAll(Arr, r);
                    term.R2Command(path, Arr );
                }
            }
            else {
                if(CLI.getArguments().size() == 0)  {
                    String [] r = term.ls(term.getDd());
                    for( String i : r){
                        System.out.println(i);
                    }
                }
                else {
                    String [] r = term.ls(CLI.getArguments().get(0));
                    for( String i : r){
                        System.out.println(i);
                    }
                }
            }
        }
        else if( CLI.getCmd().matches("more") && CLI.getArguments().size()==1  ){
            term.more( CLI.getArguments().get(0) );
        }
        else if( CLI.getCmd().matches("cd") && CLI.getArguments().size()==1  ){
            term.setDd( term.cd(CLI.getArguments().get(0)) );
        }
        else if( CLI.getCmd().matches("rm") && CLI.getArguments().size()==1  ){
            term.rm( CLI.getArguments().get(0) );
        }
        else if( CLI.getCmd().matches("cp") && CLI.getArguments().size()==2  ){
            term.cp( CLI.getArguments().get(0) , CLI.getArguments().get(1) );
        }
        else if( CLI.getCmd().matches("mv") && CLI.getArguments().size()==2  ){
            term.mv( CLI.getArguments().get(0) , CLI.getArguments().get(1) );
        }
        else if( CLI.getCmd().matches("cat") && CLI.getArguments().size()>0  ){
            if ( CLI.getArguments().contains(">") ){
                String path = CLI.getArguments().get(CLI.getArguments().size()-1);
                CLI.getArguments().remove(CLI.getArguments().size()-1);
                CLI.getArguments().remove(CLI.getArguments().size()-1);
                term.R1Command(path, term.cat( CLI.getArguments() ) ) ;
            }
            else if (CLI.getArguments().contains(">>") ){
                String path = CLI.getArguments().get(CLI.getArguments().size()-1);
                CLI.getArguments().remove(CLI.getArguments().size()-1);
                CLI.getArguments().remove(CLI.getArguments().size()-1);
                term.R2Command(path, term.cat( CLI.getArguments() ) ) ;
            }
            else {
                for (String i : term.cat( CLI.getArguments() ) ) {
                    System.out.println(i);
                }
            }
        }
        else if( CLI.getCmd().matches("echo") && CLI.getArguments().size()==1  ){
            term.echo( CLI.getArguments().get(0) );
        }
        CLI.emptyattr();
    }
}
