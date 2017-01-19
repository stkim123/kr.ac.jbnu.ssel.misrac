struct tnode * pt;        /* tnode is incomplete at this point */

struct tnode
{
   int count;
   struct tnode *left;
   struct tnode * right;
};                        /* type tnode is now complete */
