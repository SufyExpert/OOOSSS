test: main.o libmystaticlib.a libmysharedlib.so
	gcc -o test main.o -L. -lmystaticlib -Wl,-rpath=/home/nadeem/SP25/code/temp -lmysharedlib

main.o: main.c myheader.h
	gcc -c main.c 

f1.o: f1.c
	gcc -c f1.c

f2.o: f2.c
	gcc -c -fpic f2.c

libmysharedlib.so: f2.o
	gcc -shared -o libmysharedlib.so f2.o

libmystaticlib.a: f1.o
	ar cr libmystaticlib.a f1.o

clean:
	rm *.o libmystaticlib.a libmysharedlib.so test


