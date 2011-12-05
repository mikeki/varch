#include <stdlib.h>
#include <malloc.h>
#include <string.h>
#include <wchar.h>
#ifndef max
	#define max( a, b ) ( ((a) > (b)) ? (a) : (b) )
#endif

#ifndef min
	#define min( a, b ) ( ((a) < (b)) ? (a) : (b) )
#endif

int levenshtein_distance(wchar_t *s,wchar_t *t, int n, int m);
int minimum(int a, int b, int c);
void ld_many(wchar_t *s, wchar_t **others, float *ld_result, int size);
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

int		Ntoken = 1;
int		Zerobits = 0;
unsigned long	zeromask;
int		ntoken = 0;

/* wchar to ignore at start and end of each word */
wchar_t *		Ignore = L" \t\n,.<>/?;:'\"`~[]{}\\|!@#$%^&*()-+_=";

/* wchar to treat as word-separators or words on their own */
wchar_t *		Punct_full = L"";
wchar_t *		Punct = L"";

typedef struct Sig Sig;
struct Sig
{
	int		nval;
	unsigned long	*val;
};

int sherlock_compare(wchar_t *, wchar_t *);
void	init_token_array(wchar_t **	token);
Sig *	signature(wchar_t *, wchar_t ** token);
int	compare(Sig *, Sig *);

/* read_word: read a 'word' from the input, ignoring leading wchar_tacters
   which are inside the 'ignore' string, and stopping if one of
   the 'ignore' or 'punct' wchar is found.
   Uses memory allocation to avoid buffer overflow problems.
*/
int s_size = 0, curr_pos = 0;
int sherlock_compare(wchar_t *s, wchar_t *n)
{
	Sig *a, *b;
    int res;
	wchar_t **	token, ** token2;
    int i;
    
	/* create array of wchar_t* and initialise all to NULL */
	token = malloc(Ntoken * sizeof(wchar_t *));
    token2 = malloc(Ntoken * sizeof(wchar_t *));
	for (i=0; i < Ntoken; i++)
    {
		token[i] = NULL;
        token2[i] = NULL;
    }
	s_size = wcslen(s);
	curr_pos = 0;
    a = signature(s, token);
    s_size = wcslen(n);
	curr_pos = 0;
    b = signature(n, token2);    
	res = compare(a, b);
    free(a);
    free(b);
    return res;
}

wchar_t * read_word(wchar_t *f, int *length, wchar_t *ignore, wchar_t *punct)
{
	long max;
	wchar_t *word;
	long pos;
	wchar_t *c;
	int ch, is_ignore, is_punct;


	/* check for EOF first */
	if (*f == '\0') {
			length = 0;
			return NULL;
	}

	/* allocate a buffer to hold the string */
	pos = 0;
	max = 128;
	word = malloc(sizeof(wchar_t) * max);
	c = & word[pos];

	/* initialise some defaults */
	if (ignore == NULL)
		ignore = L"";
	if (punct == NULL)
		punct = L"";

	/* read wchar_tacters into the buffer, resizing it if necessary */
	
	while (s_size > curr_pos && (ch = *(f++)) != '\0') {
		is_ignore = (wcschr(ignore, ch) != NULL);
        curr_pos++;
		if (pos == 0) {
			if (is_ignore)
				/* ignorable wchar_t found at start, skip it */
				continue;
		}
		if (is_ignore)
			/* ignorable wchar_t found after start, stop */
			break;
		is_punct = (wcschr(punct, ch) != NULL);
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

/* hash:  hash an array of wchar_t* into an unsigned long hash code */
unsigned long hash(wchar_t *tok[])
{
	unsigned long h;
	wchar_t *s;
	int i;

	h = 0;
	for (i=0; i < Ntoken; i++)
		for (s=(wchar_t*)tok[i]; *s; s++)
			h = h*31 + *s;
	return h;
}

Sig * signature(wchar_t *f, wchar_t ** token)
{
	int nv, na;
	unsigned long *v, h;
	wchar_t *str;
	int i, ntoken;
	Sig *sig;
	
	/* start loading hash values, after we have Ntoken of them */
	v = NULL;
	na = 0;
	nv = 0;
	ntoken = 0;
	while ((str = read_word(f, &i, Ignore, Punct)) != NULL)
	{
printf("%s \n\n\n", str);
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
    //return 100 * (nsimilar + nsimilar) / (s0->nval + s1->nval);
}

void ld_many(wchar_t *s, wchar_t **others, float *ld_result, int size)
{
	int i;
	int *sizes = (int *) malloc(sizeof(int) * size);
	int mysize = wcslen(s);
	int min;
	for (i = 0; i < size; i++) {
		sizes[i] = wcslen(others[i]);
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

int ld_compare(wchar_t *s, wchar_t *t)
{
	int mini, size1 = wcslen(s), size2 = wcslen(t);
	float res;
	mini = max(size1, size2);
	res = 1 - (
			((float)levenshtein_distance(s, t, size1, size2))
			/ (float) mini);
	res *= 100;
	return (int) res;
}


int levenshtein_distance(wchar_t *s,wchar_t *t, int n, int m)
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
