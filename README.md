# makeGenerator
a small cli program maked in java to quicky generate a makefile based on a file structure

## Setup
  You might need to change path.lib to fit where junit
  is installed in order to run tests if it is in
  "/usr/share/java/" (default install path on Linux) you should be fine

## Build
`ant`
## Build Jar
`ant jar`

## IN FILE Commands
To select names and flags for specific files add a comment in any file or header with this format
`
/*make
  flags: put flags here
  name: outputFile.o
 */
`

## Commands
| Command | Option          | Description                       | Default Value |
|---------|-----------------|-----------------------------------|---------------|
| -D      | NA              | Turns on Debugging mode           | NA            |
| -V      | NA              | Turns on Verbose Mode             | NA            |
| -S      | NA              | Creates a shared lib              | NA            |
| -o      | File Name       | Name of the created makefile      | makefile      |
| -p      | Path            | tRoot of the projects path        | ./            |
| -f      | "flags"         | Extra flags for the compilations  | ""            |
| -e      | file Name       | Name of the executable            | a.out         |
| -c      | Compiler        | Compiler name                     | gcc           |
| -s      | Source Files    | The folder src files are in       | src/          |
| -b      | Bin files       | Where exe and .o files are stored | bin/          |
| -i      | Includes folder | Where header are stored           | includes/     |
