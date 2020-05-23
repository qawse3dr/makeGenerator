CC = gcc
CFLAGS = 
srcDIR = src/
includeDIR = includes/
binDIR = bin/

objects = 
all:$(binDIR)a.out

$(binDIR)a.out:$(objects)
	$(CC) $(CFLAGS) -I$(includeDIR)  $(objects) -o $@

clean:
	rm -rf $(binDir)*

run: all
	./$(binDIR)a.out

memtest: all
	valgrind ./$(binDIR)a.out
