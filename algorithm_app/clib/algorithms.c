#include <stdlib.h>
#include <malloc.h>
#include <string.h>
#ifndef max
	#define max( a, b ) ( ((a) > (b)) ? (a) : (b) )
#endif

#ifndef min
	#define min( a, b ) ( ((a) < (b)) ? (a) : (b) )
#endif

int levenshtein_distance(char *s,char *t, int n, int m);
int minimum(int a, int b, int c);
void ld_many(char *s, char **others, float *ld_result, int size);
/**
 * Modification of sherlock program to be used as a .so
 * Modification by Manuel Aude Morales, rest of credits are listen below.
 * 
 */

/*
 *  sherlock.c - written by Loki from Rob Pike's sig and comp programs.
 *
 *  This program takes filenames given on the command line,
 *  and reads those files into memory, then compares them
 *  all pairwise to find those which are most similar.
 *
 *  It uses a digital signature generation scheme to randomly
 *  discard information, thus allowing a better match.
 *  Essentially it hashes up N adjacent 'words' of input,
 *  and semi-randomly throws away many of the hashed values
 *  so that it become hard to hide the plagiarised text.
 */

#include <stdlib.h>
#include <string.h>
#include <stdio.h>

int		Ntoken = 3;
int		Zerobits = 4;
unsigned long	zeromask;
int		ntoken = 0;
char **		token;

/* characters to ignore at start and end of each word */
char *		Ignore = " \t\n";

/* characters to treat as word-separators or words on their own */
char *		Punct_full = ",.<>/?;:'\"`~[]{}\\|!@#$%^&*()-+_=";
char *		Punct = "";

typedef struct Sig Sig;
struct Sig
{
	int		nval;
	unsigned long	*val;
};

int sherlock_compare(char *, char *);
void	init_token_array(void);
Sig *	signature(char *);
int	compare(Sig *, Sig *);

/* read_word: read a 'word' from the input, ignoring leading characters
   which are inside the 'ignore' string, and stopping if one of
   the 'ignore' or 'punct' characters is found.
   Uses memory allocation to avoid buffer overflow problems.
*/
int s_size = 0, curr_pos = 0;
int sherlock_compare(char *s, char *n)
{
	Sig *a, *b;
	
	init_token_array();
    
	s_size = strlen(s);
	curr_pos = 0;
    a = signature(s);
    
    s_size = strlen(n);
	curr_pos = 0;
    b = signature(n);
	return compare(a, b);
}

char * read_word(char *f, int *length, char *ignore, char *punct)
{
	long max;
	char *word;
	long pos;
	char *c;
	int ch, is_ignore, is_punct;


	/* check for EOF first */
	if (*f == '\0') {
			length = 0;
			return NULL;
	}

	/* allocate a buffer to hold the string */
	pos = 0;
	max = 128;
	word = malloc(sizeof(char) * max);
	c = & word[pos];

	/* initialise some defaults */
	if (ignore == NULL)
		ignore = "";
	if (punct == NULL)
		punct = "";

	/* read characters into the buffer, resizing it if necessary */
	
	while (s_size > curr_pos && (ch = *(f++)) != '\0') {
		is_ignore = (strchr(ignore, ch) != NULL);
        curr_pos++;
		if (pos == 0) {
			if (is_ignore)
				/* ignorable char found at start, skip it */
				continue;
		}
		if (is_ignore)
			/* ignorable char found after start, stop */
			break;
		is_punct = (strchr(punct, ch) != NULL);
		if (is_punct && (pos > 0)) {
			f--;
			break;
		}
		*c = ch;
		c++;
		pos++;
		if (is_punct)
			break;
		if (pos == max) {
				/* realloc buffer twice the size */
				max += max;
				word = realloc(word, max);
				c = & word[pos];
		}
	}
	
	/* set length and check for EOF condition */
	*length = pos;
	if (pos == 0) {
		free(word);
		return NULL;
	}

	/* terminate the string and shrink to smallest required space */
	*c = '\0';
	word = realloc(word, pos+1);
	return word;
}

/* ulcmp:  compare *p1 and *p2 */
int ulcmp(const void *p1, const void *p2)
{
	unsigned long v1, v2;

	v1 = *(unsigned long *) p1;
	v2 = *(unsigned long *) p2;
	if (v1 < v2)
		return -1;
	else if (v1 == v2)
		return 0;
	else
		return 1;
}

/* hash:  hash an array of char* into an unsigned long hash code */
unsigned long hash(char *tok[])
{
	unsigned long h;
	unsigned char *s;
	int i;

	h = 0;
	for (i=0; i < Ntoken; i++)
		for (s=(unsigned char*)tok[i]; *s; s++)
			h = h*31 + *s;
	return h;
}

