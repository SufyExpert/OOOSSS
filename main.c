#include <stdio.h>
#include "myheader.h"

void f1A(void);
void f2A(void);

int main(void)
{
	union alpha a1;
	struct beta b1;
	struct theta t1;
	printf ("Size of char = %d, short = %d, int = %d, long = %d\n", 
		(int)sizeof(char), (int)sizeof(short), (int)sizeof(int), (int)sizeof(long)); 
	printf ("Size of beta is %d\n", (int)sizeof(struct beta));
	printf ("Size of theta is %d\n", (int)sizeof(struct theta));
	printf ("Location of b1 is %p \n", &b1);
	printf ("Location of :\na is %p \nb is %p \nc is %p \nd is %p\n", 
			&b1.a, &b1.b, &b1.c, &b1.d );
	b1.a = 'A';
	b1.b = 1234;
	*(&(b1.a) + 1) = 'B';
	*(&(b1.a) + 2) = 'C';
	*(&(b1.a) + 3) = 'D';
	*(&(b1.a) + 4) = 'E';
	*(&(b1.a) + 5) = '\0';
	printf ("Size of alpha is %d\n", (int)sizeof(union alpha));
        printf ("b1.a is %s\n", &b1.a );
	printf ("b1.b is %ld\n", b1.b );
	a1.a = 'A';
	printf ("Value of a is %c \n", a1.a );
	printf ("Value of b is %ld \n", a1.b );
	a1.b = 66;
	printf ("Value of a is %c \n", a1.a );
	printf ("Value of b is %ld \n", a1.b );

        f1A();
	f2A();	
	
	return 0;
}

