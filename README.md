##  Tarefa 1 - ASDR para a linguagem C--

# Escrever um ASRD para a linguagem C--, abaixo. 

  Prog -->  ListaDecl

  ListaDecl -->  DeclVar  ListaDecl
              |  DeclFun  ListaDecl
              |  /* vazio */

  DeclVar --> Tipo ListaIdent ';' DeclVar
            | /* vazio */

  Tipo --> int | double | boolean

  ListaIdent --> IDENT , ListaIdent  
               | IDENT      

  DeclFun --> FUNC tipoOuVoid IDENT '(' FormalPar ')' '{' DeclVar ListaCmd '}' DeclFun
            | /* vazio */

  TipoOuVoid --> Tipo | VOID

  FormalPar -> paramList | /* vazio */

  paramList --> Tipo IDENT , ParamList
              | Tipo IDENT 

  Bloco --> { ListaCmd }

  ListaCmd --> Cmd ListaCmd
    |    /* vazio */

  Cmd --> Bloco
      | while ( E ) Cmd
      | IDENT = E ;
      | if ( E ) Cmd RestoIf

  RestoIf -> else Cmd
        |    /* vazio */
  E --> E + T
      | E - T
      | T

  T --> T * F
      | T / F
      | F    
      
  F -->  IDENT
      | NUM
      | ( E )
