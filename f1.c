#include <stdio.h>


void f1B(void);

void f1A(void)
{
	printf("Calling from f1A\n");
	f1B();
}

void f1B(void)
{
	printf("Calling from f1B\n");
}

