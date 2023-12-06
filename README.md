# Advent of code

These are my kotlin solutions for advent of code (currently only year 2023).  
To run the program, you have to either
1) some input files and place them in `src/main/resources/y2023/`,
naming them `input$1.txt`
2) find your advent of code session cookie and set the environment variable `ADVENT_OF_CODE_COOKIES="session=<value>"`

Run the program with gradle wrapper. By default, the current days solution will be run (or 25th if it's after christmas)
The following arguments can be passed to the program in any order:
- any number between 1 and 25 will run that day's solution
- a number range in the format `<start>..<end>` will run all solutions in that range (inclusive)
- `all`will run all 25 days. if all is passed, days or ranges are not applied
- `year=<year>` will set the year to the specified year, if in future this repo has multiple years. default 2023
- `warm` will warm up the solutions so their performance is better. usefull if you want to see more accurate runtime measurements

### Examples
```
# run today's solution (or the 25th day)
./gradew run
# run all solutions, warmed up
./gradlew run --args="all warm"
# run some solutions, warmed up
./gradlew run --args="1 3 5 warm"
# run days 1-3 and 5-6
./gradlew run --args="1..3 5..6"
# args can be passed in any order
./gradlew run --args="warm 2 1 year=2023 5..6
```