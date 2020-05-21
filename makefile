CC = gcc
CFLAGS = -Wall -g --std=c99
objects = 
srcDIR = src/
includeDIR = include/
binDIR = bin/

$(binDIR)main:$(objects)
	$(CC) $(CFLAGS) -I$(includeDIR)  $(objects) -o $@