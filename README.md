# ClosestPair
finds closest pair of points among a list of points in a multidimensional space

## Run Program:

build java classes (Point.java and ClosestPoint.java) and run

you can either use an ide to build and run or from terminal.

If you want to use terminal; Download or clone the source code and navigate there, then:

``` 
javac -d "your classpath" ./src/com/company/Point.java

javac -d "your classpath" -classpath <your classpath> ./src/com/company/ClosestPoint.java

java -classpath "your classpath" com.company.ClosestPoint
```

if you use ide then simply compile and run and use ide terminal

After you run the program you should give full path of input file

```
Please Provide File name:

.../closestPoint/sample_inputs/generated_input_2_10000.tsv

reading input file...

Closest pair:

2778: 43927.745782516315 89066.563307998

3662: 43940.142436046764 89065.10048436912

min difference:

12.482662845852934
```

As seen above the lines of points are specified

## Input files
Your input file must contain one point per line and in each line you must specify your coordinates and separate them with space /tabular
You can use the input files in sample_input folder or create your own input files using InputFileGenerator.java. But you need to specify the output directory in line 62 and 107 before running

## Algorithm and Complexity

In order to implement finding closest pair solution I used various references describing the algorithm. 

https://www.cs.ucsb.edu/~suri/cs235/ClosestPair.pdf

http://citeseerx.ist.psu.edu/viewdoc/download?doi=10.1.1.366.9611&rep=rep1&type=pdf (Theorem 3)

In section "d-dimensional closest pair" it is described that we apply a recursive divide and conquer algorithm. The soltion is similar to 2D case. We first sort points according to first dimension and start dividing the points into sub spaces by using median m. When number of points is <=3 we use brute force to calculate the minimum distance d. We do the same for left and right handside of the median and set the minimum distance dmin. And as decribed in the reference we use two hyperplanes placed on m + dmin and m - dmin according to first dimension. Then we get the points between those planes and apply the same procedure but this time for second dimension. We apply this for all dimensions and all medians recursively. In order to achive this recursive algorithm I implemented methods "divideAndConquer" and "evaluateNarrowBandForMultiDim" and execute them recursively.

Based on the references the complexity is 

O(n(log n)^(d−1))

## Better Solution / What can be improved
The first thing to improve could be that; in the current implementation when the points in narrow band are filtered (points between planes placed next to medians) I sort them for every step. 
Maybe the points are presorted before starting the algorithm for every dimension and hold separately. Actually I tried this in java class ClosestPointPreSorted.java but this lead worse results in terms of computation time because I was not able to filter while using presorted point lists based on each dimension. Somehow using both filtering and presorted lists in the same time it would improve the speed of the algorithm.

But the main improvement would be understanding and implementing the part "Improving the Algorithm" in first reference or the algorithms defined at theorem 6. 

## Limitations
As described in below reference:
http://citeseerx.ist.psu.edu/viewdoc/download?doi=10.1.1.366.9611&rep=rep1&type=pdf
as the dimensions increases it has also exponential effect on the time complexity. Based on some tests I put a threshold value in the algorithm for number of dimensions. If the number of dimensions is greater than 10 I use pure brute force algorithm, otherwise I use recursive algorithm.
