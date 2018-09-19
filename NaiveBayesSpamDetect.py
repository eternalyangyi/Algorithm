## import modules here 

################# Question 1 #################

def multinomial_nb(training_data, sms):# do not change the heading of the function
    num_of_spam = 0
    num_of_ham = 0
    num_of_Dword = 0
    total_num_of_category = 0
    number_of_word = 0
    number_of_word_in_ham = 0
    number_of_word_in_spam = 0
    spam = {}
    ham = {}
    L ={}
    #modified_data = [['spam'],['ham']]
    for data in training_data:
        if data[1] == 'spam':
            num_of_spam += 1
            for word in data[0]:
                if word in spam:
                    spam[word] += data[0][word]
                else:
                    spam[word] = data[0][word]
            #print(number_of_word_in_spam)
        if data[1] == 'ham':
            num_of_ham += 1
            for word in data[0]:
                if word in ham:
                    ham[word] += data[0][word]
                else:
                    ham[word] = data[0][word]
            #print(num_of_word_in_ham)
        total_num_of_category += 1
        for word in data[0]:
            if word not in L:
                L[word] = data[0][word]
            else:
                L[word] += data[0][word]
    num_of_Dword = len(L)
    p_spam = num_of_spam/total_num_of_category
    p_ham = num_of_ham/total_num_of_category
    for word in ham:
        number_of_word_in_ham += ham[word]
    #print(ham)
    #print(spam)
    for word in spam:
        number_of_word_in_spam += spam[word]
    sms_in_spam_and_ham = []
    for word in sms:
        if word in spam or word in ham:
            sms_in_spam_and_ham.append(word)
    for word in sms_in_spam_and_ham:
        number_of_word = 0
        if word in ham:
            #print(word)
            number_of_word += ham[word]
        #print(number_of_word)
        p_ham *= (number_of_word + 1)/(num_of_Dword  + number_of_word_in_ham)
        #print(p_ham)
    for word in sms_in_spam_and_ham:
        number_of_word = 0
        if word in spam:
            #print(word)
            number_of_word += spam[word]
        #print(number_of_word)
        p_spam *= (number_of_word + 1)/(num_of_Dword  + number_of_word_in_spam)
    return (p_spam/p_ham)
                
    
