// Transport card record implementation ... Assignment 1 COMP9024 18s2
#include <stdio.h>
#include <math.h>
#include <stdlib.h>
#include "cardRecord.h"

#define LINE_LENGTH 1024
#define NO_NUMBER -999

// scan input line for a positive integer, ignores the rest, returns NO_NUMBER if none
int readInt(void) {
   char line[LINE_LENGTH];
   int  n;

   fgets(line, LINE_LENGTH, stdin);
   if ( (sscanf(line, "%d", &n) != 1) || n <= 0 )
      return NO_NUMBER;
   else
      return n;
}

// scan input for a floating point number, ignores the rest, returns NO_NUMBER if none
float readFloat(void) {
   char  line[LINE_LENGTH];
   float f;

   fgets(line, LINE_LENGTH, stdin);
   if (sscanf(line, "%f", &f) != 1)
      return NO_NUMBER;
   else
      return f;
}

int readValidID(void) {
    int line;
    printf("Enter card ID: ");
    while(1){
        line = readInt();
        if (line == -999){
            printf("Not valid. Enter a valid value: ");
        }else{
            int num_of_digits = floor(log10(abs(line))) + 1;
            if(num_of_digits != 8){
                printf("Not valid. Enter a valid value: ");
            }else{
                break;
            }
        }
    }
   return line;  /* needs to be replaced */
}

float readValidAmount(void) {
    float line;
    printf("Enter amount: ");
    while(1){
        line = readFloat();
        if (line == -999){
            printf("Not valid. Enter a valid value: ");
        }else{
            if(line <-2.3 || line > 250.0){
                printf("Not valid. Enter a valid value: ");
            }else{
                break;
            }
        }
    }
    return line; /* needs to be replaced */
}

void printCardData(cardRecordT card) {
    float zero = 0.0;
    float low = 5.0;
    printf("-----------------\n");
    printf("Card ID: %d\n", card.cardID);
    if (card.balance < zero){
        printf("Balance: -$%.2f\n", -1 * card.balance);
    }else{
        printf("Balance: $%.2f\n", card.balance);
    }
    if(card.balance < low){
        printf("Low balance\n");
    }
    printf("-----------------\n");
    
   return;  /* needs to be replaced */
}
