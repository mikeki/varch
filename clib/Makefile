algorithms.so: algorithms.o
	gcc -shared -Wl,-soname,libalgorithms -o libalgorithms.so -fPIC algorithms.o

algorithms.o:
	gcc -c -fPIC algorithms.c -o algorithms.o 

clean:
	rm *.o && rm *.so

.PHONY : clean

