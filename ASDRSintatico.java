// Natália Dal Pizzol e Ana Carolina Xavier 

import java.io.*;

public class ASDRSintatico {
    public static final int EOF = 0;
    public static final int SELECT = 1;
    public static final int FROM = 2;
    public static final int WHERE = 3;
    public static final int VIRGULA = 4;
    public static final int PONTO_VIRGULA = 5;
    public static final int ASTERISCO = 6;
    public static final int E_COMERCIAL = 7;
    public static final int IDENTIFICADOR = 8;
    public static final int STRING = 9;

    private int tokenAtual;
    private Yylex lexico;
    private boolean debug = false;

    public ASDRSintatico(java.io.Reader input) {
        lexico = new Yylex(input, this);
    }

    public void setDebug(boolean value) {
        this.debug = value;
        if (debug) {
            System.out.println("Debug mode enabled");
        }
    }

    private void verifica(int esperado) throws Exception {
        if (tokenAtual == esperado) {
            if (debug) {
                System.out.println("Token reconhecido: " + tokenToString(tokenAtual));
            }
            tokenAtual = yylex();
        } else {
            msgErro("Token inesperado: esperava " + tokenToString(esperado) + 
                   " mas encontrou " + tokenToString(tokenAtual));
        }
    }

    private void msgErro(String msg) throws Exception {
        throw new Exception(msg);
    }

    private int yylex() throws Exception {
        return lexico.yylex();
    }

    public void parse() throws Exception {
        tokenAtual = yylex();
        query();
        if (tokenAtual != EOF) {
            msgErro("Esperava fim de arquivo");
        }
    }

    private void query() throws Exception {
        if (tokenAtual == SELECT) {
            verifica(SELECT);
            selectList();
            verifica(FROM);
            tableList();
            if (tokenAtual == WHERE) {
                verifica(WHERE);
                conditions();
            }
            verifica(PONTO_VIRGULA);
        } else {
            msgErro("Esperava SELECT");
        }
    }

    private void selectList() throws Exception {
        if (tokenAtual == ASTERISCO) {
            verifica(ASTERISCO);
        } else if (tokenAtual == IDENTIFICADOR) {
            verifica(IDENTIFICADOR);
            while (tokenAtual == VIRGULA) {
                verifica(VIRGULA);
                verifica(IDENTIFICADOR);
            }
        } else {
            msgErro("Esperava * ou identificador");
        }
    }

    private void tableList() throws Exception {
        verifica(IDENTIFICADOR);
        while (tokenAtual == VIRGULA) {
            verifica(VIRGULA);
            verifica(IDENTIFICADOR);
        }
    }

    private void conditions() throws Exception {
        condition();
        while (tokenAtual == E_COMERCIAL) {
            verifica(E_COMERCIAL);
            condition();
        }
    }

    private void condition() throws Exception {
        verifica(IDENTIFICADOR);
        if (tokenAtual == STRING) {
            verifica(STRING);
        } else {
            msgErro("Esperava string");
        }
    }

    private String tokenToString(int token) {
        switch (token) {
            case EOF: return "EOF";
            case SELECT: return "SELECT";
            case FROM: return "FROM";
            case WHERE: return "WHERE";
            case VIRGULA: return ",";
            case PONTO_VIRGULA: return ";";
            case ASTERISCO: return "*";
            case E_COMERCIAL: return "&&";
            case IDENTIFICADOR: return "IDENTIFICADOR";
            case STRING: return "STRING";
            default: return "UNKNOWN";
        }
    }

    public static void main(String[] args) {
        try {
            ASDRSintatico parser = new ASDRSintatico(new java.io.InputStreamReader(System.in));
            System.out.println("Iniciando análise sintática...");
            parser.parse();
            System.out.println("Análise sintática concluída com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro durante a análise sintática: " + e.getMessage());
            System.exit(1);
        }
    }
}