void init_token_array(void)
{
	int i;

	/* create global array of char* and initialise all to NULL */
	token = malloc(Ntoken * sizeof(char*));
	for (i=0; i < Ntoken; i++)
		token[i] = NULL;
}

Sig * signature(char *f)
{
	int nv, na;
	unsigned long *v, h;
	char *str;
	int i, ntoken;
	Sig *sig;
	
	/* start loading hash values, after we have Ntoken of them */
	v = NULL;
	na = 0;
	nv = 0;
	ntoken = 0;
	while ((str = read_word(f, &i, Ignore, Punct)) != NULL)
	{
		/* step words down by one */
		free(token[0]);
		for (i=0; i < Ntoken-1; i++)
			token[i] = token[i+1];
		/* add new word into array */
		token[Ntoken-1] = str;

		/* if we don't yet have enough words in the array continue */
		ntoken++;
		if (ntoken < Ntoken)
			continue;

		/* hash the array of words */
		h = hash(token);
		if ((h & zeromask) != 0)
			continue;

		/* discard zeros from end of hash value */
		h = h >> Zerobits;

		/* add value into the signature array, resizing if needed */
		if (nv == na) {
			na += 100;
			v = realloc(v, na*sizeof(unsigned long));
		}
		v[nv++] = h;
	}
	/* sort the array of hash values for speed */
	qsort(v, nv, sizeof(v[0]), ulcmp);

	/* allocate and return the Sig structure for this file */
	sig = malloc(sizeof(Sig));
	sig->nval = nv;
	sig->val = v;
	return sig;
}

int compare(Sig *s0, Sig *s1)
{
	int i0, i1, nboth, nsimilar;
	unsigned long v;

	i0 = 0;
	i1 = 0;
	nboth = 0;
	while (i0 < s0->nval && i1 < s1->nval) {
		if (s0->val[i0] == s1->val[i1]) {
			v = s0->val[i0];
			while (i0 < s0->nval && v == s0->val[i0]) {
				i0++;
				nboth++;
			}
			while (i1 < s1->nval && v == s1->val[i1]) {
				i1++;
				nboth++;
			}
			continue;
		}
		if (s0->val[i0] < s1->val[i1])
			i0++;
		else
			i1++;
	}

	if (s0->nval + s1->nval == 0)
		return 0;	/* ignore if both are empty files */

	if (s0->nval + s1->nval == nboth)
		return 100;	/* perfect match if all hash codes match */

	nsimilar = nboth / 2;
	return 100 * nsimilar / (s0->nval + s1->nval - nsimilar);
}

void ld_many(char *s, char **others, float *ld_result, int size)
{
	int i;
	int *sizes = (int *) malloc(sizeof(int) * size);
	int mysize = strlen(s);
	int min;
	for (i = 0; i < size; i++) {
		sizes[i] = strlen(others[i]);
	}
	
	for (i = 0; i < size; i++) 
	{
		min = min(mysize, sizes[i]);
		ld_result[i] = 1 - (
			((float)levenshtein_distance(s, others[i], mysize, sizes[i]))
			/ (float) min);
		ld_result[i] *= 100;
			
	}
	
	free(sizes);
}

int ld_compare(char *s, char *t)
{
	int mini, size1 = strlen(s), size2 = strlen(t);
	float res;
	mini = max(size1, size2);
	res = 1 - (
			((float)levenshtein_distance(s, t, size1, size2))
			/ (float) mini);
	res *= 100;
	return (int) res;
}


int levenshtein_distance(char *s,char *t, int n, int m)
{
  int k, i, j, cost, *d, distance;
  if(n!=0&&m!=0)
  {
    d=malloc((sizeof(int))*(m+1)*(n+1));
    m++;
    n++;
    //Step 2	
    for(k=0;k<n;k++)
	d[k]=k;
    for(k=0;k<m;k++)
      d[k*n]=k;
    //Step 3 and 4	
    for(i=1;i<n;i++)
      for(j=1;j<m;j++)
	{
        //Step 5
        if(s[i-1]==t[j-1])
          cost=0;
        else
          cost=1;
        //Step 6			 
        d[j*n+i]=minimum(d[(j-1)*n+i]+1,d[j*n+i-1]+1,d[(j-1)*n+i-1]+cost);
      }
    distance=d[n*m-1];
    free(d);
    return distance;
  }
  else 
    return -1; //a negative return value means that one or both strings are empty.
}

int minimum(int a,int b,int c)
/*Gets the minimum of three values*/
{
  int min=a;
  if(b<min)
    min=b;
  if(c<min)
    min=c;
  return min;
}
