# closestPair
finds closest pair of points among a list of points in a multidimensional space

# Run Program:

build java classes (Point.java and ClosestPoint.java) and run

you can either use an ide to build and run or from terminal.

If you want to use terminal; Download or clone the source code and navigate there, then:

javac -d "your classpath" ./src/com/company/Point.java

javac -d "your classpath" -classpath <your classpath> ./src/com/company/ClosestPoint.java

java -classpath "your classpath" com.company.ClosestPoint


if you use ide then simply compile and run and use ide terminal

After you run the program you should give full path of input file


Please Provide File name:

.../closestPoint/sample_inputs/generated_input_2_10000.tsv

reading input file...

Closest pair: 

2778: 43927.745782516315 89066.563307998

3662: 43940.142436046764 89065.10048436912

min difference:  

12.482662845852934

As seen above the lines of points are specified

# Input files
Your input file must contain one point per line and in each line you must specify your coordinates and separate them with space /tabular
You can use the input files in sample_input folder or create your own input files using InputFileGenerator.java. But you need to specify the output directory in line 62 before running

# Algorithm and Complexity

In order to implement this I used various references describing 
