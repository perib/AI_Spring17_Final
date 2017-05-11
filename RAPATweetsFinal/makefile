#
# A simple makefile for compiling three java classes
#
# define a makefile variable for the java compiler
#
JCC = javac
# define a makefile variable for compilation flags
# the -g flag compiles with debugging information
#
JFLAGS = -g
# typing 'make' will invoke the first target entry in the makefile 
# (the default one in this case)
#
default: GetTweet.class TweetParser.class TweetReader.class

# this target entry builds the Average class
# the Average.class file is dependent on the Average.java file
# and the rule associated with this entry gives the command to create it
#
TweetParser.class: TweetParser.java
	$(JCC) $(JFLAGS) -classpath twitter4j-core-4.0.4.jar TweetParser.java

GetTweet.class: GetTweet.java
	$(JCC) $(JFLAGS) -classpath twitter4j-core-4.0.4.jar GetTweet.java

TweetReader.class: TweetReader.java
	$(JCC) $(JFLAGS) -classpath twitter4j-core-4.0.4.jar -Xlint TweetReader.java


	
	

# To start over from scratch, type 'make clean'.  
# Removes all .class files, so that the next make rebuilds them
#
clean: 
	$(RM) *.class
