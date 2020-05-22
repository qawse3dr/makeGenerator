CC = gcc
CFLAGS = 
srcDIR = src/
includeDIR = include/
binDIR = bin/

objects = $(binDIR)/srctest2.o $(binDIR)/srctest.o 
all:$(binDIR)a.out

$(binDIR)a.out:$(objects)
	$(CC) $(CFLAGS) -I$(includeDIR)  $(objects) -o $@
$(binDIR)test2.c: /srctest2.o 
	$(CC) $(CFLAGS)  -I$(includeDIR) test2.c -o $@

$(binDIR)test.c: /srctest.o 
	$(CC) $(CFLAGS)  -I$(includeDIR) test.c -o $@


clean:
	rm -rf $(binDir)*

run: all
	./$(binDIR)a.out

memtest: all
	valgrind ./$(binDIR)a.out
