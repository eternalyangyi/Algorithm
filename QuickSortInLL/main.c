/**
     main.c

     Program supplied as a starting point for
     Assignment 1: Transport card manager

     COMP9024 18s2
**/
#include <stdio.h>
#include <stdlib.h>
#include <assert.h>
#include <ctype.h>

#include "cardRecord.h"
#include "cardLL.h"

void printHelp();
void CardLinkedListProcessing();

int main(int argc, char *argv[]) {
   if (argc == 2) {
       
      /*** Insert your code for stage 1 here ***/
       int arr_size = atoi( argv[1]);
       int arr_used = 0;
       cardRecordT *arr = malloc(sizeof(cardRecordT)*arr_size);
       for(int i = 0; i < arr_size; i++){
           int id = readValidID();
           float amount = readValidAmount();
           if(arr_used == arr_size){
               arr_size *= 2;
               arr = realloc(arr, sizeof(cardRecordT)*2);
           }
           arr[arr_used].cardID = id;
           arr[arr_used].balance = amount;
           arr_used++;
       }
       float tot_balance=0.0;
       for(int i = 0; i < arr_used; i++){
           printCardData(arr[i]);
           tot_balance += arr[i].balance;
       }
       printf("Number of cards on file: %d\n",arr_used);
       printf("Average balance: $%.2f\n",tot_balance/arr_used);
       free(arr);
   } else {
      CardLinkedListProcessing();
       
   }
   return 0;
}

/* Code for Stages 2 and 3 starts here */

void CardLinkedListProcessing() {
   int op, ch;

   List list = newLL();   // create a new linked list
    //printf("%d",list->head->data.cardID);
   while (1) {
      printf("Enter command (a,g,p,q,r, h for Help)> ");
      do {
	 ch = getchar();
      } while (!isalpha(ch) && ch != '\n');  // isalpha() defined in ctype.h
      op = ch;
      // skip the rest of the line until newline is encountered
      while (ch != '\n') {
	 ch = getchar();
      }

      switch (op) {

         case 'a':
         case 'A':
          {
            /*** Insert your code for adding a card record ***/
              int id = readValidID();
              float amount = readValidAmount();
              insertLL(list, id, amount);
              
          }
	    break;

         case 'g':
         case 'G':
          {
            /*** Insert your code for getting average balance ***/
              int number = 0;
              float balance = 0.0;
              getAverageLL(list,&number,&balance);
              printf("Number of cards on file: %d\n",number);
              if(number ==0){
                  printf("Average balance: $0\n");
                  break;
              }
              float average = balance / (number*1.0);
              if(average < 0.0){
                 printf("Average balance: -$%.2f\n", average);
              }else{
                printf("Average balance: $%.2f\n", average);
              }
          }
              break;
	    
         case 'h':
         case 'H':
            printHelp();
	    break;
	    
         case 'p':
         case 'P':
            /*** Insert your code for printing all card records ***/
              showLL(list);
	    break;

         case 'r':
         case 'R':
            /*** Insert your code for removing a card record ***/
          {
              int id = readValidID();
              removeLL(list,id);
          }
	    break;

	 case 'q':
         case 'Q':
            dropLL(list);       // destroy linked list before returning
              free(list);
	    printf("Bye.\n");
	    return;
      }
   }
}

void printHelp() {
   printf("\n");
   printf(" a - Add card record\n" );
   printf(" g - Get average balance\n" );
   printf(" h - Help\n");
   printf(" p - Print all records\n" );
   printf(" r - Remove card\n");
   printf(" q - Quit\n");
   printf("\n");
}
