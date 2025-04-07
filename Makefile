JFLAGS = -g
JC = javac
JVM= java
FILE=

.SUFFIXES: .java .class

.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
        ParserVal.java \
        ASDRSintatico.java \
        Yylex.java 

MAIN = ASDRSintatico

default: classes

classes: $(CLASSES:.java=.class)

clean:
	rm -f *~ *.class Yylex.java

all: Yylex.java $(CLASSES:.java=.class)

Yylex.java: asdr_lex.flex
	$(JVM) -jar jflex.jar asdr_lex.flex

run: $(MAIN).class
	$(JVM) $(MAIN)

debug: $(MAIN).class
	$(JVM) -Xdiagnose $(MAIN)

