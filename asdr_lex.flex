%%

%class Yylex
%integer
%line
%char
%unicode
%type int
%eofval{
  return ASDRSintatico.EOF;
%eofval}

%{
  private ASDRSintatico yyparser;

  public Yylex(java.io.Reader r, ASDRSintatico yyparser) {
    this(r);
    this.yyparser = yyparser;
  }
%}

%%

[ \t\r\n\f]    { }

\$TRACE_ON     { yyparser.setDebug(true); }
\$TRACE_OFF    { yyparser.setDebug(false); }

SELECT         { return ASDRSintatico.SELECT; }
FROM           { return ASDRSintatico.FROM; }
WHERE          { return ASDRSintatico.WHERE; }

","            { return ASDRSintatico.VIRGULA; }
";"            { return ASDRSintatico.PONTO_VIRGULA; }
"*"            { return ASDRSintatico.ASTERISCO; }
"&&"           { return ASDRSintatico.E_COMERCIAL; }

[a-zA-Z][a-zA-Z0-9]*  { return ASDRSintatico.IDENTIFICADOR; }
'[^']*'               { return ASDRSintatico.STRING; }

.              { System.out.println("Erro lexico: caracter invalido: <" + yytext() + ">"); return -1; }
