// Linked list of transport card records implementation ... Assignment 1 COMP9024 18s2
#include <stdio.h>
#include <stdlib.h>
#include <assert.h>
#include "cardLL.h"
#include "cardRecord.h"

// linked list node type
// DO NOT CHANGE

typedef struct node {
    cardRecordT data;
    struct node *next;
} NodeT;

// linked list type
typedef struct ListRep {
    NodeT *head;
    NodeT *tail;
    int length;
/* Add more fields if you wish */
} ListRep;

/*** Your code for stages 2 & 3 starts here ***/

// Time complexity: O(1)
// Explanation: Initialized linked list with null pointers head and tail, default length is 0;
// It will take O(1) time.
List newLL() {
    List LL =  malloc(sizeof(ListRep));
    LL->head = NULL;
    LL->tail = NULL;
    LL->length = 0;
    return LL;  /* needs to be replaced */
}

// Time complexity: O(n)
// Explanation: Traversal the whole linked list, free memories each node located.
void dropLL(List listp) {
    NodeT *p = listp->head;
    while( p != NULL){
        NodeT *current = p->next;
        free(p);
        p = current;
    }
   return;  /* needs to be replaced */
}

// Time complexity: O(n)
// Explanation: Since the linked list is sorted, just look for the exact same cardID in ll, end the function.
// Otherwise, if a node's cardID is larger than target cardID or current pointer point to tail(null) of ll,
// the target cardID does not exist in this ll.
void removeLL(List listp, int cardID) {
    NodeT* current = listp->head;
    NodeT* previous = listp->head;
    while(current != NULL){
        if(current->data.cardID == cardID){
            if(current == listp->head){
                listp->head = current->next;
            }
            previous->next = current->next;
            NodeT* temp = current->next;
            current = temp;
            printf("Card removed.\n");
            listp->length -= 1;
            return;
        }else{
            if(current->data.cardID > cardID){
                printf("Card not found.\n");
                return;
            }
        }
        previous = current;
        current = current->next;
    }
    printf("Card not found.\n");
   return;  /* needs to be replaced */
}
NodeT* getTail(NodeT* node){
    NodeT *current = node;
    while(current != NULL&&current->next != NULL){
        current = current->next;
    }
    return current;
}
// Using the last node as pivot.
NodeT* partition(NodeT* head,NodeT* tail,NodeT** sorted_head, NodeT** sorted_end,List listp){
    NodeT* pivot = tail;
    NodeT* previous = NULL;
    NodeT* current = head;
    while(current != pivot){
        //If current node cardID is same as pivot, update pivot's data.
        // Cut the connection of the current node in linked list.
        if(current->data.cardID == pivot->data.cardID){
            if(previous!=NULL){
                previous->next = current->next;
            }
            NodeT* temp = current->next;
            pivot->data.balance += current->data.balance;
            printf("-----------------\n");
            printf("Card ID: %d\n", pivot->data.cardID);
            if (pivot->data.balance < 0){
                printf("Balance: -$%.2f\n", -1 * pivot->data.balance);
                
            }else{
                printf("Balance: $%.2f\n", pivot->data.balance);
            }
            if(pivot->data.balance < 5){
                printf("Low balance\n");
            }
            printf("-----------------\n");
            current = temp;
            listp->length -= 1;
        }
        
        else{
            // If current node cardID is smaller than pivot.
            if(current->data.cardID < pivot->data.cardID){
                //Update new head of part of the original list.
            if (*sorted_head == NULL){
                *sorted_head = current;
            }
            //current order is correct, look for next node.
            previous = current;
            current = current->next;
        }else{
            // find node not in order.
            if(previous!=NULL){
                previous->next = current->next;
            }
            NodeT* temp = current->next;
            current->next = NULL;
            // put this node after pivot. Update the tail.
            tail->next = current;
            tail = current;
            current = temp;
            }
        }
    }
    if(*sorted_head == NULL){
        *sorted_head = pivot;
    }
    *sorted_end = tail;
    return pivot;
}
NodeT* qs(NodeT* head, NodeT* tail,List listp){
    if(head == NULL || head == tail){
        return head;
    }
    NodeT* sorted_head = NULL;
    NodeT* sorted_end = NULL;
    // Using divide and conquer idea.
    NodeT* pivot = partition(head,tail,&sorted_head,&sorted_end,listp);
    //Sort left part of pivot.
    if(sorted_head != pivot){
        NodeT* temp = sorted_head;
        while(temp->next != pivot){
            temp = temp->next;
        }temp->next = NULL;
        sorted_head = qs(sorted_head,temp,listp);
        temp = getTail(sorted_head);
        temp->next = pivot;
    }
    //Sort right part of pivot.
    pivot->next = qs(pivot->next,sorted_end,listp);
    return sorted_head;
}
// Base case of recursion.
void quick_sort(List  listp){
    NodeT* tail = getTail(listp->head);
    listp->head = qs(listp->head,tail,listp);
}
// Time complexity: O(log n) for each insertion.
// Explanation: Insert new node at the head position of the linked list,
// then using quick sort to make sure the linked list in sorted as ascenting order.
void insertLL(List listp, int cardID, float amount) {
    NodeT *new = malloc(sizeof(NodeT));
    new->data.cardID = cardID;
    new->data.balance = amount;
    new->next = NULL;
    int delta = listp->length;
    listp->length += 1;
    if(listp->head == NULL){
        listp->head = new;
        listp->tail = listp->head;
        
    }else{
        NodeT *p = listp->head;
        listp->head = new;
        listp->head->next = p;
        quick_sort(listp);
    }
    if (listp->length != delta){
        printf("Card added.\n");
    }
   return;  /* needs to be replaced */
}
// Time complexity: O(n)
// Explanation: Simply traversal the linked list, sum the every balance up.
// Then do the division of total balance with number of card in linked list.
void getAverageLL(List listp, int *n, float *balance) {
    NodeT *p;
    *n = listp->length;
    if (listp->length == 0){
        return;
    }
    for (p = listp->head; p != NULL; p = p->next) {
        *balance += p->data.balance;
    }
   return;  /* needs to be replaced */
}

// Time complexity: O(n)
// Explanation: Simply traversal the linked list, print every node's data.
void showLL(List listp) {
    NodeT *p;
    //NodeT *E = getTail(listp);
    //printf("%d",E->data.cardID);
    for (p = listp->head; p != NULL; p = p->next) {
        printf("-----------------\n");
        printf("Card ID: %d\n", p->data.cardID);
        if (p->data.balance < 0){
            printf("Balance: -$%.2f\n", -1 * p->data.balance);
            
        }else{
            printf("Balance: $%.2f\n", p->data.balance);
        }
        if(p->data.balance < 5){
            printf("Low balance\n");
        }
        printf("-----------------\n");
    }
    //putchar('\n');

   return;  /* needs to be replaced */
}
